package com.mazenet.mani.gurugubera.Fragments.Settings


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.Profilemodel
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface
import android.content.Intent
import com.bumptech.glide.request.RequestOptions
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Activities.MainActivity
import com.mazenet.mani.gurugubera.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class Settings : BaseFragment() {

    lateinit var edt_as_name: TextView
    lateinit var edt_as_device: TextView
    lateinit var txt_changepassword: TextView
    lateinit var profile_image: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.fragment_settings, container, false)
        (activity as HomeActivity).setActionBarTitle("Settings")
        edt_as_device = view.findViewById(com.mazenet.mani.gurugubera.R.id.edt_as_device) as TextView
        edt_as_name = view.findViewById(com.mazenet.mani.gurugubera.R.id.edt_as_name) as TextView
        txt_changepassword = view.findViewById(com.mazenet.mani.gurugubera.R.id.txt_changepassword) as TextView
        profile_image = view.findViewById(com.mazenet.mani.gurugubera.R.id.profile_image) as CircleImageView
        get_profile_details()

        txt_changepassword.setOnClickListener {
            ChangePAsswordDilog()
        }
        return view
    }

    fun ChangePAsswordDilog() {
        val dialogBuilder = AlertDialog.Builder(context, com.mazenet.mani.gurugubera.R.style.MyDialogTheme)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.changepassword_dilog, null)
        dialogBuilder.setView(dialogView)
        val edt_cp_oldpassword =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.edt_cp_oldpassword) as EditText
        val edt_cp_newpassword =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.edt_cp_newpassword) as EditText
        dialogBuilder
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                CloseKeyBoard()
            }
        dialogBuilder.setPositiveButton("Ok", null)

        edt_cp_oldpassword.requestFocus()
        ShowKeyBoard()
        val alertDialog = dialogBuilder.create()
        alertDialog.setOnShowListener(DialogInterface.OnShowListener {
            val b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            b.setOnClickListener(View.OnClickListener {
                // TODO Do something
                if (edt_cp_oldpassword.text.isNotEmpty()) {
                    if (edt_cp_oldpassword.text.toString().trim().equals(getPrefsString(Constants.password, ""))) {
                        if (edt_cp_newpassword.text.isNotEmpty()) {
                            reset_password(edt_cp_newpassword.text.toString())
                            CloseKeyBoard()
                            alertDialog.dismiss()
                        } else {
                            edt_cp_newpassword.requestFocus()
                            ShowKeyBoard()
                            toast("Enter New Password")
                        }
                    } else {
                        edt_cp_oldpassword.requestFocus()
                        toast("Enter Correct old Password")
                    }
                } else {
                    edt_cp_oldpassword.requestFocus()
                    ShowKeyBoard()
                    toast("Enter old Password")
                }
            })
        })
        alertDialog.show()

    }

    fun get_profile_details() {
        val profilerequest: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))

        val RequestCall = profilerequest.get_profile(loginparameters)
        RequestCall.enqueue(object : Callback<Profilemodel> {
            override fun onFailure(call: Call<Profilemodel>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Profilemodel>, response: Response<Profilemodel>) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        edt_as_name.setText(response.body()!!.getUserName())
                        edt_as_device.setText(response.body()!!.getDeviceName())
//                        val resultUri = Uri.parse(response.body()!!.getProfilePhoto())
                        val placeholderreq = RequestOptions()
                        val path=response.body()!!.getProfilePhoto()
                        if(!path.isNullOrEmpty())
                        {
                            Picasso.with(context).load(response.body()!!.getProfilePhoto()).resize(150, 150).onlyScaleDown()
                                .placeholder(
                                    R.drawable.ic_account_circle
                                ).error(R.drawable.ic_account_circle)
                                .centerInside().into(profile_image)
                        }
                    } else {

                    }
                } else {

                }

            }


        })
    }

    fun reset_password(password: String) {
        val Resetrequest: ICallService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("password", password)
        loginparameters.put("tenant_id",getPrefsString(Constants.tenantid,""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val RequestCall = Resetrequest.reset_password(loginparameters)
        RequestCall.enqueue(object : Callback<successmsgmodel> {
            override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                toast("Password not updated")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<successmsgmodel>, response: Response<successmsgmodel>) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        if (response.body()!!.getStatus()!!.trim().equals("Success")) {
                            toast("Password Updated Successfully")
                            logout()
                        }
                    } else {
                    }
                } else {
                    toast("Password not updated")
                }
            }
        })
    }

    fun logout() {
        setPrefsString(Constants.application_logged_in, "no")
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
