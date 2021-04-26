package com.example.todo.tasks;

import android.content.Context;
import android.util.Log;

import com.example.todo.notifications.EmailNotification;
import com.example.todo.notifications.MultipleNotifications;
import com.example.todo.notifications.NoNotification;
import com.example.todo.notifications.Notification;
import com.example.todo.notifications.PopUpNotification;

import java.time.LocalDateTime;

public class ScheduledTask extends Task {

    private int taskId;
    private String title;
    private String notes;
    private String category;
    private LocalDateTime deadline;
    private Notification notificationType;

    public ScheduledTask(int ID, String title, String category, String notes, LocalDateTime deadline, String notificationType){
        super(ID, title, category);

        this.deadline = deadline;
        this.taskId = ID;
        this.title = title;
        this.notes = notes;
        this.category = category;

        setNotificationType(notificationType);
    }

    public ScheduledTask(int ID, String title, String category, String notes, String notification){
        super(ID, title, category);

        this.notes=notes;
        setNotificationType(notification);
        setNotificationType("None");
    }

    public void remind(Context context){
        notificationType.do_notify(context);
    }

    public void cancel(Context context){
        notificationType.cancel(context);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public void setNotificationType(String notificationType){
        switch (notificationType){
            case "PopUp":
                this.notificationType = new PopUpNotification(deadline, taskId , title, notes);
                break;
            case "E-Mail":
                this.notificationType = new EmailNotification(deadline, taskId , title, notes);
                break;
            case "Multiple":
                this.notificationType = new MultipleNotifications(deadline, taskId , title, notes);
                break;
            case "None":
                this.notificationType = new NoNotification();
                break;
        }
    }

    public String getNotes(){
        return this.notes;
    }

    public LocalDateTime getDeadline(){
        return this.deadline;
    }

    public String getType(){
        return "SCHEDULED";
    }

    public Notification getNotification(){
        return notificationType;
    }
}
