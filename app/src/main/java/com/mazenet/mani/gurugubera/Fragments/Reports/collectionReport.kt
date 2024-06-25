package com.mazenet.mani.gurugubera.Fragments.Reports

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ShowCollReportList
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Fragments.Collection.ViewReceiptPreview
import com.mazenet.mani.gurugubera.Model.*
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.BranchSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.CollAreaSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.GroupSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_collection_report.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class collectionReport : BaseFragment() {

    val customerlist = ArrayList<CollReportmodel>()
    val customerlist_showing = ArrayList<CollReportmodel>()
    lateinit var recycler_collreports: RecyclerView
    lateinit var collReportAdapte: ShowCollReportList
    var CollAreaList = ArrayList<CollAreaListmodel>()
    lateinit var btn_sel_branch: TextView
    var SelectedBranch: String = ""
    var filtered_groupid: String = ""
    var filtered_groupname: String = ""
    var filtered_areaid: String = ""
    var filtered_areaname: String = ""
    var filtered_startdate: String = ""
    var filtered_todate: String = ""
    var brancharray = ArrayList<BranchModel>()
    lateinit var groupspinner: GroupSpinnerdilog
    lateinit var colareaspinner: CollAreaSpinnerdilog
    var grouplist = ArrayList<GroupsModel>()
    val ymd_df: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dmy_df: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
    lateinit var filter_popup: PopupWindow
    lateinit var BranchSpinner: BranchSpinnerdilog
    lateinit var filtercrumbs: LinearLayout
    lateinit var edt_rep_search: EditText
    lateinit var txt_cr_col1: TextView
    lateinit var txt_cr_col2: TextView
    lateinit var txt_cr_col3: TextView
    lateinit var txt_disp_total: TextView
    lateinit var txt_total: TextView
    lateinit var imagerror: ImageView

    fun addlayout(text: String, id: Int?, operation: Boolean?) {

        val lparams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        )
        lparams.marginEnd = 12
        val tv = TextView(context)
        tv.layoutParams = lparams
        tv.text = text
        tv.id = id!!
        tv.gravity = 1
        tv.textSize = 12f
        tv.setTextColor(resources.getColor(R.color.colorWhite))
        tv.background = resources.getDrawable(R.drawable.roundcrumbs)
        filtercrumbs.visibility = View.VISIBLE
        filtercrumbs.addView(tv)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as HomeActivity).setActionBarTitle("Collection Report")
        val view = inflater.inflate(R.layout.fragment_collection_report, container, false)
        recycler_collreports = view.findViewById(R.id.recycler_collreports) as RecyclerView
        btn_sel_branch = view.findViewById(R.id.btn_sel_branch) as TextView
        txt_cr_col1 = view.findViewById(R.id.txt_cr_col1) as TextView
        txt_cr_col2 = view.findViewById(R.id.txt_cr_col2) as TextView
        txt_cr_col3 = view.findViewById(R.id.txt_cr_col3) as TextView
        txt_total = view.findViewById(R.id.txt_total) as TextView
        txt_disp_total = view.findViewById(R.id.txt_disp_total) as TextView
        edt_rep_search = view.findViewById(R.id.edt_rep_search) as EditText
        filtercrumbs = view.findViewById(R.id.filtercrumbs) as LinearLayout
        recycler_collreports.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        recycler_collreports.setLayoutManager(mLayoutManager)
        recycler_collreports.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler_collreports.setItemAnimator(DefaultItemAnimator())
        imagerror = view.findViewById(R.id.imagerror) as ImageView
        txt_cr_col1.setText(resources.getString(R.string.customer_name))
        txt_cr_col2.setText(resources.getString(R.string.received))
        txt_cr_col3.setText(resources.getString(R.string.paymenttype))
        txt_disp_total.setText(resources.getString(R.string.received))
        if (BaseUtils.masterdb.BranchTableSize() > 0) {
            brancharray.clear()
            brancharray = BaseUtils.masterdb.getBranches()
            println("brc")
            BranchSpinner = BranchSpinnerdilog(
                activity, brancharray,
                "Select Branch Name"
            )
            if (getPrefsString(Constants.selectedBranchid, "").equals("")) {
                val obj = brancharray.get(0)
                btn_sel_branch.setText(obj.getBranchName())
                setPrefsString(Constants.selectedBranchid, obj.getBranchId().toString())
                setPrefsString(Constants.selectedBranchName, obj.getBranchName()!!)
            } else {
                btn_sel_branch.setText(getPrefsString(Constants.selectedBranchName, ""))
            }
            if(getPrefsString(Constants.daterange_from,"").isNotEmpty())
            {
                filtered_startdate = getPrefsString(Constants.daterange_from,"")
                filtered_todate = getPrefsString(Constants.daterange_to,"")
            }else
            {
                filtered_startdate = BaseUtils.CurrentDate_ymd()
                filtered_todate = BaseUtils.CurrentDate_ymd()
                setPrefsString(Constants.daterange_from,filtered_startdate)
                setPrefsString(Constants.daterange_to,filtered_todate)
            }
            get_Collectionlist(
                getPrefsString(Constants.tenantid, ""),
                getPrefsString(Constants.selectedBranchid, ""),
                filtered_groupid,
                filtered_areaid,
                filtered_startdate,
                filtered_todate
            )
        } else {
            douwnloadBranches()
        }

        groupspinner = GroupSpinnerdilog(
            activity, grouplist,
            "Select Group Name"
        )
