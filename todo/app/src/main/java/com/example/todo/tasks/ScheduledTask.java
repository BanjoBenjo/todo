package com.example.todo.tasks;

import com.example.todo.notifications.EmailNotification;
import com.example.todo.notifications.MultipleNotifications;
import com.example.todo.notifications.NoNotification;
import com.example.todo.notifications.Notification;
import com.example.todo.notifications.PopUpNotification;

import java.time.LocalDateTime;

public class ScheduledTask extends Task {
    private String notes;
    private String category;
    private String title;
    private LocalDateTime deadline;
    private Notification notificationType;

    public ScheduledTask(int ID, String title, String category, String notes, LocalDateTime deadline, String notificationType){
        super(ID, title, category);

        this.notes = notes;
        this.deadline = deadline;
        setNotificationType(notificationType);

    }

    public ScheduledTask(int ID, String title, String category, String notes, String notification){
        super(ID, title, category);

        this.notes=notes;
        setNotificationType(notification);
        setNotificationType("None");
    }

    public void remind(){
        notificationType.do_notify();
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public void setNotificationType(String notificationType){
        switch (notificationType){
            case "PopUp":
                this.notificationType = new PopUpNotification(deadline);
                break;
            case "E-Mail":
                this.notificationType = new EmailNotification(deadline);
                break;
            case "Multiple":
                this.notificationType = new MultipleNotifications(deadline);
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
