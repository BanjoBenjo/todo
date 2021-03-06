package com.example.todo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.example.todo.dialogfragments.DatePickerFragment;
import com.example.todo.dialogfragments.TimePickerFragment;
import com.example.todo.tasks.ScheduledTask;
import java.time.LocalDateTime;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class EditScheduledTaskActivity extends FragmentActivity {
    /**
     * Activity for displaying existing or creating new scheduled tasks. Scheduled tasks have a
     * title, notes, one of three notification types and a deadline.
     */
    private ScheduledTask thisTask;
    int taskID;
    DatabaseHelper myDatabaseHelper;
    private EditText nameView;
    private Button submitButton;
    private EditText notes;
    private TimePickerFragment timePicker;
    private DatePickerFragment datePicker;
    private Button dateButton;
    private Button timeButton;
    // radio group for choosing notification type
    private RadioButton noneRadioButton, pushRadioButton, alarmRadioButton;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheduled_task);

        nameView = findViewById(R.id.editName);
        radioGroup = findViewById(R.id.radio_group);
        noneRadioButton = findViewById(R.id.radio_none);
        noneRadioButton.setChecked(false);
        pushRadioButton = findViewById(R.id.radio_push);
        pushRadioButton.setChecked(false);
        alarmRadioButton = findViewById(R.id.radio_alarm);
        alarmRadioButton.setChecked(false);
        submitButton = findViewById(R.id.submitButton);
        notes = findViewById(R.id.editTextTextMultiLine);
        // date and time picker to set deadline
        dateButton = findViewById(R.id.date_button);
        timeButton = findViewById(R.id.time_button);
        timePicker = new TimePickerFragment();
        datePicker = new DatePickerFragment();
        myDatabaseHelper = DatabaseHelper.getInstance(this);
        // notification channel for alarm or push notification
        createNotificationChannel();

        final Intent thisIntent = getIntent();
        // check intent to know differentiate between new scheduled task or scheduled list task
        if (thisIntent.getExtras() != null) {
            // existing task
            if (thisIntent.getExtras().containsKey("taskID")) {
                // display all attributes
                taskID = thisIntent.getIntExtra("taskID", 0);
                thisTask = (ScheduledTask) myDatabaseHelper.getTask(taskID);
                nameView.setText(thisTask.getTitle());
                switch(thisTask.getNotification().toString()){
                    case "None":
                        noneRadioButton.setChecked(true);
                        break;
                    case "Push":
                        pushRadioButton.setChecked(true);
                        break;
                    case "Alarm":
                        alarmRadioButton.setChecked(true);
                        break;
                }
                notes.setText(thisTask.getNotes());
                setDate(thisTask.getDeadline());
            }
        // new scheduled task
        }else {
            taskID = getGlobalTaskId();
            thisTask = new ScheduledTask(taskID, nameView.getText().toString(),
                    notes.getText().toString(),
                    "None"
                    );
        }
        // save the task
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(nameView) && datePicker.selected) {
                    String titleStr = nameView.getText().toString();
                    String notesStr = notes.getText().toString();
                    LocalDateTime selectedDate = getSelectedDate();

                    thisTask.setTitle(titleStr);
                    thisTask.setDeadline(selectedDate);
                    thisTask.setNotes(notesStr);
                    thisTask.setNotificationType(getNotificationType());
                    toastMessage("Task created");
                    thisTask.doRemind(getApplicationContext());
                    addTask(thisTask);

                }else {
                    if (isEmpty(nameView)) {
                        toastMessage("Please select a Name");
                    }else {
                        toastMessage("Please select a Date");
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (datePicker.getSelectedDay() != 0){
            LocalDateTime selectedDate = getSelectedDate();
            setDateString(selectedDate);
        }
    }

    private String getNotificationType(){
        String notificationType;
        switch(radioGroup.getCheckedRadioButtonId()){
            default:
            case R.id.radio_none:
                notificationType = "None";
                break;
            case R.id.radio_push:
                notificationType = "Push";
                break;
            case R.id.radio_alarm:
                notificationType = "Alarm";
                break;
        }
        return notificationType;
    }

    private void addTask(ScheduledTask mTask){
        // add task to database
        boolean insertData = myDatabaseHelper.addTask(mTask);
        // return to main activity if task was added successfully
        if (insertData) {
            Intent to_mainactivity = new Intent(this, MainActivity.class);
            to_mainactivity.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
            EditScheduledTaskActivity.this.startActivity(to_mainactivity);
        } else {
            toastMessage("There went something wrong..");
        }
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

    private LocalDateTime getSelectedDate(){
        // get deadline from date picker
        int hour = 0;
        int min = 0;
        int day = datePicker.getSelectedDay();
        int month = datePicker.getSelectedMonth();
        int year = datePicker.getSelectedYear();
        try {
             hour = timePicker.getSelectedHour();
             min = timePicker.getSelectedMin();
        }catch(Exception e){}
        return LocalDateTime.of(year, month + 1, day, hour, min);
    }

    private void setDate(LocalDateTime date){
        // when task already has existing deadline
        datePicker.setSelectedDay(date.getDayOfMonth());
        datePicker.setSelectedMonth(date.getMonthValue()-1);
        datePicker.setSelectedYear(date.getYear());
        try {
            timePicker.setSelectedHour(date.getHour());
            timePicker.setSelectedMin(date.getMinute());
        }catch(Exception e){}
        datePicker.setSelected();
    }

    private void setDateString(LocalDateTime date){
        // set button text to selected date
        String dateString = "" + date.getDayOfMonth() + "." + (date.getMonthValue()-1) + "."
                + date.getYear();
        dateButton.setText(dateString);

        String timeString = "" + date.getHour() + ":" + date.getMinute();
        timeButton.setText(timeString);
    }

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ReminderChannel";
            String description= "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyAlarm", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
