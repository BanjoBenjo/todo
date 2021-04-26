package com.example.todo.notifications;

import android.content.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmailNotification implements Notification {
    public LocalDateTime deadline;
    //TODO get emailadress from settings

    public EmailNotification(LocalDateTime deadline, int taskId , String title, String notes){
        this.deadline = deadline;
    }

    public String toString(){ return "E-Mail"; }

    @Override
    public void do_notify(Context context){}

    @Override
    public void cancel(Context context) {

    }
}

