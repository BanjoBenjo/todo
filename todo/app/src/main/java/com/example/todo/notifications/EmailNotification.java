package com.example.todo.notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmailNotification implements Notification {
    public LocalDateTime deadline;
    //TODO get emailadress from settings

    public EmailNotification(LocalDateTime deadline){
        this.deadline = deadline;
    }

    public String toString(){ return "E-Mail"; }

    public void do_notify(){
        //TODO send email here
    }
}

