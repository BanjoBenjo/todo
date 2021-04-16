package com.example.todo.notifications;

import com.example.todo.notifications.Notification;

import java.util.Date;

public class MultipleNotifications implements Notification {

    public String title;
    public Date deadline;

    public MultipleNotifications(String title, Date deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public String toString(){ return "MultipleNotifications"; }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline, title);
    }

}
