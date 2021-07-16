package com.example.todo.notifications;


import android.app.PendingIntent;
import android.content.Context;

import com.example.todo.tasks.ScheduledTask;

public interface Notification {
    /**
     * Interface for the Notification, every Child has to implement do_notify() and cancel().
     */

    String toString();
    PendingIntent getIntent(Context context, ScheduledTask task);
}
