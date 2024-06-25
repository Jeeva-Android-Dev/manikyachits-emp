package com.mazenet.mani.gurugubera.Activities

import android.app.AlertDialog
import android.app.Notification
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.telephony.TelephonyManager
import android.util.Log
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.mazenet.mani.gurugubera.Alarmreceivers.AlarmReceiver
import com.mazenet.mani.gurugubera.Alarmreceivers.BigTextIntentService
import com.mazenet.mani.gurugubera.Alarmreceivers.GlobalNotificationBuilder
import com.mazenet.mani.gurugubera.Alarmreceivers.NotificationUtil
import com.mazenet.mani.gurugubera.BuildConfig
import com.mazenet.mani.gurugubera.Fragments.Add_customer
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Fragments.Collection.*
import com.mazenet.mani.gurugubera.Fragments.Devices.devices
import com.mazenet.mani.gurugubera.Fragments.Leads.Show_leads
import com.mazenet.mani.gurugubera.Fragments.Reports.ReportsList
import com.mazenet.mani.gurugubera.Fragments.Settings.Settings
import com.mazenet.mani.gurugubera.Fragments.dashboard_fragments.Admin_Dashboard
import com.mazenet.mani.gurugubera.Fragments.dashboard_fragments.BusinessAgent_Dashboard
import com.mazenet.mani.gurugubera.Fragments.dashboard_fragments.Collection_dashboard_Fragment
import com.mazenet.mani.gurugubera.Model.Before_enroll_success_model
import com.mazenet.mani.gurugubera.Model.InstallmentListModel
import com.mazenet.mani.gurugubera.Model.Receiptsuccessmodel
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Services.OfflineService
import com.mazenet.mani.gurugubera.Utilities.BaseActivity
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.offlinedb
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Utilities.Roles
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.squareup.picasso.Picasso


class HomeActivity() : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    fun replacefragment(fragment: BaseFragment) {
        val transaction = manager.beginTransaction()
        transaction.replace(com.mazenet.mani.gurugubera.R.id.frame_container, fragment)
        transaction.addToBackStack("0")
        transaction.commit()
    }
    lateinit var navigation_header_texts: View
    lateinit var ShowCustommerAquiring: ProgressDialog
    lateinit var ShowReceiptUploading: ProgressDialog
    internal var OfflineBroadcastRegistered: Boolean? = false
    lateinit var offline_intent: Intent
    var manager = supportFragmentManager
    lateinit var navigationView: NavigationView
    private val TAG = HomeActivity::class.java.getSimpleName()
    lateinit var frag: BaseFragment
    lateinit var telephonyManager: TelephonyManager
    var upload_count = 0
    //    private var pendingIntent: PendingIntent? = null
//    private var alarmManager: AlarmManager? = null
    var mNotificationManagerCompat: NotificationManagerCompat? = null
    var areNotificationsEnabled: Boolean = false

    private fun openNotificationSettingsForApp() {
        // Links to this app's notification settings.
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("app_package", applicationContext.packageName)
        intent.putExtra("app_uid", applicationContext.applicationInfo.uid)
        applicationContext.startActivity(intent)
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
        val notificationChannelId = NotificationUtil.createNotificationChannel(applicationContext)
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
        val notifyIntent = Intent(applicationContext, HomeActivity::class.java)

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
            applicationContext,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // 4. Create additional Actions (Intents) for the Notification.

        // In our case, we create two additional actions: a Snooze action and a Dismiss action.
        // Snooze Action.
        val snoozeIntent = Intent(applicationContext, BigTextIntentService::class.java)
        snoozeIntent.action = BigTextIntentService.ACTION_SNOOZE

        val snoozePendingIntent = PendingIntent.getService(applicationContext, 0, snoozeIntent, 0)
        val snoozeAction = NotificationCompat.Action.Builder(
            R.drawable.ic_usericon,
            "Snooze",
            snoozePendingIntent
        )
            .build()


        // Dismiss Action.
        val dismissIntent = Intent(applicationContext, BigTextIntentService::class.java)
        dismissIntent.action = BigTextIntentService.ACTION_DISMISS

        val dismissPendingIntent = PendingIntent.getService(applicationContext, 0, dismissIntent, 0)
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
            this@HomeActivity, notificationChannelId!!
        )

        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(notificationCompatBuilder)

        val notification = notificationCompatBuilder
            // BIG_TEXT_STYLE sets title and content for API 16 (4.1 and after).
            .setStyle(bigTextStyle)
            // Title for API <16 (4.0 and below) devices.
            .setContentTitle(mBigContentTitle)
            // Content for API <24 (7.0 and below) devices.
            .setContentText(mContentText)
            .setSmallIcon(R.drawable.sivasakthi_logo)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.ic_usericon
                )
            )
            .setContentIntent(notifyPendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            // Set primary color (important for Wear 2.0 Notifications).
            .setColor(
                ContextCompat.getColor(
                    applicationContext.applicationContext,
                    R.color.colorPrimary
                )
            )

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

        mNotificationManagerCompat!!.notify(Constants.NOTIFICATION_ID, notification)
    }

    fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.addToBackStack("0")
        fragmentTransaction.commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mazenet.mani.gurugubera.R.layout.activity_home)
        setSupportActionBar(toolbar)
        check_attendence()
        d("dbtesthome",Constants.db)
        mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        supportActionBar!!.setTitle(com.mazenet.mani.gurugubera.R.string.app_name)
        val alarmIntent = Intent(this@HomeActivity, AlarmReceiver::class.java)
        offline_intent = Intent(this, OfflineService::class.java)
        var rolename: String
