package com.example.todo.tasks;

import android.content.Context;
import android.util.Log;

import com.example.todo.notifications.AlarmNotification;
import com.example.todo.notifications.NoNotification;
import com.example.todo.notifications.Notification;
import com.example.todo.notifications.PushNotification;

import java.time.LocalDateTime;

public class ScheduledTask extends Task {

    private String notes;
    private LocalDateTime deadline;
    private Notification notificationType;

    public ScheduledTask(int ID, String title, String notes, LocalDateTime deadline, String notificationType){
        super(ID, title);

        this.deadline = deadline;
        this.notes = notes;

        setNotificationType(notificationType);
    }

    public ScheduledTask(int ID, String title, String notes, String notification){
        super(ID, title);

        this.notes=notes;
        setNotificationType(notification);
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
            case "Push":
                Log.wtf("ScheduledTask", "Push created ID: " + super.getID());
                this.notificationType = new PushNotification(deadline, super.getID() , super.getTitle(), notes);
                break;
            case "Alarm":
                this.notificationType = new AlarmNotification(deadline, super.getID() , super.getTitle(), notes);
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
