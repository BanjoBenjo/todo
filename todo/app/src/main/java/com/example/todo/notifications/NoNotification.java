package com.example.todo.notifications;

import android.app.PendingIntent;
import android.content.Context;

import com.example.todo.tasks.ScheduledTask;

public class NoNotification implements Notification {
    /**
     * An Empty Notification for Tasks that only have a date set for informative causes.
     */

    public NoNotification(){}

    public String toString(){ return  "None"; }

    @Override
    public PendingIntent getIntent(Context context, ScheduledTask task){
        return null;
    };
}
