package com.example.todo;


public class ScheduledTask extends Task {
    public String notes;

    public ScheduledTask(String title, TaskCategory category, String deadline){
        super(title, category);

        notificationType = new PopUpNotification(title, deadline);
    }

}
