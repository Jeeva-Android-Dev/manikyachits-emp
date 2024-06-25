package com.mazenet.mani.gurugubera.Fragments.Leads


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.followstatustypeModel

import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import kotlinx.android.synthetic.main.fragment_addleadpage.*
import com.mazenet.mani.gurugubera.Model.BranchModel
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.BranchSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import kotlinx.android.synthetic.main.fragment_addleadpage.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log


class Add_Lead : BaseFragment() {

    var followstatuslist: ArrayList<followstatustypeModel> = ArrayList<followstatustypeModel>()
    lateinit var followuplayout: LinearLayout
    var followup_id: String = ""
    lateinit var df: SimpleDateFormat
    var brancharray = ArrayList<BranchModel>()
    lateinit var spn_branch: Button
    var SelectedBranch: String = ""
    var SelectedBranchName: String = ""
    var SelectedFollowupDate: String = ""
    lateinit var btn_addlead: Button
    lateinit var txt_al_edfollowupdate: TextView
    lateinit var img_dateselector: ImageView
    var branch_list: ArrayList<BranchModel> = ArrayList<BranchModel>()
    internal var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var mailok = false
    var mobilecheck = false
    var phonecheck = false
    lateinit var BranchSpinner: BranchSpinnerdilog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view =
            inflater.inflate(R.layout.fragment_addleadpage, container, false)
        (activity as HomeActivity).setActionBarTitle("Add Lead")
        spn_branch = view.findViewById(R.id.edt_al_branch) as Button
        df = SimpleDateFormat("yyyy-MM-dd")
        btn_addlead = view.findViewById(R.id.btn_al_addlead) as Button
        img_dateselector = view.findViewById(R.id.img_dateselector) as ImageView
        txt_al_edfollowupdate = view.findViewById(R.id.txt_al_edfollowupdate) as TextView
        douwnloadBranches()
        if (BaseUtils.masterdb.BranchTableSize() > 0) {
            brancharray = BaseUtils.masterdb.getBranches()
            System.out.println("greater $brancharray")
        }
        BranchSpinner = BranchSpinnerdilog(
            activity, brancharray,
            "Select Branch"
        )

