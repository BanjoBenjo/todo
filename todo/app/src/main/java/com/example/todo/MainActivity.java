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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Task> items;
    private ArrayAdapter<Task> itemsAdapter;
    private ListView listView;
    private Button addbutton;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        addbutton = findViewById(R.id.add_button);
        registerForContextMenu(addbutton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBasicTask();
            }
        });

        populateListView();

        /*
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
        */

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
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from col 1 and add to arraylist
            listData.add(data.getString(1));
        }
        //create list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

/*    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }*/

    private void addBasicTask(){
        EditText input = findViewById(R.id.editTaskName);
        String task_name = input.getText().toString();

        BasicTask new_task = new BasicTask(task_name);
        if(!(task_name.equals(""))){
            //todo add task to database
            itemsAdapter.add(new_task);
            input.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter Text..", Toast.LENGTH_LONG).show();
        }
    }
}