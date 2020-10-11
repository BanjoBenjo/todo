package com.example.todo;

public class MultipleNotifications {

    public String title;
    public String deadline;

    public MultipleNotifications(String title, String deadline){
        this.title = title;
        this.deadline = deadline;
    }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline, title);
    }

}
