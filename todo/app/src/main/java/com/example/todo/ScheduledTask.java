package com.example.todo;

public class ScheduledTask extends Task{
    public String notes;

    public ScheduledTask(String title, String deadline){
        super(title);

        notificationType = new PopUpNotification(title, deadline);
    }
}
