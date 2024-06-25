package com.mazenet.mani.gurugubera.Fragments.dashboard_fragments


import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Fragments.Leads.Add_Lead
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Fragments.Leads.Show_leads
import com.mazenet.mani.gurugubera.Fragments.Reports.VacantReport
import com.mazenet.mani.gurugubera.Model.BranchModel
import com.mazenet.mani.gurugubera.Model.BusinessDashboardModel

import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import kotlinx.android.synthetic.main.fragment_business_agent_dashboard.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BusinessAgent_Dashboard : BaseFragment(), OnChartValueSelectedListener {
    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }

    private val TAG = HomeActivity::class.java.getSimpleName()

    lateinit var leads_target_piechart: PieChart
    lateinit var fab_addLead: FloatingActionButton
    lateinit var txt_lead_generated: TextView
    lateinit var txt_lead_enrolled: TextView
    lateinit var txt_no_of_vacants: TextView
    lateinit var ref_agent_dashboard: SwipeRefreshLayout
    var branch_list: ArrayList<BranchModel> = ArrayList<BranchModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view = inflater.inflate(R.layout.fragment_business_agent_dashboard, container, false)
        (activity as HomeActivity).setActionBarTitle("Dashboard")
        leads_target_piechart = view.findViewById<PieChart>(R.id.business_target_collected_pie)
        fab_addLead = view.findViewById<FloatingActionButton>(R.id.fab_addLead)
        txt_lead_generated = view.findViewById<TextView>(R.id.txt_lead_generated)
        txt_lead_enrolled = view.findViewById<TextView>(R.id.txt_lead_enrolled)
        txt_no_of_vacants = view.findViewById<TextView>(R.id.txt_no_of_vacants)
        ref_agent_dashboard = view.findViewById(R.id.ref_agent_dashboard) as SwipeRefreshLayout
        fab_addLead.setOnClickListener {
            doFragmentTransaction(Add_Lead(), Constants.ADD_LEAD_TAG, true, "", "")
        }
        view.leads_titile_layout.setOnClickListener {
            doFragmentTransaction(Show_leads(), Constants.SHOW_LEAD_TAG, true, "", "")
        }

        ref_agent_dashboard.setOnRefreshListener {
            douwnloadBranches()
            get_dashboard()
        }
        view.vacantchit_nolayout.setOnClickListener {
            doFragmentTransaction(VacantReport(), "", true, "", "")
        }
        douwnloadBranches()
        get_dashboard()
        targetchartDrawer()
        return view
    }

    override fun onDestroyView() {
        Log.d("busiess", "destroyed")
        super.onDestroyView()
    }

    fun targetchartDrawer() {

        leads_target_piechart.setUsePercentValues(false)
        leads_target_piechart.getDescription().setEnabled(false)
        leads_target_piechart.setExtraOffsets(5f, 10f, 5f, 5f)

        leads_target_piechart.setDragDecelerationFrictionCoef(0.95f)

        leads_target_piechart.setDrawHoleEnabled(false)
        leads_target_piechart.setHoleColor(Color.WHITE)

        leads_target_piechart.setHoleRadius(58f)
        leads_target_piechart.setTransparentCircleRadius(61f)

        leads_target_piechart.setDrawCenterText(false)

        leads_target_piechart.setRotationAngle(0f)
        // enable rotation of the collection_target_piechart by touch
        leads_target_piechart.setRotationEnabled(true)
        leads_target_piechart.setHighlightPerTapEnabled(true)

        // add a selection listener
        leads_target_piechart.setOnChartValueSelectedListener(this)

        leads_target_piechart.animateY(1400, Easing.EaseOutBounce)

        val l = leads_target_piechart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        leads_target_piechart.setEntryLabelColor(Color.WHITE)
        leads_target_piechart.setEntryLabelTextSize(0f)
    }

    private fun set_target_collected(target: Float, collected: Float) {
        val entries = java.util.ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (target > 0) {
            entries.add(PieEntry(target, "Target"))
        }
        if (collected > 0) {
            entries.add(PieEntry(collected, "Acheived"))
        }


        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 1f
        dataSet.iconsOffset = MPPointF(0f, 100f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = java.util.ArrayList<Int>()

        for (c in Constants.MATERIAL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(leads_target_piechart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        leads_target_piechart.setData(data)

        // undo all highlights
        leads_target_piechart.highlightValues(null)

        leads_target_piechart.invalidate()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            //you are visible to user now - so set whatever you need
//            initResources();
            Log.d("businessfragment", "init")
        } else {
            //you are no longer visible to the user so cleanup whatever you need
//            cleanupResources();
            Log.d("businessfragment", "clean")
        }
    }

    fun get_dashboard() {
        showProgressDialog()
        val getdeviceCounts = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val device_count_req = getdeviceCounts.get_businessdashboard(loginparameters)
        device_count_req.enqueue(object : Callback<BusinessDashboardModel> {
            override fun onFailure(call: Call<BusinessDashboardModel>, t: Throwable) {
                ref_agent_dashboard.isRefreshing = false
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BusinessDashboardModel>,
                response: Response<BusinessDashboardModel>
            ) {
                ref_agent_dashboard.isRefreshing = false
                hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {

                        val targets = Constants.isEmtytoZero(response.body()!!.getTarget()!!)
                        val acheivd = Constants.isEmtytoZero(response.body()!!.getAcheived()!!)
                        val lead_genereated = Constants.isEmtytoZero(response.body()!!.getLeadGenerated()!!)
                        val lead_enrolled = Constants.isEmtytoZero(response.body()!!.getLeadEnrolled()!!)
                        val total_vacant = Constants.isEmtytoZero(response.body()!!.getTotalVacanct()!!)
                        if (targets.equals("0") and acheivd.equals("0")) {
                        } else {
                            set_target_collected(targets.toFloat(), acheivd.toFloat())
                        }
                        txt_no_of_vacants.setText(total_vacant)
                        txt_lead_generated.setText(lead_genereated)
                        txt_lead_enrolled.setText(lead_enrolled)
                    } else {
                        System.out.println("no show")

                    }
                } else {

                }
            }
        })
    }

    fun douwnloadBranches() {


        val getBranchObject = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val branchRequest = getBranchObject.get_branches(loginparameters)
        branchRequest.enqueue(object : Callback<ArrayList<BranchModel>> {
            override fun onFailure(call: Call<ArrayList<BranchModel>>, t: Throwable) {
                ref_agent_dashboard.isRefreshing = false
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<BranchModel>>,
                response: Response<ArrayList<BranchModel>>
            ) {
                ref_agent_dashboard.isRefreshing = false
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        branch_list = response.body()!!
                        System.out.println("branchlist ${branch_list}")
                        if (branch_list.size > 0) {
                            BaseUtils.masterdb.deleteBranchTable()
                            BaseUtils.masterdb.addBranches(branch_list)

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

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
