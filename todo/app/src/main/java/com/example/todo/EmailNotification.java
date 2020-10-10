package com.example.todo;

public class EmailNotification implements Notification{
    public String address;

    public EmailNotification(String address){
        this.address = address;
    }

    public void do_notify(){
        System.out.format("Sent Email to %s%n ! EmailNotification", address);
    }
}

