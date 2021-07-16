package com.example.todo.tasks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.notifications.AlarmNotification;
import com.example.todo.notifications.NoNotification;
import com.example.todo.notifications.Notification;
import com.example.todo.notifications.PushNotification;

import java.time.LocalDateTime;

public class ScheduledTask extends Task {
    /**
     * Contains a deadline and a NotificationType for Alarms. Uses the Strategy Pattern to switch
     * between the notification types.
     */

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
        // show short information and execute the notification
        Toast.makeText(context, "Alarm has been set", Toast.LENGTH_SHORT).show();
        notificationType.do_notify(context);
    }

    public void cancel(Context context){
        // show short information and cancel the reminder
        Toast.makeText(context, "Alarm has been canceled", Toast.LENGTH_SHORT).show();
        notificationType.cancel(context);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public void setNotificationType(String notificationType){
        // corresponds to setStrategy() in strategy pattern
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
