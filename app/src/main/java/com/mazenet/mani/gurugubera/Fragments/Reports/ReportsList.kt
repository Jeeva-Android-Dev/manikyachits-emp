package com.mazenet.mani.gurugubera.Fragments.Reports


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ShowReportsList
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.ReportListModel

import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Utilities.Roles

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReportsList : BaseFragment() {
    val reportlist = ArrayList<ReportListModel>()
    lateinit var ReportslistAdapter: ShowReportsList
    lateinit var recycler_reportlist: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reports_list, container, false)
        (activity as HomeActivity).setActionBarTitle("Reports")
        recycler_reportlist = view.findViewById(R.id.recycler_reportlist) as RecyclerView
        reportlist.clear()
        var rolename = getPrefsString(Constants.roletype, "")
        if(rolename==(Roles.Collection_Agent.toString())){
            reportlist.add(ReportListModel("Collections", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Outstandings", R.drawable.ic_devices))
        }
        else {
            reportlist.add(ReportListModel("Collections", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Outstandings", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Commitment Payment", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Auctions", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Business-Agent Report", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Vacant Report", R.drawable.ic_devices))
            reportlist.add(ReportListModel("Day-Closing Report", R.drawable.ic_devices))
        }
        recycler_reportlist.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        recycler_reportlist.setLayoutManager(mLayoutManager)
        recycler_reportlist.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recycler_reportlist.setItemAnimator(DefaultItemAnimator())
        ReportslistAdapter = ShowReportsList(reportlist, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                if (view.tag.equals("itemview")) {
                    if (reportlist.get(position).heading.equals("Collections", ignoreCase = true)) {
                        doFragmentTransaction(collectionReport(), "collreport", true, "", "")
                    } else if (reportlist.get(position).heading.equals("Outstandings", ignoreCase = true)) {
                        doFragmentTransaction(OutstandingReport(), "Outstandingsreport", true, "", "")
                    } else if (reportlist.get(position).heading.equals("Auctions", ignoreCase = true)) {
                        doFragmentTransaction(AuctionReport(), "Auctionssreport", true, "", "")
                    } else if (reportlist.get(position).heading.equals("Business-Agent Report", ignoreCase = true)) {
                        doFragmentTransaction(BusinessAgentReport(), "BusinessAgentReport", true, "", "")
                    } else if (reportlist.get(position).heading.equals("Commitment Payment", ignoreCase = true)) {
                        doFragmentTransaction(ComntPaymentReport(), "CommitmentPaymentReport", true, "", "")
                    } else if (reportlist.get(position).heading.equals("Vacant Report", ignoreCase = true)) {
                        doFragmentTransaction(VacantReport(), "VacantReport", true, "", "")
                    }else if (reportlist.get(position).heading.equals("Day-Closing Report", ignoreCase = true)) {
                        doFragmentTransaction(DayclosingReport(), "VacantReport", true, "", "")
                    }
                }
            }

            override fun onLongClicked(position: Int) {

            }
        })
        recycler_reportlist.setAdapter(ReportslistAdapter)

        return view
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
