package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditScheduledTaskActivity extends Activity {

    private EditText nameview;
    private Spinner notificationSpinner;
    private Spinner categorySpinner;
    private Button submitButton;
    private EditText notes;

    private ArrayAdapter<String> categoryArrayAdapter;
    private ArrayAdapter<String> notificationArrayAdapter;

    private List<String> categories = new ArrayList<>();
    private List<String> notifications = new ArrayList<>(
            Arrays.asList("None",
                    "PopUp",
                    "Multiple",
                    "E-Mail") );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_scheduled_task);

        categories.add("privat");
        categories.add("school");
        categories.add("work");
        categories.add("sport");

        nameview = findViewById(R.id.editName);
        notificationSpinner = findViewById(R.id.spinner_notifications);
        categorySpinner = findViewById(R.id.spinner_category);
        submitButton = findViewById(R.id.submitButton);

        categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);

        notificationArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, notifications);
        notificationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notificationSpinner.setAdapter(notificationArrayAdapter);

    }

    private void addTask(){
        nameview = findViewById(R.id.editName);
        categorySpinner = findViewById(R.id.spinner);
        notes = findViewById(R.id.editTextTextMultiLine);

        if(!(nameview.equals(""))){
            BasicTask task = new BasicTask(getGlobalTaskId(), nameview.getText().toString(),
                    categories.get(categorySpinner.getSelectedItemPosition()), notes.getText().toString());

            // Todo load task in database
            Intent to_mainactivity = new Intent(EditScheduledTaskActivity.this, MainActivity.class);
            EditScheduledTaskActivity.this.startActivity(to_mainactivity);
        }
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
