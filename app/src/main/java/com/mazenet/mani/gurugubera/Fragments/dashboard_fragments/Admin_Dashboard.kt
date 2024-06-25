package com.mazenet.mani.gurugubera.Fragments.dashboard_fragments


import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Fragments.Devices.devices
import com.mazenet.mani.gurugubera.Fragments.Reports.DayclosingReport
import com.mazenet.mani.gurugubera.Fragments.Reports.collectionReport
import com.mazenet.mani.gurugubera.Model.AdminDashboardModel
import com.mazenet.mani.gurugubera.Model.BranchModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.BranchSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.masterdb
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_admin_dashboard.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Admin_Dashboard() : BaseFragment(), OnChartValueSelectedListener {


    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }

    private val TAG = HomeActivity::class.java.getSimpleName()
    lateinit var BranchSpinner: BranchSpinnerdilog
    var brancharray = ArrayList<BranchModel>()
    lateinit var spn_branch: Button
    lateinit var SelectedBranch: String
    lateinit var SelectedBranchName: String
    lateinit var anychart_target: PieChart
    lateinit var dayclosing_chart: PieChart
    lateinit var txt_totaldevices: TextView
    lateinit var txt_activedevices: TextView
    lateinit var txt_inactivdevices: TextView

    lateinit var swiperefresher: SwipeRefreshLayout

    var branch_list: ArrayList<BranchModel> = ArrayList<BranchModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View
        view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)
        (activity as HomeActivity).setActionBarTitle("Dashboard")
        spn_branch = view.findViewById<Button>(R.id.spn_branch) as Button
        anychart_target = view.findViewById<PieChart>(R.id.target_collected_pie) as PieChart
        dayclosing_chart = view.findViewById<PieChart>(R.id.dayclosing_pie) as PieChart
        swiperefresher = view.findViewById<SwipeRefreshLayout>(R.id.ref_adm_dashboard)
        txt_totaldevices = view.findViewById<TextView>(R.id.txt_totaldevices)
        txt_activedevices = view.findViewById<TextView>(R.id.txt_activedevices)
        txt_inactivdevices = view.findViewById<TextView>(R.id.txt_inactivdevices)
        init()
        swiperefresher.setOnRefreshListener {
            init()
        }
        if (masterdb.BranchTableSize() > 0) {
            brancharray.clear()
            brancharray = masterdb.getBranches()
            System.out.println("greater $brancharray")
        }
        anychart_target.setOnClickListener {
            doFragmentTransaction(collectionReport(), "", true, "", "")
        }
        dayclosing_chart.setOnClickListener {
            doFragmentTransaction(DayclosingReport(), "", true, "", "")
        }

        view.devices_layout.setOnClickListener(View.OnClickListener {
            doFragmentTransaction(devices(), Constants.DEVICE, true, "", "")
        })


        targetchartDrawer()
        drawDayclosingChart()

        return view
    }

    fun targetchartDrawer() {

        anychart_target.setUsePercentValues(false)
        anychart_target.getDescription().setEnabled(false)
        anychart_target.setExtraOffsets(5f, 10f, 5f, 5f)
        anychart_target.setDragDecelerationFrictionCoef(0.95f)
        anychart_target.setDrawHoleEnabled(false)
        anychart_target.setHoleColor(Color.WHITE)
        anychart_target.setHoleRadius(58f)
        anychart_target.setTransparentCircleRadius(40f)
        anychart_target.setDrawCenterText(false)
        anychart_target.setRotationAngle(0f)
        // enable rotation of the collection_target_piechart by touch
//        anychart_target.setRotationEnabled(true)
        anychart_target.setHighlightPerTapEnabled(true)
        // add a selection listener
        anychart_target.setOnChartValueSelectedListener(this)
//        anychart_target.animateY(1400, Easing.EaseOutBounce)
        val l = anychart_target.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)
        // entry label styling
        anychart_target.setEntryLabelColor(Color.WHITE)
        anychart_target.setEntryLabelTextSize(0f)
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
        data.setValueFormatter(PercentFormatter(anychart_target))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        anychart_target.setData(data)

        // undo all highlights
        anychart_target.highlightValues(null)

        anychart_target.invalidate()
    }

    override fun onValueSelected(e: Entry?, h: Highlight) {

        if (e == null)
            return
        Log.i(
            "VAL SELECTED",
            "Value: " + e!!.y + ", index: " + h.x
                    + ", DataSet index: " + h.dataSetIndex
        )
    }

    fun drawDayclosingChart() {
        dayclosing_chart.setUsePercentValues(false)
        dayclosing_chart.getDescription().setEnabled(false)
        dayclosing_chart.setExtraOffsets(5f, 10f, 5f, 5f)

        dayclosing_chart.setDragDecelerationFrictionCoef(0.95f)

        dayclosing_chart.setDrawHoleEnabled(false)
        dayclosing_chart.setHoleColor(Color.WHITE)

        dayclosing_chart.setHoleRadius(58f)
        dayclosing_chart.setTransparentCircleRadius(61f)

        dayclosing_chart.setDrawCenterText(false)

        dayclosing_chart.setRotationAngle(0f)
        // enable rotation of the dayclosing_chart by touch
//        dayclosing_chart.setRotationEnabled(true)
        dayclosing_chart.setHighlightPerTapEnabled(true)

        // add a selection listener
        dayclosing_chart.setOnChartValueSelectedListener(this)

//        dayclosing_chart.animateY(1400, Easing.EaseOutBounce)

        val l = dayclosing_chart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        dayclosing_chart.setEntryLabelColor(Color.WHITE)
        dayclosing_chart.setEntryLabelTextSize(0f)
    }

    private fun set_dayclosingChart(cash: Float, cheque: Float, neft: Float, dd: Float, card: Float) {
        val entries = java.util.ArrayList<PieEntry>()

        if (cash > 0) {
            entries.add(PieEntry(cash, "Cash"))
        }
        if (cheque > 0) {
            entries.add(PieEntry(cheque, "Cheque"))
        }
        if (neft > 0) {
            entries.add(PieEntry(neft, "NEFT"))
        }
        if (dd > 0) {
            entries.add(PieEntry(dd, "DD"))
        }
        if (card > 0) {
            entries.add(PieEntry(dd, "Card"))
        }
        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 1f
        dataSet.iconsOffset = MPPointF(0f, 100f)
        dataSet.selectionShift = 5f

        val colors = java.util.ArrayList<Int>()

        for (c in Constants.MATERIAL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(dayclosing_chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)

        dayclosing_chart.setData(data)
        dayclosing_chart.highlightValues(null)
        dayclosing_chart.invalidate()
    }

    fun init() {
        douwnloadBranches()

    }

    fun get_dashboard() {
        showProgressDialog()
        BranchSpinner = BranchSpinnerdilog(
            activity, brancharray,
            "Select Branch Name"
        )
        spn_branch.setOnClickListener {
            System.out.println("spn lay clicked ")
            BranchSpinner.showSpinerDialog()
        }

        BranchSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {

                SelectedBranch = position.toString()
                SelectedBranchName = grpname
                spn_branch.setText(grpname)
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)
                get_dashboard()
                System.out.println("branchid $SelectedBranch branchname $grpname")
            }
        })
        SelectedBranch = getPrefsString(Constants.selectedBranchid, "")
        if (SelectedBranch.equals("")) {
            SelectedBranchName = brancharray.get(0).getBranchName().toString()
            spn_branch.setText(SelectedBranchName)
            SelectedBranch = brancharray.get(0).getBranchId().toString()

        } else {
            SelectedBranchName = getPrefsString(Constants.selectedBranchName, "")
            spn_branch.setText(getPrefsString(Constants.selectedBranchName, ""))
        }


        val getdeviceCounts = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("branch_id", SelectedBranch)
        loginparameters.put("db",getPrefsString(Constants.db,""))

        val device_count_req = getdeviceCounts.get_admindashboard(loginparameters)
        device_count_req.enqueue(object : Callback<AdminDashboardModel> {
            override fun onFailure(call: Call<AdminDashboardModel>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<AdminDashboardModel>,
                response: Response<AdminDashboardModel>
            ) {
                hideProgressDialog()
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {

                        txt_totaldevices.setText(response.body()!!.getTotalDevices().toString())
                        txt_activedevices.setText(response.body()!!.getActiveDevices().toString())
                        txt_inactivdevices.setText(response.body()!!.getDeActiveDevices().toString())
                        val targets = Constants.isEmtytoZero(response.body()!!.getTarget()!!)
                        val acheivd = Constants.isEmtytoZero(response.body()!!.getAcheived()!!)
                        val day_cash = Constants.isEmtytoZero(response.body()!!.getDayclosingCash()!!)
                        val day_cheque = Constants.isEmtytoZero(response.body()!!.getDayclosingCheque()!!)
                        val day_card = Constants.isEmtytoZero(response.body()!!.getDayclosingCard()!!)
                        val day_crtgs = Constants.isEmtytoZero(response.body()!!.getDayclosingRtgs()!!)
                        val day_dd = Constants.isEmtytoZero(response.body()!!.getDayclosingDd()!!)

                        set_target_collected(
                            Constants.isEmtytoZero(targets).toFloat(),
                            Constants.isEmtytoZero(acheivd).toFloat()
                        )


                        set_dayclosingChart(
                            Constants.isEmtytoZero(day_cash).toFloat(),
                            Constants.isEmtytoZero(day_cheque).toFloat(),
                            Constants.isEmtytoZero(day_crtgs).toFloat(),
                            Constants.isEmtytoZero(day_dd).toFloat(),
                            Constants.isEmtytoZero(day_card).toFloat()
                        )


                    } else {
                        System.out.println("no show")

                    }
                } else {

                }
            }
        })
    }

    fun douwnloadBranches() {
        swiperefresher.setRefreshing(true)
        SelectedBranch = getPrefsString(Constants.selectedBranchid, "")
        val getBranchObject = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val branchRequest = getBranchObject.get_branches(loginparameters)
        branchRequest.enqueue(object : Callback<ArrayList<BranchModel>> {
            override fun onFailure(call: Call<ArrayList<BranchModel>>, t: Throwable) {
                swiperefresher.setRefreshing(false)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<BranchModel>>,
                response: Response<ArrayList<BranchModel>>
            ) {
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        branch_list = response.body()!!
                        System.out.println("branchlist ${branch_list}")
                        if (branch_list.size > 0) {
                            masterdb.deleteBranchTable()
                            masterdb.addBranches(branch_list)

                            if (SelectedBranch.equals("")) {
                                val obj = branch_list.get(0)
                                spn_branch.setText(obj.getBranchName())
                                setPrefsString(Constants.selectedBranchid, obj.getBranchId().toString())
                                setPrefsString(Constants.selectedBranchName, obj.getBranchName()!!)
                            } else {

                            }
                            brancharray = masterdb.getBranches()
                            swiperefresher.setRefreshing(false)
                            get_dashboard()

                        } else {
                            System.out.println("no show")
                            swiperefresher.setRefreshing(false)
                        }
                    } else {
                        swiperefresher.setRefreshing(false)
                    }
                } else {
                    swiperefresher.setRefreshing(false)
                }
            }
        })
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }
}
