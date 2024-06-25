package com.mazenet.mani.gurugubera.Fragments.dashboard_fragments


import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
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
import com.mazenet.mani.gurugubera.Fragments.Collection.CashinHand_Fragment
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Fragments.Collection.Collections
import com.mazenet.mani.gurugubera.Fragments.Collection.ViewReceiptsFragment
import com.mazenet.mani.gurugubera.Model.CollectiondashboardModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import kotlinx.android.synthetic.main.fragment_collection_dashboard.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Collection_dashboard_Fragment : BaseFragment(), OnChartValueSelectedListener {
    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }
    override fun onNothingSelected() {

    }
    override fun onBackPressed(): Boolean {
        return false
    }
    private val TAG = HomeActivity::class.java.getSimpleName()
    val CASH_IN_HAND_FRAGMENT_TAG: String = "cashinhand"
    lateinit var collection_target_piechart: PieChart
    lateinit var txt_cld_cashinhand: TextView
    lateinit var txt_no_of_receipts_generated: TextView
    lateinit var fab_adreceipt: ConstraintLayout
    lateinit var ref_col_dashboard: SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View
        view =
            inflater.inflate(R.layout.fragment_collection_dashboard, container, false)
        (activity as HomeActivity).setActionBarTitle("Dashboard")
        collection_target_piechart =view.findViewById<PieChart>(com.mazenet.mani.gurugubera.R.id.collection_target_collected_pie)
        txt_cld_cashinhand = view.findViewById(R.id.txt_cld_cashinhand) as TextView
        txt_no_of_receipts_generated = view.findViewById(R.id.txt_no_of_receipts_generated) as TextView
        fab_adreceipt = view.findViewById(R.id.fab_adreceipt) as ConstraintLayout
        ref_col_dashboard = view.findViewById(R.id.ref_col_dashboard) as SwipeRefreshLayout
        get_coll_dashboard()
        ref_col_dashboard.setOnRefreshListener {
            get_coll_dashboard()
        }
        view.cashinhand_layout.setOnClickListener {
            doFragmentTransaction(
                CashinHand_Fragment(), CASH_IN_HAND_FRAGMENT_TAG, true, "",
                ""
            )
        }
        view.receipts_title_lay.setOnClickListener {
            doFragmentTransaction(
                ViewReceiptsFragment(), Constants.VIEWRECEIPTS, true, "",
                ""
            )
        }
        fab_adreceipt.setOnClickListener {
            doFragmentTransaction(
                Collections(), "Collection", true, "",
                ""
            )
        }
        return view
    }
    fun targetchartDrawer() {

        collection_target_piechart.setUsePercentValues(false)
        collection_target_piechart.getDescription().setEnabled(false)
        collection_target_piechart.setExtraOffsets(5f, 10f, 5f, 5f)

        collection_target_piechart.setDragDecelerationFrictionCoef(0.95f)

        collection_target_piechart.setDrawHoleEnabled(false)
        collection_target_piechart.setHoleColor(Color.WHITE)

        collection_target_piechart.setHoleRadius(58f)
        collection_target_piechart.setTransparentCircleRadius(61f)

        collection_target_piechart.setDrawCenterText(false)

        collection_target_piechart.setRotationAngle(0f)
        // enable rotation of the collection_target_piechart by touch
        collection_target_piechart.setRotationEnabled(true)
        collection_target_piechart.setHighlightPerTapEnabled(true)

        // add a selection listener
        collection_target_piechart.setOnChartValueSelectedListener(this)

        collection_target_piechart.animateY(1400, Easing.EaseOutBounce)

        val l = collection_target_piechart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        collection_target_piechart.setEntryLabelColor(Color.WHITE)
        collection_target_piechart.setEntryLabelTextSize(0f)
    }

    private fun set_target_collected(target: Float, collected: Float) {
        val entries = java.util.ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (target > 0) {
            entries.add(PieEntry(target, "Target"))
        }
        //if (collected > 0) {
        entries.add(PieEntry(collected, "Acheived"))
//        }


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
        data.setValueFormatter(PercentFormatter(collection_target_piechart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        collection_target_piechart.setData(data)

        // undo all highlights
        collection_target_piechart.highlightValues(null)

        collection_target_piechart.invalidate()
    }

    fun get_coll_dashboard() {
        val getdashboard = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val dashboard_req = getdashboard.get_collectiondashboard(loginparameters)

        dashboard_req.enqueue(object : Callback<CollectiondashboardModel> {
            override fun onFailure(call: Call<CollectiondashboardModel>, t: Throwable) {
                ref_col_dashboard.isRefreshing=false
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<CollectiondashboardModel>,
                response: Response<CollectiondashboardModel>
            ) {
                ref_col_dashboard.isRefreshing=false
                if (response.isSuccessful) {
                    if (response.code().equals(200)) {
                        val target = response.body()!!.getTarget()!!.toInt()
                        val acheived = response.body()!!.getAcheived()!!.toInt()
                        txt_cld_cashinhand.setText(Constants.isEmtytoZero(response.body()!!.getCashInHand().toString()))
                        if (target > 0) {
                            if (target - acheived < 0) {
                            } else {
                                targetchartDrawer()
                                set_target_collected(target.toFloat(), acheived.toFloat())
                            }
                        }else
                        {
                            toast("No Target Set")
                        }
                        var receipt_count =
                            Constants.isEmtytoZero(response.body()!!.getNoOfReceiptGenerated()!!).toInt()
                        if (BaseUtils.offlinedb.OfflineReceiptSizeToday() > 0) {
                            receipt_count += BaseUtils.offlinedb.OfflineReceiptSizeToday()
                        }
                        txt_no_of_receipts_generated.setText(receipt_count.toString())
                    } else {
                        System.out.println("no show")
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
