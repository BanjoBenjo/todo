package com.example.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.todo.dialogfragments.DatePickerFragment;
import com.example.todo.dialogfragments.TimePickerFragment;
import com.example.todo.tasks.ScheduledTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewScheduledTaskActivity extends FragmentActivity {

    private ScheduledTask thisTask;
    int taskID;
    DatabaseHelper myDatabaseHelper;

    private EditText nameView;
    private Spinner notificationSpinner;
    private Spinner categorySpinner;
    private Button submitButton;
    private EditText notes;
    private TimePickerFragment timePicker;
    private DatePickerFragment datePicker;

    private ArrayAdapter<String> categoryArrayAdapter;
    private ArrayAdapter<String> notificationArrayAdapter;

    private List<String> categories = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduled_task);

        categories.add("privat");
        categories.add("school");
        categories.add("work");
        categories.add("sport");

        notifications.add("None");
        notifications.add("PopUp");
        notifications.add("Multiple");
        notifications.add("Mail");

        nameView = findViewById(R.id.editName);
        notificationSpinner = findViewById(R.id.spinner_notifications);
        categorySpinner = findViewById(R.id.spinner_category);
        submitButton = findViewById(R.id.submitButton);
        notes = findViewById(R.id.editTextTextMultiLine);
        timePicker = new TimePickerFragment();
        datePicker = new DatePickerFragment();

        categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryArrayAdapter);

        notificationArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, notifications);
        notificationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notificationSpinner.setAdapter(notificationArrayAdapter);

        myDatabaseHelper = new DatabaseHelper(this);

        final Intent thisIntent = getIntent();
        if (thisIntent.getExtras() != null) {
            if (thisIntent.getExtras().containsKey("taskID")) {
                taskID = thisIntent.getIntExtra("taskID", 0);
                thisTask = (ScheduledTask) myDatabaseHelper.getTask(taskID);
                nameView.setText(thisTask.getTitle());
                categorySpinner.setSelection(categories.indexOf(thisTask.getCategory()));
                notificationSpinner.setSelection(notifications.indexOf(thisTask.getNotification().toString()));
                notes.setText(thisTask.getNotes());
                setDate(thisTask.getDeadline());
            }
        }else {
            thisTask = new ScheduledTask(getGlobalTaskId(), nameView.getText().toString(),
                    categories.get(categorySpinner.getSelectedItemPosition()),
                    notes.getText().toString(),
                    notifications.get(notificationSpinner.getSelectedItemPosition())
                    );
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(nameView) && datePicker.selected) {
                    thisTask.setTitle(nameView.getText().toString());
                    thisTask.setCategory(categories.get(categorySpinner.getSelectedItemPosition()));
                    thisTask.setNotificationType(notifications.get(notificationSpinner.getSelectedItemPosition()));
                    thisTask.setDeadline(getDate());
                    thisTask.setNotes(notes.getText().toString());
                    toastMessage("Task created");
                    addTask(thisTask);
                }else {
                    toastMessage("You have to enter a name!");
                }
            }
        });

    }

    private void addTask(ScheduledTask mTask){
        boolean insertData = myDatabaseHelper.addTask(mTask);

        if (insertData) {
            Intent to_mainactivity = new Intent(NewScheduledTaskActivity.this, MainActivity.class);
            NewScheduledTaskActivity.this.startActivity(to_mainactivity);

        } else {
            toastMessage("There went something wrong..");
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

    private LocalDateTime getDate(){
        int day = datePicker.getSelectedDay();
        int month = datePicker.getSelectedMonth();
        int year = datePicker.getSelectedYear();

        int hour = 0;
        int min = 0;

        try {
             hour = timePicker.getSelectedHour();
             min = timePicker.getSelectedMin();
        }catch(Exception e){
        }
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, min);

        return date;
    }

    private void setDate(LocalDateTime date){
        datePicker.setSelectedDay(date.getDayOfMonth());
        datePicker.setSelectedMonth(date.getMonthValue());
        datePicker.setSelectedYear(date.getYear());

        try {
            timePicker.setSelectedHour(date.getHour());
            timePicker.setSelectedMin(date.getMinute());
        }catch(Exception e){
        }

        datePicker.setSelected();
    }

    //todo do something with date and time provided by pickers
    public void showTimePickerDialog(View view) {
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view) {
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
