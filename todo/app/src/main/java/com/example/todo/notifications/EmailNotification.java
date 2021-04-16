package com.example.todo.notifications;

import java.util.Date;

public class EmailNotification implements Notification {
    public String title;
    public Date deadline;
    //TODO get emailadress from settings

    public EmailNotification(String title, Date deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public String toString(){ return "EmailNotification"; }

    public void do_notify(){
        //TODO send email here
    }
}

