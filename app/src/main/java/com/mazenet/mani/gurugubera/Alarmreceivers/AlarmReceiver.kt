package com.mazenet.mani.gurugubera.Alarmreceivers

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants.NOTIFICATION_ID


/**
 * Created by admin on 15/10/2016.
 */
class AlarmReceiver : BroadcastReceiver() {

    internal var pref: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null
    lateinit var mContext: Context
    private val mNotificationManagerCompat: NotificationManagerCompat? = null
    internal var TAG = "Alrmer"

    override fun onReceive(arg0: Context, arg1: Intent) {

        mContext = arg0
        val areNotificationsEnabled = mNotificationManagerCompat!!.areNotificationsEnabled()

        if (!areNotificationsEnabled) {
            // Because the user took an action to create a notification, we create a prompt to let
            // the user re-enable notifications for this application again.
            openNotificationSettingsForApp()
            return
        }
    }

    private fun openNotificationSettingsForApp() {
        // Links to this app's notification settings.
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent . putExtra ("app_package", mContext.packageName)
        intent.putExtra("app_uid", mContext.applicationInfo.uid)
        mContext.startActivity(intent)

    }

    private fun generateBigTextStyleNotification() {

        Log.d(TAG, "generateBigTextStyleNotification()")

        // Main steps for building a BIG_TEXT_STYLE notification:
        //      0. Get your data
        //      1. Create/Retrieve Notification Channel for O and beyond devices (26+)
        //      2. Build the BIG_TEXT_STYLE
        //      3. Set up main Intent for notification
        //      4. Create additional Actions for the Notification
        //      5. Build and issue the notification

        // 0. Get your data (everything unique per Notification).
        //        MockDatabase.BigTextStyleReminderAppData bigTextStyleReminderAppData =
        //                MockDatabase.getBigTextStyleData();

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        val notificationChannelId = NotificationUtil.createNotificationChannel(mContext)
        val mContentTitle = "Don't forget to..."
        // Content for API <24 (4.0 and below) devices.
        val mContentText = "Feed Dogs and check garage!"
        val mPriority = NotificationCompat.PRIORITY_DEFAULT

        // BigText Style Notification values:
        val mBigContentTitle = "Don't forget to..."
        val mBigText =
            "... feed the dogs before you leave for work, and check the garage to " + "make sure the door is closed."
        val mSummaryText = "Dogs and Garage"

        // 2. Build the BIG_TEXT_STYLE.
        val bigTextStyle = NotificationCompat.BigTextStyle()
            // Overrides ContentText in the big form of the template.
            .bigText(mBigText)
            // Overrides ContentTitle in the big form of the template.
            .setBigContentTitle(mBigContentTitle)
            // Summary line after the detail section in the big form of the template.
            // Note: To improve readability, don't overload the user with info. If Summary Text
            // doesn't add critical information, you should skip it.
            .setSummaryText(mSummaryText)


        // 3. Set up main Intent for notification.
        val notifyIntent = Intent(mContext, HomeActivity::class.java)

        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // For the BIG_TEXT_STYLE notification, we will consider the activity launched by the main
        // Intent as a special activity, so we will follow option 2.

        // For an example of option 1, check either the MESSAGING_STYLE or BIG_PICTURE_STYLE
        // examples.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        // Sets the Activity to start in a new, empty task
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val notifyPendingIntent = PendingIntent.getActivity(
            mContext,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // 4. Create additional Actions (Intents) for the Notification.

        // In our case, we create two additional actions: a Snooze action and a Dismiss action.
        // Snooze Action.
        val snoozeIntent = Intent(mContext, BigTextIntentService::class.java)
        snoozeIntent.action = BigTextIntentService.ACTION_SNOOZE

        val snoozePendingIntent = PendingIntent.getService(mContext, 0, snoozeIntent, 0)
        val snoozeAction = NotificationCompat.Action.Builder(
            R.drawable.ic_usericon,
            "Snooze",
            snoozePendingIntent
        )
            .build()


        // Dismiss Action.
        val dismissIntent = Intent(mContext, BigTextIntentService::class.java)
        dismissIntent.action = BigTextIntentService.ACTION_DISMISS

        val dismissPendingIntent = PendingIntent.getService(mContext, 0, dismissIntent, 0)
        val dismissAction = NotificationCompat.Action.Builder(
            R.drawable.ic_usericon,
            "Dismiss",
            dismissPendingIntent
        )
            .build()


        // 5. Build and issue the notification.

        // Because we want this to be a new notification (not updating a previous notification), we
        // create a new Builder. Later, we use the same global builder to get back the notification
        // we built here for the snooze action, that is, canceling the notification and relaunching
        // it several seconds later.

        // Notification Channel Id is ignored for Android pre O (26).
        val notificationCompatBuilder = NotificationCompat.Builder(
            mContext, notificationChannelId!!
        )

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder)

        val notification = notificationCompatBuilder
            // BIG_TEXT_STYLE sets title and content for API 16 (4.1 and after).
            .setStyle(bigTextStyle)
            // Title for API <16 (4.0 and below) devices.
            .setContentTitle(mBigContentTitle)
            // Content for API <24 (7.0 and below) devices.
            .setContentText(mContentText)
            .setSmallIcon(R.drawable.ic_launcher)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    mContext.resources,
                    R.drawable.ic_usericon
                )
            )
            .setContentIntent(notifyPendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            // Set primary color (important for Wear 2.0 Notifications).
            .setColor(ContextCompat.getColor(mContext.applicationContext, R.color.colorPrimary))

            // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
            // devices and all Wear devices. If you have more than one notification and
            // you prefer a different summary notification, set a group key and create a
            // summary notification via
            // .setGroupSummary(true)
            // .setGroup(GROUP_KEY_YOUR_NAME_HERE)

            .setCategory(Notification.CATEGORY_REMINDER)

            // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
            // 'importance' which is set in the NotificationChannel. The integers representing
            // 'priority' are different from 'importance', so make sure you don't mix them.
            .setPriority(mPriority)

            // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
            // visibility is set in the NotificationChannel.
            .setVisibility(mPriority)

            // Adds additional actions specified above.
            .addAction(snoozeAction)
            .addAction(dismissAction)

            .build()

        mNotificationManagerCompat!!.notify(NOTIFICATION_ID, notification)
    }

}
