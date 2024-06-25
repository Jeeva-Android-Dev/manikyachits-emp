package com.mazenet.mani.gurugubera.Alarmreceivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil {
    public static String createNotificationChannel(
            Context context) {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            String channelId ="";

            // The user-visible name of the channel.
            CharSequence channelName = "";
            // The user-visible description of the channel.
            String channelDescription = "";
            int channelImportance = 0;
            boolean channelEnableVibrate = false;
            int channelLockscreenVisibility =
                   0;


            // Notification channel values (for devices targeting 26 and above):
            channelId = "channel_reminder_1";
            // The user-visible name of the channel.
            channelName = "Sample Reminder";
            // The user-visible description of the channel.
            channelDescription = "Sample Reminder Notifications";
            channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            channelEnableVibrate = false;
            channelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
            // Initializes NotificationChannel.
            NotificationChannel notificationChannel =
                    new NotificationChannel(channelId, channelName, channelImportance);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(channelEnableVibrate);
            notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            return channelId;
        } else {
            // Returns null for pre-O (26) devices.
            return null;
        }
    }
}
