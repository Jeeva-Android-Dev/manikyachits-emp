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
import com.mazenet.mani.gurugubera.Adapters.AuctionDetailList
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.AuctionDetail
import com.mazenet.mani.gurugubera.Model.PaymentDetailModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuctionReportDetail : BaseFragment() {
    var groupid = ""
    var custid = ""
    lateinit var txt_groupname: TextView
    lateinit var txt_ticketno: TextView
    lateinit var txt_chitvalue: TextView
    lateinit var txt_months: TextView
    lateinit var txt_members: TextView
    lateinit var txt_totrelsdamnt: TextView
    lateinit var txt_tot_reldamnt: TextView
    lateinit var nonscroller_auctiondetails: RecyclerView
    val customerlist = ArrayList<AuctionDetail>()
    val customerlist_showing = ArrayList<AuctionDetail>()
    lateinit var collReportAdapte: AuctionDetailList
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as HomeActivity).setActionBarTitle("Auction Details")
        val view = inflater.inflate(R.layout.fragment_auction_report_detail, container, false)
        txt_groupname = view.findViewById(R.id.txt_groupname) as TextView
        txt_ticketno = view.findViewById(R.id.txt_ticketno) as TextView
        txt_chitvalue = view.findViewById(R.id.txt_chitvalue) as TextView
        txt_months = view.findViewById(R.id.txt_months) as TextView
        txt_members = view.findViewById(R.id.txt_members) as TextView
        txt_totrelsdamnt = view.findViewById(R.id.txt_totrelsdamnt) as TextView
        txt_tot_reldamnt = view.findViewById(R.id.txt_tot_reldamnt) as TextView
        nonscroller_auctiondetails = view.findViewById(R.id.nonscroller_auctiondetails) as RecyclerView
        nonscroller_auctiondetails.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        nonscroller_auctiondetails.setLayoutManager(mLayoutManager)
        nonscroller_auctiondetails.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        nonscroller_auctiondetails.setItemAnimator(DefaultItemAnimator())
        groupid = arguments!!.getString("groupid")
        custid = arguments!!.getString("custid")
        get_details(groupid)
        return view
    }

    fun get_details(group_id: String) {
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("group_id", group_id)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val LeadListRequest = getleadslist.get_auction_details(loginparameters)
        LeadListRequest.enqueue(object : Callback<PaymentDetailModel> {

            override fun onFailure(call: Call<PaymentDetailModel>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
                nonscroller_auctiondetails.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<PaymentDetailModel>,
                response: Response<PaymentDetailModel>
            ) {
                hideProgressDialog()
                when {
                    response.isSuccessful -> {
                        when {
                            response.code().equals(200) -> {
                                txt_chitvalue.setText(
                                    Constants.money_convertor(
                                        Constants.isEmtytoZero(response.body()!!.getChitValue()!!),
                                        false
                                    )
                                )
                                txt_groupname.setText(response.body()!!.getGroupName())
                                txt_members.setText(response.body()!!.getNoOfMembers())
                                txt_months.setText(response.body()!!.getNoOfMonths())
                                val resultlist = response.body()!!.getAuctionDetails()
                                if (resultlist!!.size > 0) {
                                    nonscroller_auctiondetails.visibility = View.VISIBLE
                                    integrateList(response.body()!!.getAuctionDetails()!!)
                                } else {
                                    nonscroller_auctiondetails.visibility = View.GONE
                                }
                            }
                            else -> nonscroller_auctiondetails.visibility = View.GONE
                        }
                    }
                    else -> {
                        hideProgressDialog()
                        nonscroller_auctiondetails.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun integrateList(leadslist: ArrayList<AuctionDetail>) {
        if (leadslist.size > 0) {
            customerlist.clear()
            customerlist_showing.clear()
            customerlist.addAll(leadslist)
            customerlist_showing.addAll(leadslist)
            collReportAdapte = AuctionDetailList(customerlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {

                }

                override fun onLongClicked(position: Int) {
                }
            })
            try {
                var total_release = 0
                var total_released = 0
                var pos = 0
                for (i in customerlist_showing.indices) {

                    total_release += Constants.isEmtytoZero(customerlist_showing.get(i).getTotalReleaseAmount()!!).toInt()
                    total_released += Constants.isEmtytoZero(customerlist_showing.get(i).getCustomerReleased()!!).toInt()
                    if (customerlist_showing.get(i).getCustomerId().equals(custid)) {
                        pos = i
                    }
                }
                println("totreld $total_released totrel $total_release")
                txt_tot_reldamnt.setText(Constants.money_convertor(total_released.toString(), false))
                txt_totrelsdamnt.setText(Constants.money_convertor(total_release.toString(), false))
                nonscroller_auctiondetails.scrollToPosition(pos)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            nonscroller_auctiondetails.setAdapter(collReportAdapte)


        } else {
            toast("No Customers Available")
        }
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
