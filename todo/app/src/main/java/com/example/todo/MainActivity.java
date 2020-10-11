package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button add_button;
    private Button etienne_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        //TODO remove, is for test only
        etienne_button = findViewById(R.id.etienne_button);
        etienne_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addItem(view);
                Intent to_edit_shopping_task = new Intent(MainActivity.this, EditShoppingTask.class);
                MainActivity.this.startActivity(to_edit_shopping_task);
            }
        });


        add_button = findViewById(R.id.add_button);

        registerForContextMenu(add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addItem(view)
                // Todo change the on click method
                Intent to_edit_basic_task = new Intent(MainActivity.this, EditBasicTask.class);
                MainActivity.this.startActivity(to_edit_basic_task);
            }
        });

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
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
                Intent to_edit_basic_task = new Intent(MainActivity.this, EditBasicTask.class);
                MainActivity.this.startActivity(to_edit_basic_task);
                return true;
            case R.id.shopping_list_option:
                Intent to_edit_shopping_task = new Intent(MainActivity.this, EditShoppingTask.class);
                MainActivity.this.startActivity(to_edit_shopping_task);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void setUpListViewListener() {
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
    }

    private void addItem(View view){
        EditText input = findViewById(R.id.editText2);
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter Text..", Toast.LENGTH_LONG).show();
        }
    }
}