package com.example.todo.notifications;

public class NoNotification implements Notification {

    public NoNotification(){}

    public String toString(){ return  "None"; }

    public void do_notify(){
        System.out.format("No Notification for %s! \n");
    }
}
