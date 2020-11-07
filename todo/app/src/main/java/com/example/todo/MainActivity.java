package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Task> activeTasks;
    private List<String> listData;

    // Interface
    private ListView listView;
    private Button addbutton;
    private Button shopping_button;
    private Button scheduled_button;
    private Button location_button;
    private Button undoButton;
    private Button redoButton;
    private Invoker invoker;

    DatabaseHelper databaseHelper;

    //for flings, can be tuned
    private float flingMin = 100;
    private float velocityMin = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        addbutton = findViewById(R.id.add_button);
        scheduled_button = findViewById(R.id.scheduled_button);
        shopping_button = findViewById(R.id.shopping_button);
        location_button = findViewById(R.id.location_button);

        undoButton = findViewById(R.id.undo_button);
        undoButton.setEnabled(false);
        redoButton = findViewById(R.id.redo_button);
        redoButton.setEnabled(false);

        registerForContextMenu(addbutton);

        populateListView();
        setUpListViewListener();

        //create list adapter and set the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        //invoker for command pattern
        invoker = new Invoker(new DatabaseHelper(this));

        //button listeners
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_edit_basic_task = new Intent(MainActivity.this, EditBasicTaskActivity.class);
                MainActivity.this.startActivity(to_edit_basic_task);
            }
        });

        scheduled_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_edit_scheduled_task = new Intent(MainActivity.this, EditScheduledTaskActivity.class);
                MainActivity.this.startActivity(to_edit_scheduled_task);
            }
        });

        shopping_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_edit_shopping_task = new Intent(MainActivity.this, EditShoppingTaskActivity.class);
                MainActivity.this.startActivity(to_edit_shopping_task);
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                invoker.clickUndo();
            }
        });
        redoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                invoker.clickRedo();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.choose_task_menu, menu);
    }

    //three bottom menu buttons
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.basic_task_option:
                Intent to_edit_basic_task = new Intent(MainActivity.this, EditBasicTaskActivity.class);
                MainActivity.this.startActivity(to_edit_basic_task);
                return true;
            case R.id.shopping_list_option:
                Intent to_edit_shopping_task = new Intent(MainActivity.this, EditShoppingTaskActivity.class);
                MainActivity.this.startActivity(to_edit_shopping_task);
                return true;
            case R.id.scheduled_task_option:
                Intent to_edit_scheduled_task = new Intent(MainActivity.this, EditScheduledTaskActivity.class);
                MainActivity.this.startActivity(to_edit_scheduled_task);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void populateListView(){
        Log.d(TAG, "populateListView : Displaying data from DB in ListView");

        // get the Data and append to a list
        activeTasks = databaseHelper.getAllActiveTasks();
        listData = new ArrayList<>();

        for (Task t : activeTasks){
            // TODO make sure task object is accessible ( same index as in array list, task has to be created in databasehelper)
            //get the value from col 3 which is the name and add to arraylist
            listData.add(t.getTitle());
        }
    }

    /**
     * Set up List view Listeners
     *
     * TODO [HARD] click to show informations (add button for editing here as well)
     *
     * Long click listener to Edit a task
     */
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Task selected_task = activeTasks.get(position);

                // command complete hier, weil swipen nicht klappt
                Complete completeCommand = new Complete(selected_task.getID(), databaseHelper);
                invoker.setCommand(completeCommand);
                invoker.clickDo();

                Toast.makeText(context,"task with id " + Integer.toString(selected_task.getID()) + " was completed", Toast.LENGTH_SHORT).show();
                //itemsAdapter.notifyDataSetChanged();

                //verschwindet nicht aus der Liste (im ui)
                populateListView();
                undoButton.setEnabled(true);
                return true;
            }
        });
    }
}
