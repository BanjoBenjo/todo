package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.todo.tasks.BasicTask;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class NewBasicTaskActivity extends Activity {

    private static final String TAG = "NewBasicTaskActivity";
    int taskID;
    BasicTask thisTask;

    DatabaseHelper myDatabaseHelper;
    private EditText nameView;
    private Button submitButton;
    private EditText notes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_task);

        nameView = findViewById(R.id.editName);
        submitButton = findViewById(R.id.submitButton);
        notes = findViewById(R.id.editNote);

        myDatabaseHelper = new DatabaseHelper(this);

        final Intent thisIntent = getIntent();
        if (thisIntent.getExtras() != null) {
            if (thisIntent.getExtras().containsKey("taskID")) {
                taskID = thisIntent.getIntExtra("taskID", 0);
                thisTask = (BasicTask) myDatabaseHelper.getTask(taskID);
                nameView.setText(thisTask.getTitle());
                notes.setText(thisTask.getNotes());
            }
        }else {
            thisTask = new BasicTask(getGlobalTaskId(), nameView.getText().toString(),
                    notes.getText().toString());
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(nameView)) {
                    thisTask.setTitle(nameView.getText().toString());
                    thisTask.setNotes(notes.getText().toString());
                    toastMessage("Task created");
                    addTask(thisTask);
                }else {
                    toastMessage("You have to enter a name!");
                }
            }
        });
    }

    public void addTask(BasicTask mTask) {
        // Todo load task obejct in database / test with name only
        boolean insertData = myDatabaseHelper.addTask(mTask);

        if (insertData) {
            Intent to_mainactivity = new Intent(NewBasicTaskActivity.this, MainActivity.class);
            to_mainactivity.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            NewBasicTaskActivity.this.startActivity(to_mainactivity);

        } else {
            toastMessage("There went something wrong..");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private int getGlobalTaskId(){
        SharedPreferences mPreferences = getSharedPreferences("CurrentUser",
                MODE_PRIVATE);

        int task_id = mPreferences.getInt("GlobalTaskID", 1);

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("GlobalTaskID", task_id+1);
        editor.commit();

        return task_id;
    }

}
