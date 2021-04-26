package com.example.todo.notifications;

import android.content.Context;
import android.icu.util.LocaleData;

import com.example.todo.notifications.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class MultipleNotifications implements Notification {

    private LocalDateTime deadline;
    private int taskId;
    private String title;
    private String notes;

    public MultipleNotifications(LocalDateTime deadline, int taskId , String title, String notes) {
        this.deadline = deadline;
        this.taskId = taskId;
        this.title = title;
        this.notes = notes;
    }

    public String toString(){ return "Multiple"; }

    public void do_notify(Context context){}

    @Override
    public void cancel(Context context) {

    }
}
