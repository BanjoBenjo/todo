package com.example.todo;

import java.util.Date;

public class ScheduledTask extends Task {
    private String notes;
    private String category;
    private String deadline;
    private Notification notificationType;

    public ScheduledTask(int ID, String title, String category, String notes, String deadline){
        super(ID, title, category);

        this.category = category;
        this.notes = notes;
        this.deadline = deadline;
        this.notificationType = new PopUpNotification(title, deadline);
    }

    public ScheduledTask(int ID, String title, String category, String notes, String deadline, String notificationType){
        super(ID, title, category);

        this.category = category;
        this.notes = notes;
        this.deadline = deadline;

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

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes(){
        return this.notes;
    }

    public String getDeadline(){
        return this.deadline;
    }

    public String getType(){
        return "SCHEDULED";
    }

    public Notification getNotification(){
        return this.notificationType;
    }
}
