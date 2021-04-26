package com.example.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String taskTitle = intent.getStringExtra("title");
        String taskNote = intent.getStringExtra("notes");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyAlarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(taskTitle)
                .setContentText(taskNote)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
