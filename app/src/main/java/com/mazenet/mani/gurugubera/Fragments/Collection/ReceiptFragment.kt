package com.mazenet.mani.gurugubera.Fragments.Collection


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
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
import com.mazenet.mani.gurugubera.Adapters.AdapterEdittextListener
import com.mazenet.mani.gurugubera.Adapters.InstallmentsAdapter
import com.mazenet.mani.gurugubera.Adapters.ReceiptAdapter
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.*
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.BankListSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import com.mazenet.mani.gurugubera.Spinners.PaymentTypeSpinnerdilog
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.CurrentDate_ymd
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.masterdb
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.offlinedb
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_receipt.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ReceiptFragment : BaseFragment() {

    lateinit var lay_re_che: LinearLayout
    lateinit var lay_re_dd: LinearLayout
    lateinit var lay_re_rtgs: LinearLayout
    lateinit var grouplistAdapter: ReceiptAdapter
    lateinit var groupInstallmentAdapter: InstallmentsAdapter
    lateinit var grouplist: ArrayList<GroupListModel>
    var Showlist = java.util.ArrayList<GroupListModel>()
    var PaymentTypeList = java.util.ArrayList<PaymentTypeModel>()
    var BanksList = java.util.ArrayList<BanksListModel>()
    var ShowlistInstallments = java.util.ArrayList<InstallmentListModel>()
    lateinit var Recycler_receipts: RecyclerView
    lateinit var installment_layout: LinearLayout
    lateinit var advance_layout: ConstraintLayout
    lateinit var Recycler_instalments: RecyclerView
    lateinit var txt_rf_receiptdat: TextView
    lateinit var txt_rf_advanceamnt: TextView
    lateinit var txt_rf_custname: TextView
    lateinit var txt_reset: TextView
    lateinit var edt_paymenttype: Button
    lateinit var PaymentSpinner: PaymentTypeSpinnerdilog
    lateinit var BankListSpinner: BankListSpinnerdilog
    lateinit var df: SimpleDateFormat
    lateinit var Receiptdate: String
    var SelectedPaymenttypeid: String = ""
    var SelectedPaymenttypename: String = ""
    var isAdvanceAvailable: Boolean = false
    var isokayToSubmit: Boolean = false
    var AdvanceOnly: Boolean = false
    var enrolid: String = ""
    var custname: String = ""
    var cust_id: String = ""
    var str_chedate: String = ""
    var str_dddate: String = ""
    var str_transdate: String = ""
    var SelectedBAnkName: String = ""
    var SelectedBAnkId: String = ""
    lateinit var edt_receiptamount: EditText
    lateinit var edt_rf_cheno: EditText
    lateinit var edt_rf_chebank: EditText
    lateinit var edt_rf_chebranch: EditText
    lateinit var edt_rf_ddno: EditText
    lateinit var edt_rf_ddbank: EditText
    lateinit var edt_rf_ddbranch: EditText
    lateinit var edt_rf_transnono: EditText
    lateinit var edt_re_debitto: EditText
    lateinit var btn_trans_date: Button
    lateinit var btn_dd_date: Button
    lateinit var btn_che_date: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.fragment_receipt, container, false)
        (activity as HomeActivity).setActionBarTitle("Generate Receipt")
        df = SimpleDateFormat("yyyy-MM-dd")
        setPrefsString(Constants.advanceAvailable, "yes")
        setPrefsBoolean(Constants.LesserThanPenalty, true)
        if (masterdb.PaymentTypeSize() > 0) {
            PaymentTypeList = masterdb.getPaymentType()
        }
        if (masterdb.BankListSize() > 0) {
            BanksList = masterdb.getBankList()
        }
        hideProgressDialog()
        //=-----------------
        Recycler_receipts = view.findViewById(R.id.Recycler_receipts) as RecyclerView
        Recycler_instalments = view.findViewById(R.id.Recycler_instalments) as RecyclerView
        installment_layout = view.findViewById(R.id.installment_layout) as LinearLayout
        advance_layout = view.findViewById(R.id.advance_layout) as ConstraintLayout
        edt_receiptamount = view.findViewById(R.id.edt_receiptamount) as EditText
        edt_rf_cheno = view.findViewById(R.id.edt_re_cheno_indiv) as EditText
        edt_rf_chebank = view.findViewById(R.id.edt_re_chebank_indiv) as EditText
        edt_rf_chebranch = view.findViewById(R.id.edt_re_chebranch_indiv) as EditText
        edt_rf_ddno = view.findViewById(R.id.edt_re_ddno_indiv) as EditText
        edt_rf_ddbank = view.findViewById(R.id.edt_re_ddbank_indiv) as EditText
        edt_rf_ddbranch = view.findViewById(R.id.edt_re_ddbranch_indiv) as EditText
        edt_rf_transnono = view.findViewById(R.id.edt_re_rtgsno_indiv) as EditText
        edt_re_debitto = view.findViewById(R.id.edt_re_debitto) as EditText
        txt_rf_receiptdat = view.findViewById(R.id.txt_rf_receiptdate) as TextView
        txt_rf_advanceamnt = view.findViewById(R.id.txt_rf_advanceamnt) as TextView
        txt_rf_custname = view.findViewById(R.id.txt_rf_custname) as TextView
        txt_reset = view.findViewById(R.id.txt_reset) as TextView
        edt_paymenttype = view.findViewById(R.id.edt_paymenttype) as Button
        btn_trans_date = view.findViewById(R.id.btn_rtgs_date_indiv) as Button
        btn_dd_date = view.findViewById(R.id.btn_dd_date_indiv) as Button
        btn_che_date = view.findViewById(R.id.btn_che_date_indiv) as Button

        btn_trans_date.text=SimpleDateFormat("yyyy-MM-dd").format(Date())
        btn_dd_date.text=SimpleDateFormat("yyyy-MM-dd").format(Date())
        btn_che_date.text=SimpleDateFormat("yyyy-MM-dd").format(Date())

        lay_re_che = view.findViewById(R.id.lay_re_che_indiv) as LinearLayout
        lay_re_dd = view.findViewById(R.id.lay_re_dd_indiv) as LinearLayout
        lay_re_rtgs = view.findViewById(R.id.lay_re_rtgs_indiv) as LinearLayout
        enrolid = arguments!!.getString("enrolid")
        custname = arguments!!.getString("cust_name")
        cust_id = arguments!!.getString("cust_id")
        txt_rf_custname.setText("Customer : " + custname)
        //=----------------
        Recycler_receipts.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        Recycler_receipts.setLayoutManager(mLayoutManager)
        Recycler_receipts.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        Recycler_receipts.setItemAnimator(DefaultItemAnimator())
        Recycler_instalments.setHasFixedSize(true)
        val mInstallmentLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Recycler_instalments.setLayoutManager(mInstallmentLayoutManager)
        Recycler_instalments.setItemAnimator(DefaultItemAnimator())
        //=----------------
        if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
            get_group_detail(CurrentDate_ymd())
        } else {
            txt_rf_receiptdat.setText(CurrentDate_ymd())
            Receiptdate = CurrentDate_ymd()
            masterdb.deleteGroupDetailsTable()
            masterdb.deleteInstallmentsDetailsTable()
            val offlinelist = BaseUtils.offlinedb.getenrollmentList(enrolid)
            if (offlinelist.size > 0) {
                integrateGroupList(offlinelist)
                masterdb.addInstallmentDetails(BaseUtils.offlinedb.getinstalls(enrolid))
                masterdb.getInstallmentDetails(enrolid)
                integrateInstallmentList(masterdb.getTempInstallments())
            } else {
                toast("No Enrollments Available")
            }

        }
        PaymentSpinner = PaymentTypeSpinnerdilog(
            activity, PaymentTypeList,
            "Select Payment Type"
        )
        BankListSpinner = BankListSpinnerdilog(activity, BanksList, "Select Bank Name")
        edt_rf_ddbank.setOnClickListener {
            BankListSpinner.showSpinerDialog()
        }
        edt_rf_chebank.setOnClickListener {
            BankListSpinner.showSpinerDialog()
        }
        edt_paymenttype.setOnClickListener {
            PaymentSpinner.showSpinerDialog()
        }
        BankListSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(var1: String?, var2: Int, var3: String?) {
                SelectedBAnkId = var2.toString()
                SelectedBAnkName = var3.toString()
                if (SelectedPaymenttypename.equals("Cheque")) {
                    edt_rf_chebank.setText(SelectedBAnkName.toString())
                } else if (SelectedPaymenttypename.equals("D.D")) {
                    edt_rf_ddbank.setText(SelectedBAnkName.toString())
                }
            }

        })
        txt_reset.setOnClickListener {
            edt_receiptamount.setText("")
            PopulateList(false)
        }
        PaymentSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, paytype: String) {
                SelectedPaymenttypeid = position.toString()
                SelectedPaymenttypename = paytype
                edt_paymenttype.setText(paytype)
                println("paytype $paytype")
                when {
                    paytype.equals("Cash", ignoreCase = true) -> {
                        lay_re_che.visibility = View.GONE
                        lay_re_dd.visibility = View.GONE
                        lay_re_rtgs.visibility = View.GONE
                        edt_re_debitto.visibility = View.VISIBLE
                    }
                    paytype.equals("Cheque", ignoreCase = true) -> {
                        lay_re_che.visibility = View.VISIBLE
                        lay_re_dd.visibility = View.GONE
                        lay_re_rtgs.visibility = View.GONE
                        edt_re_debitto.visibility = View.GONE

                    }
                    paytype.equals("D.D", ignoreCase = true) -> {
                        lay_re_che.visibility = View.GONE
                        lay_re_dd.visibility = View.VISIBLE
                        lay_re_rtgs.visibility = View.GONE
                        edt_re_debitto.visibility = View.GONE

                    }
                    paytype.equals("RTGS/NEFT", ignoreCase = true) -> {
                        lay_re_che.visibility = View.GONE
                        lay_re_dd.visibility = View.GONE
                        lay_re_rtgs.visibility = View.VISIBLE
                        edt_re_debitto.visibility = View.GONE

                    }
                    paytype.equals("Card", ignoreCase = true) -> {
                        lay_re_che.visibility = View.GONE
                        lay_re_dd.visibility = View.GONE
                        lay_re_rtgs.visibility = View.VISIBLE
                        edt_re_debitto.visibility = View.GONE

                    }
                    else -> {
                        lay_re_che.visibility = View.GONE
                        lay_re_dd.visibility = View.GONE
                        lay_re_rtgs.visibility = View.GONE
                        edt_re_debitto.visibility = View.GONE
                    }
                }
            }
        })
        txt_rf_receiptdat.setOnClickListener {
            if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
                val newCalendar = Calendar.getInstance()
                var fromDatePickerDialog: DatePickerDialog
                fromDatePickerDialog = DatePickerDialog(
                    context, R.style.MyDialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val newDate = Calendar.getInstance()
                        newDate.set(year, monthOfYear, dayOfMonth)

                        try {
                            edt_receiptamount.setText("0")
                            txt_rf_advanceamnt.setText("0")
                            Receiptdate = df.format(newDate.time)
                            get_group_detail(Receiptdate)
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
            } else {

                toast("No Back date receipts available in offline mode")
            }
        }
        btn_che_date.setOnClickListener {
            val array = btn_che_date.text.toString().split("-")
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = DatePickerDialog(
                context, R.style.MyDialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)

                    try {

                        str_chedate = df.format(newDate.time)
                        btn_che_date.text = str_chedate
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, array[0].toInt(),
                array[1].toInt()-1,
                array[2].toInt()
            )
            //fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            fromDatePickerDialog.setTitle("Cheque date")
            fromDatePickerDialog.show()
        }
        btn_dd_date.setOnClickListener {
            val array = btn_dd_date.text.toString().split("-")
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = DatePickerDialog(
                context, R.style.MyDialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)

                    try {

                        str_dddate = df.format(newDate.time)
                        btn_dd_date.text = str_dddate
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, array[0].toInt(),
                array[1].toInt()-1,
                array[2].toInt()
            )
            //fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            fromDatePickerDialog.setTitle("DD date")
            fromDatePickerDialog.show()
        }
        btn_trans_date.setOnClickListener {
            val array = btn_trans_date.text.toString().split("-")
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = DatePickerDialog(
                context, R.style.MyDialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate.set(year, monthOfYear, dayOfMonth)
                    try {
                        str_transdate = df.format(newDate.time)
                        btn_trans_date.text = str_transdate
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, array[0].toInt(),
                array[1].toInt()-1,
                array[2].toInt()
            )
            //fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
            fromDatePickerDialog.setTitle("Transaction date")
            fromDatePickerDialog.show()
        }
        edt_receiptamount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                PopulateList(true)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        view.btn_generatereceipt.setOnClickListener {


            var transactiondate = str_transdate
            val reamrks = Constants.isEmtytoZero(view.edt_rf_remarks.text.toString())
            val debit_to = Constants.isEmtytoZero(edt_re_debitto.text.toString())

            val receiptamount = Constants.isEmtytoZero(edt_receiptamount.text.toString()).toInt()
            when {
                receiptamount > 0 -> if (!SelectedPaymenttypeid.isEmpty()) {
                    if (SelectedPaymenttypename.equals("Cheque")) {
                        var chequebranch = ""
                        var chequebank = ""
                        var chequedate = ""
                        var chequeno = ""
                        var transactionno = ""
                        var advance = ""
                        chequeno = edt_rf_cheno.text.toString().trim()
                        chequedate = str_chedate
                        chequebranch = edt_rf_chebranch.text.toString().trim()
                        chequebank = edt_rf_chebank.text.toString().trim()
                        advance = txt_rf_advanceamnt.text.toString().trim()
                        println("cheno $chequeno , chedate $chequedate , chebrnch $chequebranch , chebank $chequebank")
                        if (chequeno.equals("")) {
                            toast("Enter Cheque No")
                            return@setOnClickListener
                        } else if (chequedate.equals("")) {
                            toast("Enter Cheque Date")
                            return@setOnClickListener
                        } else if (chequebank.equals("")) {
                            toast("Enter Cheque Bank")
                            return@setOnClickListener
                        } else if (chequebranch.equals("")) {
                            toast("Enter Cheque Branch name")
                            return@setOnClickListener
                        } else {
                            add_receipt(
                                Receiptdate,
                                "0",
                                cust_id,
                                Showlist.get(0).groupid,
                                Showlist.get(0).ticketno,
                                receiptamount.toString(),
                                SelectedPaymenttypeid,
                                debit_to,
                                chequeno,
                                chequedate,
                                chequebranch,
                                transactionno,
                                transactiondate,
                                BaseUtils.Currenttime(),
                                SelectedBAnkId,
                                reamrks,
                                advance,
                                ShowlistInstallments,
                                Showlist.get(0).tobecollected
                            )
                        }
                    }
                    else if (SelectedPaymenttypename.equals("D.D")) {
                        var chequebranch = ""
                        var chequebank = ""
                        var chequedate = ""
                        var chequeno = ""
                        var transactionno = ""
                        var advance = ""
                        chequeno = edt_rf_ddno.text.toString().trim()
                        chequedate = str_dddate
                        chequebranch = edt_rf_ddbranch.text.toString().trim()
                        chequebank = edt_rf_ddbank.text.toString().trim()
                        advance = txt_rf_advanceamnt.text.toString().trim()
                        println("cheno $chequeno , chedate $chequedate , chebrnch $chequebranch , chebank $chequebank")
                        if (chequeno.equals("")) {
                            toast("Enter D.D No")
                            return@setOnClickListener
                        } else if (chequedate.equals("")) {
                            toast("Enter D.D Date")
                            return@setOnClickListener
                        } else if (chequebank.equals("")) {
                            toast("Enter D.D Bank")
                            return@setOnClickListener
                        } else if (chequebranch.equals("")) {
                            toast("Enter D.D Branch name")
                            return@setOnClickListener
                        } else {
                            add_receipt(
                                Receiptdate,
                                "0",
                                cust_id,
                                Showlist.get(0).groupid,
                                Showlist.get(0).ticketno,
                                receiptamount.toString(),
                                SelectedPaymenttypeid,
                                debit_to,
                                chequeno,
                                chequedate,
                                chequebranch,
                                transactionno,
                                transactiondate,
                                BaseUtils.Currenttime(),
                                SelectedBAnkId,
                                reamrks,
                                advance,
                                ShowlistInstallments,
                                Showlist.get(0).tobecollected
                            )
                        }
                    }
                    else if (SelectedPaymenttypename.equals("RTGS/NEFT") || SelectedPaymenttypename.equals("Card")) {
                        var chequebranch = ""
                        var chequebank = ""
                        var chequedate = ""
                        var chequeno = ""
                        var transactionno = ""
                        var advance = ""
                        transactionno = edt_rf_transnono.text.toString().trim()
                        transactiondate = str_transdate
                        advance = txt_rf_advanceamnt.text.toString().trim()
                        if (transactionno.equals("")) {
                            toast("Enter Transaction No.")
                            return@setOnClickListener
                        } else if (transactiondate.equals("")) {
                            toast("Enter Transaction Date")
                            return@setOnClickListener
                        } else {
                            add_receipt(
                                Receiptdate,
                                "0",
                                cust_id,
                                Showlist.get(0).groupid,
                                Showlist.get(0).ticketno,
                                receiptamount.toString(),
                                SelectedPaymenttypeid,
                                debit_to,
                                chequeno,
                                chequedate,
                                chequebranch,
                                transactionno,
                                transactiondate,
                                BaseUtils.Currenttime(),
                                SelectedBAnkId,
                                reamrks,
                                advance,
                                ShowlistInstallments,
                                Showlist.get(0).tobecollected
                            )
                        }
                    }
                    else if (SelectedPaymenttypename.equals("Cash")) {
                        var chequebranch = ""
                        var chequebank = ""
                        var chequedate = ""
                        var chequeno = ""
                        var transactionno = ""
                        var advance = ""
                        advance = txt_rf_advanceamnt.text.toString().trim()
                        add_receipt(
                            Receiptdate,
                            "0",
                            cust_id,
                            Showlist.get(0).groupid,
                            Showlist.get(0).ticketno,
                            receiptamount.toString(),
                            SelectedPaymenttypeid,
                            debit_to,
                            chequeno,
                            chequedate,
                            chequebranch,
                            transactionno,
                            transactiondate,
                            BaseUtils.Currenttime(),
                            SelectedBAnkId,
                            reamrks,
                            advance,
                            ShowlistInstallments,
                            Showlist.get(0).tobecollected
                        )
                    }

                } else {
                    toast("Select Payment type")
                }
                else -> toast("Enter Valid Receipt Amount")
            }
        }
        return view
    }

    private fun PopulateList(neglectifZero: Boolean) {

        val enteredAmount = Constants.stringToInt(Constants.isEmtytoZero(edt_receiptamount.text.toString()))
        if (enteredAmount > 0) {
            installment_layout.visibility = View.VISIBLE
            var balance = enteredAmount
            println("enteredamnt $enteredAmount")

            for (i in ShowlistInstallments.indices) {
                val cursor = ShowlistInstallments.get(i)
                var pendingAmnt = Constants.stringToInt(cursor.pending)
                var penaltyAmnt = Constants.stringToInt(cursor.penalty)
                var bonusAmnt = Constants.stringToInt(cursor.bonus)
                var biddingId = cursor.biddingid

                if (balance > 0) {

                    var payableAmount = pendingAmnt + penaltyAmnt - bonusAmnt
                    println("payableamnt $payableAmount round ${i.toString()}")
                    if (balance >= payableAmount) {
                        val receiptamnt = (payableAmount - penaltyAmnt).toString()
                        masterdb.updatepayamount(
                            biddingId,
                            payableAmount.toString(),
                            receiptamnt,
                            penaltyAmnt.toString()
                        )
                        balance = balance - payableAmount
                        println("balance greater than payableamoount $balance round ${i.toString()}")

                    } else {
                        var re = (balance - penaltyAmnt)
                        if (re < 0) {
                            re = 0
                        }
                        val receiptamnt = re.toString()
                        if (getPrefsBoolean(Constants.LesserThanPenalty, false)) {
                            var nearpenlty: String = ""
                            if (re > 0) {
                                nearpenlty = penaltyAmnt.toString()
                            } else {
                                var nearpenalty = balance
                                nearpenlty = nearpenalty.toString().replace("-", "")
                            }

                            println("balance lesser than payableamoount $balance recptamnt $receiptamnt near $nearpenlty round ${i.toString()}")
                            masterdb.updatepayamount(biddingId, balance.toString(), receiptamnt, nearpenlty)
                            balance = balance - payableAmount
                            if (balance < 0) {
                                balance = 0
                            }
                        } else {

                            masterdb.updatepayamount(biddingId, balance.toString(), receiptamnt, balance.toString())
                            balance = balance - payableAmount
                            if (balance < 0) {
                                balance = 0
                            }
                        }


                    }
                } else {
                    masterdb.updatepayamount(biddingId, balance.toString(), "0", balance.toString())
                }
            }
            println("last balance $balance")
            if (balance > 0) {
                isokayToSubmit = true
                if (getPrefsString(Constants.advanceAvailable, "").equals("yes")) {
                    if (enteredAmount.equals(balance)) {
                        AdvanceOnly = true
                    } else {
                        AdvanceOnly = false
                    }
                    isAdvanceAvailable = true
                    advance_layout.visibility = View.VISIBLE
                    txt_rf_advanceamnt.setText(balance.toString())
                    edt_receiptamount.setTextColor(resources.getColor(R.color.colorBlack))

                } else {
                    isokayToSubmit = false
                    isAdvanceAvailable = false
                    advance_layout.visibility = View.GONE
                    txt_rf_advanceamnt.setText("0")
                    edt_receiptamount.setTextColor(resources.getColor(R.color.red_500))
                    toast("Enter Valid Receipt Amount")
                }
            } else {
                isokayToSubmit = true
                isAdvanceAvailable = false
                advance_layout.visibility = View.GONE
                txt_rf_advanceamnt.setText("0")
                edt_receiptamount.setTextColor(resources.getColor(R.color.colorBlack))
            }
            integrateInstallmentList(masterdb.getTempInstallments())
        } else {
            txt_rf_advanceamnt.setText("0")
            if (neglectifZero) {
                isokayToSubmit = false
                masterdb.makereceiptzero()
                integrateInstallmentList(masterdb.getTempInstallments())
                toast("Enter Valid Receipt Amount")
            } else {
                isokayToSubmit = false
//                masterdb.getInstallmentDetails(enrolid)
                masterdb.deleteTable_TempInstallments()
                masterdb.addTempInstallments(masterdb.getInstallmentList())
                integrateInstallmentList(masterdb.getTempInstallments())
                toast("Enter Valid Receipt Amount")
            }

        }

    }

    private fun add_receipt(
        receiptdate: String,
        other_branch: String,
        customer_id: String,
        groupid: String,
        ticketno: String,
        receiptamount: String,
        paytypeid: String,
        debitto: String,
        chequeno: String,
        cheque_date: String,
        branch_name: String,
        transaction_no: String,
        transaction_date: String,
        receipt_time: String,
        bankname: String,
        remarks: String,
        advance: String,
        installments: ArrayList<InstallmentListModel>,
        to_be_collected : String
    ) {
        if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
            if (AdvanceOnly) {
                showProgressDialog()
                val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
                val loginparameters = HashMap<String, String>()
                loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
                loginparameters.put("enrollment_id", enrolid)
                loginparameters.put("offline_receipt_no", "")
                loginparameters.put("pending_amount", to_be_collected)
                loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
                loginparameters.put("other_branch", other_branch)
                loginparameters.put("customer_id", customer_id)
                loginparameters.put("receipt_date", receiptdate)
                loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
                loginparameters.put("employee_branch_id", getPrefsString(Constants.branches, ""))
                loginparameters.put("adjust_id", "0")
                loginparameters.put("adv_receipt_amount", advance)
                loginparameters.put("payment_type_id", paytypeid)
                loginparameters.put("debit_to", debitto)
                loginparameters.put("cheque_no", chequeno)
                loginparameters.put("cheque_date", cheque_date)
                loginparameters.put("cheque_clear_return_date", "0")
                loginparameters.put("bank_name_id", bankname)
                loginparameters.put("branch_name", branch_name)
                loginparameters.put("transaction_no", transaction_no)
                loginparameters.put("transaction_date", transaction_date)
                loginparameters.put("receipt_time", receipt_time)
                loginparameters.put("printed", "0")
                loginparameters.put("remarks", remarks)
                loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
                loginparameters.put("db",getPrefsString(Constants.db,""))
                val LeadListRequest = getleadslist.add_adv_receipt(loginparameters)
                LeadListRequest.enqueue(object : Callback<Receiptsuccessmodel> {
                    override fun onFailure(call: Call<Receiptsuccessmodel>, t: Throwable) {
                        hideProgressDialog()
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<Receiptsuccessmodel>, response: Response<Receiptsuccessmodel>
                    ) {
                        hideProgressDialog()
                        when {
                            response.isSuccessful -> {
                                when {
                                    response.code().equals(200) -> {
                                        val bundle = Bundle()
                                        bundle.putString("recptno", response.body()!!.getReceiptNo())
                                        bundle.putString(
                                            "recptdate",
                                            BaseUtils.ConvertTodmY(receiptdate) + " / " + BaseUtils.ConvertTohma(
                                                receipt_time
                                            )
                                        )
                                        bundle.putString("customername", custname)
                                        var mobile = ""
                                        if (arguments!!.getString("cust_mobile").isNullOrEmpty()) {
                                            try {
                                                mobile = arguments!!.getString("cust_phone")
                                            } catch (e: java.lang.Exception) {
                                                mobile = ""
                                            }
                                        } else {
                                            try {
                                                mobile = arguments!!.getString("cust_mobile")
                                            } catch (e: java.lang.Exception) {
                                                mobile = ""
                                            }
                                        }
                                        bundle.putString("customermobile", mobile)
                                        bundle.putString("customerid", arguments!!.getString("cust_code"))
                                        bundle.putString("groupname", Showlist.get(0).groupname)
                                        bundle.putString("ticketno", Showlist.get(0).ticketno)
                                        bundle.putString("totaldue", Showlist.get(0).tobecollected)
                                        bundle.putString("penalty", "0")
                                        bundle.putString("bonus", "0")
                                        bundle.putString("receivedamount", receiptamount)
                                        bundle.putString("paymentmode", SelectedPaymenttypename)
                                        bundle.putString("chequeno", chequeno)
                                        bundle.putString("chequedate", cheque_date)
                                        bundle.putString("chequebank", SelectedBAnkName)
                                        bundle.putString("chequebranch", branch_name)
                                        bundle.putString("transactionno", transaction_no)
                                        bundle.putString("transactiondate", transaction_date)
                                        bundle.putString("installmentno", " ")
                                        bundle.putString("isprinted", "No")
                                        doFragmentTransactionWithBundle(ReceiptPreview(), "preview", true, bundle)
                                        toast("Receipt Generated Successfully")
                                    }
                                    else -> {
                                        toast(response.message().toString())
                                    }
                                }
                            }
                        }
                    }
                })

            }
            else {
                showProgressDialog()
                val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
                val loginparameters = HashMap<String, String>()
                loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
                loginparameters.put("enrollment_id", enrolid)
                loginparameters.put("pending_amount", to_be_collected)
                loginparameters.put("offline_receipt_no", "")
                loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
                loginparameters.put("other_branch", other_branch)
                loginparameters.put("customer_id", customer_id)
                loginparameters.put("receipt_date", receiptdate)
                loginparameters.put("group_id", groupid)
                loginparameters.put("ticket_no", ticketno)
                loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
                loginparameters.put("employee_branch_id", getPrefsString(Constants.branches, ""))
                loginparameters.put("adjust_id", "0")
                loginparameters.put("amount", receiptamount)
                loginparameters.put("payment_type_id", paytypeid)
                loginparameters.put("debit_to", debitto)
                loginparameters.put("cheque_no", chequeno)
                loginparameters.put("cheque_date", cheque_date)
                loginparameters.put("cheque_clear_return_date", "0")
                loginparameters.put("bank_name_id", bankname)
                loginparameters.put("branch_name", branch_name)
                loginparameters.put("transaction_no", transaction_no)
                loginparameters.put("transaction_date", transaction_date)
                loginparameters.put("receipt_time", receipt_time)
                loginparameters.put("printed", "0")
                loginparameters.put("remarks", remarks)
                loginparameters.put("adv_receipt_amount", advance)
                loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
                loginparameters.put("db",getPrefsString(Constants.db,""))
                var installment_penalty = 0
                var installment_bonus = 0
                var installmentnos: StringBuilder = StringBuilder()
                for (i in installments.indices) {
                    val payable = installments.get(i).payableamount.toInt()
                    if (payable > 0) {
                        loginparameters.put("Installments[$i][auction_id]", installments.get(i).biddingid)
                        loginparameters.put("Installments[$i][installment_no]", installments.get(i).auctionno)
                        loginparameters.put("Installments[$i][pending_days]", installments.get(i).pendingdays)
                        loginparameters.put("Installments[$i][penalty_inst_wise]", installments.get(i).nearpenalty)
                        val payableamnt =
                            (Constants.isEmtytoZero(installments.get(i).installment).toInt()) - (Constants.isEmtytoZero(
                                installments.get(i).bonus
                            ).toInt())
                        println("receiptamnt ${installments.get(i).Receiptamount} instlment ${installments.get(i).installment} payable $payableamnt")
                        if ((Constants.isEmtytoZero(installments.get(i).Receiptamount).toInt()) < payableamnt) {
                            installments.get(i).bonus = "0"
                            loginparameters.put("Installments[$i][bonus_inst_wise]", "0")
                        } else {
                            loginparameters.put("Installments[$i][bonus_inst_wise]", installments.get(i).bonus)
                        }
                        loginparameters.put("Installments[$i][discount_inst_wise]", installments.get(i).discontondue)
                        loginparameters.put("Installments[$i][received_amount]", installments.get(i).Receiptamount)
                        installment_penalty += installments.get(i).nearpenalty.toInt()
                        installment_bonus += installments.get(i).bonus.toInt()
                        installmentnos.append(installments.get(i).auctionno + ",")
                        val payt = edt_paymenttype.text.toString()
                        if (payt.equals("Cheque", ignoreCase = true)) {
                            toast("Cheque Receipts will not reflect until it is cleared")
                        } else {
                            offlinedb.update_amnt_customer_only(
                                cust_id,
                                Constants.isEmtytoZero(installments.get(i).Receiptamount).toInt() + Constants.isEmtytoZero(installments.get(i).bonus).toInt())
                        }

                    }
                }
                val LeadListRequest = getleadslist.add_receipt(loginparameters)
                LeadListRequest.enqueue(object : Callback<Receiptsuccessmodel> {
                    override fun onFailure(call: Call<Receiptsuccessmodel>, t: Throwable) {
                        hideProgressDialog()
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<Receiptsuccessmodel>, response: Response<Receiptsuccessmodel>
                    ) {
                        hideProgressDialog()
                        when {
                            response.isSuccessful -> {
                                when {
                                    response.code().equals(200) -> {
                                        val bundle = Bundle()
                                        bundle.putString("recptno", response.body()!!.getReceiptNo())
                                        bundle.putString(
                                            "recptdate",
                                            BaseUtils.ConvertTodmY(receiptdate) + " / " + BaseUtils.ConvertTohma(
                                                receipt_time
                                            )
                                        )
                                        bundle.putString("customername", custname)
                                        var mobile = ""
                                        if (arguments!!.getString("cust_mobile").isNullOrEmpty()) {
                                            try {
                                                mobile = arguments!!.getString("cust_phone")
                                            } catch (e: java.lang.Exception) {
                                                mobile = ""
                                            }

                                        } else {
                                            try {
                                                mobile = arguments!!.getString("cust_mobile")
                                            } catch (e: java.lang.Exception) {
                                                mobile = ""
                                            }

                                        }
                                        bundle.putString("customermobile", mobile)
                                        bundle.putString("customerid", arguments!!.getString("cust_code"))
                                        bundle.putString("groupname", Showlist.get(0).groupname)
                                        bundle.putString("ticketno", Showlist.get(0).ticketno)
                                        bundle.putString("totaldue", Showlist.get(0).tobecollected)
                                        bundle.putString("penalty", installment_penalty.toString())
                                        bundle.putString("bonus", installment_bonus.toString())
                                        bundle.putString("receivedamount", receiptamount)
                                        bundle.putString("paymentmode", SelectedPaymenttypename)
                                        bundle.putString("chequeno", chequeno)
                                        bundle.putString("chequedate", cheque_date)
                                        bundle.putString("chequebank", SelectedBAnkName)
                                        bundle.putString("chequebranch", branch_name)
                                        bundle.putString("transactionno", transaction_no)
                                        bundle.putString("transactiondate", transaction_date)
                                        bundle.putString("installmentno", installmentnos.toString())
                                        bundle.putString("isprinted", "No")
                                        doFragmentTransactionWithBundle(ReceiptPreview(), "preview", true, bundle)

                                        toast("Receipt Genearted Successfully")
                                    }
                                    else -> {
                                        toast(response.message().toString())
                                    }
                                }
                            }
                        }
                    }
                })
            }
        }
        else {
            val offrecno = "OFF-${CurrentDate_ymd()}_${BaseUtils.Currenttime()}_${getPrefsInt(
                Constants.OFFLINE_RECNO,
                0
            ).toString()}"
            offlinedb.addOfflineReceiptdetails(
                getPrefsInt(Constants.OFFLINE_RECNO, 0).toString(),
                offrecno,
                enrolid,
                "0",
                customer_id,
                receiptdate,
                groupid,
                ticketno,
                "0",
                receiptamount,
                paytypeid,
                debitto,
                chequeno,
                cheque_date,
                "0",
                bankname,
                branch_name,
                transaction_no,
                transaction_date,
                receipt_time,
                "0",
                remarks,
                "No",
                Constants.isEmtytoZero(advance),
                AdvanceOnly.toString()
            )

            if (AdvanceOnly) {
                val payt = edt_paymenttype.text.toString()
                if (payt.equals("Cheque", ignoreCase = true)) {
                    toast("Cheque Receipts will not reflect until it is cleared")
                } else {
                    offlinedb.update_off_advance(enrolid, Constants.isEmtytoZero(advance).toInt())
                }
                val bundle = Bundle()
                bundle.putString("recptno", offrecno)
                bundle.putString(
                    "recptdate",
                    BaseUtils.ConvertTodmY(receiptdate) + " / " + BaseUtils.ConvertTohma(
                        receipt_time
                    )
                )
                bundle.putString("customername", custname)
                var mobile = ""
                if (arguments!!.getString("cust_mobile").isNullOrEmpty()) {
                    try {
                        mobile = arguments!!.getString("cust_phone")
                    } catch (e: java.lang.Exception) {
                        mobile = ""
                    }

                } else {
                    try {
                        mobile = arguments!!.getString("cust_mobile")
                    } catch (e: java.lang.Exception) {
                        mobile = ""
                    }

                }
                bundle.putString("customermobile", mobile)
                bundle.putString("customerid", arguments!!.getString("cust_code"))
                bundle.putString("groupname", Showlist.get(0).groupname)
                bundle.putString("ticketno", Showlist.get(0).ticketno)
                bundle.putString("totaldue", Showlist.get(0).tobecollected)
                bundle.putString("penalty", "0")
                bundle.putString("bonus", "0")
                bundle.putString("receivedamount", receiptamount)
                bundle.putString("paymentmode", SelectedPaymenttypename)
                bundle.putString("chequeno", chequeno)
                bundle.putString("chequedate", cheque_date)
                bundle.putString("chequebank", SelectedBAnkName)
                bundle.putString("chequebranch", branch_name)
                bundle.putString("transactionno", transaction_no)
                bundle.putString("transactiondate", transaction_date)
                bundle.putString("installmentno", " ")
                bundle.putString("isprinted", "No")
                doFragmentTransactionWithBundle(ReceiptPreview(), "preview", true, bundle)
                toast("Receipt Generated Successfully")
            } else
            {
                offlinedb.update_off_advance(enrolid, Constants.isEmtytoZero(advance).toInt())
                var installment_penalty = 0
                var installment_bonus = 0
                var installmentnos: StringBuilder = StringBuilder()
                for (i in installments.indices) {
                    val payable = installments.get(i).payableamount.toInt()
                    if (payable > 0) {
                        var bonus: String = "0"
                        val payableamnt =
                            (Constants.isEmtytoZero(installments.get(i).installment).toInt()) - (Constants.isEmtytoZero(
                                installments.get(i).bonus
                            ).toInt())
                        println("receiptamnt ${installments.get(i).Receiptamount} instlment ${installments.get(i).installment} payable $payableamnt")
                        if (installments.get(i).Receiptamount.toInt() < payableamnt) {
                            println("leser ")
                            bonus = "0"
                        } else {
                            println("not leser ")
                            bonus = installments.get(i).bonus
                        }
                        offlinedb.addOfflineInstallmentdetails(
                            getPrefsInt(Constants.OFFLINE_RECNO, 0).toString(),
                            enrolid,
                            installments.get(i).payableamount,
                            installments.get(i).auctionno,
                            installments.get(i).biddingid,
                            installments.get(i).pendingdays,
                            installments.get(i).nearpenalty,
                            bonus,
                            installments.get(i).discontondue,
                            installments.get(i).discountonpenalty,
                            installments.get(i).Receiptamount
                        )
                        installment_penalty += installments.get(i).nearpenalty.toInt()
                        installment_bonus += bonus.toInt()
                        installmentnos.append(installments.get(i).auctionno + ",")
                        val payt = edt_paymenttype.text.toString()
                        if (payt.equals("Cheque", ignoreCase = true)) {
                            toast("Cheque Receipts will not reflect until it is cleared")
                        } else {
                            var receiptamnt = Constants.isEmtytoZero(installments.get(i).Receiptamount).toInt()
                            if (receiptamnt <= 0) {
                                offlinedb.Upd_Off_insmt(
                                    cust_id,
                                    enrolid,
                                    installments.get(i).biddingid,
                                    0,
                                    Constants.isEmtytoZero(installments.get(i).bonus).toInt(),
                                    Constants.isEmtytoZero(installments.get(i).Receiptamount).toInt() + bonus.toInt()
                                )
                            } else {
                                offlinedb.Upd_Off_insmt(
                                    cust_id,
                                    enrolid,
                                    installments.get(i).biddingid,
                                    Constants.isEmtytoZero(installments.get(i).nearpenalty).toInt(),
                                    Constants.isEmtytoZero(installments.get(i).bonus).toInt(),
                                    Constants.isEmtytoZero(installments.get(i).Receiptamount).toInt() + bonus.toInt()
                                )
                            }

                        }
                    }
                }
                val bundle = Bundle()
                bundle.putString("recptno", offrecno)
                bundle.putString(
                    "recptdate",
                    BaseUtils.ConvertTodmY(receiptdate) + " / " + BaseUtils.ConvertTohma(
                        receipt_time
                    )
                )
                bundle.putString("customername", custname)
                var mobile = ""
                try {
                    if (arguments!!.getString("cust_mobile").isNullOrEmpty()) {
                        mobile = arguments!!.getString("cust_phone")
                    } else {
                        mobile = arguments!!.getString("cust_mobile")
                    }
                } catch (e: java.lang.Exception) {
                    mobile = ""
                }
                bundle.putString("customermobile", mobile)
                bundle.putString("customerid", arguments!!.getString("cust_code"))
                bundle.putString("groupname", Showlist.get(0).groupname)
                bundle.putString("ticketno", Showlist.get(0).ticketno)
                bundle.putString("totaldue", Showlist.get(0).tobecollected)
                bundle.putString("penalty", installment_penalty.toString())
                bundle.putString("bonus", installment_bonus.toString())
                bundle.putString("receivedamount", receiptamount)
                bundle.putString("paymentmode", SelectedPaymenttypename)
                bundle.putString("chequeno", chequeno)
                bundle.putString("chequedate", cheque_date)
                bundle.putString("chequebank", SelectedBAnkName)
                bundle.putString("chequebranch", branch_name)
                bundle.putString("transactionno", transaction_no)
                bundle.putString("transactiondate", transaction_date)
                bundle.putString("installmentno", installmentnos.toString())
                bundle.putString("isprinted", "No")
                doFragmentTransactionWithBundle(ReceiptPreview(), "preview", true, bundle)
                toast("Offline Receipt Generated Successfully")
                var recno = getPrefsInt(Constants.OFFLINE_RECNO, 0)
                recno += 1
                setPrefsInt(Constants.OFFLINE_RECNO, recno)
            }
        }
    }

    private fun get_group_detail(dates: String) {
        showProgressDialog()
        var resultlist = java.util.ArrayList<GroupdetailsModel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        println("ten ${getPrefsString(Constants.tenantid, "")} enrlid $enrolid date $dates")
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("entrollment_id", enrolid)
        loginparameters.put("to_date", dates)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        txt_rf_receiptdat.setText(dates)
        Receiptdate = dates
        val LeadListRequest = getleadslist.get_individual_enrollment(loginparameters)
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
                                    grouplist = masterdb.getenrollmentList(enrolid)
                                    integrateGroupList(grouplist)
                                    masterdb.getInstallmentDetails(enrolid)
                                    integrateInstallmentList(masterdb.getTempInstallments())
                                } else {
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    fun integrateGroupList(leadslist: ArrayList<GroupListModel>) {
        Showlist.clear()
        Showlist.addAll(leadslist)
        System.out.println("selectedlist ${Showlist[0].groupname}")
        grouplistAdapter = ReceiptAdapter(Showlist, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
            }

            override fun onLongClicked(position: Int) {
            }
        })
        Recycler_receipts.setAdapter(grouplistAdapter)
    }

    fun integrateInstallmentList(leadslist: ArrayList<InstallmentListModel>) {
        if (leadslist.size > 0) {
            ShowlistInstallments.clear()
            ShowlistInstallments.addAll(leadslist)
            groupInstallmentAdapter = InstallmentsAdapter(ShowlistInstallments, object : AdapterEdittextListener {
                override fun onEdited(position: Int, value: String) {
                }

                override fun onPositionClicked(view: View, position: Int, oldvalue: String) {
                    if (view.getTag().equals("penalty_text")) {
                        changevalueDilog("Penalty", position, oldvalue)
                    } else if (view.getTag().equals("custname")) {

                    }
                }

                override fun onLongClicked(position: Int) {
                }
            })
            Recycler_instalments.setAdapter(groupInstallmentAdapter)
        } else {

            toast("No instalments available to pay")
        }
    }

    fun changevalueDilog(item: String, position: Int, oldvalue: String) {
        val dialogBuilder = AlertDialog.Builder(context, com.mazenet.mani.gurugubera.R.style.MyDialogTheme)

        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.change_value_dilog, null)
        dialogBuilder.setView(dialogView)
        val txt_cv_heading =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.txt_cv_heading) as TextView
        val edt_cv_value =
            dialogView.findViewById(com.mazenet.mani.gurugubera.R.id.edt_cv_value) as EditText
        dialogBuilder
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                CloseKeyBoard()
            }
        if (item.equals("Penalty")) {
            txt_cv_heading.setText(Constants.Changepenalty)
            edt_cv_value.setHint(Constants.newenalty)
            edt_cv_value.setText(oldvalue)
        }
        dialogBuilder.setPositiveButton("Ok", null)

        edt_cv_value.requestFocus()
        ShowKeyBoard()
        val alertDialog = dialogBuilder.create()
        alertDialog.setOnShowListener(DialogInterface.OnShowListener {
            val b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            b.setOnClickListener(View.OnClickListener {
                if (edt_cv_value.text.isNotEmpty()) {
                    edt_receiptamount.setText("")
                    val penalty = edt_cv_value.text.toString()
                    masterdb.updatepenalty(ShowlistInstallments[position].biddingid, penalty)
                    PopulateList(true)
                    alertDialog.dismiss()
                } else {
                    edt_cv_value.requestFocus()
                    ShowKeyBoard()
                    toast("Enter $item Amount")
                }
            })
        })
        alertDialog.show()
    }

    override fun onBackPressed(): Boolean {
        var bool = false
        val builder = AlertDialog.Builder(context, com.mazenet.mani.gurugubera.R.style.MyErrorDialogTheme)
        builder.setCancelable(false)
        builder.setTitle("Caution")
            .setIcon(resources.getDrawable(R.drawable.ic_info_red))
            .setMessage("Do you want to discard receipt generation ?")
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
                bool = false
            }
            .setPositiveButton("Yes") { dialog, which ->
                dialog.dismiss()
                val fragmentManager = fragmentManager
                fragmentManager!!.popBackStack(
                    fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                bool = true
            }
        builder.create().show()
        return bool
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }


}

