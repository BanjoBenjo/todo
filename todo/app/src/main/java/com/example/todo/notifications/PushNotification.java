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
    /**
     * Notification that triggers a Push Notification on the set Deadline,
     * When do_notify() is called the Alarm is set in the Android system.
     * When cancel() is called the Alarm is canceled.
     */

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
        // activates Alarm in Android Backend
        PendingIntent pushIntent = getPushIntent(context, taskId, title, notes);

        long millisTillDeadline = getDeadlineMillis(deadline);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pushIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, millisTillDeadline, pushIntent);
    }

    @Override
    public void cancel(Context context) {
        // cancels the Alarm in Android Backend
        PendingIntent popUpIntent = getPushIntent(context, taskId, title, notes);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(popUpIntent); // cancel alarm
    }

    private PendingIntent getPushIntent(Context context, int taskID, String titleStr, String notesStr){
        // gets the Intent from Android
        Intent pushIntent = new Intent(context, ReminderBroadcast.class);
        pushIntent.putExtra("title", titleStr);
        pushIntent.putExtra("notes", notesStr);
        pushIntent.putExtra("type", this.toString());

        return PendingIntent.getBroadcast(context, taskID, pushIntent, 0);
    }

    private long getDeadlineMillis(LocalDateTime deadline){
        // calculate milliseconds from date
        LocalDateTime timeNow = LocalDateTime.now();

        long millisTillDeadline = Duration.between(timeNow, deadline).toMillis();
        return System.currentTimeMillis() + millisTillDeadline;
    }

}
