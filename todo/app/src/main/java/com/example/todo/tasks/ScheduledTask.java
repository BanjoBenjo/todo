package com.example.todo.tasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.widget.Toast;

import com.example.todo.notifications.AlarmNotification;
import com.example.todo.notifications.NoNotification;
import com.example.todo.notifications.Notification;
import com.example.todo.notifications.PushNotification;

import java.time.Duration;
import java.time.LocalDateTime;

import static android.content.Context.ALARM_SERVICE;

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

    public void doRemind(Context context){
        // show short information and execute the notification
        long millisTillDeadline = getDeadlineMillis(deadline);

        PendingIntent alarmIntent = notificationType.getIntent(context);

        if (alarmIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(alarmIntent); // cancel alarm
            alarmManager.set(AlarmManager.RTC_WAKEUP, millisTillDeadline, alarmIntent);

            Toast.makeText(context, "Alarm has been set", Toast.LENGTH_SHORT).show();
        }
    }

    public void doCancel(Context context){
        // show short information and cancel the reminder
        PendingIntent alarmIntent = notificationType.getIntent(context);

        if (alarmIntent != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(alarmIntent); // cancel alarm

            Toast.makeText(context, "Alarm has been canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public void setNotificationType(String notificationType){
        // corresponds to setStrategy() in strategy pattern
        switch (notificationType){
            case "Push":
                this.notificationType = new PushNotification(this);
                break;
            case "Alarm":
                this.notificationType = new AlarmNotification(this);
                break;
            case "None":
                this.notificationType = new NoNotification(this);
                break;
        }
    }

    private long getDeadlineMillis(LocalDateTime deadline){
        // calculate milliseconds from date
        LocalDateTime timeNow = LocalDateTime.now();

        long millisTillDeadline = Duration.between(timeNow, deadline).toMillis();
        return System.currentTimeMillis() + millisTillDeadline;
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
