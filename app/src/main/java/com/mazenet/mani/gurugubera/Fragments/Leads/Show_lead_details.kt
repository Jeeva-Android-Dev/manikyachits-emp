package com.mazenet.mani.gurugubera.Fragments.Leads


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ShowLeadEnrollments
import com.mazenet.mani.gurugubera.Adapters.ShowLeadFollowups
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.EnrolledDetail
import com.mazenet.mani.gurugubera.Model.LastFollowupDetail
import com.mazenet.mani.gurugubera.Model.Leaddetailsmodel
import com.mazenet.mani.gurugubera.Model.showleadmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Show_lead_details : BaseFragment() {
    var FollwoupList = ArrayList<LastFollowupDetail>()
    var ShowingList = ArrayList<LastFollowupDetail>()
    var ShowingListEnroll = ArrayList<EnrolledDetail>()
    lateinit var followup_detail_title: ConstraintLayout
    lateinit var followup_layout: ConstraintLayout
    lateinit var enroll_detail_title: ConstraintLayout
    lateinit var enrollment_layout: ConstraintLayout
    lateinit var showleadsRecyclerList: RecyclerView
    lateinit var showenrollRecyclerview: RecyclerView
    lateinit var fab_add_followup: FloatingActionButton
    lateinit var showleadsadapter1: ShowLeadFollowups
    lateinit var showleadsenrolAdapter: ShowLeadEnrollments
    lateinit var txt_sld_leadname: TextView
    lateinit var txt_ald_branch: TextView
    lateinit var txt_sld_mobileno: TextView
    lateinit var txt_sld_leadgenon: TextView
    lateinit var txt_sld_email: TextView
    lateinit var txt_sld_phoneno: TextView
    lateinit var txt_sld_address1: TextView
    lateinit var txt_sld_address2: TextView
    lateinit var txt_nextfollowup: TextView
    lateinit var img_enroll: TextView
    lateinit var img_followup: TextView
    var showing_followup = false
    var showing_enroll = false
    lateinit var leadid: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_lead_details, container, false)
        (activity as HomeActivity).setActionBarTitle("Leads")
        fab_add_followup = view.findViewById(R.id.fab_add_followup) as FloatingActionButton
        followup_detail_title = view.findViewById(R.id.followup_detail_title) as ConstraintLayout
        followup_layout = view.findViewById(R.id.followup_layout) as ConstraintLayout
        enroll_detail_title = view.findViewById(R.id.enroll_detail_title) as ConstraintLayout
        enrollment_layout = view.findViewById(R.id.enrollment_layout) as ConstraintLayout
        showleadsRecyclerList = view.findViewById<RecyclerView>(R.id.showleadsRecyclerview) as RecyclerView
        showenrollRecyclerview = view.findViewById<RecyclerView>(R.id.showenrollRecyclerview) as RecyclerView
        txt_sld_leadname = view.findViewById(R.id.txt_sld_leadname) as TextView
        txt_ald_branch = view.findViewById(R.id.txt_ald_branch) as TextView
        txt_sld_mobileno = view.findViewById(R.id.txt_sld_mobileno) as TextView
        txt_sld_leadgenon = view.findViewById(R.id.txt_sld_leadgenon) as TextView
        txt_sld_email = view.findViewById(R.id.txt_sld_email) as TextView
        txt_sld_phoneno = view.findViewById(R.id.txt_sld_phoneno) as TextView
        txt_sld_address1 = view.findViewById(R.id.txt_sld_address1) as TextView
        txt_sld_address2 = view.findViewById(R.id.txt_sld_address2) as TextView
        txt_nextfollowup = view.findViewById(R.id.txt_nextfollowup) as TextView
        img_enroll = view.findViewById(R.id.img_enroll) as TextView
        img_followup = view.findViewById(R.id.img_followup) as TextView
        leadid = arguments!!.getString("lead_id")
        showleadsRecyclerList.setHasFixedSize(true)
        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        val mLayoutManager = LinearLayoutManager(context)
        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        showleadsRecyclerList.setLayoutManager(mLayoutManager)
        // adding inbuilt divider line
        showleadsRecyclerList.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
