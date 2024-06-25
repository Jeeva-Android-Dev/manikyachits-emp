package com.mazenet.mani.gurugubera.Utilities

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import java.text.NumberFormat
import java.util.*

object Constants {


    //Preferences variables
    var db = "null"
    val branchName = "branch"
    val profilePic = "pic"
    val UserPref = "UserPreferences"
    val IS_ONLINE = "isonline"
    val First_time = "firsttime"
    val DAYCHECK_COLL = "DAYCHECKCOLL"
    val WEEKCHECK_COLL = "WEEKCHECK_COLL"
    val online_cache_date = "cache_date"
    val daterange_from = "daterange_from"
    val daterange_to = "daterange_to"
    val dayclose_date = "dayclose_date"
    val showing_online_view_receipts = "showing_online"
    val role = "Roles"
    val email = "email"
    val password = "password"
    val collection = "collection"
    val leads = "laeds"
    val devices = "devices"
    val reports = "reports"
    val cashinhand = "cashinhand"
    val teanant_name = "teanant_name"
    val tenant_gst = "tenant_gst"
    val tenant_address1 = "tenant_address1"
    val tenant_address2 = "tenant_address2"
    val tenant_state = "tenant_state"
    val tenant_phone = "tenant_phone"
    val tenant_mobile = "tenant_mobile"
    val loggeduser = "loggeduser"//login variables
    val username = "username"
    val token = "token"
    val branches = "branches"
    val selectedBranchid = "brancheid"
    val selectedBranchName = "branchname"
    val tenantid = "tenantid"
    val roleid = "roleid"
    val employee_id = "employee_id"
    val roletype = "roletype"
    val advanceAvailable = "advavaiolable"
    val LesserThanPenalty = "LesserThanPenalty"
    val bonusAvailable = "bonusavaiolable"
    val intentmsg = "fragintentmsg"
    val imei = "imei"
    val ADD_LEAD_TAG = "Addlead"
    val SHOW_LEAD_TAG = "showlead"
    val SHOW_LEAD_DETAILS_TAG = "showleaddetails"
    val GROUP_TAG = "grouptag"
    val VIEW_RECEIPT_DETAILS_TAG = "receiptdetailstag"
    val COLLECTION_TAG = "collectiontag"
    val DEVICE_USERS = "deviceusers"
    val DEVICE = "devices"
    val VIEWRECEIPTS = "viewreceipts"
    val thisdeviceid = "deviceid"
    val Changepenalty = "Update Penalty"
    val newenalty = "Update Penalty"
    val RECEIPTING_FRAG = "receiptingfragment"
    val No_offline_receipts = "No Offline Receipts Available"
    val No_receipts = "No Receipts Available"

    val OFFLINE_RECNO = "offlinerecno"
    val SPLASH_TIME_OUT = 200
    val MY_PERMISSION_ID = 1
    val fingerprint_set = "Finngerprint_set"
    val application_logged_in = "application_logged_in"
    val str_keyvariable = "mazechit_finger_key"
    val str_doesnt_support_fingerprint = "Your device doesn't support fingerprint authentication"
    val str_enable_fingerprint_permission = "Please enable the fingerprint permission"
    val str_fingerprint_not_configured =
        "No fingerprint configured. Please register at least one fingerprint in your device's Settings"
    val str_enable_security = "Please enable lockscreen security in your device's Settings"
    val NOTIFICATION_ID = 888


    //absent

    val remark = "remarks"

    // Used for Notification Style array and switch statement for Spinner selection.
    val BIG_TEXT_STYLE = "BIG_TEXT_STYLE"
    val BIG_PICTURE_STYLE = "BIG_PICTURE_STYLE"
    val INBOX_STYLE = "INBOX_STYLE"
    val MESSAGING_STYLE = "MESSAGING_STYLE"
    val MATERIAL_COLORS = intArrayOf(
        Color.rgb(0, 115, 255),
        Color.rgb(255, 152, 35),
        Color.rgb(40, 180, 222),
        Color.rgb(0, 176, 80),
        Color.rgb(112, 48, 160)
    )

    fun getnotifyid():Int {
    return this.NOTIFICATION_ID
    }

    fun showToast(string: String, activity: Activity) {
        Toast.makeText(
            activity,
            string,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun money_convertor(string_amount: String, showminus: Boolean?): String {
        var tobe_converted = string_amount
        var minus: Boolean? = false
        val curLocale = Locale("en", "IN")
        //get current locale and display number
        //Locale curLocale = Locale.getDefault();
        try {
            try {
                tobe_converted = tobe_converted.trim { it <= ' ' }.replace(" ".toRegex(), "")
                if (tobe_converted.contains("-")) {
                    minus = true
                    tobe_converted = tobe_converted.replace("-".toRegex(), "")
                }
                try {
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

                tobe_converted = tobe_converted.replace(",".toRegex(), "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (tobe_converted.contains("-")) {
                tobe_converted = "0"
            }
            if (tobe_converted.trim { it <= ' ' }.equals("", ignoreCase = true)) {
                tobe_converted = "0"
            }
            if (tobe_converted.trim { it <= ' ' }.equals("null", ignoreCase = true)) {
                tobe_converted = "0"
            }
            val d = java.lang.Double.parseDouble(tobe_converted.trim { it <= ' ' })
            tobe_converted = NumberFormat.getNumberInstance(curLocale).format(d)

            if (minus!!) {
                if (showminus!!) {
                    tobe_converted = "(-)$tobe_converted"
                }
            }
        } catch (e: NumberFormatException) {
            tobe_converted = string_amount
            e.printStackTrace()
        }

        return tobe_converted
    }

    fun isEmtytoZero(string: String): String {
        var result = string
        if (string.isEmpty()) {
            result = "0"
        } else {
            result = string
        }
        return result
    }

    fun stringToInt(string: String): Int {
        var result: Int
        if (string.isEmpty()) {
            result = 0
        } else {
            try {
                result = string.toInt()
            } catch (e: java.lang.NumberFormatException) {
                result = 0
            }

        }
        return result
    }
    fun DoubletoString(string: Double): String {
        var result: String
        if (string.equals("0")) {
            result = "0"
        } else {
            try {
                result = string.toString()
            } catch (e: java.lang.NumberFormatException) {
                result = "0"
            }

        }
        return result
    }
}