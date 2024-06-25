/*
Copyright 2016 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.mazenet.mani.gurugubera.Alarmreceivers

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants
import java.util.concurrent.TimeUnit

/**
 * Asynchronously handles snooze and dismiss actions for reminder app (and active Notification).
 * Notification for for reminder app uses BigTextStyle.
 */
class BigTextIntentService : IntentService("BigTextIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent(): " + intent!!)

        if (intent != null) {
            val action = intent.action
            if (ACTION_DISMISS == action) {
                handleActionDismiss()
            } else if (ACTION_SNOOZE == action) {
                handleActionSnooze()
            }
        }
    }

    /**
     * Handles action Dismiss in the provided background thread.
     */
    private fun handleActionDismiss() {
        Log.d(TAG, "handleActionDismiss()")

        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        notificationManagerCompat.cancel(Constants.NOTIFICATION_ID)
    }

    /**
     * Handles action Snooze in the provided background thread.
     */
    private fun handleActionSnooze() {
        Log.d(TAG, "handleActionSnooze()")

        // You could use NotificationManager.getActiveNotifications() if you are targeting SDK 23
        // and above, but we are targeting devices with lower SDK API numbers, so we saved the
        // builder globally and get the notification back to recreate it later.

        var notificationCompatBuilder: NotificationCompat.Builder? =
            GlobalNotificationBuilder.getNotificationCompatBuilderInstance()

        // Recreate builder from persistent state if app process is killed
        if (notificationCompatBuilder == null) {
            // Note: New builder set globally in the method
            notificationCompatBuilder = recreateBuilderWithBigTextStyle()
        }

        val notification: Notification?
        notification = notificationCompatBuilder.build()


        if (notification != null) {
            val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)

            notificationManagerCompat.cancel(Constants.NOTIFICATION_ID)

            try {
                Thread.sleep(SNOOZE_TIME)
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }

            notificationManagerCompat.notify(Constants.NOTIFICATION_ID, notification)
        }

    }

    /*
     * This recreates the notification from the persistent state in case the app process was killed.
     * It is basically the same code for creating the Notification from MainActivity.
     */
    private fun recreateBuilderWithBigTextStyle(): NotificationCompat.Builder {

        // Main steps for building a BIG_TEXT_STYLE notification (for more detailed comments on
        // building this notification, check MainActivity.java)::
        //      0. Get your data
        //      1. Build the BIG_TEXT_STYLE
        //      2. Set up main Intent for notification
        //      3. Create additional Actions for the Notification
        //      4. Build and issue the notification

        // 0. Get your data

        val notificationChannelId = NotificationUtil.createNotificationChannel(this)
        val mContentTitle = "Don't forget to..."
        // Content for API <24 (4.0 and below) devices.
        val mContentText = "Feed Dogs and check garage!"
        val mPriority = NotificationCompat.PRIORITY_DEFAULT

        // BigText Style Notification values:
        val mBigContentTitle = "Don't forget to..."
        val mBigText =
            "... feed the dogs before you leave for work, and check the garage to " + "make sure the door is closed."
        val mSummaryText = "Dogs and Garage"
        // 1. Build the BIG_TEXT_STYLE
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(mBigText)
            .setBigContentTitle(mBigContentTitle)
            .setSummaryText(mSummaryText)


        // 2. Set up main Intent for notification
        val notifyIntent = Intent(this, HomeActivity::class.java)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val notifyPendingIntent = PendingIntent.getActivity(
            this,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // 3. Create additional Actions (Intents) for the Notification

        // Snooze Action
        val snoozeIntent = Intent(this, BigTextIntentService::class.java)
        snoozeIntent.action = BigTextIntentService.ACTION_SNOOZE

        val snoozePendingIntent = PendingIntent.getService(this, 0, snoozeIntent, 0)
        val snoozeAction = NotificationCompat.Action.Builder(
            R.drawable.ic_usericon,
            "Snooze",
            snoozePendingIntent
        )
            .build()


        // Dismiss Action
        val dismissIntent = Intent(this, BigTextIntentService::class.java)
        dismissIntent.action = BigTextIntentService.ACTION_DISMISS

        val dismissPendingIntent = PendingIntent.getService(this, 0, dismissIntent, 0)
        val dismissAction = NotificationCompat.Action.Builder(
            R.drawable.ic_usericon,
            "Dismiss",
            dismissPendingIntent
        )
            .build()

        // 4. Build and issue the notification
        val notificationCompatBuilder = NotificationCompat.Builder(applicationContext)

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder)

        notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentTitle(mContentTitle)
            .setContentText(mContentText)
            .setSmallIcon(R.drawable.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_usericon
                )
            )
            .setContentIntent(notifyPendingIntent)
            .setColor(resources.getColor(R.color.colorPrimary))
            .setCategory(Notification.CATEGORY_REMINDER)
            .setPriority(Notification.PRIORITY_HIGH)
            .setVisibility(Notification.VISIBILITY_PUBLIC)
            .addAction(snoozeAction)
            .addAction(dismissAction)

        return notificationCompatBuilder
    }

    companion object {

        private val TAG = "BigTextService"

        val ACTION_DISMISS = "com.example.android.wearable.wear.wearnotifications.handlers.action.DISMISS"
        val ACTION_SNOOZE = "com.example.android.wearable.wear.wearnotifications.handlers.action.SNOOZE"

        private val SNOOZE_TIME = TimeUnit.SECONDS.toMillis(5)
    }
}
