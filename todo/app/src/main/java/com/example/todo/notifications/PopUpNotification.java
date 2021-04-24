package com.example.todo.notifications;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class PopUpNotification implements Notification {

    public LocalDateTime deadline;

    public PopUpNotification(LocalDateTime deadline){
        this.deadline = deadline;
    }

    public String toString(){ return  "PopUp"; }

    public void do_notify(){
        System.out.format("It's %s, %s! PopUp Notification \n", deadline);
    }
}
