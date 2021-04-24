package com.example.todo.notifications;

import android.icu.util.LocaleData;

import com.example.todo.notifications.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class MultipleNotifications implements Notification {

    public LocalDateTime deadline;

    public MultipleNotifications(LocalDateTime deadline){
        this.deadline = deadline;
    }

    public String toString(){ return "Multiple"; }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline);
    }

}
