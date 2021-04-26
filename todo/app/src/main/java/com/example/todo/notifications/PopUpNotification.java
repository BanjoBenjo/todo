package com.example.todo.notifications;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.todo.NewScheduledTaskActivity;
import com.example.todo.ReminderBroadcast;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static android.content.Context.ALARM_SERVICE;

public class PopUpNotification implements Notification {

    private LocalDateTime deadline;
    private int taskId;
    private String title;
    private String notes;

    public PopUpNotification(LocalDateTime deadline, int taskId , String title, String notes) {
        this.deadline = deadline;
        this.taskId = taskId;
        this.title = title;
        this.notes = notes;
    }

    public String toString(){ return  "PopUp"; }

    @Override
    public void do_notify(Context context){
        PendingIntent popUpIntent = getPopupIntent(context, taskId, title, notes);
        Toast.makeText(context, "TITLE " + title +" NOTES " + notes, Toast.LENGTH_SHORT).show();

        long millisTillDeadline = getDeadlineMillis(deadline);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(popUpIntent); // cancel alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, millisTillDeadline, popUpIntent);

    }

    @Override
    public void cancel(Context context) {
        PendingIntent popUpIntent = getPopupIntent(context, taskId, title, notes);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(popUpIntent); // cancel alarm

        Toast.makeText(context, "CANCELED", Toast.LENGTH_SHORT).show();
    }

    private PendingIntent getPopupIntent(Context context, int taskID, String titleStr, String notesStr){
        Intent popUpIntent = new Intent(context, ReminderBroadcast.class);
        popUpIntent.putExtra("title", titleStr);
        popUpIntent.putExtra("notes", notesStr);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                taskID, popUpIntent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getBroadcast(context, taskID , popUpIntent, 0);
        }

        return pendingIntent;
    }

    private long getDeadlineMillis(LocalDateTime deadline){
        LocalDateTime timeNow = LocalDateTime.now();

        long millisTillDeadline = Duration.between(timeNow, deadline).toMillis();
        long deadlineMillis = System.currentTimeMillis() + millisTillDeadline;

        return deadlineMillis;
    }

}
