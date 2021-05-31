
package com.example.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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

import com.example.todo.tasks.ListTask;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class EditListTaskActivity extends Activity {
    private ArrayList<ListItem> listItemList;
    private ArrayAdapter<String> listItemAdapter;
    private ListView itemListView;
    private Button addItem;
    private Button submitList;
    private EditText inputItemName;
    private EditText inputListName;
    private ListTask listTask;
    private int taskID;

    private DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_task);
        itemListView = findViewById(R.id.itemListView);
        itemListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        addItem = findViewById(R.id.addListItem);
        submitList = findViewById(R.id.submitButton);
        inputItemName = findViewById(R.id.nameOfListItem);
        inputListName = findViewById(R.id.itemListName);
        listItemList = new ArrayList<>();
        listItemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice);
        itemListView.setAdapter(listItemAdapter);

        myDatabaseHelper = DatabaseHelper.getInstance(this);

        Intent thisIntent = getIntent();
        if (thisIntent.getExtras() != null) {
            if (thisIntent.getExtras().containsKey("taskID")) {
                taskID = thisIntent.getIntExtra("taskID", 0);
                listTask = (ListTask) myDatabaseHelper.getTask(taskID);
                inputListName.setText(listTask.getTitle());
                listItemList = listTask.getItems();
                Collections.sort(listItemList);

                int counter = 0;
                for (ListItem i: listItemList) {
                    listItemAdapter.add(i.toString());
                    itemListView.setItemChecked(counter, i.isChecked());
                    listItemAdapter.notifyDataSetChanged();
                    counter++;
                }
            }
        } else {
            listTask = new ListTask(getGlobalTaskId(), "dummytext");
        }

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToList(v);
            }
        });

        submitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!isEmpty(inputListName) && listItemList.size() > 0) {
                listTask.setTitle(String.valueOf(inputListName.getText()));
                saveTask(listTask);
                toMainActivity();
            }
            }
        });
        setUpListViewListener();
    }


    private void setUpListViewListener() {
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem itemToCheck = listItemList.get(position);
                itemToCheck.toggleCheck();
                CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.setChecked(itemToCheck.isChecked());
                saveTask(listTask);
            }
        });

        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem itemToEdit = listItemList.get(position);
                final EditText editText = new EditText(EditListTaskActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListTaskActivity.this);
                builder.setTitle("Edit Item");
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                String oldName = itemToEdit.getName();
                editText.setText(oldName);
                builder.setView(editText);
                // Set up the buttons
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItemAdapter.clear();
                        if(!isEmpty(editText)) {
                            itemToEdit.setName(editText.getText().toString());

                        } else {
                            itemToEdit.setName(oldName);
                        }
                        listItemList = listTask.getItems();
                        for (ListItem i: listItemList) {
                            listItemAdapter.add(i.toString());
                        }
                        listItemAdapter.notifyDataSetChanged();
                        saveTask(listTask);
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItemAdapter.clear();
                        listItemList.remove(itemToEdit);

                        listItemList = listTask.getItems();
                        for (ListItem i: listItemList) {
                            listItemAdapter.add(i.toString());
                        }
                        listItemAdapter.notifyDataSetChanged();
                        saveTask(listTask);
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
        listItemAdapter.clear();

        if(!isEmpty(inputItemName)) {
            ListItem newItem = new ListItem(itemName);
            listTask.addItem(newItem);
            inputItemName.setText("");
        } else {
            Toast.makeText(EditListTaskActivity.this, "Enter name first!", Toast.LENGTH_LONG);
        }
        listItemList = listTask.getItems();
        for (ListItem i: listItemList) {
            listItemAdapter.add(i.toString());
        }
        listItemAdapter.notifyDataSetChanged();
        saveTask(listTask);
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

    public void toMainActivity(){
        Intent to_mainactivity = new Intent(this, MainActivity.class);
        // prevents MainActivity's onCreate call
        to_mainactivity.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        EditListTaskActivity.this.startActivity(to_mainactivity);
    }

    public void saveTask(ListTask mTask) {
        // Todo load task object into database / test with name only
        mTask.setTitle(String.valueOf(inputListName.getText()));
        myDatabaseHelper.addTask(mTask);
    }

}