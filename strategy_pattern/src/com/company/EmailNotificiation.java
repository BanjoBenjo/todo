package com.company;

public class EmailNotificiation implements Notification {
    public String address;

    public EmailNotificiation(String address){
        this.address = address;
    }

    public void do_notify(){
        System.out.format("Sent Email to %s%n ! EmailNotification", address);
    }
}
