package com.company;

public class NoNotification implements Notification{

    private String title;

    public NoNotification(String title){
        this.title = title;
    }
    public void do_notify(){
        System.out.format("No Notification for %s! \n", title);
    }
}
