package com.example.todo;

public class PopUpNotification implements Notification{

    public String title;
    public String deadline;

    public PopUpNotification(String title, String deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public String toString(){ return  "PopUpNotification"; }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline, title);
    }
}
