package com.example.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.todo.tasks.ShoppingTask;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class NewShoppingTaskActivity extends Activity {
    private static final String TAG = "NewShoppingTaskActivity";

    private ArrayList<ShoppingItem> shoppingItemsList;
    private ArrayAdapter<String> shoppingItemsAdapter;
    private ListView shoppingListView;
    private Button addShoppingItem;
    private Button submitShoppingList;
    private EditText inputItemName;
    private EditText inputListName;
    private ShoppingTask shoppingTask;
    private int taskID;

    private DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_task);
        shoppingListView = findViewById(R.id.shoppingListView);
        shoppingListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        addShoppingItem = findViewById(R.id.addShoppingItem);
        submitShoppingList = findViewById(R.id.submit_shopping_list);
        inputItemName = findViewById(R.id.nameOfShoppingItem);
        inputListName = findViewById(R.id.shopping_list_name);
        shoppingItemsList = new ArrayList<>();
        shoppingItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice);
        shoppingListView.setAdapter(shoppingItemsAdapter);

        myDatabaseHelper = new DatabaseHelper(this);

        Intent thisIntent = getIntent();
        if (thisIntent.getExtras() != null) {
            if (thisIntent.getExtras().containsKey("taskID")) {
                taskID = thisIntent.getIntExtra("taskID", 0);
                shoppingTask = (ShoppingTask) myDatabaseHelper.getTask(taskID);
                inputListName.setText(shoppingTask.getTitle());
                shoppingItemsList = shoppingTask.getItems();

                int counter = 0;
                for (ShoppingItem i: shoppingItemsList) {
                    shoppingItemsAdapter.add(i.toString());
                    shoppingListView.setItemChecked(counter, i.isChecked());
                    counter++;
                }
            }
        } else {
            shoppingTask  = new ShoppingTask(getGlobalTaskId(), "dummytext", "privat");

        }

        addShoppingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList(v);
            }
        });

        submitShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!isEmpty(inputListName) && shoppingItemsList.size() > 0) {
                shoppingTask.setTitle(String.valueOf(inputListName.getText()));
                addTask(shoppingTask);
            }
            }
        });

        setUpListViewListener();
    }

    private void setUpListViewListener() {
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingItem itemToCheck = shoppingItemsList.get(position);
                itemToCheck.toggleCheck();
                CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.setChecked(itemToCheck.isChecked());
                shoppingItemsAdapter.notifyDataSetChanged();
            }
        });
        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingItem itemToEdit = shoppingItemsList.get(position);
                final EditText editText = new EditText(NewShoppingTaskActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewShoppingTaskActivity.this);
                builder.setTitle("Edit Item");
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                String oldName = itemToEdit.getName();
                editText.setText(oldName);
                builder.setView(editText);
                // Set up the buttons
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shoppingItemsAdapter.clear();
                        if(!isEmpty(editText)) {
                            itemToEdit.setName(editText.getText().toString());

                        } else {
                            itemToEdit.setName(oldName);
                        }
                        shoppingItemsList = shoppingTask.getItems();
                        for (ShoppingItem i: shoppingItemsList) {
                            shoppingItemsAdapter.add(i.toString());
                        }
                        shoppingItemsAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void addItemToList(View view) {
        String itemName = inputItemName.getText().toString();
        shoppingItemsAdapter.clear();

        if(!isEmpty(inputItemName)) {
            ShoppingItem newItem = new ShoppingItem(itemName);
            shoppingTask.addItem(newItem);
            shoppingItemsList = shoppingTask.getItems();
            for (ShoppingItem i: shoppingItemsList) {
                shoppingItemsAdapter.add(i.toString());
            }
            inputItemName.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_LONG).show();
        }
        shoppingItemsAdapter.notifyDataSetChanged();
    }

    private int getGlobalTaskId(){
        SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        int task_id = mPreferences.getInt("GlobalTaskID", 1);

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("GlobalTaskID", task_id+1);
        editor.commit();

        return task_id;
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void addTask(ShoppingTask mTask) {
        // Todo load task object into database / test with name only
        boolean insertData = myDatabaseHelper.addTask(mTask);

        if (insertData) {
            Intent to_mainactivity = new Intent(this, MainActivity.class);
            // prevents MainActivity's onCreate call
            to_mainactivity.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            NewShoppingTaskActivity.this.startActivity(to_mainactivity);

        } else {
            Log.e("NewShoppingTaskActivity", "Error while creating task from Shopping List.");
        }
    }

}