        spn_branch.setOnClickListener {
            System.out.println("spn lay clicked ")
            BranchSpinner.showSpinerDialog()
        }
        BranchSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {

                SelectedBranch = position.toString()
                SelectedBranchName = grpname
                spn_branch.setText(grpname)
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)
                System.out.println("branchid $SelectedBranch branchname $grpname")

            }
        })
        followuplayout = view.findViewById(R.id.followuplayout) as LinearLayout
        followstatuslist.clear()
        followstatuslist = BaseUtils.masterdb.getFollowupstatusType()

        val listsize = followstatuslist.size
        var iteratedsize = 1
        reemoveview(followuplayout)
        for (item in followstatuslist) {
            item.getFollowupStatusName()?.let {
                when (iteratedsize) {
                    1 -> addlayout(followuplayout, it, item.getFollowupStatusId(), true, "first")
                    listsize -> addlayout(followuplayout, it, item.getFollowupStatusId(), false, "last")
                    else -> addlayout(followuplayout, it, item.getFollowupStatusId(), true, "not")
                }
                iteratedsize = iteratedsize + 1
            }
        }
        view.edt_al_edemailid.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                if (view.edt_al_edemailid.getText().toString().matches(emailPattern.toRegex()) && s.length > 0) {
                    mailok = true
                } else {
                    view.edt_al_edemailid.setError("Enter Valid Email")
                    mailok = false
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // other stuffs
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // other stuffs
            }
        })
        view.edt_al_edmobileno.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                println("${view.edt_al_edmobileno.getText().toString()}")
                if (view.edt_al_edmobileno.getText().toString().length < 10 || view.edt_al_edmobileno.getText().toString().length > 13) {
                    view.edt_al_edmobileno.setError("Enter valid Mobile Number")
                    println(" eroor che")
                    mobilecheck = false
                } else {
                    mobilecheck = true
                }
            }
        })
        view.edt_al_edphoneno.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {

                if (view.edt_al_edphoneno.getText().toString().length < 6 || view.edt_al_edphoneno.getText().toString().length > 13) {
                    phonecheck = false
                    view.edt_al_edphoneno.setError("Enter valid Phone Number")
                } else {
                    phonecheck = true
                }
            }
        })
        txt_al_edfollowupdate.setOnClickListener(View.OnClickListener {
            val newCalendar = Calendar.getInstance()
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = DatePickerDialog(
                context, R.style.MyDialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)

                    try {
                        SelectedFollowupDate = df.format(newDate.time)
                        txt_al_edfollowupdate.setText(SelectedFollowupDate)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
            )
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000 * 48)
            fromDatePickerDialog.setTitle("Next Followup date")
            fromDatePickerDialog.show()
        })
        btn_addlead.setOnClickListener {

            if (spn_branch.text.toString().isEmpty()) {
                spn_branch.background = resources.getDrawable(R.drawable.error_highlight)
                toast("Select Branch")
                return@setOnClickListener
            } else {
                spn_branch.background = resources.getDrawable(R.drawable.spinner_box_no_background)
                if (edt_al_edcustname.text.toString().isEmpty()) {
                    edt_al_edcustname.background = resources.getDrawable(R.drawable.error_highlight)
                    toast("Enter Customer name")
                    return@setOnClickListener
                } else {
                    edt_al_edcustname.background = resources.getDrawable(R.drawable.edittext_nullbackground)
                    if (edt_al_edmobileno.text.toString().isEmpty() || !mobilecheck) {
                        edt_al_edmobileno.background = resources.getDrawable(R.drawable.error_highlight)
                        toast("Enter Mobile no.")
                        return@setOnClickListener
                    } else {
                        edt_al_edmobileno.background = resources.getDrawable(R.drawable.edittext_nullbackground)
                        if (followup_id.isEmpty()) {
                            followuplayout.background = resources.getDrawable(R.drawable.error_followuplayout)
                            toast("Choose Followup Status")
                            return@setOnClickListener
                        } else {
                            addlead(
                                SelectedBranch,
                                edt_al_edcustname.text.toString(),
                                edt_al_edmobileno.text.toString(),
                                edt_al_edphoneno.text.toString(),
                                edt_al_edemailid.text.toString(),
                                edt_al_edadress1.text.toString(),
                                edt_al_edadress2.text.toString(),
                                txt_al_edfollowupdate.text.toString(),
                                followup_id,
                                edt_al_remarks.text.toString()
                            )
                        }
                    }
                }
            }
        }


        return view
    }

    private fun addlead(
        branchid: String,
        custname: String,
        mobileno: String,
        phone: String,
        email: String,
        address1: String,
        address2: String,
        nextfollowupdate: String,
        followupsattus: String,
        remarks: String
    ) {
        showProgressDialog()
        val addleadservice = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("branch_id", branchid)
        loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
        loginparameters.put("lead_customer_name", custname)
        loginparameters.put("phone_no", phone)
        loginparameters.put("mobile_no", mobileno)
        loginparameters.put("email_id", email)
        loginparameters.put("address_line_1", address1)
        loginparameters.put("address_line_2", address2)
        loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("followup_date", BaseUtils.CurrentDate_ymd())
        loginparameters.put("next_followup_date", nextfollowupdate)
        loginparameters.put("followup_status_type_id", followupsattus)
        loginparameters.put("remarks", remarks)
        loginparameters.put("db",getPrefsString(Constants.db,""))

        val addlead_req = addleadservice.add_lead(loginparameters)
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
                            toast("lead Added Successfully")
                            doFragmentTransaction(Show_leads(), Constants.SHOW_LEAD_TAG, false, "", "")
                        } else {
                            toast(response.body()!!.getMsg()!!)
                        }
                    } else {
                        System.out.println("no show")
                    }
                } else {
                    hideProgressDialog()
                }
            }
        })
    }

    fun addlayout(layout: LinearLayout, text: String, id: Int?, operation: Boolean?, first_or_last: String) {

        val lparams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f
        )
        lparams.marginEnd = 0
        lparams.gravity = Gravity.CENTER
        val tv = TextView(context)
        tv.layoutParams = lparams
        tv.text = text
        tv.tag = first_or_last
        tv.id = id!!
        tv.width = 0
        tv.gravity = Gravity.CENTER
        tv.textSize = 16f
        tv.setTextColor(resources.getColor(R.color.grey_700))
        tv.setOnClickListener {
            followup_id = tv.id.toString()
            highlightText(tv.id, tv.tag as String)
        }
        layout.visibility = View.VISIBLE
        layout.addView(tv)
        if (operation!!) {
            val Lineparams = LinearLayout.LayoutParams(
                1, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            Lineparams.marginEnd = 0
            var separatorLine = View(context)
            separatorLine.layoutParams = Lineparams
            separatorLine.setBackgroundColor(resources.getColor(R.color.grey_700))
            layout.addView(separatorLine)
        }
    }

    fun highlightText(id: Int, first_or_last: String) {
        val childCount = followuplayout.getChildCount()
        for (i in 0 until childCount) {
            val v = followuplayout.getChildAt(i)
            if (v is TextView) {
                val textview = v as TextView
                textview.setBackgroundColor(resources.getColor(R.color.grey_100))
                textview.setTextColor(resources.getColor(R.color.grey_700))
                followuplayout.background = resources.getDrawable(R.drawable.followupstatus_loyoutstyle)
                if (textview.id.equals(id)) {
                    if (first_or_last.equals("first")) {
                        textview.setBackgroundColor(resources.getColor(com.mazenet.mani.gurugubera.R.color.colorPrimary))
                        textview.setTextColor(resources.getColor(R.color.colorWhite))
                        textview.background = resources.getDrawable(R.drawable.followupstatus_loyout_left)
                    } else if (first_or_last.equals("not")) {
                        textview.setBackgroundColor(resources.getColor(com.mazenet.mani.gurugubera.R.color.colorPrimary))
                        textview.setTextColor(resources.getColor(R.color.colorWhite))
                        textview.background = resources.getDrawable(R.drawable.followupstatus_loyout_center)
                    } else if (first_or_last.equals("last")) {
                        textview.setBackgroundColor(resources.getColor(com.mazenet.mani.gurugubera.R.color.colorPrimary))
                        textview.setTextColor(resources.getColor(R.color.colorWhite))
                        textview.background = resources.getDrawable(R.drawable.followupstatus_loyout_right)
                    }

                }
            }
        }
    }

    fun reemoveview(layout: LinearLayout) {
        try {
            layout.removeAllViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun douwnloadBranches() {

        SelectedBranch = getPrefsString(Constants.selectedBranchid, "")
        val getBranchObject = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val branchRequest = getBranchObject.get_branches(loginparameters)
        branchRequest.enqueue(object : Callback<ArrayList<BranchModel>> {
            override fun onFailure(call: Call<ArrayList<BranchModel>>, t: Throwable) {

                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<BranchModel>>,
                response: Response<ArrayList<BranchModel>>
            ) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        branch_list = response.body()!!
                        System.out.println("branchlist ${branch_list}")
                        if (branch_list.size > 0) {
                            BaseUtils.masterdb.deleteBranchTable()
                            BaseUtils.masterdb.addBranches(branch_list)

                            if (SelectedBranch.equals("")) {
                                val obj = branch_list.get(0)
                                spn_branch.setText(obj.getBranchName())
                                setPrefsString(Constants.selectedBranchid, obj.getBranchId().toString())
                                setPrefsString(Constants.selectedBranchName, obj.getBranchName()!!)
                            } else {

                            }
                            brancharray = BaseUtils.masterdb.getBranches()

                        } else {
                            System.out.println("no show")

                        }
                    } else {

                    }
                } else {

                }
            }
        })
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
