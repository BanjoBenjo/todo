package com.example.todo;

import com.example.todo.notifications.EmailNotification;
import com.example.todo.notifications.MultipleNotifications;
import com.example.todo.notifications.NoNotification;
import com.example.todo.notifications.Notification;
import com.example.todo.notifications.PopUpNotification;

import java.util.Date;

public class ScheduledTask extends Task {
    private String notes;
    private String category;
    private String title;
    private Date deadline;
    private Notification notificationType;

    ScheduledTask(int ID, String title, String category, String notes, String deadline, String notification){
        super(ID, title, category);

    }

    ScheduledTask(int ID, String title, String category){
        super(ID, title, category);

        this.title = title;
        this.category = category;
    }



    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public void setCategory(String category){ this.category = category; }

    public void setNotificationType(String notificationType){
        switch (notificationType){
            case "PopUp":
                this.notificationType = new PopUpNotification(title, deadline);
            case "EMail":
                this.notificationType = new EmailNotification(title, deadline);
            case "MULTI":
                this.notificationType = new MultipleNotifications(title, deadline);
            case "NONE":
                this.notificationType = new NoNotification(title);
        }
    }

    public String getNotes(){
        return this.notes;
    }

    public Date getDeadline(){
        return this.deadline;
    }

    public String getType(){
        return "SCHEDULED";
    }

    public Notification getNotification(){
        return this.notificationType;
    }
}
