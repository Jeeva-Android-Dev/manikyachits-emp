package com.mazenet.mani.gurugubera.Fragments.Collection


import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ShowReceiptsList
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.GeneratedReceiptModel
import com.mazenet.mani.gurugubera.Model.ReceiptDetailsModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_view_receipts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ViewReceiptsFragment : BaseFragment() {
    lateinit var txt_vr_receiptdate: Button
    lateinit var edt_vr_search: EditText
    lateinit var table_header: LinearLayout
    var resultlist = ArrayList<GeneratedReceiptModel>()
    var resultlist_showing = ArrayList<GeneratedReceiptModel>()
    lateinit var showReceiptsAdapters: ShowReceiptsList
    lateinit var recycler_view_receips: RecyclerView
    lateinit var btn_onlinerecpts: Button
    lateinit var btn_off_recpts: Button
    lateinit var btn_feedbacks: Button
    lateinit var view_online_recpts: View
    lateinit var view_off_recpts: View
    lateinit var view_feedbacks: View
    lateinit var txt_total: TextView
    lateinit var txt_col1: TextView
    lateinit var txt_col2: TextView
    lateinit var txt_col3: TextView
    lateinit var df: SimpleDateFormat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view = inflater.inflate(R.layout.fragment_view_receipts, container, false)
        (activity as HomeActivity).setActionBarTitle("View Receipts")
        recycler_view_receips = view.findViewById(R.id.recycler_view_receips) as RecyclerView
        txt_vr_receiptdate = view.findViewById(R.id.txt_vr_receiptdate) as Button
        edt_vr_search = view.findViewById(R.id.edt_vr_search) as EditText
        table_header = view.findViewById(R.id.table_header) as LinearLayout
        btn_onlinerecpts = view.findViewById(R.id.btn_onlinerecpts) as Button
        btn_off_recpts = view.findViewById(R.id.btn_off_recpts) as Button
        btn_feedbacks = view.findViewById(R.id.btn_feedbacks) as Button
        view_online_recpts = view.findViewById(R.id.view_online_recpts) as View
        view_off_recpts = view.findViewById(R.id.view_off_recpts) as View
        view_feedbacks = view.findViewById(R.id.view_feedbacks) as View
        txt_total = view.findViewById(R.id.txt_total) as TextView
        txt_col1 = view.findViewById(R.id.txt_col1) as TextView
        txt_col2 = view.findViewById(R.id.txt_col2) as TextView
        txt_col3 = view.findViewById(R.id.txt_col3) as TextView
        df = SimpleDateFormat("yyyy-MM-dd")
        recycler_view_receips.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        recycler_view_receips.setLayoutManager(mLayoutManager)
        recycler_view_receips.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler_view_receips.setItemAnimator(DefaultItemAnimator())
        init()
        btn_onlinerecpts.setOnClickListener {
            setPrefsString(Constants.showing_online_view_receipts, "online")
            btn_onlinerecpts.setTextColor(resources.getColor(R.color.colorWhite))
            btn_off_recpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.grey_a100))
            view_online_recpts.visibility = View.VISIBLE
            view_off_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.GONE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.paymenttype)
            txt_col3.text = resources.getString(R.string.received)
            if (checkForInternet()) {
                if (getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    get_receipts(BaseUtils.CurrentDate_ymd())
                } else {
                    get_receipts(getPrefsString(Constants.online_cache_date, ""))
                }
            } else {
                txt_total.text = "0"
                recycler_view_receips.visibility = View.GONE
                toast(Constants.No_receipts)
            }

        }
        btn_off_recpts.setOnClickListener {
            setPrefsString(Constants.showing_online_view_receipts, "offline")
            btn_onlinerecpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.grey_a100))
            btn_off_recpts.setTextColor(resources.getColor(R.color.colorWhite))
            view_online_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.GONE
            view_off_recpts.visibility = View.VISIBLE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.paymenttype)
            txt_col3.text = resources.getString(R.string.received)
            var resultlist = ArrayList<GeneratedReceiptModel>()
            val offlinelist = BaseUtils.offlinedb.getOFf_receipts_list(BaseUtils.CurrentDate_ymd())
            for (i in offlinelist) {
                val recptmodel = GeneratedReceiptModel()
                recptmodel.setCustomerName(i.custname)
                recptmodel.setCustomerId(i.of_customerid)
                recptmodel.setPaymentMode(BaseUtils.masterdb.get_paymentTypeName(i.of_paytypeid))
                recptmodel.setReceivedAmount(i.of_receivedamount)
                recptmodel.setReceiptNo(i.of_offrecno)
                resultlist.add(recptmodel)
            }
            if (resultlist.size > 0) {
                integrateList(resultlist)
            } else {
                txt_total.text = "0"
                recycler_view_receips.visibility = View.GONE
                toast(Constants.No_offline_receipts)
            }
        }
        btn_feedbacks.setOnClickListener {
            setPrefsString(Constants.showing_online_view_receipts, "feedback")
            btn_onlinerecpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.colorWhite))
            btn_off_recpts.setTextColor(resources.getColor(R.color.grey_a100))
            view_online_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.VISIBLE
            view_off_recpts.visibility = View.GONE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.feedback)
            txt_col3.text = resources.getString(R.string.str_nextfollowup)
            if (checkForInternet()) {
                if (getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    get_feedbacks(BaseUtils.CurrentDate_ymd())
                } else {
                    get_feedbacks(getPrefsString(Constants.online_cache_date, ""))
                }
            } else {
                toast(resources.getString(R.string.nointernet))
            }
        }
        txt_vr_receiptdate.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = DatePickerDialog(
                context, R.style.MyDialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)

                    try {
                        val Receiptdate = df.format(newDate.time)
                        if (getPrefsString(Constants.showing_online_view_receipts, "").equals("feedback")) {
                            get_feedbacks(Receiptdate)
                        } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals("online")) {
                            get_receipts(Receiptdate)
                        } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals("offline")) {
                            var resultlist = ArrayList<GeneratedReceiptModel>()
                            val offlinelist = BaseUtils.offlinedb.getOFf_receipts_list(BaseUtils.CurrentDate_ymd())
                            for (i in offlinelist) {
                                val recptmodel = GeneratedReceiptModel()
                                recptmodel.setCustomerName(i.custname)
                                recptmodel.setCustomerId(i.of_customerid)
                                recptmodel.setPaymentMode(BaseUtils.masterdb.get_paymentTypeName(i.of_paytypeid))
                                recptmodel.setReceivedAmount(i.of_receivedamount)
                                recptmodel.setReceiptNo(i.of_offrecno)
                                resultlist.add(recptmodel)
                            }
                            if (resultlist.size > 0) {
                                integrateList(resultlist)
                            } else {
                                txt_total.text = "0"
                                recycler_view_receips.visibility = View.GONE
                                toast(Constants.No_offline_receipts)
                            }
                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
            )
            fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis())
            fromDatePickerDialog.setTitle("Receipt date")
            fromDatePickerDialog.show()
        }
        edt_vr_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                val text = edt_vr_search.getText().toString()
                if (text.equals("", ignoreCase = true) || text.equals(null, ignoreCase = true)) {
                    resultlist_showing.clear()
                    resultlist_showing.addAll(resultlist)
                    if (resultlist_showing.size > 0) {
                        recycler_view_receips.visibility = View.VISIBLE
                        showReceiptsAdapters =
                            ShowReceiptsList(resultlist_showing, object : AdapterClickListener {
                                override fun onPositionClicked(view: View, position: Int) {
                                    if (getPrefsBoolean(Constants.showing_online_view_receipts, true)) {
                                        if (view.getTag().equals("listitem")) {
                                            if (getPrefsString(
                                                    Constants.showing_online_view_receipts,
                                                    ""
                                                ).equals("feedback")
                                            ) {
                                            } else {
                                                get_receipt_details(
                                                    resultlist_showing.get(position).getReceiptNo().toString(),
                                                    resultlist_showing.get(position).getReceipt_type()!!
                                                )
                                            }
                                        } else if (view.getTag().equals("custname")) {
                                        }
                                    } else {
                                        get_offline_recept_details(resultlist_showing.get(position).getReceiptNo().toString())
                                    }
                                }
                                override fun onLongClicked(position: Int) {
                                }
                            }, getPrefsString(Constants.showing_online_view_receipts, ""))
                        showReceiptsAdapters.notifyDataSetChanged()
                        recycler_view_receips.setAdapter(showReceiptsAdapters)
                    } else {
                        recycler_view_receips.visibility = View.GONE
                    }
                } else {
                    setnewadapter()
                    recycler_view_receips.visibility = View.VISIBLE

                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                edt_vr_search.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(
                        cs: CharSequence, arg1: Int, arg2: Int,
                        arg3: Int
                    ) {
                        val text = edt_vr_search.getText().toString()
                        if (text.equals("", ignoreCase = true) || text.equals(null, ignoreCase = true)) {
                            resultlist_showing.clear()
                            resultlist_showing.addAll(resultlist)
                            if (resultlist_showing.size > 0) {
                                recycler_view_receips.visibility = View.VISIBLE
                                showReceiptsAdapters =
                                    ShowReceiptsList(resultlist_showing, object : AdapterClickListener {
                                        override fun onPositionClicked(view: View, position: Int) {

                                            if (view.getTag().equals("listitem")) {
                                                if (getPrefsString(
                                                        Constants.showing_online_view_receipts,
                                                        ""
                                                    ).equals("feedback")
                                                ) {

                                                } else if (getPrefsString(
                                                        Constants.showing_online_view_receipts,
                                                        ""
                                                    ).equals(
                                                        "online"
                                                    )
                                                ) {
                                                    get_receipt_details(
                                                        resultlist_showing.get(position).getReceiptNo().toString(),
                                                        resultlist_showing.get(position).getReceipt_type()!!
                                                    )
                                                } else if (getPrefsString(
                                                        Constants.showing_online_view_receipts,
                                                        ""
                                                    ).equals(
                                                        "offline"
                                                    )
                                                ) {
                                                    get_offline_recept_details(resultlist_showing.get(position).getReceiptNo().toString())
                                                }
                                            } else if (view.getTag().equals("custname")) {
                                            }

                                        }

                                        override fun onLongClicked(position: Int) {
                                        }
                                    }, getPrefsString(Constants.showing_online_view_receipts, ""))
                                showReceiptsAdapters.notifyDataSetChanged()
                                recycler_view_receips.setAdapter(showReceiptsAdapters)

                            } else {
                                recycler_view_receips.visibility = View.GONE
                            }
                        } else {
                            setnewadapter()
                            recycler_view_receips.visibility = View.VISIBLE

                        }
                    }

                    override fun beforeTextChanged(
                        arg0: CharSequence, arg1: Int,
                        arg2: Int, arg3: Int
                    ) {
                    }

                    override fun afterTextChanged(arg0: Editable) {
                        // TODO Auto-generated method stub
                        if (resultlist_showing.size > 0) {
                            recycler_view_receips.visibility = View.VISIBLE
                        } else {
                            recycler_view_receips.visibility = View.GONE
                        }
                    }
                })
            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
                // lv.setVisibility(View.GONE);
                if (resultlist_showing.size > 0) {
                    recycler_view_receips.visibility = View.VISIBLE
                } else {
                    recycler_view_receips.visibility = View.GONE
                }
            }
        })
        return view
    }

    private fun get_receipts(receiptdate: String) {
        if (checkForInternet()) {
            txt_vr_receiptdate.setText(receiptdate)
            showProgressDialog()
            setPrefsString(Constants.online_cache_date, receiptdate);
            var resultlist = ArrayList<GeneratedReceiptModel>()
            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
            loginparameters.put("receipt_date", receiptdate)
            loginparameters.put("db",getPrefsString(Constants.db,""))
            val LeadListRequest = getleadslist.get_generated_receipt(loginparameters)
            LeadListRequest.enqueue(object : Callback<ArrayList<GeneratedReceiptModel>> {
                override fun onFailure(call: Call<ArrayList<GeneratedReceiptModel>>, t: Throwable) {
                    hideProgressDialog()
                    recycler_view_receips.visibility = View.GONE
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ArrayList<GeneratedReceiptModel>>, response: Response<ArrayList<GeneratedReceiptModel>>
                ) {
                    when {
                        response.isSuccessful -> {
                            hideProgressDialog()
                            when {
                                response.code().equals(200) -> {
                                    recycler_view_receips.visibility = View.VISIBLE
                                    resultlist = response.body()!!
                                    System.out.println("recycler ${response.body()}")
                                    if (resultlist.size > 0) {
                                        integrateList(response.body()!!)
                                    } else {
                                        recycler_view_receips.visibility = View.GONE
                                        txt_total.text = "0"
                                    }
                                }
                            }
                        }
                        else -> {
                            txt_total.text = "0"
                            recycler_view_receips.visibility = View.GONE
                            recycler_view_offreceips.visibility = View.GONE
                            hideProgressDialog()
                            toast("No Receipts Available")
                        }
                    }
                }
            })
        } else {
            toast(resources.getString(R.string.nointernet))
        }
    }

    private fun get_feedbacks(receiptdate: String) {
        if (checkForInternet()) {

            txt_vr_receiptdate.setText(receiptdate)
            showProgressDialog()
            setPrefsString(Constants.online_cache_date, receiptdate);
            var resultlist = ArrayList<GeneratedReceiptModel>()
            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
            loginparameters.put("remark_date", receiptdate)
            loginparameters.put("db",getPrefsString(Constants.db,""))
            val LeadListRequest = getleadslist.get_feedback_report(loginparameters)
            LeadListRequest.enqueue(object : Callback<ArrayList<GeneratedReceiptModel>> {
                override fun onFailure(call: Call<ArrayList<GeneratedReceiptModel>>, t: Throwable) {
                    hideProgressDialog()
                    recycler_view_receips.visibility = View.GONE
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ArrayList<GeneratedReceiptModel>>, response: Response<ArrayList<GeneratedReceiptModel>>
                ) {
                    when {
                        response.isSuccessful -> {
                            hideProgressDialog()
                            when {
                                response.code().equals(200) -> {
                                    recycler_view_receips.visibility = View.VISIBLE
                                    resultlist = response.body()!!
                                    System.out.println("recycler ${response.body()}")
                                    if (resultlist.size > 0) {
                                        integrateList(response.body()!!)
                                    } else {
                                        recycler_view_receips.visibility = View.GONE
                                        txt_total.text = "0"
                                    }
                                }
                            }
                        }
                        else -> {
                            txt_total.text = "0"
                            recycler_view_receips.visibility = View.GONE
                            recycler_view_offreceips.visibility = View.GONE
                            hideProgressDialog()
                            toast("No Receipts Available")
                        }
                    }
                }
            })
        } else {
            toast(resources.getString(R.string.nointernet))
        }
    }

    private fun get_receipt_details(receiptno: String, receipt_type: String) {
        showProgressDialog()

        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
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

    private fun setnewadapter() {
        resultlist_showing.clear()

        var text = edt_vr_search.text.toString()
        text = text.toLowerCase(Locale.getDefault())
        if (text.equals(null)) {
            recycler_view_receips.visibility = View.GONE
            return
        } else {
            for (i in resultlist.indices) {
                val schedte = resultlist.get(i)
                var name = schedte.getCustomerName()
//                var mobile = schedte.getMOBILE()
                name = name!!.toLowerCase(Locale.getDefault())
//                mobile = mobile.toLowerCase(Locale.getDefault())
                if (name.contains(text)) {
                    resultlist_showing.add(resultlist.get(i))
                }
            }
        }
        if (resultlist_showing.size > 0) {
            recycler_view_receips.visibility = View.VISIBLE
            showReceiptsAdapters = ShowReceiptsList(resultlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (getPrefsBoolean(Constants.showing_online_view_receipts, true)) {
                        if (view.getTag().equals("listitem")) {
                            if (getPrefsString(
                                    Constants.showing_online_view_receipts,
                                    ""
                                ).equals("feedback")
                            ) {

                            } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals(
                                    "online"
                                )
                            ) {
                                get_receipt_details(
                                    resultlist_showing.get(position).getReceiptNo().toString(),
                                    resultlist_showing.get(position).getReceipt_type()!!
                                )
                            } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals(
                                    "offline"
                                )
                            ) {
                                get_offline_recept_details(resultlist_showing.get(position).getReceiptNo().toString())
                            }
                        } else if (view.getTag().equals("custname")) {
                        }
                    } else {
                        get_offline_recept_details(resultlist_showing.get(position).getReceiptNo().toString())
                    }
                }

                override fun onLongClicked(position: Int) {
                }
            }, getPrefsString(Constants.showing_online_view_receipts, ""))
            showReceiptsAdapters.notifyDataSetChanged()
            recycler_view_receips.setAdapter(showReceiptsAdapters)
        } else {
            recycler_view_receips.visibility = View.GONE
        }
    }

    fun integrateList(leadslist: ArrayList<GeneratedReceiptModel>) {

        if (leadslist.size > 0) {
            resultlist.clear()
            resultlist_showing.clear()
            resultlist.addAll(leadslist)
            resultlist_showing.addAll(leadslist)
            recycler_view_receips.visibility = View.VISIBLE
            var amount_Total = 0
            for (i in resultlist_showing) {
                try {
                    amount_Total += Constants.isEmtytoZero(i.getReceivedAmount()!!).toInt()
                } catch (e: NumberFormatException) {
                    amount_Total = 0
                    e.printStackTrace()
                }
            }
            txt_total.text = Constants.money_convertor(amount_Total.toString(), false)
            showReceiptsAdapters = ShowReceiptsList(resultlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {

                    if (view.getTag().equals("listitem")) {
                        if (getPrefsString(
                                Constants.showing_online_view_receipts,
                                ""
                            ).equals("feedback")
                        ) {

                        } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals(
                                "online"
                            )
                        ) {
                            get_receipt_details(
                                resultlist_showing.get(position).getReceiptNo().toString(),
                                resultlist_showing.get(position).getReceipt_type()!!
                            )
                        } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals(
                                "offline"
                            )
                        ) {
                            get_offline_recept_details(resultlist_showing.get(position).getReceiptNo().toString())
                        }
                    } else if (view.getTag().equals("custname")) {
                    }

                }

                override fun onLongClicked(position: Int) {
                }
            }, getPrefsString(Constants.showing_online_view_receipts, ""))
            showReceiptsAdapters.notifyDataSetChanged()
            recycler_view_receips.setAdapter(showReceiptsAdapters)
        } else {
            recycler_view_receips.visibility = View.GONE
            toast("No Receipts Available")
        }
    }

    private fun get_offline_recept_details(recno: String) {
        val resultlist = BaseUtils.offlinedb.getOFf_receipts_details(recno)
        for (i in resultlist) {
            val bundle = Bundle()
            bundle.putString("recptno", i.of_offrecno)
            bundle.putString(
                "recptdate",
                BaseUtils.ConvertTodmY(i.of_receiptdate) + " / " + BaseUtils.ConvertTohma(
                    i.of_receipttime
                )
            )
            bundle.putString("customername", i.custname)
            bundle.putString("customermobile", i.custmobile)
            bundle.putString("customerid", i.custcode)
            bundle.putString("groupname", BaseUtils.offlinedb.getGroupName(i.of_groupid.toInt()))
            bundle.putString("ticketno", i.of_ticketno)
            bundle.putString("penalty", i.penalty)
            bundle.putString("bonus", i.bonus)
            bundle.putString("receivedamount", i.of_receivedamount)
            bundle.putString("paymentmode", BaseUtils.masterdb.get_paymentTypeName(i.of_paytypeid))
            bundle.putString("chequeno", i.of_chequeno)
            bundle.putString("chequedate", i.of_chequedate)
            bundle.putString("chequebank", BaseUtils.masterdb.get_BankName(i.of_banknameid))
            bundle.putString("chequebranch", i.of_chequebranchname)
            bundle.putString("transactionno", i.of_transno)
            bundle.putString("transactiondate", i.of_transdate)
            bundle.putString("installmentno", i.installmentnos)
            bundle.putString("isprinted", i.of_printed)
            doFragmentTransactionWithBundle(ViewReceiptPreview(), "viewpreview", true, bundle)
        }
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }

    fun init() {
        if (getPrefsString(Constants.showing_online_view_receipts, "").equals("online")) {
            btn_onlinerecpts.setTextColor(resources.getColor(R.color.colorWhite))
            btn_off_recpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.grey_a100))
            view_online_recpts.visibility = View.VISIBLE
            view_off_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.GONE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.paymenttype)
            txt_col3.text = resources.getString(R.string.received)
            if (checkForInternet()) {
                if (getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    get_receipts(BaseUtils.CurrentDate_ymd())
                } else {
                    get_receipts(getPrefsString(Constants.online_cache_date, ""))
                }
            } else {
                toast(resources.getString(R.string.nointernet))
            }
        } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals("offline")) {

            btn_onlinerecpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.grey_a100))
            btn_off_recpts.setTextColor(resources.getColor(R.color.colorWhite))
            view_online_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.GONE
            view_off_recpts.visibility = View.VISIBLE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.paymenttype)
            txt_col3.text = resources.getString(R.string.received)
            var resultlist = ArrayList<GeneratedReceiptModel>()
            val offlinelist = BaseUtils.offlinedb.getOFf_receipts_list(BaseUtils.CurrentDate_ymd())
            for (i in offlinelist) {
                val recptmodel = GeneratedReceiptModel()
                recptmodel.setCustomerName(i.custname)
                recptmodel.setCustomerId(i.of_customerid)
                recptmodel.setPaymentMode(BaseUtils.masterdb.get_paymentTypeName(i.of_paytypeid))
                recptmodel.setReceivedAmount(i.of_receivedamount)
                recptmodel.setReceiptNo(i.of_offrecno)
                resultlist.add(recptmodel)
            }
            if (resultlist.size > 0) {
                integrateList(resultlist)
            } else {
                txt_total.text = "0"
                recycler_view_receips.visibility = View.GONE
                toast(Constants.No_offline_receipts)
            }
        } else if (getPrefsString(Constants.showing_online_view_receipts, "").equals("feedback")) {
            btn_onlinerecpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.colorWhite))
            btn_off_recpts.setTextColor(resources.getColor(R.color.grey_a100))
            view_online_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.VISIBLE
            view_off_recpts.visibility = View.GONE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.feedback)
            txt_col3.text = resources.getString(R.string.str_nextfollowup)
            if (checkForInternet()) {
                if (getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    get_feedbacks(BaseUtils.CurrentDate_ymd())
                } else {
                    get_feedbacks(getPrefsString(Constants.online_cache_date, ""))
                }
            } else {
                toast(resources.getString(R.string.nointernet))
            }
        } else {
            btn_onlinerecpts.setTextColor(resources.getColor(R.color.colorWhite))
            btn_off_recpts.setTextColor(resources.getColor(R.color.grey_a100))
            btn_feedbacks.setTextColor(resources.getColor(R.color.grey_a100))
            view_online_recpts.visibility = View.VISIBLE
            view_off_recpts.visibility = View.GONE
            view_feedbacks.visibility = View.GONE
            txt_col1.text = resources.getString(R.string.customer_name)
            txt_col2.text = resources.getString(R.string.paymenttype)
            txt_col3.text = resources.getString(R.string.received)
            if (checkForInternet()) {
                if (getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    get_receipts(BaseUtils.CurrentDate_ymd())
                } else {
                    get_receipts(getPrefsString(Constants.online_cache_date, ""))
                }
            } else {
                toast(resources.getString(R.string.nointernet))
            }
        }
    }
}
