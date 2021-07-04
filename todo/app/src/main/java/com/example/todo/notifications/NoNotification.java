package com.example.todo.notifications;

import android.content.Context;

public class NoNotification implements Notification {
    /**
     * An Empty Notification for Tasks that only have a date set for informative causes.
     */

    public NoNotification(){}

    public String toString(){ return  "None"; }

    public void do_notify(Context context){}

    @Override
    public void cancel(Context context) {}
}
