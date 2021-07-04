package com.example.todo.notifications;

import android.content.Context;

public interface Notification {
    /**
     * Interface for the Notification, every Child has to implement do_notify() and cancel().
     */
    void do_notify(Context context);
    void cancel(Context context);
}
