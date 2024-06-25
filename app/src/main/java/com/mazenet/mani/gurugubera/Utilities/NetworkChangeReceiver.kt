package com.mazenet.mani.gurugubera.Utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 * Created by MZS119 on 3/31/2018.
 */

class NetworkChangeReceiver : BroadcastReceiver() {
    private var isConnected = false
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onReceive(context: Context, intent: Intent) {
        Log.v(LOG_TAG, "Receieved notification about network status")
        pref = context.getSharedPreferences(Constants.UserPref, Context.MODE_PRIVATE)
        editor = pref.edit()
        isNetworkAvailable(context)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.activeNetworkInfo
            if (info != null) {

                if (info.state == NetworkInfo.State.CONNECTED) {
                    if (!isConnected) {
                        Log.v(LOG_TAG, "Now you are connected to Internet!")
//                        Toast.makeText(context, "You are Online", Toast.LENGTH_SHORT).show()
//                        editor.putBoolean(Constants.IS_ONLINE,true)
//                        editor.commit()
                        isConnected = true
                    }
                    return true

                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!")
//        editor.putBoolean(Constants.IS_ONLINE,false)
//        editor.commit()
//        Toast.makeText(context, "You are Offline!", Toast.LENGTH_SHORT).show()
        isConnected = false
        return false
    }

    companion object {

        private val LOG_TAG = "NetworkChangeReceiver"
    }


}