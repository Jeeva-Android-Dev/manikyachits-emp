package com.mazenet.mani.gurugubera.Activities


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.fingerprint.FingerprintManager
import android.os.*
import android.provider.Settings
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Log.d
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mazenet.mani.gurugubera.Databases.DatabaseMasters
import com.mazenet.mani.gurugubera.Model.checkloginmodel
import com.mazenet.mani.gurugubera.Model.loginmodel
import com.mazenet.mani.gurugubera.Model.otpverifymodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.*
import com.mazenet.mani.gurugubera.Utilities.custom_view.IndeterminateProgressButton
import com.mazenet.mani.gurugubera.Utilities.custom_view.MorphingButton
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class login : BaseActivity() {


    private val TAG = HomeActivity::class.java.getSimpleName()
    lateinit var KEY_NAME: String
    private var cipher: Cipher? = null
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private var mBtnSignin: IndeterminateProgressButton? = null
    private var btn_login: IndeterminateProgressButton? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null
    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null
    internal lateinit var fingersensor_text: TextView
    internal var Fingerprint_broadcast: Boolean = false
    lateinit var masterdb: DatabaseMasters
    lateinit var android_id: String
    var showingPassword: Boolean = false
    lateinit var telephonyManager: TelephonyManager
    var imei: String = ""
    lateinit var txt_timer: TextView
    internal lateinit var v: Vibrator
    internal var fingerprint_login_popup: PopupWindow? = null
    var progress: View? = null
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        img_userimage.setImageResource(R.drawable.mpcl)
        masterdb = DatabaseMasters(this@login)
        mBtnSignin = findViewById(R.id.btn_ln_signin) as IndeterminateProgressButton
        v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        KEY_NAME = Constants.str_keyvariable
        getPrefsString(Constants.role, Roles.Admin.toString())
        imei = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        println("ime log $imei")
        setPrefsString(Constants.imei, imei!!)
        setPrefsString(Constants.thisdeviceid, imei!!)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ContextCompat.checkSelfPermission(
//                    this@login,
//                    Manifest.permission.READ_PHONE_STATE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//
//                    // Permission is  granted
//                    var imei : String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        telephonyManager.imei
//                    } else { // older OS  versions
//                        telephonyManager.getDeviceId()
//                    }
//                println("ime log to  $imei")
//                imei?.let { setPrefsString(Constants.imei, it) }
//                    imei?.let {
//                        Log.i("Log", "DeviceId=$imei" )
//                        if(getPrefsString(Constants.imei,"").isBlank()||imei.equals("null",ignoreCase = true))
//                        {
//
//                        }
//                    }
//            } else {
//
//                ActivityCompat.requestPermissions(
//                    this@login,
//                    arrayOf(
//                        Manifest.permission.READ_PHONE_STATE
//                    ),
//                    Constants.MY_PERMISSION_ID
//                )
//            }
//        } else {
//            imei = Settings.Secure.getString(
//                this.contentResolver,
//                Settings.Secure.ANDROID_ID
//            )
//            setPrefsString(Constants.imei, imei)
//            setPrefsString(Constants.thisdeviceid, imei)
//        }

        btn_ln_signin.setOnClickListener {
            val email = edt_ln_email.text.toString()
            val password = edt_ln_password.text.toString()
            when {
                TextUtils.isEmpty(email) -> Constants.showToast("Enter Valid Email Id", this@login)
                TextUtils.isEmpty(password) -> Constants.showToast("Invalid Passsword", this@login)
                else -> checklogin(email, password, imei, false)
            }
        }
      //  initfingerprint()
//        fingerprint_layout.setOnClickListener {
//
//            initfingerprint()
//            if (getPrefsString(
//                    Constants.fingerprint_set,
//                    "no"
//                ).equals("yes")
//            ) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    showfinger()
//                }
//            } else {
//
//                loginfirst(this@login)
//            }
//        }
        edt_ln_password.setOnTouchListener(object : DrawableClickListener.RightDrawableClickListener(edt_ln_password) {
            override fun onDrawableClick(): Boolean {
                if (!showingPassword) {
                    edt_ln_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_off, 0
                    )
                    showingPassword = true

                    edt_ln_password.transformationMethod = PasswordTransformationMethod()
                } else {
                    edt_ln_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_on, 0
                    )
                    showingPassword = false
                    edt_ln_password.setTransformationMethod(null);
                }
                return true
            }
        })


    }

    fun checklogin(email: String, password: String, imei: String, fingerprinted: Boolean) {
        System.out.println("retro called")
        showProgressDialog()
        val checklogin: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("email", email)
        loginparameters.put("password", password)
        loginparameters.put("device_id", getPrefsString(Constants.imei,""))
       // loginparameters.put("db",Constants.db)
        setPrefsString(Constants.email, email)
        setPrefsString(Constants.password, password)
        if (fingerprinted) {
            simulateProgressSignIN(btn_login!!)
        } else {
            simulateProgressSignIN(btn_ln_signin)
        }

        val RequestCall = checklogin.check_login(loginparameters)
        RequestCall.enqueue(object : Callback<checkloginmodel> {
            override fun onFailure(call: Call<checkloginmodel>, t: Throwable) {
                morphToFailureSignIN(fingerprinted)
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<checkloginmodel>, response: Response<checkloginmodel>) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    when {
                        response.code().equals(200) -> {
                           setPrefsString(Constants.db, response.body()!!.getDb()!!)
                            Log.d("dbcheck",response.body()!!.getDb()!!)
                            System.out.println("response token  ${response.body()?.getStatus()}")
                            if (response.body()?.getStatus().equals("New Device")) {
                                val otp = response.body()!!.getOtp()
                                val device_id = response.body()!!.getDevicePrimaryId()
                                val userid = response.body()!!.getUserId()
                                showotpdilog(otp!!, device_id!!, userid!!, email, password, imei, fingerprinted)
                            } else if (response.body()?.getStatus().equals("New Login")) {
                                val otp = response.body()!!.getOtp()
                                val device_id = response.body()!!.getDevicePrimaryId()
                                val userid = response.body()!!.getUserId()
                                showotpdilog(otp!!, device_id!!, userid!!, email, password, imei, fingerprinted)
                            } else if (response.body()?.getStatus().equals("Success")) {
                                login(email, password, fingerprinted)
                            } else if (response.body()?.getStatus().equals("Error")) {
                                toast(response.body()!!.getMsg().toString())
                            } else if (response.body()?.getStatus().equals("New Loginss")) {
                                val otp = response.body()!!.getOtp()
                                val device_id = response.body()!!.getDevicePrimaryId()
                                val userid = response.body()!!.getUserId()
                                showotpdilog(
                                    otp!!,
                                    device_id!!,
                                    userid!!,
                                    email,
                                    password,
                                    imei,
                                    fingerprinted
                                )
                            }

                        }
                        response.code().equals(401) -> toast(response.body()!!.getMsg().toString())
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                } else {
                    hideProgressDialog()
                    morphToFailureSignIN(fingerprinted)
                    when {
                        response.code().equals(401) -> toast(response.message())
                        response.code().equals(500) -> toast("Internal server Error")
                    }
                    Log.e(TAG, "Response not Successfull")
                }

            }


        })
    }

    fun login(email: String, password: String, fingerprinted: Boolean) {
        System.out.println("retro called")
        val loginrequest: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("email", email)
        loginparameters.put("password", password)
       // loginparameters.put("db",Constants.db)
        simulateProgressSignIN(btn_ln_signin)
        val RequestCall = loginrequest.getdetails(loginparameters)
        RequestCall.enqueue(object : Callback<loginmodel> {
            override fun onFailure(call: Call<loginmodel>, t: Throwable) {
                morphToFailureSignIN(fingerprinted)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<loginmodel>, response: Response<loginmodel>) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        System.out.println("response token  ${response.body()?.getToken()}")
                        d("dbtest",response.body()!!.getDb().toString())

                        morphToSuccessSignIN(response, fingerprinted)

                    }
                } else {
                    morphToFailureSignIN(fingerprinted)
                    Log.e(TAG, "Response not Successfull")
                }

            }


        })
    }

    fun confirm_device_login_otp(
        userid: String,
        device_id: String,
        email: String,
        password: String,
        fingerprinted: Boolean
    ) {

        val loginrequest: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("user_id", userid)
        loginparameters.put("device_primary_id", device_id)
        loginparameters.put("db",getPrefsString(Constants.db,""))

        val RequestCall = loginrequest.verify_otp(loginparameters)
        RequestCall.enqueue(object : Callback<otpverifymodel> {
            override fun onFailure(call: Call<otpverifymodel>, t: Throwable) {
                Constants.showToast("device verification failed", this@login)
                morphToFailureSignIN(fingerprinted)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<otpverifymodel>, response: Response<otpverifymodel>) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        System.out.println("response token  ${response.body()?.getStatus()}")
                        login(email, password, fingerprinted)
                    } else {
                        morphToFailureSignIN(fingerprinted)
                        Constants.showToast("device verification failed", this@login)
                    }
                } else {
                    morphToFailureSignIN(fingerprinted)
                    Constants.showToast("device verification failed", this@login)
                    Log.e(TAG, "Response not Successfull")
                }

            }


        })
    }

    @SuppressLint("MissingPermission")
    fun initfingerprint() {
        // If you’ve set your app’s minSdkVersion to anything lower than 23, then you’ll need to verify that the device is running Marshmallow
        // or higher before executing any fingerprint-related code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager


            //Check whether the device has a fingerprint sensor//
            if (!fingerprintManager!!.isHardwareDetected()) {
                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                Constants.showToast(Constants.str_doesnt_support_fingerprint.toString(), this@login)
                fingerprint_layout.visibility = View.GONE
            } else {
                //Check whether the user has granted your app the USE_FINGERPRINT permission//
                if (ActivityCompat.checkSelfPermission(
                        this@login,
                        Manifest.permission.USE_FINGERPRINT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // If your app doesn't have this permission, then display the following text//
                    Constants.showToast(Constants.str_enable_fingerprint_permission.toString(), this@login)
                    fingerprint_layout.visibility = View.GONE
                } else {
                    //Check that the user has registered at least one fingerprint//
                    if (!fingerprintManager!!.hasEnrolledFingerprints()) {
                        // If the user hasn’t configured any fingerprints, then display the following message//
                        Constants.showToast(Constants.str_fingerprint_not_configured.toString(), this@login)
                        fingerprint_layout.visibility = View.GONE
                    } else {
                        //Check that the lockscreen is secured//
                        if (!keyguardManager!!.isKeyguardSecure()) {
                            // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                            Constants.showToast(Constants.str_enable_security.toString(), this@login)
                            fingerprint_layout.visibility = View.GONE
                            //                fingersensor_text.setText("Please enable lockscreen security in your device's Settings");
                        } else {
                            try {
                                generateKey()
                            } catch (e: FingerprintException) {
                                e.printStackTrace()
                            }

                            if (initCipher()) {
                                //If the cipher is initialized successfully, then create a CryptoObject instance//
                                cryptoObject = cipher?.let { FingerprintManager.CryptoObject(it) }
                                fingerprint_layout.visibility = View.VISIBLE
                                // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                                // for starting the authentication process (via the startAuth method) and processing the authentication process events//

                            }
                        }
                    }


                }


            }
        }else
        {
            fingerprint_layout.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun initCipher(): Boolean {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        try {
            keyStore!!.load(
                null
            )
            val key = keyStore!!.getKey(KEY_NAME.toString(), null) as SecretKey
            cipher!!.init(Cipher.ENCRYPT_MODE, key)
            //Return true if the cipher has been initialized successfully//
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {

            //Return false if cipher initialization failed//
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }

    }

    private inner class FingerprintException(e: Exception) : Exception(e)

    //Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//

    @Throws(FingerprintException::class)
    private fun generateKey() {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore")

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

            //Initialize an empty KeyStore//
            keyStore!!.load(null)

            //Initialize the KeyGenerator//
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator!!.init(
                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(
                        KEY_NAME.toString(),
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                        //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7
                        )
                        .build()
                )
            }

            //Generate the key//
            keyGenerator!!.generateKey()

        } catch (exc: KeyStoreException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchAlgorithmException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: NoSuchProviderException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: InvalidAlgorithmParameterException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: CertificateException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        } catch (exc: IOException) {
            exc.printStackTrace()
            throw FingerprintException(exc)
        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun showfinger() {
        val dialogBuilder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fingerprintdilog, null)
        dialogBuilder.setView(dialogView)

        val textView = dialogView.findViewById(R.id.txt_cancel) as TextView
        fingersensor_text = dialogView.findViewById(R.id.txt_warm) as TextView
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        val helper = FingerprintHandler(this@login)
        fingerprintManager?.let { cryptoObject?.let { it1 -> helper.startAuth(it, it1) } }
        textView.setOnClickListener {
            FingerprintHandler.stopFingerAuth()
            alertDialog.dismiss()
        }
    }

    fun showotpdilog(
        receivedOtp: Int,
        device_id: Int,
        userid: Int,
        email: String,
        password: String,
        imei: String,
        fingerprinted: Boolean
    ) {
        System.out.println("otp " + receivedOtp.toString())
        val dialogBuilder = AlertDialog.Builder(this)
        // ...Irrelevant code for customizing the buttons and title
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.showotpdilog, null)
        dialogBuilder.setView(dialogView)

        val edt_otp = dialogView.findViewById(R.id.edt_otp) as EditText
        val txt_cancel = dialogView.findViewById(R.id.btn_otp_cancel) as TextView
        val txt_submit = dialogView.findViewById(R.id.btn_otp_submit) as Button
        val txt_timer = dialogView.findViewById(R.id.txt_timer) as TextView


        val alertDialog = dialogBuilder.create()
        alertDialog.show()
        txt_timer.visibility = View.VISIBLE
        val timer = object : CountDownTimer(120000, 1000) {
            override fun onFinish() {
                txt_timer.setText("Resend OTP")
            }

            override fun onTick(millisUntilFinished: Long) {
                val time: Int = (millisUntilFinished / 1000).toInt()
                val minutes = (time % 3600) / 60;
                val seconds = time % 60;
                val timeString = String.format("%02d:%02d", minutes, seconds);
                txt_timer.setText(timeString)
            }

        }
        timer.start()

        txt_cancel.setOnClickListener {
            morphToFailureSignIN(fingerprinted)
            alertDialog.dismiss()
        }
        txt_timer.setOnClickListener {
            val text = txt_timer.toString()
            if (text.equals("Resend OTP")) {
                alertDialog.dismiss()
                checklogin(email, password, imei, fingerprinted)
            } else {

            }
        }
        txt_submit.setOnClickListener {
            val text = txt_timer.text.toString()

            if (text.equals("Resend OTP")) {
                Constants.showToast("OTP expired, Sign in Again", this@login)
            } else {
                val enteredOtpString = edt_otp.text.toString()
                if (enteredOtpString.isBlank()) {
                    Constants.showToast("Enter OTP", this@login)
                    return@setOnClickListener
                } else {
                    if (enteredOtpString.equals(receivedOtp.toString())) {
                        alertDialog.dismiss()
                        Constants.showToast("OTP Verified", this@login)
                        confirm_device_login_otp(
                            userid.toString(),
                            device_id.toString(),
                            email,
                            password,
                            fingerprinted
                        )
                    } else {
                        Constants.showToast("Enter Correct OTP", this@login)
                    }
                }

            }

        }
    }

    @SuppressLint("MissingPermission")
    fun vibrate(count: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mVibratePattern = longArrayOf(50, 100)
            if (count.equals("1", ignoreCase = true)) {
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))

            } else if (count.equals("2", ignoreCase = true)) {
                v.vibrate(VibrationEffect.createWaveform(mVibratePattern, 2))
            }

        } else {
            if (count.equals("1", ignoreCase = true)) {
                //deprecated in API 26
                v.vibrate(50)
            } else if (count.equals("2", ignoreCase = true)) {
                v.vibrate(50)
                v.vibrate(50)

            }
        }
    }

    override fun onResume() {
        if (!Fingerprint_broadcast) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

                    if (fingerprintManager!!.isHardwareDetected()) {
                        registerReceiver(
                            broadcastBufferReceiver, IntentFilter(
                                FingerprintHandler.FINGERPRINT_INTENT
                            )
                        )

                    } else {
                        Constants.showToast(Constants.str_doesnt_support_fingerprint.toString(), this@login)
                        fingerprint_layout.visibility = View.GONE
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Fingerprint_broadcast = true
        }
        super.onResume()
    }

    override fun onDestroy() {
        if (Fingerprint_broadcast) {
            try {
                unregisterReceiver(broadcastBufferReceiver)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Fingerprint_broadcast = false
        }

        super.onDestroy()
    }

    private val broadcastBufferReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, bufferIntent: Intent) {
            try {
                showPD(bufferIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun showPD(bufferIntent: Intent) {
        val bufferValue = bufferIntent.getStringExtra("result")
        val bufferIntValue = Integer.parseInt(bufferValue)
        when (bufferIntValue) {
            0 -> if (getPrefsString(Constants.fingerprint_set, "no")!!.equals("no")) {
                setPrefsString(Constants.fingerprint_set, "yes")
                setPrefsString(Constants.application_logged_in, "yes")
                fingersensor_text.text = "Success"
                vibrate("1")
                goToHome()

            } else {

                vibrate("1")
                fingersensor_text.text = "Success"
                setPrefsString(Constants.fingerprint_set, "yes")
                setPrefsString(Constants.application_logged_in, "yes")
                goToHome()
            }


            1 -> {
            }

            2 -> {
                vibrate("2")
                fingersensor_text.text = "Try Again"
            }
        }
    }


    fun loginfirst(context: Activity) {

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup = context.findViewById(R.id.loginfirstlayout) as? RelativeLayout
        val layout = layoutInflater.inflate(R.layout.loginfirst, viewGroup)

        fingerprint_login_popup = PopupWindow(context)
        fingerprint_login_popup!!.setContentView(layout)
        fingerprint_login_popup!!.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT)
        fingerprint_login_popup!!.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT)
        fingerprint_login_popup!!.setAnimationStyle(R.style.AnimationPopup);
        fingerprint_login_popup!!.setFocusable(true)
        fingerprint_login_popup!!.setBackgroundDrawable(null)
        fingerprint_login_popup!!.showAtLocation(layout, Gravity.CENTER, 0, 0)


        val edt_password = layout.findViewById(R.id.edt_password) as EditText
        val edt_email = layout.findViewById(R.id.edt_email) as EditText
         btn_login = layout.findViewById(R.id.btn_login) as IndeterminateProgressButton
        val no = layout.findViewById(R.id.btn_back) as Button
        var showingPassword = false
        edt_password.setOnTouchListener(object : DrawableClickListener.RightDrawableClickListener(edt_password) {
            override fun onDrawableClick(): Boolean {
                if (!showingPassword) {
                    edt_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_off, 0
                    )
                    showingPassword = true

                    edt_password.transformationMethod = PasswordTransformationMethod()
                } else {
                    edt_password.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0, 0,
                        R.drawable.ic_eye_on, 0
                    )
                    showingPassword = false
                    edt_password.setTransformationMethod(null);
                }
                return true
            }
        })
        edt_password.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            when {
                TextUtils.isEmpty(email) -> Constants.showToast("Enter Valid Email Id", this@login)
                TextUtils.isEmpty(password) -> Constants.showToast("Invalid Passsword", this@login)
                else -> checklogin(email, password, imei, true)
            }
            true
        })
        btn_login!!.setOnClickListener {
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            when {
                TextUtils.isEmpty(email) -> Constants.showToast("Enter Valid Email Id", this@login)
                TextUtils.isEmpty(password) -> Constants.showToast("Invalid Passsword", this@login)
                else -> checklogin(email, password, imei, true)
            }
        }
        fingerprint_login_popup!!.setOnDismissListener {
        }
        no.setOnClickListener {
            fingerprint_login_popup!!.dismiss()
        }
    }

    private fun simulateProgressSignIN(mBtSignin: IndeterminateProgressButton) {

        val progressColor1 = Color.rgb(0, 159, 255)
        val progressColor2 = Color.rgb(0, 197, 255)
        val progressColor3 = Color.rgb(0, 223, 255)
        val progressColor4 = Color.rgb(0, 255, 223)
        val color = ContextCompat.getColor(this, R.color.colorWhite)
        val progressCornerRadius = resources.getDimension(R.dimen._4ssp) as Float
        val width = resources.getDimension(R.dimen._20ssp).toInt()
        val height = resources.getDimension(R.dimen.sp48_space).toInt()
        val duration = 100

        mBtSignin.blockTouch() // prevent user from clicking while button is in progress
        mBtSignin.morphToProgress(
            color,
            progressCornerRadius.toInt(),
            width.toInt(),
            height.toInt(),
            duration,
            progressColor1,
            progressColor2,
            progressColor3,
            progressColor4
        )

    }

    private fun morphToSquareSignIN(mBtSignin: IndeterminateProgressButton) {
        val square = MorphingButton.Params.create()
            .duration(100)
            .cornerRadius(resources.getDimension(R.dimen._2ssp).toInt())
            .width(resources.getDimension(R.dimen._20ssp).toInt())
            .height(resources.getDimension(R.dimen.sp48_space).toInt())
            .color(ContextCompat.getColor(this, R.color.colorCancelled))
            .colorPressed(ContextCompat.getColor(this, R.color.colorPrimary))
            .text("Retry Sign In")
        mBtSignin.morph(square)
        mBtSignin.unblockTouch()
        mBtSignin.visibility = View.VISIBLE
    }

    private fun morphToSuccessSignIN(response: Response<loginmodel>, fingerprinted: Boolean) {
        val circle = MorphingButton.Params.create()
            .duration(500)
            .cornerRadius(resources.getDimension(R.dimen._4ssp).toInt())
            .width(resources.getDimension(R.dimen._20ssp).toInt())
            .height(resources.getDimension(R.dimen.sp48_space).toInt())
            .color(ContextCompat.getColor(this, R.color.green_500))
            .colorPressed(ContextCompat.getColor(this, R.color.colorWhite))
            .icon(R.drawable.ic_tick)
        if (fingerprinted) {
            btn_login!!.morph(circle)
            btn_login!!.unblockTouch()
            btn_login!!.setVisibility(View.VISIBLE)
        } else {
            mBtnSignin!!.morph(circle)
            mBtnSignin!!.unblockTouch()
            mBtnSignin!!.setVisibility(View.VISIBLE)
        }


        Handler().postDelayed({
            if (fingerprinted) {
                System.out.println("fingerprinted yes")
                response.body()!!.getLoggedUserId()
                    ?.let { setPrefsString(Constants.loggeduser, it.toString()) }
                setPrefsString(Constants.token, response.body()!!.getToken()!!)
                setPrefsString(Constants.tenantid, response.body()!!.getTenantId()!!.toString())
                setPrefsString(Constants.username, response.body()!!.getLoggedUserName()!!)
                setPrefsString(Constants.role, response.body()!!.getRoleName()!!)
                response.body()!!.getBranchId()
                    ?.let { setPrefsString(Constants.branches, it.toString()) }
                setPrefsString(Constants.roleid, response.body()!!.getRoleId()!!.toString())
                setPrefsString(Constants.roletype, response.body()!!.getRoleType()!!)
                setPrefsString(Constants.db,response.body()!!.getDb()!!)
                setPrefsString(Constants.branchName,response.body()!!.getBranchName()!!)
                setPrefsString(Constants.profilePic,response.body()!!.getProfilePic()!!)
                setPrefsString(Constants.advanceAvailable, "yes")
                setPrefsString(Constants.bonusAvailable, "yes")
                setPrefsString(Constants.teanant_name, response.body()!!.getTenantName()!!.toString())
                setPrefsString(Constants.tenant_address1, response.body()!!.getTenantAddress1()!!.toString())
                setPrefsString(Constants.tenant_address2, response.body()!!.getTenantAddress2()!!.toString())
                setPrefsString(Constants.tenant_gst, response.body()!!.getTenantGstNo()!!.toString())
                setPrefsString(Constants.tenant_mobile, response.body()!!.getTenantMobile()!!.toString())
                setPrefsString(Constants.tenant_phone, response.body()!!.getTenantPhone()!!.toString())
                setPrefsString(Constants.tenant_state, response.body()!!.getStateName()!!.toString())
                setPrefsInt(Constants.employee_id, response.body()!!.getEmployeeId()!!)
                setPrefsString(Constants.application_logged_in, "yes")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    System.out.println("fingerprinted yes")
                    showfinger()
                }

            } else {
                System.out.println("fingerprinted no")
                response.body()!!.getLoggedUserId()
                    ?.let { setPrefsString(Constants.loggeduser, it.toString()) }
                setPrefsString(Constants.token, response.body()!!.getToken()!!)
                setPrefsString(Constants.tenantid, response.body()!!.getTenantId()!!.toString())
                setPrefsString(Constants.username, response.body()!!.getLoggedUserName()!!)
                setPrefsString(Constants.role, response.body()!!.getRoleName()!!)
                response.body()!!.getBranchId()
                    ?.let { setPrefsString(Constants.branches, it.toString()) }
                setPrefsString(Constants.roleid, response.body()!!.getRoleId()!!.toString())
                setPrefsString(Constants.roletype, response.body()!!.getRoleType()!!)
                setPrefsString(Constants.db,response.body()!!.getDb()!!)
              //  setPrefsString(Constants.branchName,response.body()!!.getBranchName()!!)
              //  setPrefsString(Constants.profilePic,response.body()!!.getProfilePic()!!)
              //  Log.d("logcheckdata",getPrefsString(Constants.profilePic,""))
              //  Log.d("logcheckdata",getPrefsString(Constants.branchName,""))
              //  Log.d("logcheckdata",response.body()!!.getBranchName()!!)
                setPrefsString(Constants.application_logged_in, "yes")
                setPrefsString(Constants.advanceAvailable, "yes")
                setPrefsString(Constants.bonusAvailable, "yes")
                setPrefsString(Constants.teanant_name, response.body()!!.getTenantName()!!.toString())
                setPrefsString(Constants.tenant_address1, response.body()!!.getTenantAddress1()!!.toString())
                setPrefsString(Constants.tenant_address2, response.body()!!.getTenantAddress2()!!.toString())
                setPrefsString(Constants.tenant_gst, response.body()!!.getTenantGstNo()!!.toString())
                setPrefsString(Constants.tenant_mobile, response.body()!!.getTenantMobile()!!.toString())
                setPrefsString(Constants.tenant_phone, response.body()!!.getTenantPhone()!!.toString())
                setPrefsString(Constants.tenant_state, response.body()!!.getStateName()!!.toString())
                setPrefsInt(Constants.employee_id, response.body()!!.getEmployeeId()!!)
                setPrefsString(Constants.fingerprint_set, "no")

                finishAffinity()
                goToHome()

            }

        }, 700)

    }

    private fun morphToFailureSignIN(fingerprinted: Boolean) {
        if (fingerprinted) {
            Handler().postDelayed({
                btn_login?.let(this::morphToSquareSignIN)
                btn_login!!.unblockTouch()
            }, 500)
        } else {
            Handler().postDelayed({
                mBtnSignin?.let(this::morphToSquareSignIN)
                mBtnSignin!!.unblockTouch()
            }, 500)
        }

    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>, grantResults: IntArray
//    ) {
//        if (grantResults.size > 0
//            && grantResults[0] == PackageManager.PERMISSION_GRANTED
//        ) {
//            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            // Permission is  granted
//            var imei : String? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                telephonyManager.imei
//            } else { // older OS  versions
//                telephonyManager.getDeviceId()
//            }
//            println("ime log $imei")
//            imei?.let { setPrefsString(Constants.imei, it) }
//            imei?.let { setPrefsString(Constants.thisdeviceid, it) }
//            imei?.let {
//                Log.i("Log", "DeviceId=$imei" )
//                if(imei.isNullOrEmpty())
//                {
//                    imei = Settings.Secure.getString(
//                        this.contentResolver,
//                        Settings.Secure.ANDROID_ID
//                    )
//                    println("ime log $imei")
//                    setPrefsString(Constants.imei, imei!!)
//                    setPrefsString(Constants.thisdeviceid, imei!!)
//                }
//            }
//        } else {
//            // permission denied, boo! Disable the
//            // functionality that depends on this permission.
//            Toast.makeText(this@login, "Please allow permission to Login", Toast.LENGTH_SHORT).show();
//            finish()
//        }
//
//    }

    fun goToHome() {

        setPrefsInt(Constants.First_time, 1)
        douwnloadBranches()
        getPAymeType()
        get_banks_list()
        get_remarks()
        getFollowStatusType()
        getStates()
        getDistrict()
        getCities()

        val intent: Intent
        intent = Intent(
            this@login,
            HomeActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}

