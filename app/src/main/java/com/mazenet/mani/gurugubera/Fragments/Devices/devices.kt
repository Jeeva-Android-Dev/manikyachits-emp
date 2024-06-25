package com.mazenet.mani.gurugubera.Fragments.Devices


import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.DevicesAdapter
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.BranchModel
import com.mazenet.mani.gurugubera.Model.deviceListModel
import com.mazenet.mani.gurugubera.Model.successmsgmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.BranchSpinnerdilog
import com.mazenet.mani.gurugubera.Spinners.OnSpinnerItemClick
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import kotlinx.android.synthetic.main.fragment_admin_dashboard.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class devices : BaseFragment() {
    lateinit var showDevicesRecyclerList: RecyclerView
    lateinit var txt_sd_inactivdevices: TextView
    lateinit var txt_sd_activedevices: TextView
    lateinit var txt_sd_totaldevices: TextView
    lateinit var checkBox_branch: CheckBox
    lateinit var showDevicesadapter: DevicesAdapter
    var TotalDevicesList = ArrayList<deviceListModel>()
    var ActiveDevicesList = ArrayList<deviceListModel>()
    var InActiveDevicesList = ArrayList<deviceListModel>()
    var ShowingList = ArrayList<deviceListModel>()
    var ShowingList_main = ArrayList<deviceListModel>()
    lateinit var spn_branch: Button
    lateinit var SelectedBranch: String
    lateinit var SelectedBranchName: String
    lateinit var edt_d_search: EditText
    var brancharray = ArrayList<BranchModel>()
    lateinit var BranchSpinner: BranchSpinnerdilog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.fragment_devices, container, false)
        (activity as HomeActivity).setActionBarTitle("Devices")
        spn_branch = view.findViewById(R.id.spn_showdevices_branch) as Button
        showDevicesRecyclerList =
            view.findViewById<RecyclerView>(com.mazenet.mani.gurugubera.R.id.showDevices) as RecyclerView
        txt_sd_inactivdevices =
            view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.txt_sd_inactivdevices) as TextView
        txt_sd_activedevices =
            view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.txt_sd_activedevices) as TextView
        txt_sd_totaldevices =
            view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.txt_sd_totaldevices) as TextView
        checkBox_branch =
            view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.checkBox_su_branch) as CheckBox
        edt_d_search = view.findViewById<EditText>(com.mazenet.mani.gurugubera.R.id.edt_d_search) as EditText
        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        val mLayoutManager = LinearLayoutManager(context)
        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        showDevicesRecyclerList.setLayoutManager(mLayoutManager)
        showDevicesRecyclerList.setHasFixedSize(true)
        // adding inbuilt divider line
        showDevicesRecyclerList.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
