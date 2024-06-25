package com.mazenet.mani.gurugubera.Fragments.Reports


import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ComtPayDetailList
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.CommitpayModel
import com.mazenet.mani.gurugubera.Model.GroupDetail

import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommitmentPaymentReportDetail : BaseFragment() {
    var agentid = ""
    var custid = ""
    lateinit var agent_image: CircleImageView
    lateinit var txt_ticketno: TextView
    lateinit var txt_chitvalue: TextView
    lateinit var txt_months: TextView
    lateinit var txt_members: TextView
    lateinit var txt_totrelsdamnt: TextView
    lateinit var txt_tot_reldamnt: TextView
    lateinit var nonscroller_agentdetails: RecyclerView
    val customerlist = ArrayList<GroupDetail>()
    val customerlist_showing = ArrayList<GroupDetail>()
    lateinit var collReportAdapte: ComtPayDetailList
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as HomeActivity).setActionBarTitle("Payment Details")
        val view = inflater.inflate(R.layout.fragmentcomtreport_details, container, false)
        agent_image = view.findViewById(R.id.cust_image) as CircleImageView
        txt_ticketno = view.findViewById(R.id.txt_ticketno) as TextView
        txt_chitvalue = view.findViewById(R.id.txt_chitvalue) as TextView
        txt_months = view.findViewById(R.id.txt_months) as TextView
        txt_members = view.findViewById(R.id.txt_members) as TextView
        txt_totrelsdamnt = view.findViewById(R.id.txt_totcomsnamnt) as TextView
        txt_tot_reldamnt = view.findViewById(R.id.txt_tot_paidamnt) as TextView
        nonscroller_agentdetails = view.findViewById(R.id.nonscroller_agentdetails) as RecyclerView
        nonscroller_agentdetails.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        nonscroller_agentdetails.setLayoutManager(mLayoutManager)
        nonscroller_agentdetails.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        nonscroller_agentdetails.setItemAnimator(DefaultItemAnimator())
        agentid = arguments!!.getString("customerid")
        get_details(agentid)
        return view
    }

    fun get_details(group_id: String) {
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("customer_id", group_id)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val LeadListRequest = getleadslist.get_comtpayment_details(loginparameters)
        LeadListRequest.enqueue(object : Callback<CommitpayModel> {

            override fun onFailure(call: Call<CommitpayModel>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
                nonscroller_agentdetails.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<CommitpayModel>,
                response: Response<CommitpayModel>
            ) {
                hideProgressDialog()
                when {
                    response.isSuccessful -> {
                        when {
                            response.code().equals(200) -> {
                                txt_chitvalue.setText(response.body()!!.getCustomerName())
                                if (response.body()!!.getProfilePhoto().isNullOrEmpty()) {

                                } else {
                                    Picasso.with(context).load(response.body()!!.getProfilePhoto()).resize(150, 150)
                                        .onlyScaleDown()
                                        .placeholder(
                                            R.drawable.ic_account_circle
                                        ).error(R.drawable.ic_account_circle)
                                        .centerInside().into(agent_image)
                                }

                                txt_members.setText(response.body()!!.getPhoneNo())
                                txt_months.setText(response.body()!!.getMobileNo())
                                val resultlist = response.body()!!.getGroupDetails()
                                if (resultlist!!.size > 0) {
                                    nonscroller_agentdetails.visibility = View.VISIBLE
                                    integrateList(resultlist)
                                } else {
                                    nonscroller_agentdetails.visibility = View.GONE
                                }
                            }
                            else -> nonscroller_agentdetails.visibility = View.GONE
                        }
                    }
                    else -> {
                        hideProgressDialog()
                        nonscroller_agentdetails.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun integrateList(leadslist: ArrayList<GroupDetail>) {
        if (leadslist.size > 0) {
            customerlist.clear()
            customerlist_showing.clear()
            customerlist.addAll(leadslist)
            customerlist_showing.addAll(leadslist)
            collReportAdapte = ComtPayDetailList(customerlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {

                }

                override fun onLongClicked(position: Int) {
                }
            })
            nonscroller_agentdetails.setAdapter(collReportAdapte)
            try {
                var total_release = 0
                var total_released = 0
                for (i in customerlist_showing) {
                    total_release += Constants.isEmtytoZero(i.getTotalPayment()!!).toInt()
                    total_released += Constants.isEmtytoZero(i.getPaymentPaid()!!).toInt()
                }
                txt_tot_reldamnt.setText(Constants.money_convertor(total_released.toString(), false))
                txt_totrelsdamnt.setText(Constants.money_convertor(total_release.toString(), false))
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
}
