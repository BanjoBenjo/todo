package com.example.todo.notifications;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.todo.ReminderBroadcast;

import java.time.Duration;
import java.time.LocalDateTime;

import static android.content.Context.ALARM_SERVICE;

public class PushNotification implements Notification {

    private LocalDateTime deadline;
    private int taskId;
    private String title;
    private String notes;

    public PushNotification(LocalDateTime deadline, int taskId , String title, String notes) {
        this.deadline = deadline;
        this.taskId = taskId;
        this.title = title;
        this.notes = notes;
    }

    public String toString(){ return  "Push"; }

    @Override
    public void do_notify(Context context){
        PendingIntent pushIntent = getPushIntent(context, taskId, title, notes);
        Log.wtf("PushNotification/notify", "TaskId: " + taskId);

        long millisTillDeadline = getDeadlineMillis(deadline);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pushIntent); // cancel alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, millisTillDeadline, pushIntent);
    }

    @Override
    public void cancel(Context context) {
        PendingIntent popUpIntent = getPushIntent(context, taskId, title, notes);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(popUpIntent); // cancel alarm
    }

    private PendingIntent getPushIntent(Context context, int taskID, String titleStr, String notesStr){
        Intent pushIntent = new Intent(context, ReminderBroadcast.class);
        pushIntent.putExtra("title", titleStr);
        pushIntent.putExtra("notes", notesStr);
        pushIntent.putExtra("type", this.toString());

        return PendingIntent.getBroadcast(context, taskID, pushIntent, 0);
    }

    private long getDeadlineMillis(LocalDateTime deadline){
        LocalDateTime timeNow = LocalDateTime.now();

        long millisTillDeadline = Duration.between(timeNow, deadline).toMillis();

        return System.currentTimeMillis() + millisTillDeadline;
    }

}