//      collection_recyclerview.addItemDecoration( MarginItemDecoration( resources.getDimension(R.dimen.sp8_space).toInt()))
//      recyclerView.addItemDecoration(MyDividerItemDecoration(context, LinearLayoutManager.HORIZONTAL, 16));
        // adding custom divider line with padding 16dp
        showleadsRecyclerList.setItemAnimator(DefaultItemAnimator())
        //=======================================================
        showenrollRecyclerview.setHasFixedSize(true)
        val enrolllayoutmanager = LinearLayoutManager(context)
        showenrollRecyclerview.setLayoutManager(enrolllayoutmanager)
        showenrollRecyclerview.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        showenrollRecyclerview.setItemAnimator(DefaultItemAnimator())

        get_leads_list()

        fab_add_followup.setOnClickListener {
            doFragmentTransaction(Add_Followup(), Constants.SHOW_LEAD_DETAILS_TAG, true, leadid, "lead_id")
        }

        followup_detail_title.setOnClickListener {
            if (showing_followup) {
                showing_followup=false
                img_followup.text = resources.getText(R.string.show_plus)
                followup_layout.visibility = View.GONE
            }else
            {
                showing_followup=true
                img_followup.text = resources.getText(R.string.show_minus)
                followup_layout.visibility = View.VISIBLE
            }
        }
        enroll_detail_title.setOnClickListener {
            if (showing_enroll) {
                showing_enroll=false
                img_enroll.text = resources.getText(R.string.show_plus)
                enrollment_layout.visibility = View.GONE
            }else
            {
                showing_enroll=true
                img_enroll.text = resources.getText(R.string.show_minus)
                enrollment_layout.visibility = View.VISIBLE
            }
        }
        return view
    }


    fun get_leads_list() {

        FollwoupList.clear()
        showProgressDialog()
        val get_lead_details = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("lead_id", leadid)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val Lead_Detail_Req = get_lead_details.get_lead_details(loginparameters)
        Lead_Detail_Req.enqueue(object : Callback<Leaddetailsmodel> {
            override fun onFailure(call: Call<Leaddetailsmodel>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<Leaddetailsmodel>,
                response: Response<Leaddetailsmodel>
            ) {
                when {
                    response.isSuccessful -> {
                        when {
                            response.code().equals(200) -> {
                                hideProgressDialog()
                                txt_sld_leadname.setText(response.body()!!.getLeadName())
                                txt_sld_mobileno.setText(response.body()!!.getLeadMobile())
                                txt_sld_phoneno.setText(response.body()!!.getLeadPhone())
                                txt_sld_email.setText(response.body()!!.getLeadEmail())
                                txt_sld_address1.setText(response.body()!!.getLeadAddress1())
                                txt_sld_address2.setText(response.body()!!.getLeadAddress2())
                                txt_nextfollowup.setText(response.body()!!.getNextFollowupDate())
                                txt_sld_leadgenon.setText(response.body()!!.getGeneratedOn())
                                val branchname = BaseUtils.masterdb.getbranchname(response.body()!!.getBranchId()!!)
                                txt_ald_branch.setText(branchname)

                                if (response.body()!!.getLastFollowupDetails().isNullOrEmpty()) {
                                } else {
                                    val follouuplist = response.body()!!.getLastFollowupDetails()
                                    if (follouuplist!!.size > 0) {
                                        FollwoupList = follouuplist
                                        integrateList(FollwoupList)
                                    }
                                }
                                if (response.body()!!.getEnrolledDetails().isNullOrEmpty()) {
                                } else {
                                    val follouuplist = response.body()!!.getEnrolledDetails()
                                    if (follouuplist!!.size > 0) {
                                        println("list size " + follouuplist.size.toString())
                                        integrateEnrollList(follouuplist)
                                    }
                                }
                            }
                            else -> {
                                hideProgressDialog()
                                toast("${response.errorBody()}")
                            }
                        }
                    }
                }
            }
        })
    }

    fun integrateList(leadslist: ArrayList<LastFollowupDetail>) {
        println("showing " + leadslist.size.toString())
        ShowingList.clear()
        ShowingList.addAll(leadslist)
        //---------_Recycler Adapter=------------------
        showleadsadapter1 = ShowLeadFollowups(ShowingList, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                if (view.getTag().equals("listitem")) {

                }

            }

            override fun onLongClicked(position: Int) {
            }
        })

        showleadsRecyclerList.setAdapter(showleadsadapter1)
        when {
            ShowingList.size > 0 -> {
                val lastposition = ShowingList.size - 1
                showleadsRecyclerList.smoothScrollToPosition(lastposition)
                showleadsRecyclerList.smoothScrollToPosition(0)
            }
            else -> {
            }
        }

        //-------------------------------------------------
    }

    fun integrateEnrollList(leadslist: ArrayList<EnrolledDetail>) {
        println("showing " + leadslist.size.toString())
        ShowingListEnroll.clear()
        ShowingListEnroll.addAll(leadslist)
        //---------_Recycler Adapter=------------------
        showleadsenrolAdapter = ShowLeadEnrollments(ShowingListEnroll, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                if (view.getTag().equals("listitem")) {

                }

            }

            override fun onLongClicked(position: Int) {
            }
        })

        showenrollRecyclerview.setAdapter(showleadsenrolAdapter)

        //-------------------------------------------------
    }

    fun getEnrolledList(leadslist: ArrayList<showleadmodel>): ArrayList<showleadmodel> {
        val EnrolledList = java.util.ArrayList<showleadmodel>()
        for (i in leadslist.indices) {
            val c = leadslist.get(i)
            val isEnrolled: String = c.getLeadEntrolled()!!
            if (isEnrolled.equals("yes")) {
                println("iadding")
                EnrolledList.add(c)
            } else {
            }
        }
        if (EnrolledList.size > 0) {
            println("add to leadlist")
            leadslist.clear()
            leadslist.addAll(EnrolledList)
            EnrolledList.clear()
        } else {
            leadslist.clear()
        }
        return leadslist
    }

    fun sortList(leadslist: ArrayList<showleadmodel>): ArrayList<showleadmodel> {
        val df: SimpleDateFormat
        df = SimpleDateFormat("yyyy-MM-dd")
        var todaydate = ""
        var tomorowdate = ""
        val c = Calendar.getInstance().time
        todaydate = df.format(c)
        println("Current time => $todaydate")
        val now = Calendar.getInstance()
        now.add(Calendar.DATE, 1)
        val resultdate = Date(now.timeInMillis)
        tomorowdate = df.format(resultdate)
        println("then date : $tomorowdate")

        val otherlist = java.util.ArrayList<showleadmodel>()
        val tomoroowlist = java.util.ArrayList<showleadmodel>()
        val todaylist = java.util.ArrayList<showleadmodel>()
        val resultList = java.util.ArrayList<showleadmodel>()
        for (i in leadslist.indices) {
            val c = leadslist.get(i)
            val tod = c.getNextFollowupDate()
            if (tod.equals(todaydate, ignoreCase = true)) {
                todaylist.add(c)
            } else {
                if (tod.equals(tomorowdate, ignoreCase = true)) {
                    tomoroowlist.add(c)
                } else {
                    otherlist.add(c)
                }

            }
        }
        resultList.clear()
        resultList.addAll(todaylist)
        resultList.addAll(tomoroowlist)
        resultList.addAll(otherlist)
        otherlist.clear()
        todaylist.clear()
        tomoroowlist.clear()

        return resultList
    }
    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}