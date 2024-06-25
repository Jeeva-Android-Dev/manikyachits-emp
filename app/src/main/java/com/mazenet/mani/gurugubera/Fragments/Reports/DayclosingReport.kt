package com.mazenet.mani.gurugubera.Fragments.Reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.google.gson.JsonObject
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.BranchModel
import com.mazenet.mani.gurugubera.Model.DayclosingModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.BranchSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DayclosingReport : BaseFragment() {

    lateinit var btn_sel_branch: TextView
    var SelectedBranch: String = ""
    var filtered_date: String = ""
    var brancharray = ArrayList<BranchModel>()
    val ymd_df: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val dmy_df: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
    lateinit var filter_popup: PopupWindow
    lateinit var BranchSpinner: BranchSpinnerdilog


    lateinit var t1cashcollection: TextView
    lateinit var t1cashpenaulty: TextView
    lateinit var t1AdvanceCashAgainstCustomer: TextView
    lateinit var t1AdvanceCashAgainstEnrollment: TextView
    lateinit var t1BonusAmountCash: TextView
    lateinit var t1OtherChargesCash: TextView
    lateinit var t1totalCash: TextView
    lateinit var t2ChequeReceived: TextView
    lateinit var t2ClearedCheque: TextView
    lateinit var t2AdvanceChequeAgainstCustomer: TextView
    lateinit var t2AdvanceChequeAgainstEnrollment: TextView
    lateinit var t2ChequePenaulty: TextView
    lateinit var t2BonusAmountCheque: TextView
    lateinit var t2OtherChargesCheque: TextView
    lateinit var t2TotalCheque: TextView
    lateinit var t3NEFTCollection: TextView
    lateinit var t3NEFTPenaulty: TextView
    lateinit var t3AdvanceNEFTAgainstCustomer: TextView
    lateinit var t3AdvanceNEFTAgainstEnrollment: TextView
    lateinit var t3BonusAmountNEFT: TextView
    lateinit var t3OtherChargesNEFT: TextView
    lateinit var t3TotalNEFTorRTGSAmount: TextView
    lateinit var t4DDCollection: TextView
    lateinit var t4DDPenaulty: TextView
    lateinit var t4AdvanceDDAgainstCustomer: TextView
    lateinit var t4AdvanceCashAgainstEnrollment: TextView
    lateinit var t4OtherChargesDD: TextView
    lateinit var t4TotalDDAmount: TextView
    lateinit var t5AdvanceviaCardAgainstCustomer: TextView
    lateinit var t5AdvanceviaCardAgainstEnrollment: TextView
    lateinit var t5TotalCardAmount: TextView
    lateinit var t5CardPenalty: TextView
    lateinit var t5CardCollection: TextView
    lateinit var t6AdjustedAmountagainstcustomer: TextView
    lateinit var t6AdjustedAmountagainstEnrollment: TextView
    lateinit var t6AdjustedPenaultyagainstCustomer: TextView
    lateinit var t6AdjustedPenaultyagainstEnrollment: TextView
    lateinit var t6BidPaymentAdjustment: TextView
    lateinit var t6BidAdjustmnetpenaulty: TextView
    lateinit var t6BidAdjustmnetBonus: TextView
    lateinit var t6BonusforAdvanceagainstCustomer: TextView
    lateinit var t6BonusforAdvanceagainstEnrollment: TextView
    lateinit var t6TotalAdjustmentAmount: TextView
    lateinit var t7ReturnedAmountagainstCustomer: TextView
    lateinit var t7ReturnedAmountagainstEnrollment: TextView
    lateinit var t7ReturnedAmountTotal: TextView
    lateinit var t8CashPayment: TextView
    lateinit var t8ChequePayment: TextView
    lateinit var t8CardPayment: TextView
    lateinit var t8DDPayment: TextView
    lateinit var t8NeftPayment: TextView
    lateinit var t8TotalPayment: TextView
    lateinit var t9OtherBranchCashCollection: TextView
    lateinit var t9OtherBranchChequeCollection: TextView
    lateinit var t9OtherBranchCashPenaulty: TextView
    lateinit var t9OtherBranchChequepenaulty: TextView
    lateinit var t9OtherBranchCashBonus: TextView
    lateinit var t9OtherBranchChequeBonus: TextView
    lateinit var t21clearedCheque: TextView
    lateinit var t9Otherenrolmentneft: TextView
    lateinit var t9Otherenrolmentcheque: TextView
    lateinit var t9Otherenrolmentcash: TextView
    lateinit var t9OtherBranchNeftPenalty: TextView
    lateinit var t9OtherBranchNeftColelction: TextView
    lateinit var t9OtherBranchDDPenalty: TextView
    lateinit var t9OtherBranchDDCollection: TextView
    lateinit var spn_date: TextView
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
//        filtercrumbs.visibility = View.VISIBLE
//        filtercrumbs.addView(tv)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dayclosing_report, container, false)
        (activity as HomeActivity).setActionBarTitle("Day Closing Report")
        btn_sel_branch = view.findViewById(R.id.spn_branch) as TextView
        spn_date = view.findViewById(R.id.spn_date) as TextView
        t1cashcollection = view.findViewById<View>(R.id.t1cashcollection) as TextView
        t1cashpenaulty = view.findViewById<View>(R.id.t1cashpenaulty) as TextView
        t1AdvanceCashAgainstCustomer = view.findViewById<View>(R.id.t1AdvanceCashAgainstCustomer) as TextView
        t1AdvanceCashAgainstEnrollment = view.findViewById<View>(R.id.t1AdvanceCashAgainstEnrollment) as TextView
        t1BonusAmountCash = view.findViewById<View>(R.id.t1BonusAmountCash) as TextView
        t1OtherChargesCash = view.findViewById<View>(R.id.t1OtherChargesCash) as TextView
        t1totalCash = view.findViewById<View>(R.id.t1totalCash) as TextView
        t2ChequeReceived = view.findViewById<View>(R.id.t2ChequeReceived) as TextView
        t2ClearedCheque = view.findViewById<View>(R.id.t2ClearedCheque) as TextView
        t2AdvanceChequeAgainstCustomer = view.findViewById<View>(R.id.t2AdvanceChequeAgainstCustomer) as TextView
        t2AdvanceChequeAgainstEnrollment = view.findViewById<View>(R.id.t2AdvanceChequeAgainstEnrollment) as TextView
        t2ChequePenaulty = view.findViewById<View>(R.id.t2ChequePenaulty) as TextView
        t2BonusAmountCheque = view.findViewById<View>(R.id.t2BonusAmountCheque) as TextView
        t2OtherChargesCheque = view.findViewById<View>(R.id.t2OtherChargesCheque) as TextView
        t2TotalCheque = view.findViewById<View>(R.id.t2TotalCheque) as TextView
        t3NEFTCollection = view.findViewById<View>(R.id.t3NEFTCollection) as TextView
        t3NEFTPenaulty = view.findViewById<View>(R.id.t3NEFTPenaulty) as TextView
        t3AdvanceNEFTAgainstCustomer = view.findViewById<View>(R.id.t3AdvanceNEFTAgainstCustomer) as TextView
        t3AdvanceNEFTAgainstEnrollment = view.findViewById<View>(R.id.t3AdvanceNEFTAgainstEnrollment) as TextView
        t3BonusAmountNEFT = view.findViewById<View>(R.id.t3BonusAmountNEFT) as TextView
        t3OtherChargesNEFT = view.findViewById<View>(R.id.t3OtherChargesNEFT) as TextView
        t3TotalNEFTorRTGSAmount = view.findViewById<View>(R.id.t3TotalNEFTorRTGSAmount) as TextView
        t4DDCollection = view.findViewById<View>(R.id.t4DDCollection) as TextView
        t4DDPenaulty = view.findViewById<View>(R.id.t4DDPenaulty) as TextView
        t4AdvanceDDAgainstCustomer = view.findViewById<View>(R.id.t4AdvanceDDAgainstCustomer) as TextView
        t4AdvanceCashAgainstEnrollment = view.findViewById<View>(R.id.t4AdvanceCashAgainstEnrollment) as TextView
        t4OtherChargesDD = view.findViewById<View>(R.id.t4OtherChargesDD) as TextView
        t4TotalDDAmount = view.findViewById<View>(R.id.t4TotalDDAmount) as TextView
        t5AdvanceviaCardAgainstCustomer = view.findViewById<View>(R.id.t5AdvanceviaCardAgainstCustomer) as TextView
        t5AdvanceviaCardAgainstEnrollment = view.findViewById<View>(R.id.t5AdvanceviaCardAgainstEnrollment) as TextView
        t5TotalCardAmount = view.findViewById<View>(R.id.t5TotalCardAmount) as TextView
        t5CardCollection = view.findViewById<View>(R.id.t5CardCollection) as TextView
        t5CardPenalty = view.findViewById<View>(R.id.t5CardPenalty) as TextView
        t6AdjustedAmountagainstcustomer = view.findViewById<View>(R.id.t6AdjustedAmountagainstcustomer) as TextView
        t6AdjustedAmountagainstEnrollment = view.findViewById<View>(R.id.t6AdjustedAmountagainstEnrollment) as TextView
        t6AdjustedPenaultyagainstCustomer = view.findViewById<View>(R.id.t6AdjustedPenaultyagainstCustomer) as TextView
        t6AdjustedPenaultyagainstEnrollment =
            view.findViewById<View>(R.id.t6AdjustedPenaultyagainstEnrollment) as TextView
        t6BidPaymentAdjustment = view.findViewById<View>(R.id.t6BidPaymentAdjustment) as TextView
        t6BidAdjustmnetpenaulty = view.findViewById<View>(R.id.t6BidAdjustmnetpenaulty) as TextView
        t6BidAdjustmnetBonus = view.findViewById<View>(R.id.t6BidAdjustmnetBonus) as TextView
        t6BonusforAdvanceagainstCustomer = view.findViewById<View>(R.id.t6BonusforAdvanceagainstCustomer) as TextView
        t6BonusforAdvanceagainstEnrollment =
            view.findViewById<View>(R.id.t6BonusforAdvanceagainstEnrollment) as TextView
        t6TotalAdjustmentAmount = view.findViewById<View>(R.id.t6TotalAdjustmentAmount) as TextView
        t7ReturnedAmountagainstCustomer = view.findViewById<View>(R.id.t7ReturnedAmountagainstCustomer) as TextView
        t7ReturnedAmountagainstEnrollment = view.findViewById<View>(R.id.t7ReturnedAmountagainstEnrollment) as TextView
        t7ReturnedAmountTotal = view.findViewById<View>(R.id.t7ReturnedAmountTotal) as TextView
        t8CashPayment = view.findViewById<View>(R.id.t8CashPayment) as TextView
        t8ChequePayment = view.findViewById<View>(R.id.t8CashPayment) as TextView
        t8CardPayment = view.findViewById<View>(R.id.t8CashPayment) as TextView
        t8DDPayment = view.findViewById<View>(R.id.t8CashPayment) as TextView
        t8NeftPayment = view.findViewById<View>(R.id.t8CashPayment) as TextView
        t8TotalPayment = view.findViewById<View>(R.id.t8CashPayment) as TextView
        t9OtherBranchCashCollection = view.findViewById<View>(R.id.t9OtherBranchCashCollection) as TextView
        t9OtherBranchChequeCollection = view.findViewById<View>(R.id.t9OtherBranchChequeCollection) as TextView
        t9OtherBranchCashPenaulty = view.findViewById<View>(R.id.t9OtherBranchCashPenaulty) as TextView
        t9OtherBranchChequepenaulty = view.findViewById<View>(R.id.t9OtherBranchChequepenaulty) as TextView
        t9OtherBranchCashBonus = view.findViewById<View>(R.id.t9OtherBranchCashBonus) as TextView
        t9OtherBranchChequeBonus = view.findViewById<View>(R.id.t9OtherBranchChequeBonus) as TextView
        t9OtherBranchDDCollection = view.findViewById<View>(R.id.t9OtherBranchDDCollection) as TextView
        t9OtherBranchDDPenalty = view.findViewById<View>(R.id.t9OtherBranchDDPenalty) as TextView
        t9OtherBranchNeftColelction = view.findViewById<View>(R.id.t9OtherBranchNeftColelction) as TextView
        t9OtherBranchNeftPenalty = view.findViewById<View>(R.id.t9OtherBranchNeftPenalty) as TextView
        t21clearedCheque = view.findViewById<View>(R.id.t21clearedCheque) as TextView
        t9Otherenrolmentcash = view.findViewById<View>(R.id.t9Otherenrolmentcash) as TextView
        t9Otherenrolmentcheque = view.findViewById<View>(R.id.t9Otherenrolmentcheque) as TextView
        t9Otherenrolmentneft = view.findViewById<View>(R.id.t9Otherenrolmentneft) as TextView

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
                if (!getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    d("dayclosecheck","running ")
                    get_Collectionlist(
                        getPrefsString(Constants.tenantid, ""),
                        getPrefsString(Constants.selectedBranchid, ""), getPrefsString(Constants.online_cache_date, "")
                    )
                }
            }
            else {
                btn_sel_branch.setText(getPrefsString(Constants.selectedBranchName, ""))
                if (!getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                    d("dayclosecheck","else  running ")
                    get_Collectionlist(
                        getPrefsString(Constants.tenantid, ""),
                        getPrefsString(Constants.selectedBranchid, ""), getPrefsString(Constants.online_cache_date, "")
                    )
                }
            }

        }
        else {
            douwnloadBranches()
        }
        spn_date.setOnClickListener {
            fromdate()
        }

