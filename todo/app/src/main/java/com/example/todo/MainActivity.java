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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Intents
    Intent toNewBasicTask;
    Intent toNewScheduledTask;
    Intent toNewShoppingTask;

    /*
    This Activity holds two seperate lists of the tasks. One List holds the actual Task objects
    The second List only holds the Titles of the objects for representation in the list view
    Very important to keep both synchronized
     */
    private ArrayList<Task> activeTasks;
    private List<String> listData;
    ArrayAdapter<String> listAdapter;

    // Interface
    private ListView listView;
    private Button addButton;
    private Button shoppingButton;
    private Button scheduledButton;
    private Button locationButton;
    private Button undoButton;
    private Button redoButton;
    private Invoker invoker;

    DatabaseHelper myDatabaseHelper;

    //for flings, can be tuned, not used atm
    private float flingMin = 100;
    private float velocityMin = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Intents
        toNewBasicTask = new Intent(MainActivity.this, NewBasicTaskActivity.class);
        toNewScheduledTask = new Intent(MainActivity.this, NewScheduledTaskActivity.class);
        toNewShoppingTask = new Intent(MainActivity.this, NewShoppingTaskActivity.class);

        listView = findViewById(R.id.listView);
        myDatabaseHelper = new DatabaseHelper(this);

        addButton = findViewById(R.id.add_button);
        scheduledButton = findViewById(R.id.scheduled_button);
        shoppingButton = findViewById(R.id.shopping_button);
        locationButton = findViewById(R.id.location_button);

        undoButton = findViewById(R.id.undo_button);
        undoButton.setEnabled(false);
        redoButton = findViewById(R.id.redo_button);
        redoButton.setEnabled(false);

        registerForContextMenu(addButton);

        populateListView();
        setUpListViewListener();

        //invoker for command pattern
        invoker = new Invoker();

        //button listeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(toNewBasicTask);
            }
        });

        scheduledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(toNewScheduledTask);
            }
        });

        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(toNewShoppingTask);
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                invoker.clickUndo();
                updateTitleList();
            }
        });
        redoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                invoker.clickRedo();
                updateTitleList();
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
                MainActivity.this.startActivity(toNewBasicTask);
                return true;
            case R.id.shopping_list_option:
                MainActivity.this.startActivity(toNewShoppingTask);
                return true;
            case R.id.scheduled_task_option:
                MainActivity.this.startActivity(toNewScheduledTask);
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void populateListView(){
        Log.d(TAG, "populateListView : Displaying data from DB in ListView");

        // get Task list and empty Title list
        activeTasks = myDatabaseHelper.getAllActiveTasks();
        listData = new ArrayList<>();

        //create list adapter and set the adapter
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(listAdapter);

        for (Task t : activeTasks){
            listData.add(t.getTitle());
        }
    }

    public void updateButtons() {
        undoButton.setEnabled(invoker.getUndoState());
        redoButton.setEnabled(invoker.getRedoState());
    }

    public void updateTitleList() {
        // ist das die ListView der Tasks?
        listData.clear();
        activeTasks = myDatabaseHelper.getAllActiveTasks();

        for (Task t : activeTasks){
            listData.add(t.getTitle());
        }
        listAdapter.notifyDataSetChanged();
    }

    /**
     * Set up List view Listeners
     *
     * TODO [HARD] click to show information (add button for editing here as well)
     *
     * Long click listener to Edit a task
     */
    private void setUpListViewListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Task selected_task = activeTasks.get(position);

                // command complete hier, weil swipen nicht klappt
                Complete completeCommand = new Complete(selected_task.getID(), myDatabaseHelper);
                invoker.setCommand(completeCommand);
                invoker.clickDo();

                Toast.makeText(context,"task with id " + Integer.toString(selected_task.getID()) + " was completed", Toast.LENGTH_SHORT).show();
                //itemsAdapter.notifyDataSetChanged();

                updateTitleList();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Task selected_task = activeTasks.get(position);
                Toast.makeText(context,"ID of task is " + selected_task.getID(), Toast.LENGTH_SHORT).show();

                Intent toEditBasicTask = new Intent(MainActivity.this, EditBasicTaskActivity.class);
                toEditBasicTask.putExtra("taskID", selected_task.getID());
                MainActivity.this.startActivity(toEditBasicTask);
                return true;
            }
        });
    }
}
