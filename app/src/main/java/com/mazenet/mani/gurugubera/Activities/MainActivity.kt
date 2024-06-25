package com.mazenet.mani.gurugubera.Activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.BaseActivity
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.splash.*

class MainActivity : BaseActivity() {
    lateinit var telephonyManager: TelephonyManager
    var imei: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        initDb()
        spalsh.setImageResource(R.drawable.mpcl)

                System.out.println("inside permission success ")
                Handler().postDelayed({
                    var counter = getPrefsInt(Constants.First_time, 0)
                    if (counter > 0) {
                    } else {
                        counter += 1
                        setPrefsInt(Constants.First_time, counter)
                    }
                    if (getPrefsString(Constants.application_logged_in, "no").equals("yes")) {
                        if (checkForInternet()) {
                            douwnloadBranches()
                            getStates()
                            getDistrict()
                            getCities()
                            getFollowStatusType()
                            if (BaseUtils.offlinedb.OfflineReceiptSize() > 0) {
                                setPrefsBoolean(Constants.IS_ONLINE, false)
                            } else {
                                setPrefsBoolean(Constants.IS_ONLINE, true)
                            }
                        } else {
                        }

                        val i = Intent(this, HomeActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        val i = Intent(this, login::class.java)
                        startActivity(i)
                        finish()
                    }
                }, Constants.SPLASH_TIME_OUT.toLong())


    }


}