//        get_Collectionlist( getPrefsString(Constants.tenantid, ""), getPrefsString(Constants.selectedBranchid, ""), filtered_date)
        btn_sel_branch.setOnClickListener {
            BranchSpinner.showSpinerDialog()
        }

        BranchSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {

                SelectedBranch = position.toString()
                btn_sel_branch.setText(grpname)
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)
                spn_date.setText("Select Date")
                toast("Select a date")
            }
        })


        return view
    }


    fun reemoveview() {
        try {
//            filtercrumbs.removeAllViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun get_Collectionlist(
        tenant_id: String,
        branchid: String,
        date: String
    ) {
        reemoveview()
        if (checkForInternet()) {
            showProgressDialog()
            println("fetching")

            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", tenant_id)
            loginparameters.put("branch_id", branchid)
            loginparameters.put("start_date", date)
            loginparameters.put("db",getPrefsString(Constants.db,""))
            setPrefsString(Constants.online_cache_date, date)
            spn_date.setText(date)
            val LeadListRequest = getleadslist.get_dayclosing(loginparameters)
            LeadListRequest.enqueue(object : Callback<DayclosingModel> {

                override fun onFailure(call: Call<DayclosingModel>, t: Throwable) {
                    hideProgressDialog()
                    t.printStackTrace()

                }

                override fun onResponse(
                    call: Call<DayclosingModel>,
                    response: Response<DayclosingModel>
                ) {
                    hideProgressDialog()
                    when {
                        response.isSuccessful -> {

                            when {
                                response.code().equals(200) -> {
                                    if (response.body()!!.status.equals("Success")) {


                                        /*t1cashcollection.text =
                                            response.body()!!.getData()?.cashCollection ?: "0"
                                        t1cashpenaulty.text =
                                            response.body()!!.getData()?.cashPenalty ?: "0"*/
                                       /* t1AdvanceCashAgainstCustomer.setText(response.body()!!.getData()!!.getAdvanceCash())
//                                        t1AdvanceCashAgainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstCash())
                                        t1OtherChargesCash.setText(response.body()!!.getData()!!.getOtherChargesCash())
                                        t1totalCash.setText(response.body()!!.getData()!!.getTotalCash())
                                        t2ChequeReceived.setText(response.body()!!.getData()!!.getChequeReceivedCollection())
                                        t2ClearedCheque.setText(response.body()!!.getData()!!.getChequeClearedCollection())
                                        t2AdvanceChequeAgainstCustomer.setText(response.body()!!.getData()!!.getAdvanceCheque())
//                                        t2AdvanceChequeAgainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstAdvCheque())
                                        t2ChequePenaulty.setText(response.body()!!.getData()!!.getChequePenalty())
                                        t2OtherChargesCheque.setText(response.body()!!.getData()!!.getOtherChargesCheque())
                                        t21clearedCheque.setText(response.body()!!.getData()!!.getChequeClearedPenalty())
                                        t2TotalCheque.setText(response.body()!!.getData()!!.getTotalChequeLeft())
                                        t3NEFTCollection.setText(response.body()!!.getData()!!.getNeftCollection())
                                        t3NEFTPenaulty.setText(response.body()!!.getData()!!.getNeftPenalty())
                                        t3AdvanceNEFTAgainstCustomer.setText(response.body()!!.getData()!!.getAdvanceNeft())
//                                        t3AdvanceNEFTAgainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstAdvNeft())
                                        t3OtherChargesNEFT.setText(response.body()!!.getData()!!.getOtherChargesNeft())
                                        t3TotalNEFTorRTGSAmount.setText(response.body()!!.getData()!!.getTotalNeft())
                                        t4DDCollection.setText(response.body()!!.getData()!!.getDdCollection())
                                        t4DDPenaulty.setText(response.body()!!.getData()!!.getDdPenalty())
                                        t4AdvanceDDAgainstCustomer.setText(response.body()!!.getData()!!.getAdvanceDd())
//                                        t4AdvanceCashAgainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstAdvDd())
                                        t4TotalDDAmount.setText(response.body()!!.getData()!!.getTotalDd())
                                        t5CardCollection.setText(response.body()!!.getData()!!.getCardCollection())
                                        t5CardPenalty.setText(response.body()!!.getData()!!.getCardPenalty())
                                        t5AdvanceviaCardAgainstCustomer.setText(response.body()!!.getData()!!.getAdvanceCard())
//                                        t5AdvanceviaCardAgainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstAdvCard())
                                        t5TotalCardAmount.setText(response.body()!!.getData()!!.getTotalCard())
//                                        t6AdjustedAmountagainstcustomer.setText(response.body()!!.getData()!!.getCustomerAgainstAdjustAmt())
//                                        t6AdjustedAmountagainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstAdjustAmt())
                                        t6BidPaymentAdjustment.setText(response.body()!!.getData()!!.getBpAdjustCollection())
                                        t6BidAdjustmnetpenaulty.setText(response.body()!!.getData()!!.getBpAdjustPenalty())
//                                        t6AdjustedPenaultyagainstCustomer.setText(response.body()!!.getData()!!.getCustomerAgainstPenalty())
//                                        t6AdjustedPenaultyagainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstPenalty())
                                        t6TotalAdjustmentAmount.setText(response.body()!!.getData()!!.getTotalAdjustment())
//                                        t7ReturnedAmountagainstCustomer.setText(response.body()!!.getData()!!.getCustomerAgainstReturn())
//                                        t7ReturnedAmountagainstEnrollment.setText(response.body()!!.getData()!!.getEnrolAgainstReturn())
//                                        t7ReturnedAmountTotal.setText(response.body()!!.getData()!!.getTotalReturnAmount())
                                        t8CashPayment.setText(response.body()!!.getData()!!.getCashPayment())
                                        t8CardPayment.setText(response.body()!!.getData()!!.getCardPayment())
                                        t8ChequePayment.setText(response.body()!!.getData()!!.getChequePayment())
                                        t8DDPayment.setText(response.body()!!.getData()!!.getDdPayment())
                                        t8NeftPayment.setText(response.body()!!.getData()!!.getNeftPayment())
                                        t8TotalPayment.setText(response.body()!!.getData()!!.getTotalPayment())
                                        t9OtherBranchCashCollection.setText(response.body()!!.getData()!!.getOtherBranchCashCollection())
                                        t9OtherBranchCashPenaulty.setText(response.body()!!.getData()!!.getOtherBranchCashPenalty())
                                        t9OtherBranchChequeCollection.setText(response.body()!!.getData()!!.getOtherBranchChequeCollection())
                                        t9OtherBranchChequepenaulty.setText(response.body()!!.getData()!!.getOtherBranchChequePenalty())
//                                        t9Otherenrolmentcash.setText(response.body()!!.getData()!!.getOtherBranchEnrolAgainstCash())
//                                        t9Otherenrolmentcheque.setText(response.body()!!.getData()!!.getOtherBranchEnrolAgainstCheque())
//                                        t9Otherenrolmentneft.setText(response.body()!!.getData()!!.getOtherBranchEnrolAgainstNeft())
                                        t9OtherBranchDDCollection.setText(response.body()!!.getData()!!.getOtherBranchDdCollection())
                                        t9OtherBranchDDPenalty.setText(response.body()!!.getData()!!.getOtherBranchDdPenalty())
                                        t9OtherBranchNeftColelction.setText(response.body()!!.getData()!!.getOtherBranchNeftCollection())
                                        t9OtherBranchNeftPenalty.setText(response.body()!!.getData()!!.getOtherBranchNeftPenalty())*/
                                    }

                                }

                            }

                        }
                        else -> {
                            hideProgressDialog()

                        }
                    }
                }
            })
        }
    }


    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }


    fun fromdate() {
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
                    filtered_date = from
                    spn_date.setText(filtered_date)
                    get_Collectionlist(
                        getPrefsString(Constants.tenantid, ""),
                        getPrefsString(Constants.selectedBranchid, ""), filtered_date
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, newCalendar.get(Calendar.YEAR),
            newCalendar.get(Calendar.MONTH),
            newCalendar.get(Calendar.DAY_OF_MONTH)
        )
        fromDatePickerDialog.getDatePicker().maxDate = (System.currentTimeMillis())
        fromDatePickerDialog.setTitle("Choose date")

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
                                if (!getPrefsString(Constants.online_cache_date, "").isNullOrEmpty()) {
                                    get_Collectionlist(
                                        getPrefsString(Constants.tenantid, ""),
                                        getPrefsString(Constants.selectedBranchid, ""),
                                        getPrefsString(Constants.online_cache_date, "")
                                    )
                                }
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

}
