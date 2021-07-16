package com.example.todo.notifications;

import android.app.PendingIntent;
import android.content.Context;

public interface Notification {
    /**
     * Interface for the Notification, Class implementing this Interface has to implement the method getIntent().
     */

    PendingIntent getIntent(Context context);
}
