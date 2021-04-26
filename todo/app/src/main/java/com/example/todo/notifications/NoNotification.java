package com.example.todo.notifications;

import android.content.Context;

import java.time.LocalDateTime;

public class NoNotification implements Notification {

    public NoNotification(){}

    public String toString(){ return  "None"; }

    public void do_notify(Context context){}

    @Override
    public void cancel(Context context) {

    }
}
