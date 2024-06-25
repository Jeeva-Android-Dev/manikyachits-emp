package com.mazenet.mani.gurugubera.Fragments.Leads


import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ShowLeadsAdapterK
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.showleadmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_collections.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Show_leads : BaseFragment() {
    var TotalLeadsList = ArrayList<showleadmodel>()
    var EnrolledLeadList = ArrayList<showleadmodel>()
    var ShowingList = ArrayList<showleadmodel>()
    var ShowingListMain = ArrayList<showleadmodel>()
    lateinit var showleadsRecyclerList: RecyclerView
    lateinit var fab_addLead: FloatingActionButton
    lateinit var showleadsadapter1: ShowLeadsAdapterK
    lateinit var txt_totalleadgenerated: TextView
    lateinit var txt_leads_enrolled: TextView
    lateinit var switch_enrolledOnly: Switch
    lateinit var edt_sl_search: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_leads, container, false)
        (activity as HomeActivity).setActionBarTitle("Leads")
        fab_addLead = view.findViewById(R.id.fab_addLeadfromshow) as FloatingActionButton
        showleadsRecyclerList = view.findViewById(R.id.showleadsRecyclerview) as RecyclerView
        txt_totalleadgenerated = view.findViewById(R.id.txt_totalleadgenerated) as TextView
        txt_leads_enrolled = view.findViewById(R.id.txt_leads_enrolled) as TextView
        switch_enrolledOnly = view.findViewById(R.id.switch_enrolledOnly) as Switch
        edt_sl_search = view.findViewById(R.id.edt_sl_search) as EditText
        switch_enrolledOnly.isChecked = false
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
//      showleadsRecyclerList.addItemDecoration( MarginItemDecoration( resources.getDimension(R.dimen.sp8_space).toInt()))
//      recyclerView.addItemDecoration(MyDividerItemDecoration(context, LinearLayoutManager.HORIZONTAL, 16));
        // adding custom divider line with padding 16dp
        showleadsRecyclerList.setItemAnimator(DefaultItemAnimator())
        switch_enrolledOnly.setOnClickListener {
            if (switch_enrolledOnly.isChecked) {
//                if (EnrolledLeadList.size > 0) {
                integrateList(EnrolledLeadList)
//                } else {
//                }
            } else {
                if (TotalLeadsList.size > 0) {
                    integrateList(TotalLeadsList)
                } else {}
            }
        }
        this.context?.let { get_leads_list(it) }
        fab_addLead.setOnClickListener { doFragmentTransaction(Add_Lead(), Constants.ADD_LEAD_TAG, true, "", "") }

        edt_sl_search.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(
                cs: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {

                val text = edt_sl_search.getText().toString()
                if (text == "" || text == null) {

                    ShowingListMain.clear()

                    ShowingListMain.addAll(ShowingList)


                    if (ShowingListMain.size > 0) {
                        showleadsRecyclerList.setVisibility(View.VISIBLE)
                        showleadsadapter1 = ShowLeadsAdapterK(ShowingListMain, object : AdapterClickListener {
                            override fun onPositionClicked(view: View, position: Int) {
                                if (view.getTag().equals("listitem")) {
                                    println("postio ${position.toString()} leadid ${ShowingListMain.get(position).getLeadId().toString()}")
                                    doFragmentTransaction(Show_lead_details(), Constants.SHOW_LEAD_DETAILS_TAG, true, ShowingListMain.get(position).getLeadId().toString(), "lead_id")
                                }

                            }
                            override fun onLongClicked(position: Int) {
                            }
                        })

                        showleadsRecyclerList.setAdapter(showleadsadapter1)

                    } else {

                        showleadsRecyclerList.setVisibility(View.GONE)
                    }
                }
                else {
                    setnewadapter()
                    showleadsRecyclerList.setVisibility(View.VISIBLE)
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                // TODO Auto-generated method stub

            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
                // lv.setVisibility(View.GONE);

                if (ShowingListMain.size > 0) {
                    showleadsRecyclerList.setVisibility(View.VISIBLE)

                } else {

                    showleadsRecyclerList.setVisibility(View.GONE)
                }

            }
        })

        return view
    }

    private fun setnewadapter() {
        ShowingListMain.clear()

        var text = edt_sl_search.text.toString()
        text = text.toLowerCase(Locale.getDefault())
        if (text == null) {

            showleadsRecyclerList.setVisibility(View.GONE)
            return

        } else {


            for (i in ShowingList.indices) {
                val schedte = ShowingList.get(i)
                var name = schedte.getLeadCustomerName()
//                var mobile = schedte.getMOBILE()

                name = name!!.toLowerCase(Locale.getDefault())
//                mobile = mobile.toLowerCase(Locale.getDefault())

                if (name.contains(text)) {
                    ShowingListMain.add(ShowingList.get(i))

                }

            }


        }

        if (ShowingListMain.size > 0) {
            showleadsRecyclerList.setVisibility(View.VISIBLE)
            showleadsadapter1 = ShowLeadsAdapterK(ShowingListMain, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("listitem")) {
                        println("postio ${position.toString()} leadid ${ShowingListMain.get(position).getLeadId().toString()}")
                        doFragmentTransaction(Show_lead_details(), Constants.SHOW_LEAD_DETAILS_TAG, true, ShowingListMain.get(position).getLeadId().toString(), "lead_id")
                    }

                }
                override fun onLongClicked(position: Int) {
                }
            })

            showleadsRecyclerList.setAdapter(showleadsadapter1)

        } else {

            showleadsRecyclerList.setVisibility(View.GONE)
        }

    }
    fun get_leads_list(context: Context) {

        TotalLeadsList.clear()
        showProgressDialog()
        var EndList = java.util.ArrayList<showleadmodel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val LeadListRequest = getleadslist.show_leads(loginparameters)
        LeadListRequest.enqueue(object : Callback<ArrayList<showleadmodel>> {
            override fun onFailure(call: Call<ArrayList<showleadmodel>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ArrayList<showleadmodel>>,
                response: Response<ArrayList<showleadmodel>>
            ) {
                when {
                    response.isSuccessful -> {
                        when {
                            response.code().equals(200) -> {
                                hideProgressDialog()
                                EndList = response.body()!!
                                System.out.println("recycler ${response.body()}")
                                if (EndList.size > 0) {
                                    var enrolledLead = 0
                                    var notEnrolledLead = 0
                                    for (i in EndList.indices) {
                                        val c = EndList.get(i)
                                        val isEnrolled: String = c.getLeadEntrolled()!!
                                        if (isEnrolled.equals("yes" , ignoreCase = true)) {
                                            enrolledLead += 1
                                            notEnrolledLead += 1
                                        } else {
                                            notEnrolledLead += 1
                                        }
                                    }
                                    txt_leads_enrolled.text = enrolledLead.toString()
                                    txt_totalleadgenerated.text = notEnrolledLead.toString()
                                    TotalLeadsList = sortList(EndList)
                                    EnrolledLeadList = getEnrolledList(sortList(EndList))
                                    integrateList(TotalLeadsList)
                                } else {
                                }
                            }
                        }
                    }else -> {
                    hideProgressDialog()
                }
                }
            }
        })

    }

    fun integrateList(leadslist: ArrayList<showleadmodel>) {
        println("showing "+leadslist.size.toString())
        ShowingList.clear()
        ShowingListMain.clear()
        ShowingList.addAll(leadslist)
        ShowingListMain.addAll(leadslist)
        //---------_Recycler Adapter=------------------
        showleadsadapter1 = ShowLeadsAdapterK(ShowingListMain, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
               if (view.getTag().equals("listitem")) {
                   println("postio ${position.toString()} leadid ${ShowingListMain.get(position).getLeadId().toString()}")
                    doFragmentTransaction(Show_lead_details(), Constants.SHOW_LEAD_DETAILS_TAG, true, ShowingListMain.get(position).getLeadId().toString(), "lead_id")
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

    fun getEnrolledList(leadslist: ArrayList<showleadmodel>): ArrayList<showleadmodel> {
        val EnrolledList = java.util.ArrayList<showleadmodel>()
        for (i in leadslist.indices) {
            val c = leadslist.get(i)
            val isEnrolled: String = c.getLeadEntrolled()!!
            if (isEnrolled.equals("yes",ignoreCase = true)) {
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