//        filtered_startdate = BaseUtils.CurrentDate_ymd()
//        filtered_todate = BaseUtils.CurrentDate_ymd()
//        get_Collectionlist(
//            getPrefsString(Constants.tenantid, ""),
//            getPrefsString(Constants.selectedBranchid, ""),
//            filtered_groupid,
//            filtered_areaid,
//            filtered_startdate,
//            filtered_todate
//        )
        btn_sel_branch.setOnClickListener {
            BranchSpinner.showSpinerDialog()
        }

        BranchSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {

                SelectedBranch = position.toString()
                btn_sel_branch.setText(grpname)
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)
                get_Collectionlist(
                    getPrefsString(Constants.tenantid, ""),
                    getPrefsString(Constants.selectedBranchid, ""),
                    filtered_groupid,
                    filtered_areaid,
                    filtered_startdate,
                    filtered_todate
                )
            }
        })

        view.txt_filter.setOnClickListener {
            showfilter(context as Activity)
        }

        edt_rep_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                val text = edt_rep_search.getText().toString()
                if (text == "" || text == null) {
                    customerlist_showing.clear()
                    customerlist_showing.addAll(customerlist)
                    if (customerlist_showing.size > 0) {
                        recycler_collreports.setVisibility(View.VISIBLE)
                        collReportAdapte =
                            ShowCollReportList(customerlist_showing, object : AdapterClickListener {
                                override fun onPositionClicked(view: View, position: Int) {
                                    if (view.getTag().equals("listitem")) {
                                        get_receipt_details(
                                            customerlist_showing.get(position).getReceiptNo()!!,
                                            customerlist_showing.get(position).getReceiptType()!!
                                        )
                                    } else if (view.getTag().equals("custname")) {
                                    }
                                }

                                override fun onLongClicked(position: Int) {
                                }
                            })
                        recycler_collreports.setAdapter(collReportAdapte)

                    } else {

                        recycler_collreports.setVisibility(View.GONE)
                    }
                } else {
                    setnewadapter()
                    recycler_collreports.setVisibility(View.VISIBLE)
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
                // lv.setVisibility(View.GONE);
                if (customerlist_showing.size > 0) {
                    recycler_collreports.setVisibility(View.VISIBLE)
                } else {
                    recycler_collreports.setVisibility(View.GONE)
                }
            }
        })
        return view
    }

    private fun setnewadapter() {
        customerlist_showing.clear()

        var text = edt_rep_search.text.toString()
        text = text.toLowerCase(Locale.getDefault())
        if (text == null) {
            recycler_collreports.setVisibility(View.GONE)
            return
        } else {
            for (i in customerlist.indices) {
                val schedte = customerlist.get(i)
                var name = schedte.getCustomerName()
//                var mobile = schedte.getMOBILE()
                name = name!!.toLowerCase(Locale.getDefault())
//                mobile = mobile.toLowerCase(Locale.getDefault())
                if (name.contains(text)) {
                    customerlist_showing.add(customerlist.get(i))
                }
            }
        }
        if (customerlist_showing.size > 0) {
            recycler_collreports.setVisibility(View.VISIBLE)
            collReportAdapte = ShowCollReportList(customerlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("listitem")) {
                        get_receipt_details(
                            customerlist_showing.get(position).getReceiptNo()!!,
                            customerlist_showing.get(position).getReceiptType()!!
                        )
                    } else if (view.getTag().equals("custname")) {
                    }
                }

                override fun onLongClicked(position: Int) {
                }
            })
            recycler_collreports.setAdapter(collReportAdapte)
        } else {
            recycler_collreports.setVisibility(View.GONE)
        }
    }

    fun reemoveview() {
        try {
            filtercrumbs.removeAllViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun get_Collectionlist(
        tenant_id: String,
        branchid: String,
        group_id: String,
        area_id: String,
        startdate: String,
        enddate: String
    ) {
        txt_total.setText("0")
        reemoveview()
        if (!branchid.equals("")) {
            addlayout(getPrefsString(Constants.selectedBranchName, ""), 1, true)
        }
        if (!group_id.equals("")) {
            addlayout(filtered_groupname, 2, true)
        }
        if (!filtered_areaid.equals("")) {
            addlayout(filtered_areaname, 3, true)
        }
        if (!filtered_startdate.equals("")) {
            if (!filtered_todate.equals("")) {
                addlayout(filtered_startdate + " - " + filtered_todate, 4, true)
                setPrefsString(Constants.daterange_from,filtered_startdate)
                setPrefsString(Constants.daterange_to,filtered_todate)
            } else {
                filtered_startdate = ""
                toast("Select End date")
            }
        }
        if (checkForInternet()) {
            showProgressDialog()
            println("fetching")
            var resultlist = java.util.ArrayList<CollReportmodel>()
            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", tenant_id)
            loginparameters.put("branch_id", branchid)
            loginparameters.put("group_id", group_id)
            loginparameters.put("collection_area_id", area_id)
            loginparameters.put("start_date", startdate)
            loginparameters.put("end_date", enddate)
            loginparameters.put("db",getPrefsString(Constants.db,""))
            val LeadListRequest = getleadslist.get_collection_reports(loginparameters)
            LeadListRequest.enqueue(object : Callback<ArrayList<CollReportmodel>> {

                override fun onFailure(call: Call<ArrayList<CollReportmodel>>, t: Throwable) {
                    hideProgressDialog()
                    t.printStackTrace()
                    recycler_collreports.visibility = View.GONE
                    imagerror.visibility = View.VISIBLE
                }

                override fun onResponse(
                    call: Call<ArrayList<CollReportmodel>>,
                    response: Response<ArrayList<CollReportmodel>>
                ) {
                    hideProgressDialog()
                    when {
                        response.isSuccessful -> {

                            when {
                                response.code().equals(200) -> {
                                    resultlist = response.body()!!
                                    if (resultlist.size > 0) {
                                        recycler_collreports.visibility = View.VISIBLE
                                        integrateList(response.body()!!)
                                    } else {
                                        recycler_collreports.visibility = View.GONE
                                        imagerror.visibility = View.VISIBLE
                                    }
                                }
                                else -> {
                                    recycler_collreports.visibility = View.GONE
                                    imagerror.visibility = View.VISIBLE
                                }
                            }

                        }
                        else -> {
                            hideProgressDialog()
                            recycler_collreports.visibility = View.GONE
                            imagerror.visibility = View.VISIBLE
                        }
                    }
                }
            })
        } else {
            toast(resources.getString(R.string.nointernet))
        }
    }

    private fun integrateList(leadslist: ArrayList<CollReportmodel>) {
        if (leadslist.size > 0) {
            customerlist.clear()
            customerlist_showing.clear()
            customerlist.addAll(leadslist)
            customerlist_showing.addAll(leadslist)
            imagerror.visibility = View.GONE
            collReportAdapte = ShowCollReportList(customerlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("listitem")) {
                        get_receipt_details(
                            customerlist_showing.get(position).getReceiptNo()!!,
                            customerlist_showing.get(position).getReceiptType()!!
                        )
                    } else if (view.getTag().equals("custname")) {
                    }
                }

                override fun onLongClicked(position: Int) {
                }
            })
            recycler_collreports.setAdapter(collReportAdapte)
            try {
                var total = 0
                for (i in customerlist_showing) {
                    total += Constants.isEmtytoZero(i.getReceivedAmount()!!).toInt()
                }
                txt_total.setText(Constants.money_convertor(total.toString(), false))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            toast("No Customers Available")
        }
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }

    private fun get_receipt_details(receiptno: String, receipt_type: String) {
        showProgressDialog()

        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = java.util.HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("receipt_no", receiptno)
        loginparameters.put("receipt_type", receipt_type)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val LeadListRequest = getleadslist.get_receipt_details(loginparameters)
        LeadListRequest.enqueue(object : Callback<ReceiptDetailsModel> {
            override fun onFailure(call: Call<ReceiptDetailsModel>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ReceiptDetailsModel>, response: Response<ReceiptDetailsModel>
            ) {
                when {
                    response.isSuccessful -> {
                        hideProgressDialog()
                        when {
                            response.code().equals(200) -> {
                                val bundle = Bundle()
                                bundle.putString("recptno", response.body()!!.getReceiptNo())
                                bundle.putString(
                                    "recptdate",
                                    response.body()!!.getReceiptDate() + " / " + response.body()!!.getReceiptTime()
                                )
                                bundle.putString("customername", response.body()!!.getCustomerName())
                                bundle.putString("customermobile", response.body()!!.getMobileNo())
                                bundle.putString("customerid", response.body()!!.getCustomerCode())
                                bundle.putString("groupname", response.body()!!.getGroupname())
                                bundle.putString("ticketno", response.body()!!.getTicketno())
                                bundle.putString("penalty", response.body()!!.getPenaltyAmount())
                                bundle.putString("bonus", response.body()!!.getBonusAmount())
                                bundle.putString("receivedamount", response.body()!!.getReceivedAmount())
                                bundle.putString("paymentmode", response.body()!!.getPayemntType())
                                bundle.putString("chequeno", response.body()!!.getChequeNo())
                                bundle.putString("chequedate", response.body()!!.getChequeDate())
                                bundle.putString("chequebank", response.body()!!.getBankNameId())
                                bundle.putString("chequebranch", response.body()!!.getBranchName())
                                bundle.putString("transactionno", response.body()!!.getTransactionNo())
                                bundle.putString("transactiondate", response.body()!!.getTransactionDate())
                                bundle.putString("installmentno", response.body()!!.getInstallmentno())
                                bundle.putString("isprinted", response.body()!!.getIsPrinted())
                                doFragmentTransactionWithBundle(ViewReceiptPreview(), "viewpreview", true, bundle)
                            }
                        }
                    }
                    else -> hideProgressDialog()
                }
            }
        })

    }

    fun showfilter(activity: Activity) {

        val layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewGroup = activity.findViewById(R.id.filterlayout) as? ConstraintLayout
        val layout = layoutInflater.inflate(R.layout.filter_screen, viewGroup)

        filter_popup = PopupWindow(activity)
        filter_popup!!.setContentView(layout)
        filter_popup!!.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT)
        filter_popup!!.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT)
        filter_popup!!.setAnimationStyle(R.style.AnimationPopup)
        filter_popup!!.setFocusable(true)
        filter_popup!!.setBackgroundDrawable(null)
        filter_popup!!.showAtLocation(layout, Gravity.CENTER, 0, 0)


        val txt_grpname = layout.findViewById(R.id.txt_grpname) as TextView
        val txt_colarea = layout.findViewById(R.id.txt_colarea) as TextView
        val txt_daterange = layout.findViewById(R.id.txt_daterange) as TextView
        val btn_applyfilter = layout.findViewById(R.id.btn_applyfilter) as Button
        val btn_resetfilter = layout.findViewById(R.id.btn_resetfilter) as Button
        val img_close = layout.findViewById(R.id.img_close) as ImageView
        val col_area_layout = layout.findViewById(R.id.col_area_layout) as ConstraintLayout
        val group_layout = layout.findViewById(R.id.group_layout) as ConstraintLayout
        val daterange_layout = layout.findViewById(R.id.daterange_layout) as ConstraintLayout
        img_close.setOnClickListener {
            filter_popup.dismiss()
        }
        btn_resetfilter.setOnClickListener {
            filtered_groupid = ""
            filtered_groupname = ""
            filtered_startdate = BaseUtils.CurrentDate_ymd()
            filtered_todate = BaseUtils.CurrentDate_ymd()
            filtered_areaid = ""
            filtered_areaname = ""
            txt_grpname.setText(resources.getString(R.string.selct_group))
            txt_colarea.setText(resources.getString(R.string.sel_coll_area))
            txt_daterange.setText(resources.getString(R.string.sel_daterange))
        }
        if (!filtered_groupid.equals("")) {
            txt_grpname.setText(filtered_groupname)
        }
        if (!filtered_areaid.equals("")) {
            txt_colarea.setText(filtered_areaname)
        }
        if (!filtered_startdate.equals("")) {
            txt_daterange.setText("$filtered_startdate - $filtered_todate")
        }
        btn_applyfilter.setOnClickListener {
            get_Collectionlist(
                getPrefsString(Constants.tenantid, ""),
                getPrefsString(Constants.selectedBranchid, ""),
                filtered_groupid,
                filtered_areaid,
                filtered_startdate,
                filtered_todate
            )
            filter_popup.dismiss()
        }

        //------------------------get_groups-----------------------------------
        SelectedBranch = getPrefsString(Constants.selectedBranchid, "")
        if (SelectedBranch.equals("")) {
            val SelectedBranchName = brancharray.get(0).getBranchName().toString()
            btn_sel_branch.setText(SelectedBranchName)
            SelectedBranch = brancharray.get(0).getBranchId().toString()
        } else {
            val SelectedBranchName = getPrefsString(Constants.selectedBranchName, "")
            btn_sel_branch.setText(SelectedBranchName)
        }
        val getBranchObject = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("branch_id", SelectedBranch)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val branchRequest = getBranchObject.get_groupslist(loginparameters)
        branchRequest.enqueue(object : Callback<ArrayList<GroupsModel>> {
            override fun onFailure(call: Call<ArrayList<GroupsModel>>, t: Throwable) {
                group_layout.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<GroupsModel>>, response: Response<ArrayList<GroupsModel>>) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        grouplist = response.body()!!
                        if (grouplist.size > 0) {
                            group_layout.visibility = View.VISIBLE
                            groupspinner = GroupSpinnerdilog(
                                activity, grouplist,
                                "Select Group Name"
                            )
                            groupspinner.bindOnSpinerListener(object : OnSpinnerItemClick {
                                override fun onClick(item: String, position: Int, grpname: String) {
                                    txt_grpname.setText(grpname)
                                    filtered_groupid = position.toString()
                                    filtered_groupname = grpname
                                }
                            })
                        } else {
                            group_layout.visibility = View.GONE
                        }
                    } else {
                        group_layout.visibility = View.GONE
                    }
                } else {
                    group_layout.visibility = View.GONE
                }
            }
        })
        //-------------------------------get_collection Area----------------------------------------------

        val getcollAreaObj = RetrofitBuilder.buildservice(ICallService::class.java)
        val collareaparams = java.util.HashMap<String, String>()
        collareaparams.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        collareaparams.put("db",getPrefsString(Constants.db,""))
        val collareaReq = getcollAreaObj.get_collection_area(collareaparams)
        collareaReq.enqueue(object : Callback<ArrayList<CollAreaListmodel>> {
            override fun onFailure(call: Call<ArrayList<CollAreaListmodel>>, t: Throwable) {
                col_area_layout.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<CollAreaListmodel>>,
                response: Response<ArrayList<CollAreaListmodel>>
            ) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        CollAreaList = response.body()!!
                        if (CollAreaList.size > 0) {
                            col_area_layout.visibility = View.VISIBLE
                            colareaspinner = CollAreaSpinnerdilog(
                                activity, CollAreaList,
                                "Select Collection Area"
                            )
                            colareaspinner.bindOnSpinerListener(object : OnSpinnerItemClick {
                                override fun onClick(item: String, position: Int, grpname: String) {
                                    txt_colarea.setText(grpname)
                                    filtered_areaid = position.toString()
                                    filtered_areaname = grpname
                                }
                            })
                        } else {
                            col_area_layout.visibility = View.GONE
                        }
                    } else {
                        col_area_layout.visibility = View.GONE
                    }
                } else {
                    col_area_layout.visibility = View.GONE
                }
            }
        })

        txt_grpname.setOnClickListener {
            groupspinner.showSpinerDialog()
        }
        txt_colarea.setOnClickListener {
            colareaspinner.showSpinerDialog()
        }
        txt_daterange.setOnClickListener {
            fromdate(txt_daterange)
        }

    }


    fun fromdate(textrange: TextView) {
        val newCalendar = Calendar.getInstance()
        val fromDatePickerDialog = DatePickerDialog(
            context,
            R.style.MyDialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                val calendar = Calendar.getInstance()


                newDate.set(year, monthOfYear, dayOfMonth)
                calendar.set(year, monthOfYear, dayOfMonth)

                try {
                    val from = ymd_df.format(newDate.time)
                    filtered_startdate = from
                    todate(textrange)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        fromDatePickerDialog.getDatePicker().maxDate = (System.currentTimeMillis())
        fromDatePickerDialog.setTitle("Choose From date")

        fromDatePickerDialog.show()
    }

    fun todate(textrange: TextView) {
        val newCalendar = Calendar.getInstance()
        val fromDatePickerDialog = DatePickerDialog(
            context,
            R.style.MyDialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                val calendar = Calendar.getInstance()


                newDate.set(year, monthOfYear, dayOfMonth)
                calendar.set(year, monthOfYear, dayOfMonth)

                try {
                    val to = ymd_df.format(newDate.time)
                    filtered_todate = to
                    textrange.setText(filtered_startdate + " - " + filtered_todate)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        fromDatePickerDialog.getDatePicker().maxDate = (System.currentTimeMillis())
        fromDatePickerDialog.setTitle("Choose To date")

        fromDatePickerDialog.show()
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
                        val branch_list = response.body()!!
                        if (branch_list.size > 0) {
                            BaseUtils.masterdb.deleteBranchTable()
                            BaseUtils.masterdb.addBranches(branch_list)
                            if (SelectedBranch.equals("")) {
                                val obj = branch_list.get(0)
                                btn_sel_branch.setText(obj.getBranchName())
                                setPrefsString(Constants.selectedBranchid, obj.getBranchId().toString())
                                setPrefsString(Constants.selectedBranchName, obj.getBranchName()!!)
                                filtered_startdate = BaseUtils.CurrentDate_ymd()
                                filtered_todate = BaseUtils.CurrentDate_ymd()
                                get_Collectionlist(
                                    getPrefsString(Constants.tenantid, ""),
                                    getPrefsString(Constants.selectedBranchid, ""),
                                    filtered_groupid,
                                    filtered_areaid,
                                    filtered_startdate,
                                    filtered_todate
                                )
                            } else {
                            }
                            brancharray = BaseUtils.masterdb.getBranches()
                            BranchSpinner = BranchSpinnerdilog(
                                activity, brancharray,
                                "Select Branch Name"
                            )
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
//    override fun onDateSet(
//        view: com.borax12.materialdaterangepicker.date.DatePickerDialog,
//        year: Int,
//        monthOfYear: Int,
//        dayOfMonth: Int,
//        yearEnd: Int,
//        monthOfYearEnd: Int,
//        dayOfMonthEnd: Int
//    ) {
//       val from = Calendar.getInstance()
//       val to = Calendar.getInstance()
//       val ymd_df = SimpleDateFormat("yyyy-MM-dd")
//        from.set(year, monthOfYear, dayOfMonth)
//
//        to.set(yearEnd, monthOfYearEnd, dayOfMonthEnd)
//
//        filtered_startdate = ymd_df.format(from.getTime())
//        filtered_todate = ymd_df.format(to.getTime())
//
//    }
}
