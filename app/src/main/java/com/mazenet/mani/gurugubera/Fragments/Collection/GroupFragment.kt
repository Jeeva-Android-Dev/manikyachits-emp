package com.mazenet.mani.gurugubera.Fragments.Collection


import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.GroupsAdapter
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.GroupListModel
import com.mazenet.mani.gurugubera.Model.GroupdetailsModel
import com.mazenet.mani.gurugubera.Model.RemarksListmodel
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import com.mazenet.mani.gurugubera.Spinners.RemarkSpinnerdilog
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.masterdb
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_group.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class GroupFragment : BaseFragment() {
    lateinit var grouplistAdapter: GroupsAdapter
    lateinit var txt_gf_custname: TextView
    lateinit var enter_remark: Button
    var entrolId : String =""
    var custid: String = ""
    var custname: String = ""
    var custcode: String = ""
    var custmobile: String = ""
    var custphone: String = ""

    var Grouplist = ArrayList<GroupListModel>()
    internal var remark_popup: PopupWindow? = null
    lateinit var Recycler_groups: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.fragment_group, container, false)
        hideProgressDialog()
        (activity as HomeActivity).setActionBarTitle("Generate Receipt")
        Recycler_groups = view.findViewById(com.mazenet.mani.gurugubera.R.id.Recycler_groups) as RecyclerView
        txt_gf_custname = view.findViewById(com.mazenet.mani.gurugubera.R.id.txt_gf_custname) as TextView
        enter_remark = view.findViewById(com.mazenet.mani.gurugubera.R.id.enter_remark) as Button
        custid = arguments!!.getString("cust_id").toString()
        custname = arguments!!.getString("cust_name").toString()
        //entrollment_id = arguments!!.getString("enroll")
        println("custid ${arguments!!.getString("cust_id")}")
        try {
            println("cusco ${arguments!!.getString("cust_code")}")
            custcode = arguments!!.getString("cust_code").toString()
        } catch (e: Exception) {
            custcode = " "
        }
        try {
            custmobile = arguments!!.getString("cust_mobile").toString()
        } catch (e: Exception) {
            custmobile = " "
        }
        try {
            custphone = arguments!!.getString("cust_phone").toString()
        } catch (e: Exception) {
            custphone = " "
        }


        txt_gf_custname.setText("Customer : " + custname)
        Recycler_groups.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_groups.setLayoutManager(mLayoutManager)
