package com.mazenet.mani.gurugubera.Utilities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.widget.Toast


@RequiresApi(api = Build.VERSION_CODES.M)
class FingerprintHandler internal constructor(private val context: Context) :
    FingerprintManager.AuthenticationCallback() {
    private var fingerprint_intent: Intent? = null

    private fun sendBufferProfileCompleteBroadcast() {
        // Log.v(TAG, "BufferCompleteSent");
        fingerprint_intent!!.putExtra("result", "0")
        context.sendBroadcast(fingerprint_intent)
    }

    private fun sendBufferEroorProfileCompleteBroadcast() {
        // Log.v(TAG, "BufferCompleteSent");
        fingerprint_intent!!.putExtra("result", "2")
        context.sendBroadcast(fingerprint_intent)
    }

    private fun sendBufferingBroadcast() {
        // Log.v(TAG, "BufferStartedSent");
        fingerprint_intent!!.putExtra("result", "1")
        context.sendBroadcast(fingerprint_intent)
    }
    //Implement the startAuth method, which is responsible for starting the fingerprint authentication process//

    internal fun startAuth(manager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject) {
        fingerprint_intent = Intent(FINGERPRINT_INTENT)
        cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override//onAuthenticationError is called when a fatal error has occurred. It provides the error code and error message as its parameters//
    fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {

        //I’m going to display the results of fingerprint authentication as a series of toasts.
        //Here, I’m creating the message that’ll be displayed if an error occurs//
        sendBufferEroorProfileCompleteBroadcast()
        Toast.makeText(context, "Authentication error\n$errString", Toast.LENGTH_LONG).show()
    }

    override//onAuthenticationFailed is called when the fingerprint doesn’t match with any of the fingerprints registered on the device//
    fun onAuthenticationFailed() {
        sendBufferEroorProfileCompleteBroadcast()
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
    }

    override//onAuthenticationHelp is called when a non-fatal error has occurred. This method provides additional information about the error,
    //so to provide the user with as much feedback as possible I’m incorporating this information into my toast//
    fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        sendBufferEroorProfileCompleteBroadcast()
        Toast.makeText(context, "Authentication help\n$helpString", Toast.LENGTH_SHORT).show()
    }

    override//onAuthenticationSucceeded is called when a fingerprint has been successfully matched to one of the fingerprints stored on the user’s device//
    fun onAuthenticationSucceeded(
        result: FingerprintManager.AuthenticationResult
    ) {
        sendBufferProfileCompleteBroadcast()
//        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
    }

    companion object {

        // You should use the CancellationSignal method whenever your app can no longer process user input, for example when your app goes
        // into the background. If you don’t use this method, then other apps will be unable to access the touch sensor, including the lockscreen!//

        private var cancellationSignal: CancellationSignal? = null
        var FINGERPRINT_INTENT = "fingerprint"

        internal fun stopFingerAuth() {
            if (cancellationSignal != null && !cancellationSignal!!.isCanceled) {
                cancellationSignal!!.cancel()
            }
        }
    }

}