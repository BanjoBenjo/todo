package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Task> items;
    private ListView listView;
    private List<String> listData;
    private Button addbutton;
    private Button shopping_button;
    private Button scheduled_button;
    private Button location_button;
    private Button undoButton;
    private Button redoButton;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        addbutton = findViewById(R.id.add_button);
        scheduled_button = findViewById(R.id.scheduled_button);
        shopping_button = findViewById(R.id.shopping_button);
        location_button = findViewById(R.id.location_button);

        undoButton = findViewById(R.id.undo_button);
        redoButton = findViewById(R.id.redo_button);

        registerForContextMenu(addbutton);

        populateListView();

        //create list adapter and set the adapter

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.choose_task_menu, menu);
    }

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
        Log.d(TAG, "populateListeView : Displaying data from DB in ListView");

        // get the Data and append to a list
        ArrayList<Task> activeTasks = mDatabaseHelper.getAllActiveTasks();
        listData = new ArrayList<>();

        for (Task t : activeTasks){
            // TODO make sure task object is accessable ( same index as in array list, task has to be created in databasehelper)
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


                //itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }


}