//      showDevicesRecyclerList.addItemDecoration( MarginItemDecoration( resources.getDimension(R.dimen.sp8_space).toInt()))
//      recyclerView.addItemDecoration(MyDividerItemDecoration(context, LinearLayoutManager.HORIZONTAL, 16));
        // adding custom divider line with padding 16dp
        showDevicesRecyclerList.setItemAnimator(DefaultItemAnimator())
        checkBox_branch.setOnClickListener {
            if (checkBox_branch.isChecked) {
                integrateList(TotalDevicesList, getPrefsString(Constants.selectedBranchid, ""))
                spn_branch.text = getPrefsString(Constants.selectedBranchName, "")


            } else {
                integrateList(TotalDevicesList, "")
                spn_branch.text = ""

            }
        }
        if (BaseUtils.masterdb.BranchTableSize() > 0) {
            brancharray = BaseUtils.masterdb.getBranches()
        }
        BranchSpinner = BranchSpinnerdilog(
            activity, brancharray,
            "Select Branch Name"
        )

        spn_branch.setOnClickListener {
            BranchSpinner.showSpinerDialog()
        }
        view.devices_layout.setOnClickListener(View.OnClickListener {
            System.out.println("device lay clicked ")
        })
        BranchSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {

                SelectedBranch = position.toString()
                SelectedBranchName = grpname
                spn_branch.setText(grpname)
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)
                integrateList(TotalDevicesList, SelectedBranch)
                checkBox_branch.isChecked = true
            }
        })
        get_devices_list()
        edt_d_search.setText("")
        edt_d_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                val text = edt_d_search.getText().toString()
                if (text == "" || text == null) {
                    ShowingList_main.clear()
                    ShowingList_main.addAll(ShowingList)
                    if (ShowingList_main.size > 0) {
                        showDevicesRecyclerList.setVisibility(View.VISIBLE)
                        showDevicesadapter =
                            DevicesAdapter(ShowingList_main, object : AdapterClickListener {
                                override fun onPositionClicked(view: View, position: Int) {
                                    if (view.getTag().equals("switch")) {
                                        if (ShowingList_main.get(position).getIsActive().equals("Yes")) {
                                            showDialog(
                                                ShowingList_main.get(position).getDeviceId().toString(), "0",
                                                ShowingList_main.get(position).getDeviceName().toString()
                                            )
                                        } else {
                                            showDialog(
                                                ShowingList_main.get(position).getDeviceId().toString(), "1",
                                                ShowingList_main.get(position).getDeviceName().toString()
                                            )
                                        }
                                    } else if (view.getTag().equals("listitem")) {
                                        doFragmentTransaction(
                                            deviceUsers(),
                                            Constants.DEVICE_USERS,
                                            true,
                                            ShowingList_main.get(position).getDeviceId().toString(),
                                            "device_id"
                                        )
                                    }

                                }

                                override fun onLongClicked(position: Int) {
                                }
                            }, getPrefsString(Constants.thisdeviceid, ""))
                        showDevicesRecyclerList.setAdapter(showDevicesadapter)

                    } else {

                        showDevicesRecyclerList.setVisibility(View.GONE)
                    }
                } else {
                    setnewadapter()
                    showDevicesRecyclerList.setVisibility(View.VISIBLE)
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
            }

            override fun afterTextChanged(arg0: Editable) {
                // TODO Auto-generated method stub
                // lv.setVisibility(View.GONE);
                if (ShowingList_main.size > 0) {
                    showDevicesRecyclerList.setVisibility(View.VISIBLE)
                } else {
                    showDevicesRecyclerList.setVisibility(View.GONE)
                }
            }
        })
        return view
    }

    private fun setnewadapter() {
        ShowingList_main.clear()

        var text = edt_d_search.text.toString()
        text = text.toLowerCase(Locale.getDefault())
        if (text == null) {
            showDevicesRecyclerList.setVisibility(View.GONE)
            return
        } else {
            for (i in ShowingList.indices) {
                val schedte = ShowingList.get(i)
                var name = schedte.getDeviceName()
//                var mobile = schedte.getMOBILE()
                name = name!!.toLowerCase(Locale.getDefault())
//                mobile = mobile.toLowerCase(Locale.getDefault())
                if (name.contains(text)) {
                    ShowingList_main.add(ShowingList.get(i))
                }
            }
        }
        if (ShowingList_main.size > 0) {
            showDevicesRecyclerList.setVisibility(View.VISIBLE)
            showDevicesadapter = DevicesAdapter(ShowingList_main, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("switch")) {
                        if (ShowingList_main.get(position).getIsActive().equals("Yes")) {
                            showDialog(
                                ShowingList_main.get(position).getDeviceId().toString(), "0",
                                ShowingList_main.get(position).getDeviceName().toString()
                            )
                        } else {
                            showDialog(
                                ShowingList_main.get(position).getDeviceId().toString(), "1",
                                ShowingList_main.get(position).getDeviceName().toString()
                            )
                        }
                    } else if (view.getTag().equals("listitem")) {
                        doFragmentTransaction(
                            deviceUsers(),
                            Constants.DEVICE_USERS,
                            true,
                            ShowingList_main.get(position).getDeviceId().toString(),
                            "device_id"
                        )
                    }

                }

                override fun onLongClicked(position: Int) {
                }
            }, getPrefsString(Constants.thisdeviceid, ""))
            showDevicesRecyclerList.setAdapter(showDevicesadapter)
        } else {
            showDevicesRecyclerList.setVisibility(View.GONE)
        }
    }

    fun get_devices_list() {

        TotalDevicesList.clear()
        showProgressDialog()
        var EndList = java.util.ArrayList<deviceListModel>()
        val getDeviceslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val devicesRequest = getDeviceslist.show_devices(loginparameters)
        devicesRequest.enqueue(object : Callback<ArrayList<deviceListModel>> {
            override fun onFailure(call: Call<ArrayList<deviceListModel>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<deviceListModel>>,
                response: Response<ArrayList<deviceListModel>>
            ) {
                when {
                    response.isSuccessful -> {
                        hideProgressDialog()
                        when {
                            response.code().equals(200) -> {

                                EndList = response.body()!!
                                System.out.println("recycler ${response.body()}")
                                if (EndList.size > 0) {
                                    var ActiveDevices_No = 0
                                    var InActiveDevices_No = 0
                                    for (i in EndList.indices) {
                                        val c = EndList.get(i)
                                        val isActive: String = c.getIsActive()!!
                                        if (isActive.equals("yes", ignoreCase = true)) {
                                            ActiveDevices_No += 1
                                            ActiveDevicesList.add(c)
                                        } else {
                                            InActiveDevices_No += 1
                                            InActiveDevicesList.add(c)
                                        }
                                    }
                                    txt_sd_totaldevices.text = EndList.size.toString()
                                    TotalDevicesList.addAll(EndList)
                                    txt_sd_activedevices.text = ActiveDevices_No.toString()
                                    txt_sd_inactivdevices.text = InActiveDevices_No.toString()
                                    integrateList(TotalDevicesList, "")
                                } else {
                                    hideProgressDialog()
                                }
                            }
                        }
                    }
                    else -> hideProgressDialog()
                }
            }
        })
    }

    fun integrateList(leadslist: ArrayList<deviceListModel>, branch: String) {
        println("frag id " + getPrefsString(Constants.thisdeviceid, ""))
        ShowingList.clear()
        ShowingList_main.clear()
        ShowingList.addAll(leadslist)
        ShowingList_main.addAll(leadslist)

        if (branch.equals("")) {
            spn_branch.setBackgroundColor(resources.getColor(R.color.grey_400))
            ShowingList_main.clear()
            ShowingList_main.addAll(ShowingList)
            populateList()
        } else {
            spn_branch.background=resources.getDrawable(R.drawable.spinner_box)
            var SelectedBranchList = ArrayList<deviceListModel>()
            for (i in leadslist.indices) {
                val c = leadslist.get(i)
                val isActive: String = c.getBranchId().toString()
                if (isActive.equals(branch, ignoreCase = true)) {
                    SelectedBranchList.add(c)
                }
            }
            ShowingList_main.clear()
            ShowingList_main.addAll(SelectedBranchList)
            populateList()
        }

    }

    fun populateList() {

        //-------------------------------------Recycler Adapter=-------------------------------------

        showDevicesadapter = DevicesAdapter(ShowingList_main, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                println("clicked pos ${position.toString()} view ${view.getTag()}")
                if (view.getTag().equals("switch")) {
                    if (ShowingList_main.get(position).getIsActive().equals("Yes")) {
                        showDialog(
                            ShowingList_main.get(position).getDeviceId().toString(), "0",
                            ShowingList_main.get(position).getDeviceName().toString()
                        )
                    } else {
                        showDialog(
                            ShowingList_main.get(position).getDeviceId().toString(), "1",
                            ShowingList_main.get(position).getDeviceName().toString()
                        )
                    }
                } else if (view.getTag().equals("listitem")) {
                    doFragmentTransaction(
                        deviceUsers(),
                        Constants.DEVICE_USERS,
                        true,
                        ShowingList_main.get(position).getDeviceId().toString(),
                        "device_id"
                    )
                }

            }

            override fun onLongClicked(position: Int) {
            }
        }, getPrefsString(Constants.thisdeviceid, ""))

        showDevicesRecyclerList.setAdapter(showDevicesadapter)

    }

    fun set_device_status(deviceid: String, deviceStatus: String) {
        showProgressDialog()
        println("eviceid $deviceid devname $deviceStatus")
        val deviceStatusService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("device_id", deviceid)
        loginparameters.put("device_status", deviceStatus)
        loginparameters.put("db",getPrefsString(Constants.db,""))

        val setDeviceStatus = deviceStatusService.update_device_status(loginparameters)
        setDeviceStatus.enqueue(object : Callback<successmsgmodel> {
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
                            if (deviceStatus.equals("0")) {
                                toast("Device Deactivated")
                            } else {
                                toast("Device Activated")
                            }
                            get_devices_list()
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

    fun showDialog(deviceid: String, deviceStatus: String, devicename: String) {
        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        if (deviceStatus.equals("1", ignoreCase = true)) {
            builder.setTitle("Activation ")
                .setMessage("Do you wish to Activate the device '$devicename'")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    get_devices_list()
                }
                .setPositiveButton("OK") { dialog, which -> set_device_status(deviceid, deviceStatus) }
        } else {
            builder.setTitle("Deactivation ")
                .setMessage("Do you wish to Deactivate the device '$devicename'")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    get_devices_list()
                }
                .setPositiveButton("Ok") { dialog, which -> set_device_status(deviceid, deviceStatus) }
        }
        builder.create().show()
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
