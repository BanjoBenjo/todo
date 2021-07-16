package com.example.todo.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todo.R;
import com.example.todo.tasks.ScheduledTask;


public class PushNotification extends BroadcastReceiver implements Notification {
    /**
     * Notification that triggers a Push Notification on the set Deadline,
     * When do_notify() is called the Alarm is set in the Android system.
     * When cancel() is called the Alarm is canceled.
     */

    private ScheduledTask task;

    public PushNotification() {
    }

    public PushNotification(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public String toString(){ return "Push"; }

    @Override
    public PendingIntent getIntent(Context context){
        // gets the Intent from Android
        Intent pushIntent = new Intent(context, PushNotification.class);
        pushIntent.putExtra("title", task.getTitle());
        pushIntent.putExtra("notes", task.getNotes());

        return PendingIntent.getBroadcast(context, task.getID(), pushIntent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        startPushNotification(context, intent);
    }

    private void startPushNotification(Context context, Intent intent){
        // setting up the PushNotification
        String CHANNEL_ID="1";
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "Alarm", NotificationManager.IMPORTANCE_HIGH);
            mChannel.setLightColor(Color.GRAY);
            mChannel.enableLights(true);
            mChannel.setDescription("Alarm");

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel( mChannel );
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.mipmap.ic_launcher_foreground2)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("notes"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());
    }
}
