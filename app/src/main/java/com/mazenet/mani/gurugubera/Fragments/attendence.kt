package com.mazenet.mani.gurugubera.Activities

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat


/**
 * A simple [Fragment] subclass.
 */
class attendence : BaseFragment() {
    lateinit var btn_att_submit: Button
    lateinit var btn_att_present: Button
    lateinit var btn_att_absent: Button
    lateinit var checkBox1: CheckBox
    lateinit var checkBox2: CheckBox
    var absentOrPresent: String = "0"
    var morning_absent: String = ""
    var afternoon_absent: String = ""
    lateinit var attenremarks: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(
            com.mazenet.mani.gurugubera.R.layout.fragment_attendence,
            container,
            false
        )

        btn_att_absent = view.findViewById(R.id.btn_att_absent) as Button
        btn_att_present = view.findViewById(R.id.btn_att_present) as Button
        btn_att_submit = view.findViewById(R.id.btn_att_submit) as Button
        checkBox1 = view.findViewById(R.id.morningcheck) as CheckBox
        checkBox2 = view.findViewById(R.id.afternooncheck) as CheckBox
        attenremarks = view.findViewById(R.id.Reasonforleave) as EditText
        val constraintLayout = view.findViewById(R.id.session) as ConstraintLayout
        btn_att_absent.setOnClickListener(View.OnClickListener {
            constraintLayout.visibility = View.VISIBLE
        })
        btn_att_present.setOnClickListener(View.OnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this.context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this.context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val gps = GPSTracker(this.context)
                println("lat " + gps.getLatitude())
                println("lat " + gps.getLongitude())
                val str_uplat = gps.getLatitude()
                val str_uplong = gps.getLongitude()
                set_attendence("1", "0", "0",str_uplat.toString(),str_uplong.toString())
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1
                )
            }

        })
        btn_att_submit.setOnClickListener(View.OnClickListener {
            if (checkBox1.isChecked) {
                morning_absent = "1"
            } else {
                morning_absent = "0"
            }
            if (checkBox2.isChecked) {
                afternoon_absent = "1"
            } else {
                afternoon_absent = "0"
            }
            if (morning_absent.equals("0") && afternoon_absent.equals("0")) {
                toast("Check Absent")
            } else {
                if (ContextCompat.checkSelfPermission(
                        this.context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        this.context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val gps = GPSTracker(this.context)
                    println("lat " + gps.getLatitude())
                    println("lat " + gps.getLongitude())
                    val str_uplat = gps.getLatitude()
                    val str_uplong = gps.getLongitude()
                    set_attendence("0", morning_absent, afternoon_absent,str_uplat.toString(),str_uplong.toString())
                } else {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        1
                    )
                }

            }

        })
        return view
    }
    private fun set_attendence(
        presentOrAbsent: String,
        morningAbsent: String,
        afternoonAbsent: String,
        lat:String,
        long:String)  {
        println("ap $presentOrAbsent mor $morningAbsent after $afternoonAbsent lat $lat long $long")
        val attedenceservice = RetrofitBuilder.buildservice(ICallService::class.java)
        val absentparameters = HashMap<String, String>()
        absentparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        absentparameters.put("branch_id", getPrefsString(Constants.branches, ""))
        absentparameters.put("att_date", BaseUtils.CurrentDate_ymd())
        absentparameters.put("attendance_type", presentOrAbsent)
        absentparameters.put("afternoon_session", afternoonAbsent)
        absentparameters.put("forenoon_session", morningAbsent)
        absentparameters.put("remarks", attenremarks.text.toString())
        absentparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
        absentparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
        absentparameters.put("latitude",lat)
        absentparameters.put("longitude",long)
        absentparameters.put("db",getPrefsString(Constants.db,""))
        val absendservice = attedenceservice.add_employee_attendance(absentparameters)
        absendservice.enqueue(object : Callback<successmsgmodel> {
            override fun onResponse(
                call: Call<successmsgmodel>,
                response: Response<successmsgmodel>
            ) {


                getActivity()?.onBackPressed();
            }

            override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                toast(t.toString())
                t.printStackTrace()
            }

        })
        }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if (grantResults.size > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(this.context, "Please allow permission to get GPS Location", Toast.LENGTH_SHORT).show();
        }

    }
    }







