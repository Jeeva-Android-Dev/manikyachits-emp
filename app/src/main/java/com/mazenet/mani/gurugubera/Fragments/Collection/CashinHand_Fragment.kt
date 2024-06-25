package com.mazenet.mani.gurugubera.Fragments.Collection


import android.app.AlertDialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.CashinhandModel
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CashinHand_Fragment : BaseFragment() {


    var cashinhand: String = ""
    var ShowingSettlementPage: Boolean = false
    lateinit var btn_settleamount: Button
    lateinit var btn_viewgenrecpts: Button
    lateinit var edt_ch_1: TextInputEditText
    lateinit var edt_ch_2: TextInputEditText
    lateinit var edt_ch_5: TextInputEditText
    lateinit var edt_ch_10: TextInputEditText
    lateinit var edt_ch_20: TextInputEditText
    lateinit var edt_ch_50: TextInputEditText
    lateinit var edt_ch_100: TextInputEditText
    lateinit var edt_ch_200: TextInputEditText
    lateinit var edt_ch_500: TextInputEditText
    lateinit var edt_ch_2000: TextInputEditText
    lateinit var edt_ch_othercharges: TextInputEditText
    lateinit var edt_ch_total: TextInputEditText
    lateinit var edt_ch_remarks: EditText
    lateinit var settlement_layout: ConstraintLayout
    lateinit var layout_buttons: ConstraintLayout
    lateinit var txt_cashnhand: TextView
    lateinit var btn_refresh: ImageView
    var totalAmount = "0"
    var cashInHand = "0"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view =
            inflater.inflate(R.layout.fragment_cashin_hand_, container, false)
        (activity as HomeActivity).setActionBarTitle("Cash in Hand")
        btn_settleamount = view.findViewById(R.id.btn_settleamount) as Button
        btn_viewgenrecpts = view.findViewById(R.id.btn_viewgenrecpts) as Button
        edt_ch_1 = view.findViewById(R.id.edt_ch_1) as TextInputEditText
        edt_ch_2 = view.findViewById(R.id.edt_ch_2) as TextInputEditText
        edt_ch_5 = view.findViewById(R.id.edt_ch_5) as TextInputEditText
        edt_ch_10 = view.findViewById(R.id.edt_ch_10) as TextInputEditText
        edt_ch_20 = view.findViewById(R.id.edt_ch_20) as TextInputEditText
        edt_ch_50 = view.findViewById(R.id.edt_ch_50) as TextInputEditText
        edt_ch_100 = view.findViewById(R.id.edt_ch_100) as TextInputEditText
        edt_ch_200 = view.findViewById(R.id.edt_ch_200) as TextInputEditText
        edt_ch_500 = view.findViewById(R.id.edt_ch_500) as TextInputEditText
        edt_ch_2000 = view.findViewById(R.id.edt_ch_2000) as TextInputEditText
        edt_ch_othercharges = view.findViewById(R.id.edt_ch_othercharges) as TextInputEditText
        edt_ch_total = view.findViewById(R.id.edt_ch_total) as TextInputEditText
        edt_ch_remarks = view.findViewById(R.id.edt_ch_remarks) as EditText
        settlement_layout = view.findViewById(R.id.settlement_layout) as ConstraintLayout
        layout_buttons = view.findViewById(R.id.layout_buttons) as ConstraintLayout
        txt_cashnhand = view.findViewById(R.id.txt_cashnhand) as TextView
        btn_refresh = view.findViewById(R.id.btn_refresh) as ImageView
        if (checkForInternet()) {
            get_cashinhand()
        } else {
            toast("No Internat Connecton!")
        }
        edt_ch_1.requestFocus()
        ShowKeyBoard()
        btn_refresh.setOnClickListener {
            if (checkForInternet()) {
                get_cashinhand()
            } else {
                toast("No Internat Connecton!")
            }
        }
        btn_settleamount.setOnClickListener {
            val cashinhand = Constants.stringToInt(Constants.isEmtytoZero(cashInHand))
            val totalamount = Constants.stringToInt(Constants.isEmtytoZero(edt_ch_total.text.toString()))
            if (totalamount > 0) {
                if (totalamount <= cashinhand) {
                    if (checkForInternet()) {
                        add_cash_settlement(
                            cashinhand.toString(),
                            Constants.isEmtytoZero(edt_ch_1.text.toString()),
                            Constants.isEmtytoZero(edt_ch_2.text.toString()),
                            Constants.isEmtytoZero(edt_ch_5.text.toString()),
                            Constants.isEmtytoZero(edt_ch_10.text.toString()),
                            Constants.isEmtytoZero(edt_ch_20.text.toString()),
                            Constants.isEmtytoZero(edt_ch_50.text.toString()),
                            Constants.isEmtytoZero(edt_ch_100.text.toString()),
                            Constants.isEmtytoZero(edt_ch_200.text.toString()),
                            Constants.isEmtytoZero(edt_ch_500.text.toString()),
                            "0",
                            Constants.isEmtytoZero(edt_ch_2000.text.toString()),
                            totalAmount,
                            Constants.isEmtytoZero(edt_ch_remarks.text.toString())
                        )
                    } else {
                        toast("No Internet connection !")
                    }

                } else {
                    edt_ch_total.setTextColor(resources.getColor(R.color.red_500))
                    toast("Settlement amount cannot be greater than Cashinhand")
                }
            } else {
                edt_ch_total.setTextColor(resources.getColor(R.color.red_500))
                toast("Settlement amount cannot be Zero")
            }
        }
        edt_ch_total.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val cashinhand = Constants.stringToInt(Constants.isEmtytoZero(cashInHand))
                    val totalamount = Constants.stringToInt(Constants.isEmtytoZero(edt_ch_total.text.toString()))
                    if (totalamount > 0) {
                        if (totalamount > cashinhand) {
                            edt_ch_total.setTextColor(resources.getColor(R.color.red_500))
                            toast("Settlement amount cannot be greater than Cashinhand")
                        } else {
                            edt_ch_total.setTextColor(resources.getColor(R.color.colorBlack))
                        }
                    } else {
                        edt_ch_total.setTextColor(resources.getColor(R.color.red_500))
                        toast("Settlement amount cannot be Zero")
                    }
                }

            })
        edt_ch_othercharges.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_1.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_2.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_5.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_10.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_20.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_50.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_100.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_200.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_500.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        edt_ch_2000.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    textwatch()
                }

                override fun afterTextChanged(editable: Editable) {
                    edt_ch_total.setText(totalAmount)
                }
            })
        return view
    }

    fun textwatch() {
        var edt2000 = 0
        var edt1 = 0
        var edt2 = 0
        var edt5 = 0
        var edt10 = 0
        var edt20 = 0
        var edt50 = 0
        var edt100 = 0
        var edt200 = 0
        var edt500 = 0
        var edtmisc = 0
        var edt1000 = 0
        try {
            var str1 = edt_ch_1.getText().toString()
            if (str1.equals("", ignoreCase = true)) {
                str1 = "0"
                edt1 = Integer.parseInt(str1)
            } else {
                edt1 = Integer.parseInt(str1)
            }
            var str2 = edt_ch_2.getText().toString()
            if (str2.equals("", ignoreCase = true)) {
                str2 = "0"
                edt2 = Integer.parseInt(str2)
            } else {
                edt2 = Integer.parseInt(str2)
            }
            var str5 = edt_ch_5.getText().toString()
            if (str5.equals("", ignoreCase = true)) {
                str5 = "0"
                edt5 = Integer.parseInt(str5)
            } else {
                edt5 = Integer.parseInt(str5)
            }
            var str10 = edt_ch_10.getText().toString()
            if (str10.equals("", ignoreCase = true)) {
                str10 = "0"
                edt10 = Integer.parseInt(str10)
            } else {
                edt10 = Integer.parseInt(str10)
            }
            var str20 = edt_ch_20.getText().toString()
            if (str20.equals("", ignoreCase = true)) {
                str20 = "0"
                edt20 = Integer.parseInt(str20)
            } else {
                edt20 = Integer.parseInt(str20)
            }
            var str50 = edt_ch_50.getText().toString()
            if (str50.equals("", ignoreCase = true)) {
                str50 = "0"
                edt50 = Integer.parseInt(str50)
            } else {
                edt50 = Integer.parseInt(str50)
            }
            var str100 = edt_ch_100.getText().toString()
            if (str100.equals("", ignoreCase = true)) {
                str100 = "0"
                edt100 = Integer.parseInt(str100)
            } else {
                edt100 = Integer.parseInt(str100)
            }
            var str200 = edt_ch_200.getText().toString()
            if (str200.equals("", ignoreCase = true)) {
                str200 = "0"
                edt200 = Integer.parseInt(str200)
            } else {
                edt200 = Integer.parseInt(str200)
            }
            var str500 = edt_ch_500.getText().toString()
            if (str500.equals("", ignoreCase = true)) {
                str500 = "0"
                edt500 = Integer.parseInt(str500)
            } else {
                edt500 = Integer.parseInt(str500)
            }

            var str2000 = edt_ch_2000.getText().toString()
            println("str 2000  $str2000")
            if (str2000.equals("", ignoreCase = true)) {
                str2000 = "0"
                edt2000 = Integer.parseInt(str2000)
            } else {
                edt2000 = Integer.parseInt(str2000)
            }
            var strmisc = edt_ch_othercharges.getText().toString()
            if (strmisc.equals("", ignoreCase = true)) {
                strmisc = "0"
                edtmisc = Integer.parseInt(strmisc)
            } else {
                edtmisc = Integer.parseInt(strmisc)
            }

            totalAmount =
                (edt1 + edt2 * 2 + edt5 * 5 + edt10 * 10 + edt20 * 20 + edt50 * 50 + edt100 * 100 + edt200 * 200 + edt500 * 500 + edt2000 * 2000 + edt1000 * 1000 + edtmisc).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun add_cash_settlement(
        total_amount: String,
        amt_1: String,
        amt_2: String,
        amt_5: String,
        amt_10: String,
        amt_20: String,
        amt_50: String,
        amt_100: String,
        amt_200: String,
        amt_500: String,
        amt_1000: String,
        amt_2000: String,
        received_amount: String,
        remarks: String
    ) {
        showProgressDialog()
        val cashsettlementService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("branch_id", getPrefsString(Constants.branches, ""))
        loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
        loginparameters.put("settlement_date", BaseUtils.CurrentDate_ymd())
        loginparameters.put("total_amount", total_amount)
        loginparameters.put("amt_1", amt_1)
        loginparameters.put("amt_2", amt_2)
        loginparameters.put("amt_5", amt_5)
        loginparameters.put("amt_10", amt_10)
        loginparameters.put("amt_20", amt_20)
        loginparameters.put("amt_50", amt_50)
        loginparameters.put("amt_100", amt_100)
        loginparameters.put("amt_200", amt_200)
        loginparameters.put("amt_500", amt_500)
        loginparameters.put("amt_1000", amt_1000)
        loginparameters.put("amt_2000", amt_2000)
        loginparameters.put("received_amount", received_amount)
        loginparameters.put("collection_employee", getPrefsInt(Constants.employee_id, 0).toString())
        loginparameters.put("remarks", remarks)
        loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val add_cashsettle = cashsettlementService.add_cash_settlement(loginparameters)
        add_cashsettle.enqueue(object : Callback<successmsgmodel> {
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
                            edt_ch_1.setText("")
                            edt_ch_2.setText("")
                            edt_ch_5.setText("")
                            edt_ch_10.setText("")
                            edt_ch_20.setText("")
                            edt_ch_50.setText("")
                            edt_ch_100.setText("")
                            edt_ch_200.setText("")
                            edt_ch_500.setText("")
                            edt_ch_2000.setText("")
                            edt_ch_othercharges.setText("")
                            edt_ch_remarks.setText("")
                            get_cashinhand()
                            edt_ch_1.requestFocus()
                            ShowKeyBoard()
                            val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
                            builder.setCancelable(false)
                            builder.setMessage("Settlement successful")
                                .setPositiveButton("Ok") { dialog, which ->
                                    dialog.dismiss()
                                }
                            builder.create().show()
                        } else {
                            hideProgressDialog()
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

    fun get_cashinhand() {
        if (checkForInternet()) {
            showProgressDialog()

            val cashinhand_serv = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("db",getPrefsString(Constants.db,""))
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
            val cashinhand_req = cashinhand_serv.get_cashinhand(loginparameters)
            cashinhand_req.enqueue(object : Callback<CashinhandModel> {
                override fun onFailure(call: Call<CashinhandModel>, t: Throwable) {
                    toast(t.toString())
                    hideProgressDialog()
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<CashinhandModel>,
                    response: Response<CashinhandModel>
                ) {
                    if (response.isSuccessful) {
                        hideProgressDialog()
                        if (response.code().equals(200)) {
                            if (response.body()!!.getStatus().equals("Success")) {
                                txt_cashnhand.text =
                                    Constants.isEmtytoZero(response.body()!!.getCashInHand().toString())
                                cashInHand = response.body()!!.getCashInHand().toString()
                            } else {
                                hideProgressDialog()
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
        } else {
            toast(resources.getString(R.string.nointernet))
        }
    }

    override fun onBackPressed(): Boolean {
        var bool = false
        val builder = AlertDialog.Builder(context, com.mazenet.mani.gurugubera.R.style.MyErrorDialogTheme)
        builder.setCancelable(false)
        builder.setTitle("Caution")
            .setIcon(resources.getDrawable(R.drawable.ic_info_red))
            .setMessage("Do you want to discard Cash Settlement ?")
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
