package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.todo.tasks.BasicTask;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class EditBasicTaskActivity extends Activity {
    /**
     * Activity for displaying existing or creating new basic task. Task only has a title and a
     * note.
     */
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

        myDatabaseHelper = DatabaseHelper.getInstance(this);

        final Intent thisIntent = getIntent();
        // check intent to differentiate between new basic task or existing basic task
        if (thisIntent.getExtras() != null) {
            // existing task
            if (thisIntent.getExtras().containsKey("taskID")) {
                // set title and notes
                taskID = thisIntent.getIntExtra("taskID", 0);
                thisTask = (BasicTask) myDatabaseHelper.getTask(taskID);
                nameView.setText(thisTask.getTitle());
                notes.setText(thisTask.getNotes());
            }
        }else {
            // new task
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
        // add task to database
        boolean insertData = myDatabaseHelper.addTask(mTask);
        // if successful return to main activity
        if (insertData) {
            Intent to_mainactivity = new Intent(EditBasicTaskActivity.this, MainActivity.class);
            to_mainactivity.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            EditBasicTaskActivity.this.startActivity(to_mainactivity);
        } else {
            toastMessage("Something went wrong while saving this task.");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private int getGlobalTaskId(){
        // needed after restart of app so we know highest task id
        SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        int task_id = mPreferences.getInt("GlobalTaskID", 1);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("GlobalTaskID", task_id+1);
        editor.commit();

        return task_id;
    }

}
