package com.example.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewScheduledTaskActivity extends FragmentActivity {

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
        setContentView(R.layout.scheduled_task);

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
            Intent to_mainactivity = new Intent(NewScheduledTaskActivity.this, MainActivity.class);
            NewScheduledTaskActivity.this.startActivity(to_mainactivity);
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

    //todo do something with date and time provided by pickers
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
