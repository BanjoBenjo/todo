package com.example.todo;


public class ScheduledTask extends Task {
    private String notes;
    private TaskCategory category;

    public ScheduledTask(String title, TaskCategory category, String deadline){
        super(title, category);

        notificationType = new PopUpNotification(title, deadline);
    }

}
