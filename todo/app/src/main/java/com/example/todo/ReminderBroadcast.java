package com.example.todo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ReminderBroadcast extends BroadcastReceiver {
    /**
     * This BroadcastReceiver handles Androids AlarmManager.
     * depending on the Notification type the Broadcaster sets a PushNotification or additionally
     * a vibrating Alarm.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("type");

        switch (type){
            case "Push":
                startPushNotification(context, intent);
                break;
            case "Alarm":
                startPushNotification(context, intent);
                startVibrating(context, intent);
                break;
            default:
                break;
        }
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
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText(intent.getStringExtra("notes"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());
    }

    private void startVibrating(Context context, Intent intent){
        // setting up the Vibrating Alarm
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        long[] pattern = {0, 700, 300, 700, 300, 700, 300, 2000, 300, 500};
        v.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