//        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        val imei = telephonyManager.getDeviceId();
//        setPrefsString(Constants.thisdeviceid, imei)
        rolename = getPrefsString(Constants.roletype, "")
        when {
            rolename.equals(Roles.Admin.toString()) -> {
                setPrefsBoolean(Constants.collection, true)
                setPrefsBoolean(Constants.leads, true)
                setPrefsBoolean(Constants.devices, true)
                setPrefsBoolean(Constants.reports, true)
            }
            rolename.equals(Roles.Collection_Agent.toString()) -> {
                setPrefsBoolean(Constants.collection, true)
                setPrefsBoolean(Constants.leads, false)
                setPrefsBoolean(Constants.devices, false)
                setPrefsBoolean(Constants.reports, true)
            }
            rolename.equals(Roles.Business_Agent.toString()) -> {
                setPrefsBoolean(Constants.collection, false)
                setPrefsBoolean(Constants.leads, true)
                setPrefsBoolean(Constants.devices, false)
                setPrefsBoolean(Constants.reports, false)
            }
            rolename.equals(Roles.Employee.toString()) -> {
                setPrefsBoolean(Constants.collection, true)
                setPrefsBoolean(Constants.leads, true)
                setPrefsBoolean(Constants.devices, true)
                setPrefsBoolean(Constants.reports, true)
            }
        }
