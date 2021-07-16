package com.example.todo.notifications;

import android.app.PendingIntent;
import android.content.Context;

import com.example.todo.tasks.ScheduledTask;

public class NoNotification implements Notification {
    /**
     * An Empty Notification for Tasks that only have date and time set for informative reasons.
     */

    private ScheduledTask task;

    public NoNotification(){}

    public NoNotification(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public String toString(){ return "None"; }

    @Override
    public PendingIntent getIntent(Context context){
        return null;
    };
}
