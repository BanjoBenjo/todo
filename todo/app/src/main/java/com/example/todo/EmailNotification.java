package com.example.todo;

public class EmailNotification implements Notification{
    public String title;
    public String deadline;
    //TODO get emailadress from settings

    public EmailNotification(String title, String deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public String toString(){ return "EmailNotification"; }

    public void do_notify(){
        //TODO send email here
    }
}

