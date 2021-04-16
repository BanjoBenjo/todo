package com.example.todo.notifications;

public class NoNotification implements Notification {

    private String title;

    public NoNotification(String title){
        this.title = title;
    }
    public String toString(){ return  "NoNotification"; }

    public void do_notify(){
        System.out.format("No Notification for %s! \n", title);
    }
}