//        Recycler_groups.addItemDecoration(MyDividerItemDecoration(context,LinearLayoutManager.VERTICAL,4))
//        Recycler_groups.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        Recycler_groups.setItemAnimator(DefaultItemAnimator())
        if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
            if (checkForInternet()) {
                get_groups()
            } else {
                toast("No Internet Connection !")
            }
        } else {
            val offlinelist = BaseUtils.offlinedb.getCustomerwiseGroupsList(custid)
            if (offlinelist.size > 0) {
                integrateList(offlinelist)
            } else {
                toast("No Enrollments Available !")
            }
        }
        enter_remark.setOnClickListener {
            showremark(context as Activity)
        }

        view.before_enroll_btn.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putString("enrolid", entrolId)
            bundle.putString("cust_name", custname)
            bundle.putString("cust_id", custid)
            bundle.putString("cust_code", custcode)
            bundle.putString("cust_mobile", custmobile)
            bundle.putString("cust_phone", custphone)

            doFragmentTransactionWithBundle(
                Before_enroll_ReceiptFragment(),
                Constants.GROUP_TAG,
                true, bundle
            )

        }

        return view
    }

    private fun get_groups() {
        showProgressDialog()
        var resultlist = java.util.ArrayList<GroupdetailsModel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("customer_id", custid)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        println(custid)
        val LeadListRequest = getleadslist.get_enrollments(loginparameters)
        LeadListRequest.enqueue(object : Callback<ArrayList<GroupdetailsModel>> {
            override fun onFailure(call: Call<ArrayList<GroupdetailsModel>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<GroupdetailsModel>>, response: Response<ArrayList<GroupdetailsModel>>
            ) {
                hideProgressDialog()
                when {
                    response.isSuccessful -> {
                        when {
                            response.code().equals(200) -> {
                                resultlist = response.body()!!
                                System.out.println("recycler ${response.body()}")
                                if (resultlist.size > 0) {
                                    masterdb.deleteGroupDetailsTable()
                                    masterdb.deleteInstallmentsDetailsTable()
                                    masterdb.addGroupDetails(resultlist)
                                    for (i in resultlist.indices) {
                                        val installlist = response.body()!!.get(i).getInstallmentsDet()!!
                                        if (!installlist.isNullOrEmpty()) {
                                            masterdb.addInstallmentDetails(installlist)
                                        }
                                    }
                                    integrateList(masterdb.getenrollmentList())
                                } else {
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    fun integrateList(leadslist: ArrayList<GroupListModel>) {
        Grouplist.clear()
        Grouplist.addAll(leadslist)

        grouplistAdapter = GroupsAdapter(Grouplist, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                val enrolid = Grouplist.get(position).enrolid
                val bundle: Bundle = Bundle()
                bundle.putString("enrolid", enrolid)
                bundle.putString("cust_name", custname)
                bundle.putString("cust_id", custid)
                bundle.putString("cust_code", custcode)
                bundle.putString("cust_mobile", custmobile)
                bundle.putString("cust_phone", custphone)
                doFragmentTransactionWithBundle(ReceiptFragment(), Constants.RECEIPTING_FRAG, true, bundle)
            }

            override fun onLongClicked(position: Int) {
            }
        })
        Recycler_groups.setAdapter(grouplistAdapter)
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }

    fun showremark(con: Activity) {
        hideProgressDialog()
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.remark_layout, null)
        dialogBuilder.setView(dialogView)
        var selected_followupdate: String = ""
        var remarksarray = ArrayList<RemarksListmodel>()
        if (masterdb.RemarksListSize() > 0) {
            remarksarray.clear()
            remarksarray = masterdb.getRemarks()
        }
        var remarksSpinner: RemarkSpinnerdilog
        var remarkid = ""
        val editText = dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.edt_remark) as EditText
        val img_folowup = dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.img_folowup) as ImageButton
        val fab_add_rem =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.fab_add_rem) as FloatingActionButton
        val btn_submitremark =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.btn_submitremark) as Button
        val alertDialog = dialogBuilder.create()
        remarksSpinner = RemarkSpinnerdilog(
            con, remarksarray,
            "Select Remark"
        )
        fab_add_rem.setOnClickListener {
            alertDialog.dismiss()
            add_new_remark_dilog()
        }
        img_folowup.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1, R.style.MyDialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val newDate = Calendar.getInstance()
                        newDate.set(year, monthOfYear, dayOfMonth)
                        var df = SimpleDateFormat("yyyy-MM-dd")
                        try {
                            selected_followupdate = df.format(newDate.time)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH)
                )
            }!!
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            fromDatePickerDialog.setTitle("Followup date")
            fromDatePickerDialog.show()
        }
        editText.setOnClickListener {
            remarksSpinner.showSpinerDialog()
        }

        remarksSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {
                editText.setText(grpname.toString())
                remarkid = position.toString()
            }
        })
        btn_submitremark.setOnClickListener {
            val remark = editText.text.toString()
            if (remark.isNullOrEmpty()) {
                toast("Enter Remark")
            } else {
                store_remark(remarkid, selected_followupdate)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    fun store_remark(remarkid: String, followupdate: String) {
        val addleadservice = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("customer_id", custid)
        loginparameters.put("remark_id", remarkid)
        loginparameters.put("remark_date", BaseUtils.CurrentDate_ymd())
        loginparameters.put("remark_time", BaseUtils.Currenttime())
        loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
        loginparameters.put("next_followup_date", followupdate)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val addlead_req = addleadservice.store_remarks(loginparameters)
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
                            toast(response.body()!!.getMsg()!!)
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

    fun add_new_remark_dilog() {
        val dialogBuilder = AlertDialog.Builder(context, com.mazenet.mani.gurugubera.R.style.MyDialogTheme)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.add_remark_dilog, null)
        dialogBuilder.setView(dialogView)
        val edt_remark =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.edt_remark) as EditText

        dialogBuilder
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                CloseKeyBoard()
            }
        dialogBuilder.setPositiveButton("Add", null)

        edt_remark.requestFocus()
        ShowKeyBoard()
        val alertDialog = dialogBuilder.create()
        alertDialog.setOnShowListener(DialogInterface.OnShowListener {
            val b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            b.setOnClickListener(View.OnClickListener {
                // TODO Do something
                if (edt_remark.text.trim().isNotEmpty()) {
                    CloseKeyBoard()
                    add_ne_remark(edt_remark.text.toString(), alertDialog)
                    alertDialog.dismiss()
                } else {
                    edt_remark.requestFocus()
                    ShowKeyBoard()
                }
            })
        })
        alertDialog.show()
    }

    fun get_remarks() {
        showProgressDialog()
        var remarklist = ArrayList<RemarksListmodel>()
        val getBranchObject = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = java.util.HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val branchRequest = getBranchObject.get_remarks(loginparameters)
        branchRequest.enqueue(object : Callback<ArrayList<RemarksListmodel>> {
            override fun onFailure(call: Call<ArrayList<RemarksListmodel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<RemarksListmodel>>,
                response: Response<ArrayList<RemarksListmodel>>
            ) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        remarklist = response.body()!!
                        if (remarklist.size > 0) {
                            masterdb.deleteRemarkTable()
                            masterdb.addRemarks(remarklist)
                            showremark(context as Activity)
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

    fun add_ne_remark(remark: String, dilog: AlertDialog) {
        val addleadservice = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("remark_name", remark)
        loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val addlead_req = addleadservice.add_nrew_remark(loginparameters)
        addlead_req.enqueue(object : Callback<successmsgmodel> {
            override fun onFailure(call: Call<successmsgmodel>, t: Throwable) {
                toast(t.toString())
                hideProgressDialog()
                t.printStackTrace()
                dilog.dismiss()
            }

            override fun onResponse(
                call: Call<successmsgmodel>,
                response: Response<successmsgmodel>
            ) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    if (response.code().equals(200)) {
                        if (response.body()!!.getStatus().equals("Success")) {
                            toast(response.body()!!.getMsg()!!)
                            dilog.dismiss()
                            get_remarks()
                        } else {
                            toast(response.body()!!.getMsg()!!)
                        }
                    } else {
                        System.out.println("no show")
                    }
                } else {
                    dilog.dismiss()
                    hideProgressDialog()
                }
            }
        })
    }
}