//        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
////           pendingIntent.cancel();
//        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val interval = 1200
//        alarmManager!!.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis(),
//            interval.toLong(),
//            pendingIntent
//        )


        navigationView =
            findViewById(com.mazenet.mani.gurugubera.R.id.nav_view) as NavigationView
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            com.mazenet.mani.gurugubera.R.string.navigation_drawer_open,
            com.mazenet.mani.gurugubera.R.string.navigation_drawer_close
        )
        val header=navigationView.getHeaderView(0)
        val image = header.findViewById<ImageView>(R.id.img_userimage)
        val path=getPrefsString(Constants.profilePic,"")
        if(!path.isNullOrEmpty())
        {
            Picasso.with(this).load(path).resize(35, 39).onlyScaleDown()
                .placeholder(
                    R.drawable.ic_account_circle
                ).error(R.drawable.ic_account_circle)
                .centerInside().into(image)
        }
        navigation_header_texts = navigationView.getHeaderView(0)
        val nav_menu: Menu?
        val collectionMenu: MenuItem?
        val LeadMenu: MenuItem?
        val Devicemenu: MenuItem?
        val ReportsMenu: MenuItem?

        nav_menu = navigationView.menu
        navigation_header_texts.txt_accesstype.setText(
            getPrefsString(
                Constants.role.toString(),
                ""
            )
        )
        navigation_header_texts.txt_username.setText(
            getPrefsString(
                Constants.username.toString(),
                ""
            )
        )
        if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
            navigation_header_texts.switch_offline.isChecked = false
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorPrimaryDark)));
        } else {
            navigation_header_texts.switch_offline.isChecked = true
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.orange_700)));
        }

        navigation_header_texts.switch_offline.setOnClickListener {
            if (navigation_header_texts.switch_offline.isChecked) {
                showDialog(false)
            } else {
                showDialog(true)
            }
        }
        nav_menu.clear()
        navigationView.inflateMenu(com.mazenet.mani.gurugubera.R.menu.admin_drawer)
        collectionMenu = nav_menu.getItem(2)
        ReportsMenu = nav_menu.getItem(3)
        Devicemenu = nav_menu.getItem(4)
        LeadMenu = nav_menu.getItem(5)
        nav_menu.getItem(8).setTitle("App Version : ${BuildConfig.VERSION_NAME}")

        if (getPrefsBoolean(Constants.collection, false)) {
            collectionMenu.setVisible(true)
        } else {
            collectionMenu.setVisible(false)
        }
        if (getPrefsBoolean(Constants.reports, false)) {
            ReportsMenu.setVisible(true)
        } else {
            ReportsMenu.setVisible(false)
        }
        if (getPrefsBoolean(Constants.leads, false)) {
            LeadMenu.setVisible(true)
        } else {
            LeadMenu.setVisible(false)
        }
        if (getPrefsBoolean(Constants.devices, false)) {
            Devicemenu.setVisible(true)
        } else {
            Devicemenu.setVisible(false)
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        ShowDashboard()

        nav_view.setNavigationItemSelectedListener(this)

    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.mazenet.mani.gurugubera.R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            com.mazenet.mani.gurugubera.R.id.menu_notification -> {
//                openNotificationSettingsForApp()
                areNotificationsEnabled = mNotificationManagerCompat!!.areNotificationsEnabled()
                println("notifi $areNotificationsEnabled")
                if (!areNotificationsEnabled) {
                    // Because the user took an action to create a notification, we create a prompt to let
                    // the user re-enable notifications for this application again.
                    openNotificationSettingsForApp()
                    return true
                } else {
                    generateBigTextStyleNotification()
                    return true
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        when (item.itemId) {

            com.mazenet.mani.gurugubera.R.id.nav_dashboard -> {

                ShowDashboard()
            }


            com.mazenet.mani.gurugubera.R.id.nav_collection -> {
                replacefragment(Collections())
//                supportFragmentManager.inTransaction {
//                    replace(R.id.frame_container, Collections())
//                }
            }
            com.mazenet.mani.gurugubera.R.id.nav_add_customer -> {
                replacefragment(Add_customer())
//                supportFragmentManager.inTransaction {
//                    replace(R.id.frame_container, Collections())
//                }
            }
            com.mazenet.mani.gurugubera.R.id.nav_devices -> {
                replacefragment(devices())
//                supportFragmentManager.inTransaction {
//                    replace(R.id.frame_container, devices())
//                }
            }
            com.mazenet.mani.gurugubera.R.id.nav_leads -> {
                replacefragment(Show_leads())
//                supportFragmentManager.inTransaction {
//                    replace(R.id.frame_container, Show_leads())
//                }
            }
            com.mazenet.mani.gurugubera.R.id.nav_reports -> {
                replacefragment(ReportsList())
//                supportFragmentManager.inTransaction {
//                    replace(R.id.frame_container, CominSoon())
//                }
            }
            com.mazenet.mani.gurugubera.R.id.nav_settings -> {
                replacefragment(Settings())
//                supportFragmentManager.inTransaction {
//                    replace(R.id.frame_container, Settings())
//                }
            }
            com.mazenet.mani.gurugubera.R.id.nav_logout -> {
                logout()
            }


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun ShowDashboard() {


        when {
            getPrefsString(
                Constants.roletype,
                ""
            ).equals(Roles.Admin.toString()) -> frag = Admin_Dashboard()

            getPrefsString(
                Constants.roletype,
                ""
            ).equals(Roles.Collection_Agent.toString()) ->

                frag = Collection_dashboard_Fragment()

            getPrefsString(
                Constants.roletype,
                ""
            ).equals(Roles.Business_Agent.toString()) ->

                frag = BusinessAgent_Dashboard()
        }
        //replacefragment(frag)
    }

    fun logout() {
        if (BaseUtils.offlinedb.OfflineReceiptSize() > 0 || BaseUtils.offlinedb.getOfflineAddCustomer().size >0) {
            if(BaseUtils.offlinedb.OfflineReceiptSize() > 0 ) {
                println("off rec size ${BaseUtils.offlinedb.OfflineReceiptSize()}")
                showReceiptuploaddilog("logout")
            }
            if(offlinedb.getOfflineAddCustomer().size >0) {
                println("off add customer size : ${offlinedb.getOfflineAddCustomer().size}")
                showAddCustomeruploaddilog("logout")
            }
        } else {
            logoutdb()
        }
    }

    fun logoutdb() {

        BaseUtils.offlinedb.deleteCustomersTable()
        BaseUtils.offlinedb.deleteOfflineReceiptsTable()
        BaseUtils.offlinedb.deleteAddCustomer()

        setPrefsString(Constants.application_logged_in, "no")
        setPrefsString(Constants.db,"null")
        val intent = Intent(this@HomeActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val fragmentList = manager.fragments
            if (fragmentList.size > 0) {
                val fragments = manager
                if (fragments.getBackStackEntryCount() > 1) {
                    // We have fragments on the backstack that are poppable
                    val f = supportFragmentManager.findFragmentById(R.id.frame_container)
                    if (f is ReceiptPreview) {
                        (f as ReceiptPreview).onBackPressed()
                    } else if (f is ReceiptFragment) {
                        (f as ReceiptFragment).onBackPressed()
                    } else if (f is CashinHand_Fragment) {
                        (f as CashinHand_Fragment).onBackPressed()
                    } else if (f is ViewReceiptPreview) {
                        (f as ViewReceiptPreview).onBackPressed()
                    } else if (f is Admin_Dashboard) {
                        finishscreen()
                    } else if (f is BusinessAgent_Dashboard) {
                        finishscreen()
                    } else if (f is Collection_dashboard_Fragment) {
                        finishscreen()
                    } else {
                        fragments.popBackStackImmediate()
                    }
                } else {
                    finishscreen()
                }
            }
        }
    }

    fun finishscreen() {
        val builder = AlertDialog.Builder(
            this@HomeActivity,
            com.mazenet.mani.gurugubera.R.style.MyErrorDialogTheme
        )
        builder.setCancelable(false)
        builder.setTitle("Caution")
            .setIcon(resources.getDrawable(R.drawable.ic_info_red))
            .setMessage("Do you want exit the application?")
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("Yes") { dialog, which ->
                dialog.dismiss()
                supportFinishAfterTransition()
            }
        builder.create().show()
    }

    override fun onResume() {

        if (OfflineBroadcastRegistered!!.equals(false)) {
            registerReceiver(
                broadcastBufferReceiver, IntentFilter(
                    "buferedoffline"
                )
            )
            OfflineBroadcastRegistered = true
        }
        super.onResume()
    }

    override fun onDestroy() {
        if (OfflineBroadcastRegistered!!) {
            unregisterReceiver(broadcastBufferReceiver)
            stopService(offline_intent)
            OfflineBroadcastRegistered = false
        }
//        alarmManager!!.cancel(pendingIntent);

        super.onDestroy()
    }

    private val broadcastBufferReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, bufferIntent: Intent) {
            showCustomersbuffering(bufferIntent)
        }
    }

    private fun showCustomersbuffering(bufferIntent: Intent) {
        val customerBuffer = bufferIntent.getStringExtra("Acquiring customers")?.toInt()

        when (customerBuffer) {
            0 -> {
                CustomerAquring()
            }
            1 -> {
                navigation_header_texts.switch_offline.isChecked = true
                setPrefsBoolean(Constants.IS_ONLINE, false)
                ShowCustommerAquiring.dismiss()
                supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.orange_700)));
            }
            2 -> {
                ShowCustommerAquiring.dismiss()
            }
        }
    }

    private fun CustomerAquring() {
        ShowCustommerAquiring = ProgressDialog.show(
            this@HomeActivity,
            resources.getString(com.mazenet.mani.gurugubera.R.string.buffertitlecust),
            resources.getString(com.mazenet.mani.gurugubera.R.string.bufferstring),
            true
        )
    }

    private fun Uploading() {
        ShowReceiptUploading = ProgressDialog.show(
            this@HomeActivity,
            resources.getString(com.mazenet.mani.gurugubera.R.string.buffer_uploadingl),
            resources.getString(com.mazenet.mani.gurugubera.R.string.nulls),
            true
        )
    }

    fun showDialog(Status: Boolean) {
        val builder = AlertDialog.Builder(
            this@HomeActivity,
            com.mazenet.mani.gurugubera.R.style.MyDialogTheme
        )
        builder.setCancelable(false)
        if (Status.equals(false)) {
            builder.setTitle("Offline ")
                .setMessage("Do you wish to Go offline Mode")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    navigation_header_texts.switch_offline.isChecked = false
                    setPrefsBoolean(Constants.IS_ONLINE, true)
                    supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorPrimaryDark)));
                }
                .setPositiveButton("OK") { dialog, which ->
                    if (checkForInternet()) {
                        startService(offline_intent)
                    } else {
                        toast(resources.getString(R.string.nointernet))
                        navigation_header_texts.switch_offline.isChecked = false
                        setPrefsBoolean(Constants.IS_ONLINE, true)
                        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorPrimaryDark)));
                    }

                    dialog.dismiss()
                }

        } else {
            builder.setTitle("Online ")
                .setMessage("Do you wish to go Online?.")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    navigation_header_texts.switch_offline.isChecked = true
                    setPrefsBoolean(Constants.IS_ONLINE, false)
                    supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.orange_700)));
                }
                .setPositiveButton("OK") { dialog, which ->

                    if (BaseUtils.offlinedb.OfflineReceiptSize() > 0 || offlinedb.getOfflineAddCustomer().size > 0) {
                        if(BaseUtils.offlinedb.OfflineReceiptSize() > 0 ) {
                            println("off rec size ${BaseUtils.offlinedb.OfflineReceiptSize()}")
                            showReceiptuploaddilog("upload")

                        }
                        if(offlinedb.getOfflineAddCustomer().size > 0) {
                            println("off add customer size : ${offlinedb.getOfflineAddCustomer().size}")
                            Log.d("Customer_list",offlinedb.getOfflineAddCustomer().size.toString())
                            showAddCustomeruploaddilog("upload")
                        }
                    } else {
                        navigation_header_texts.switch_offline.isChecked = false
                        setPrefsBoolean(Constants.IS_ONLINE, true)
                        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorPrimaryDark)));
                    }
                    dialog.dismiss()

                }
        }

        builder.create().show()
    }

    fun showReceiptuploaddilog(action: String) {
        val builder = AlertDialog.Builder(
            this@HomeActivity,
            com.mazenet.mani.gurugubera.R.style.MyDialogTheme
        )
        builder.setCancelable(false)
        if (checkForInternet()) {
            builder.setMessage("Upload offline receipts")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    showCannotgoOnline("upload")
                }
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()

                    start_uploading(action)
                }
            builder.create().show()
        } else {
            showCannotgoOnline("internet")
        }


    }

    fun showAddCustomeruploaddilog(action: String) {
        val builder = AlertDialog.Builder(
            this@HomeActivity,
            com.mazenet.mani.gurugubera.R.style.MyDialogTheme
        )
        builder.setCancelable(false)
        if (checkForInternet()) {
            builder.setMessage("Upload offline customer")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    showCannotgoOnline("upload")
                }
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    start_uploading_customer(action)
                    //start_uploading(action)
                }
            builder.create().show()
        } else {
            showCannotgoOnline("internet")
        }


    }

    private fun start_uploading_customer(action: String) {
        println("uploading customer")
        upload_count = 0
        for(i in 0 until offlinedb.getOfflineAddCustomer().size){
            val value: java.util.HashMap<String, Any> = offlinedb.getOfflineAddCustomer()[i]
            value["db"]= getPrefsString(Constants.db,"")

            val addleadservice = RetrofitBuilder.buildservice(ICallService::class.java)
            val addlead_req = addleadservice.add_user(value)
            addlead_req.enqueue(object : Callback<successmsgmodel> {
                override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                    toast(t.toString())
                    hideProgressDialog()
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<successmsgmodel>,
                    response: Response<successmsgmodel>
                ) {
                    if (response.isSuccessful) {
                        hideProgressDialog()
                        if (response.code().equals(200)) {
                            if (response.body()!!.getStatus().equals("Success")) {
                                upload_count+=1
                                if (upload_count >= (BaseUtils.offlinedb.getOfflineAddCustomer().size) && action.equals(
                                        "logout"
                                    )
                                ) {
                                    if (upload_count > 0) {
                                        toast("${upload_count.toString()} Customers uploaded")
                                    } else {
                                        toast("${upload_count.toString()} Customers uploaded")
                                    }
                                    logoutdb()
                                } else if (upload_count >= (BaseUtils.offlinedb.getOfflineAddCustomer().size) && action.equals(
                                        "upload"
                                    )
                                ) {
                                    if (upload_count > 0) {
                                        toast("${upload_count.toString()} Customers uploaded")
                                    } else {
                                        toast("${upload_count.toString()} Customers uploaded")
                                    }
                                    offlinedb.deleteAddCustomer()
                                    navigation_header_texts.switch_offline.isChecked = false
                                    setPrefsBoolean(Constants.IS_ONLINE, true)
                                    supportActionBar!!.setBackgroundDrawable(
                                        ColorDrawable(
                                            resources.getColor(R.color.colorPrimaryDark)
                                        )
                                    );
                                }
                            } else {
                                toast(response.body()!!.getMsg()!!)
                            }
                        } else {
                            System.out.println("no show")
                        }
                    }
                    else {
                        hideProgressDialog()
                    }
                }
            })
        }
    }

    fun showCannotgoOnline(action: String) {
        val builder = AlertDialog.Builder(
            this@HomeActivity,
            com.mazenet.mani.gurugubera.R.style.MyDialogTheme
        )
        builder.setCancelable(false)
        if (action.equals("internet")) {
            builder.setTitle("Error")
                .setMessage("Turn on Internet connection to go online")
                .setNegativeButton("Ok") { dialog, which ->
                    dialog.dismiss()
                    navigation_header_texts.switch_offline.isChecked = true
                    setPrefsBoolean(Constants.IS_ONLINE, false)
                    supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.orange_700)));
                }

        } else if (action.equals("upload")) {
            builder.setTitle("Error")
                .setMessage("You should upload Offline receipts before going Online again. Do you want to upload receipts now?")
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                    navigation_header_texts.switch_offline.isChecked = true
                    setPrefsBoolean(Constants.IS_ONLINE, false)
                    supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.orange_700)));
                }
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    start_uploading(action)
                }

        }
        builder.create().show()

    }

    fun start_uploading(action: String) {
        println("uploading receipts")
        upload_count = 0
        val offlinereceipts = BaseUtils.offlinedb.getOFf_receipts_list()
        for (i in offlinereceipts) {
            var installmentdetailslist = ArrayList<InstallmentListModel>()

            val rec_instmnt_details = BaseUtils.offlinedb.getOfflineinstalllist(i.of_recno)
            if (rec_instmnt_details.size > 0) {
                for (instamnt_nos in rec_instmnt_details) {
                    val instalmentmodel = InstallmentListModel()
                    instalmentmodel.payableamount = instamnt_nos.of_payableamount
                    instalmentmodel.biddingid = instamnt_nos.of_biddingid
                    instalmentmodel.auctionno = instamnt_nos.of_auctionno
                    instalmentmodel.pendingdays = instamnt_nos.of_pendingdays
                    instalmentmodel.penalty = instamnt_nos.of_penaltyamnt
                    instalmentmodel.discontondue = instamnt_nos.of_discountondue
                    instalmentmodel.bonus = instamnt_nos.of_bonusamnt
                    instalmentmodel.Receiptamount = instamnt_nos.of_receiptamount
                    installmentdetailslist.add(instalmentmodel)
                }
                println("uploading ${i.of_recno}")
            }


            add_receipt(
                i.of_recno,
                i.of_offrecno,
                i.of_enrolid,
                i.of_receiptdate,
                i.of_otherbranch,
                i.of_customerid,
                i.of_groupid,
                i.of_ticketno,
                i.of_receivedamount,
                i.of_paytypeid,
                i.of_debitto,
                i.of_chequeno,
                i.of_chequedate,
                i.of_chequebranchname,
                i.of_transno,
                i.of_transdate,
                i.of_receipttime,
                i.of_banknameid,
                i.of_remarks,
                i.of_advance,
                i.of_advanceonly,
                installmentdetailslist,
                action
            )
        }

    }

    private fun add_receipt(

        recno: String,
        offrecno: String,
        enrolid: String,
        receiptdate: String,
        other_branch: String,
        customer_id: String,
        groupid: String,
        ticketno: String,
        receiptamount: String,
        paytypeid: String,
        debitto: String,
        chequeno: String,
        cheque_date: String,
        branch_name: String,
        transaction_no: String,
        transaction_date: String,
        receipt_time: String,
        bankname: String,
        remarks: String,
        advance: String,
        advanceOnly: String,
        installments: ArrayList<InstallmentListModel>,
        action: String
    ) {
        println("adv only $advanceOnly")
        if (advanceOnly.equals("true")) {

            showProgressDialog()
            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("enrollment_id", enrolid)
            loginparameters.put("offline_receipt_no", "")
            loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
            loginparameters.put("other_branch", other_branch)
            loginparameters.put("customer_id", customer_id)
            loginparameters.put("receipt_date", receiptdate)
            loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
            loginparameters.put("employee_branch_id", getPrefsString(Constants.branches, ""))
            loginparameters.put("adjust_id", "0")
            loginparameters.put("adv_receipt_amount", advance)
            loginparameters.put("payment_type_id", paytypeid)
            loginparameters.put("debit_to", debitto)
            loginparameters.put("cheque_no", chequeno)
            loginparameters.put("cheque_date", cheque_date)
            loginparameters.put("cheque_clear_return_date", "0")
            loginparameters.put("bank_name_id", bankname)
            loginparameters.put("branch_name", branch_name)
            loginparameters.put("transaction_no", transaction_no)
            loginparameters.put("transaction_date", transaction_date)
            loginparameters.put("receipt_time", receipt_time)
            loginparameters.put("printed", "0")
            loginparameters.put("remarks", remarks)
            loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
            loginparameters.put("db",getPrefsString(Constants.db,""))
            if(enrolid=="") {
                val param = HashMap<String,String>()
                param.put("branch_id", getPrefsString(Constants.branches, ""))

                param.put("customer_id", customer_id)
                param.put("received_date", receiptdate)
                param.put("amount", advance)
                param.put("payment_type_id", paytypeid)
                param.put("db",getPrefsString(Constants.db,""))

                val LeadListRequest = getleadslist.before_enroll(param)
                LeadListRequest.enqueue(object : Callback<Before_enroll_success_model> {
                    override fun onFailure(call: Call<Before_enroll_success_model>, t: Throwable) {
                        hideProgressDialog()
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<Before_enroll_success_model>, response: Response<Before_enroll_success_model>
                    ) {
                        hideProgressDialog()
                        when {
                            response.isSuccessful -> {
                                when {
                                    response.code().equals(200) -> {
                                        BaseUtils.offlinedb.update_uploaded_receipts(recno)
                                        upload_count += 1

                                        if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "logout"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            logoutdb()
                                        } else if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "upload"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            offlinedb.deleteOfflineReceiptsTable()
                                            navigation_header_texts.switch_offline.isChecked = false
                                            setPrefsBoolean(Constants.IS_ONLINE, true)
                                            supportActionBar!!.setBackgroundDrawable(
                                                ColorDrawable(
                                                    resources.getColor(R.color.colorPrimaryDark)
                                                )
                                            );
                                        }
                                    }
                                    else -> {
                                        toast(response.message().toString())
                                    }
                                }
                            }
                        }
                    }
                })

            }
            else{
                val LeadListRequest = getleadslist.add_adv_receipt(loginparameters)
                LeadListRequest.enqueue(object : Callback<Receiptsuccessmodel> {
                    override fun onFailure(call: Call<Receiptsuccessmodel>, t: Throwable) {
                        hideProgressDialog()
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<Receiptsuccessmodel>, response: Response<Receiptsuccessmodel>
                    ) {
                        hideProgressDialog()
                        when {
                            response.isSuccessful -> {
                                when {
                                    response.code().equals(200) -> {
                                        BaseUtils.offlinedb.update_uploaded_receipts(recno)
                                        upload_count += 1

                                        if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "logout"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            logoutdb()
                                        } else if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "upload"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            offlinedb.deleteOfflineReceiptsTable()
                                            navigation_header_texts.switch_offline.isChecked = false
                                            setPrefsBoolean(Constants.IS_ONLINE, true)
                                            supportActionBar!!.setBackgroundDrawable(
                                                ColorDrawable(
                                                    resources.getColor(R.color.colorPrimaryDark)
                                                )
                                            );
                                        }
                                    }
                                    else -> {
                                        toast(response.message().toString())
                                    }
                                }
                            }
                        }
                    }
                })
            }
        }
        else
        {

            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("enrollment_id", enrolid)
            Log.d("enrolid",enrolid)
            loginparameters.put("offline_receipt_no", offrecno)
            loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
            loginparameters.put("other_branch", other_branch)
            loginparameters.put("customer_id", customer_id)
            loginparameters.put("receipt_date", receiptdate)
            loginparameters.put("group_id", groupid)
            loginparameters.put("ticket_no", ticketno)
            loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
            loginparameters.put("employee_branch_id", getPrefsString(Constants.branches, ""))
            loginparameters.put("adjust_id", "0")
            loginparameters.put("amount", receiptamount)
            loginparameters.put("payment_type_id", paytypeid)
            loginparameters.put("debit_to", debitto)
            loginparameters.put("cheque_no", chequeno)
            loginparameters.put("cheque_date", cheque_date)
            loginparameters.put("cheque_clear_return_date", "0")
            loginparameters.put("bank_name_id", bankname)
            loginparameters.put("branch_name", branch_name)
            loginparameters.put("transaction_no", transaction_no)
            loginparameters.put("transaction_date", transaction_date)
            loginparameters.put("receipt_time", receipt_time)
            loginparameters.put("printed", "0")
            loginparameters.put("remarks", remarks)
            loginparameters.put("adv_receipt_amount", advance)
            loginparameters.put("db",getPrefsString(Constants.db,""))
            loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
            for (i in installments.indices) {
                val payable = installments.get(i).payableamount.toInt()
                if (payable > 0) {
                    loginparameters.put(
                        "Installments[$i][auction_id]",
                        installments.get(i).biddingid
                    )
                    loginparameters.put(
                        "Installments[$i][installment_no]",
                        installments.get(i).auctionno
                    )
                    loginparameters.put(
                        "Installments[$i][pending_days]",
                        installments.get(i).pendingdays
                    )
//                    loginparameters.put("Installments[$i][penalty_inst_wise]", installments.get(i).penalty)
//                    loginparameters.put("Installments[$i][bonus_inst_wise]", installments.get(i).bonus)
                    loginparameters.put(
                        "Installments[$i][penalty_inst_wise]",
                        installments.get(i).nearpenalty
                    )
                    val payableamnt =
                        (Constants.isEmtytoZero(installments.get(i).installment).toInt()) - (Constants.isEmtytoZero(
                            installments.get(i).bonus
                        ).toInt())
                    println(
                        "receiptamnt ${installments.get(i).Receiptamount} instlment ${installments.get(
                            i
                        ).installment} payable $payableamnt"
                    )
                    if ((Constants.isEmtytoZero(installments.get(i).Receiptamount).toInt()) < payableamnt) {
                        installments.get(i).bonus = "0"
                        loginparameters.put("Installments[$i][bonus_inst_wise]", "0")
                    } else {
                        loginparameters.put(
                            "Installments[$i][bonus_inst_wise]",
                            installments.get(i).bonus
                        )
                    }
                    loginparameters.put(
                        "Installments[$i][discount_inst_wise]",
                        installments.get(i).discontondue
                    )
                    loginparameters.put(
                        "Installments[$i][received_amount]",
                        installments.get(i).Receiptamount
                    )
                }
            }

            if(enrolid ==""){
                val param = HashMap<String,String>()
                param.put("branch_id", getPrefsString(Constants.branches, ""))

                param.put("customer_id", customer_id)
                param.put("received_date", receiptdate)
                param.put("amount", advance)
                param.put("payment_type_id", paytypeid)
                loginparameters.put("db",getPrefsString(Constants.db,""))
                val LeadListRequest = getleadslist.before_enroll(param)
                LeadListRequest.enqueue(object : Callback<Before_enroll_success_model> {
                    override fun onFailure(call: Call<Before_enroll_success_model>, t: Throwable) {
                        hideProgressDialog()
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<Before_enroll_success_model>, response: Response<Before_enroll_success_model>
                    ) {
                        hideProgressDialog()
                        when {
                            response.isSuccessful -> {
                                when {
                                    response.code().equals(200) -> {
                                        BaseUtils.offlinedb.update_uploaded_receipts(recno)
                                        upload_count += 1

                                        if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "logout"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            logoutdb()
                                        } else if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "upload"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            offlinedb.deleteOfflineReceiptsTable()
                                            navigation_header_texts.switch_offline.isChecked = false
                                            setPrefsBoolean(Constants.IS_ONLINE, true)
                                            supportActionBar!!.setBackgroundDrawable(
                                                ColorDrawable(
                                                    resources.getColor(R.color.colorPrimaryDark)
                                                )
                                            );
                                        }
                                    }
                                    else -> {
                                        toast(response.message().toString())
                                    }
                                }
                            }
                        }
                    }
                })

            }
            else{
                val LeadListRequest = getleadslist.add_receipt(loginparameters)
                LeadListRequest.enqueue(object : Callback<Receiptsuccessmodel> {
                    override fun onFailure(call: Call<Receiptsuccessmodel>, t: Throwable) {
                        hideProgressDialog()
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<Receiptsuccessmodel>, response: Response<Receiptsuccessmodel>
                    ) {
                        hideProgressDialog()
                        when {
                            response.isSuccessful -> {
                                when {
                                    response.code().equals(200) -> {
                                        BaseUtils.offlinedb.update_uploaded_receipts(recno)
                                        upload_count += 1

                                        if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "logout"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            logoutdb()
                                        } else if (upload_count >= (BaseUtils.offlinedb.OfflineReceiptSize()) && action.equals(
                                                "upload"
                                            )
                                        ) {
                                            if (upload_count > 0) {
                                                toast("${upload_count.toString()} receipts uploaded")
                                            } else {
                                                toast("${upload_count.toString()} receipt uploaded")
                                            }
                                            offlinedb.deleteOfflineReceiptsTable()
                                            navigation_header_texts.switch_offline.isChecked = false
                                            setPrefsBoolean(Constants.IS_ONLINE, true)
                                            supportActionBar!!.setBackgroundDrawable(
                                                ColorDrawable(
                                                    resources.getColor(R.color.colorPrimaryDark)
                                                )
                                            );
                                        }
                                    }
                                    else -> {
                                        toast(response.message().toString())
                                    }
                                }
                            }
                        }
                    }
                })
            }

        }
    }

    fun check_attendence() {
        val cashsettlementService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
        loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
        loginparameters.put("attendance_date", BaseUtils.CurrentDate_ymd())
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val add_cashsettle = cashsettlementService.get_employee_attendence(loginparameters)
        add_cashsettle.enqueue(object : Callback<successmsgmodel> {
            override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                toast(t.toString())

                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<successmsgmodel>,
                response: Response<successmsgmodel>
            ) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        if (response.body()!!.getStatus().equals("Success")) {
                            if (response.body()!!.getMsg().equals("0", ignoreCase = true)) {
                                replacefragment(attendence())


                            } else {
                                println("howdy")
                            }
                        } else {
                            hideProgressDialog()
                            toast(response.body()!!.getMsg()!!)
                        }
                    } else {
                        System.out.println("no show")
                    }
                } else {

                }
            }
        })
    }
}

