package com.example.todo;

public class MultipleNotifications implements Notification{

    public String title;
    public String deadline;

    public MultipleNotifications(String title, String deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public String toString(){ return "MultipleNotifications"; }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline, title);
    }

}
