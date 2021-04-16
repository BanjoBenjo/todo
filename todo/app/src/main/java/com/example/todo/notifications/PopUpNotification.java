package com.example.todo.notifications;

import java.util.Date;

public class PopUpNotification implements Notification {

    public String title;
    public Date deadline;

    public PopUpNotification(String title, Date deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public String toString(){ return  "PopUpNotification"; }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline, title);
    }
}
