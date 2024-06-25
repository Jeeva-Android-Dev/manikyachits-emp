package com.mazenet.mani.gurugubera.Fragments.Collection

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Activities.PrintActivity
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_receipt_preview.view.*


class ReceiptPreview : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.fragment_receipt_preview, container, false)
        (activity as HomeActivity).setActionBarTitle("Preview")
        if (arguments!!.getString("isprinted").equals("Yes",ignoreCase = true)) {
            try {
                val recno = arguments!!.getString("recptno")
                println("rec $recno")
                if (recno.isNullOrEmpty()) {
println("rec $recno")
                } else {
                    view.txt_rp_recno.setText(recno)
                    view.txt_rp_isduplicate.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                val recno = arguments!!.getString("recptno")
                if (recno.isNullOrEmpty()) {
                } else {
                    view.txt_rp_recno.setText(recno)
                    view.txt_rp_isduplicate.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        view.txt_rp_date.setText(arguments!!.getString("recptdate"))
        view.txt_rp_cusname.setText(arguments!!.getString("customername"))
        if(arguments!!.getString("customerid").isNullOrEmpty())
        {
            view.txt_rp_custid.visibility=View.INVISIBLE
        }
        if(arguments!!.getString("customermobile").isNullOrEmpty())
        {
            view.txt_rp_cusmobile.visibility=View.INVISIBLE
        }
        view.txt_rp_custid.setText(arguments!!.getString("customerid"))
        view.txt_rp_cusmobile.setText(arguments!!.getString("customermobile"))
        view.txt_rp_grpno.setText(
            arguments!!.getString("groupname")

        )
        view.txt_rp_tktno.setText(
            arguments!!.getString("ticketno")
        )

        view.txt_rp_dueno.setText(
            arguments!!.getString(
                "installmentno"
            )
        )
        view.txt_rp_totaamntdue.setText(arguments!!.getString("totaldue"))
        view.txt_rp_penaulty.setText(arguments!!.getString("penalty"))
        view.txt_rp_bonus.setText(arguments!!.getString("bonus"))
        view.txt_rp_receivedamnt.setText(arguments!!.getString("receivedamount"))
        val paytype = arguments!!.getString("paymentmode")
        view.txt_rp_preparedby.setText(getPrefsString(Constants.username, ""))
        view.txt_rp_paymentmode.setText(paytype)
        if (paytype.equals("Cheque")) {
            view.chequelayout.visibility = View.VISIBLE
            view.ddlayout.visibility = View.GONE
            view.transactionlayout.visibility = View.GONE
            view.txt_rp_chequebank.setText(arguments!!.getString("chequebank"))
            view.txt_rp_chequeno.setText(arguments!!.getString("chequeno"))
            view.txt_rp_chequebranch.setText(arguments!!.getString("chequebranch"))
            view.txt_rp_chequebdate.setText(arguments!!.getString("chequedate"))
        } else if (paytype.equals("D.D")) {
            view.chequelayout.visibility = View.GONE
            view.ddlayout.visibility = View.VISIBLE
            view.transactionlayout.visibility = View.GONE
            view.txt_rp_ddbank.setText(arguments!!.getString("chequebank"))
            view.txt_rp_ddno.setText(arguments!!.getString("chequeno"))
            view.txt_rp_ddbranch.setText(arguments!!.getString("chequebranch"))
            view.txt_rp_dddate.setText(arguments!!.getString("chequedate"))
        } else if (paytype.equals("RTGS/NEFT") || paytype.equals("Card")) {
            view.chequelayout.visibility = View.GONE
            view.ddlayout.visibility = View.GONE
            view.transactionlayout.visibility = View.VISIBLE
            view.txt_rp_transaction_no.setText(arguments!!.getString("transactionno"))
            view.txt_rp_transactiondate.setText(arguments!!.getString("transactiondate"))
        } else {
            view.chequelayout.visibility = View.GONE
            view.ddlayout.visibility = View.GONE
            view.transactionlayout.visibility = View.GONE
        }
        view.txt_rp_tenantname.setText(getPrefsString("teanant_name", ""))
        val address1 = getPrefsString("tenant_address1", "")
        val address2 = getPrefsString("tenant_address2", "")
        val tenant_state = getPrefsString("tenant_state", "")
        val tenant_phone = getPrefsString("tenant_phone", "")
        val tenant_mobile = getPrefsString("tenant_mobile", "")
        var full_address = StringBuilder()
        if (address1.isEmpty()) {
            if (address2.isEmpty()) {
            } else {
                full_address.append(address2 + "\n")
            }
        } else {
            full_address.append(address1 + "\n")
        }
        full_address.append(tenant_state + ",")
        if (tenant_phone.isEmpty()) {
            if (tenant_mobile.isEmpty()) {
            } else {
                full_address.append(tenant_mobile)
            }
        } else {
            full_address.append(tenant_phone)
        }
        view.txt_rp_address.setText(full_address.toString())
        view.fab_print.setOnClickListener {
            val intent = Intent(activity, PrintActivity::class.java)
            intent.putExtra("Cusname", arguments!!.getString("customername"))
            intent.putExtra("recptno", arguments!!.getString("recptno"))
            intent.putExtra("recptdate", arguments!!.getString("recptdate"))
            intent.putExtra("customerid", arguments!!.getString("customerid"))
            intent.putExtra("customermobile", arguments!!.getString("customermobile"))
            intent.putExtra("groupname", arguments!!.getString("groupname"))
            intent.putExtra("ticketno", arguments!!.getString("ticketno"))
            intent.putExtra("totaldue", arguments!!.getString("totaldue"))
            intent.putExtra("penalty", arguments!!.getString("penalty"))
            intent.putExtra("bonus", arguments!!.getString("bonus"))
            intent.putExtra("receivedamount", arguments!!.getString("receivedamount"))
            intent.putExtra("installmentno", arguments!!.getString("installmentno"))
            intent.putExtra("paymentmode", arguments!!.getString("paymentmode"))
            intent.putExtra("chequebank", arguments!!.getString("chequebank"))
            intent.putExtra("chequeno", arguments!!.getString("chequeno"))
            intent.putExtra("chequebranch", arguments!!.getString("chequebranch"))
            intent.putExtra("chequedate", arguments!!.getString("chequedate"))
            intent.putExtra("transactionno", arguments!!.getString("transactionno"))
            intent.putExtra("transactiondate", arguments!!.getString("transactiondate"))
            intent.putExtra("isprinted", arguments!!.getString("isprinted"))
            startActivity(intent)
            onBackPressed()
        }
        return view
    }

    override fun onBackPressed(): Boolean {
        val fragmentManager = fragmentManager
        fragmentManager!!.popBackStack(
            fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 2).id,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        return super.onBackPressed()
    }
}
