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
import com.mazenet.mani.gurugubera.Adapters.DeviceUsersAdapter
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.BranchModel
import com.mazenet.mani.gurugubera.Model.DeviceUserListModel
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


class deviceUsers : BaseFragment() {
    lateinit var showDevicesRecyclerList: RecyclerView
    lateinit var txt_sd_inactivdevices: TextView
    lateinit var txt_sd_activedevices: TextView
    lateinit var txt_su_totaldevices: TextView
    lateinit var checkBox_branch: CheckBox
    lateinit var showDevicesadapter: DeviceUsersAdapter
    var TotalDevicesList = ArrayList<DeviceUserListModel>()
    var ActiveDevicesList = ArrayList<DeviceUserListModel>()
    var InActiveDevicesList = ArrayList<DeviceUserListModel>()
    var ShowingList = ArrayList<DeviceUserListModel>()
    var ShowingList_main = ArrayList<DeviceUserListModel>()
    lateinit var spn_branch: Button
    lateinit var SelectedBranch: String
    lateinit var SelectedBranchName: String
    lateinit var SelectedDeviceId: String
    var brancharray = ArrayList<BranchModel>()
    lateinit var BranchSpinner: BranchSpinnerdilog
    lateinit var edt_du_search: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.mazenet.mani.gurugubera.R.layout.fragment_devices_users, container, false)
        (activity as HomeActivity).setActionBarTitle("Device Users")
        spn_branch = view.findViewById(R.id.spn_showdusers_branch) as Button
        showDevicesRecyclerList =
            view.findViewById<RecyclerView>(com.mazenet.mani.gurugubera.R.id.showUsers) as RecyclerView
        txt_sd_inactivdevices =
            view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.txt_su_inactiveusers) as TextView
        txt_sd_activedevices = view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.txt_su_activeusers) as TextView
        txt_su_totaldevices = view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.txt_su_totaldevices) as TextView
        checkBox_branch = view.findViewById<TextView>(com.mazenet.mani.gurugubera.R.id.checkBox_su_branch) as CheckBox
        edt_du_search = view.findViewById<EditText>(com.mazenet.mani.gurugubera.R.id.edt_du_search) as EditText
        SelectedDeviceId=arguments!!.getString("device_id")
        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        val mLayoutManager = LinearLayoutManager(context)
        showDevicesRecyclerList.setHasFixedSize(true)
        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        showDevicesRecyclerList.setLayoutManager(mLayoutManager)
        // adding inbuilt divider line
        showDevicesRecyclerList.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
//      collection_recyclerview.addItemDecoration( MarginItemDecoration( resources.getDimension(R.dimen.sp8_space).toInt()))
//      recyclerView.addItemDecoration(MyDividerItemDecoration(context, LinearLayoutManager.HORIZONTAL, 16));
        // adding custom divider line with padding 16dp
        showDevicesRecyclerList.setItemAnimator(DefaultItemAnimator())
        checkBox_branch.setOnClickListener {
            if (checkBox_branch.isChecked) {
                integrateList(TotalDevicesList, getPrefsString(Constants.selectedBranchid, ""))
                spn_branch.text=getPrefsString(Constants.selectedBranchName, "")
            } else {
                integrateList(TotalDevicesList, "")
                spn_branch.text=""

            }
        }
        if (BaseUtils.masterdb.BranchTableSize() > 0) {
            brancharray = BaseUtils.masterdb.getBranches()
            System.out.println("greater $brancharray")
        }
        BranchSpinner = BranchSpinnerdilog(
            activity, brancharray,
            "Select Branch Name"
        )

        spn_branch.setOnClickListener {
            System.out.println("spn lay clicked ")
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
                checkBox_branch.isChecked=true
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)
                integrateList(TotalDevicesList, SelectedBranch)
                System.out.println("branchid $SelectedBranch branchname $grpname")
            }
        })
        get_devices_list()
        edt_du_search.setText("")
        edt_du_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                val text = edt_du_search.getText().toString()
                if (text == "" || text == null) {
                    println("text emppty")
                    ShowingList_main.clear()
                    ShowingList_main.addAll(ShowingList)
                    if (ShowingList_main.size > 0) {
                        showDevicesRecyclerList.setVisibility(View.VISIBLE)
                        showDevicesadapter =
                            DeviceUsersAdapter(ShowingList_main, object : AdapterClickListener {
                                override fun onPositionClicked(view: View, position: Int) {
                                    if (view.getTag().equals("switch")) {
                                        if (ShowingList_main.get(position).getIsActive().equals("Yes")) {
                                            showDialog(
                                                ShowingList_main.get(position).getUserId().toString(), "0",
                                                ShowingList_main.get(position).getUserName().toString()
                                            )
                                        } else {
                                            showDialog(
                                                ShowingList_main.get(position).getUserId().toString(), "1",
                                                ShowingList_main.get(position).getUserName().toString()
                                            )
                                        }
                                    }
                                }
                                override fun onLongClicked(position: Int) {
                                }
                            }, getPrefsString(Constants.loggeduser, ""))
                        showDevicesRecyclerList.setAdapter(showDevicesadapter)

                    } else {

                        showDevicesRecyclerList.setVisibility(View.GONE)
                    }
                } else {
                    println("text $text")
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

        var text = edt_du_search.text.toString()
        text = text.toLowerCase(Locale.getDefault())
        if (text == null) {
            showDevicesRecyclerList.setVisibility(View.GONE)
            return
        } else {
            for (i in ShowingList.indices) {
                val schedte = ShowingList.get(i)
                var name = schedte.getUserName()
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
            showDevicesadapter = DeviceUsersAdapter(ShowingList_main, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("switch")) {
                        if (ShowingList_main.get(position).getIsActive().equals("Yes")) {
                            showDialog(
                                ShowingList_main.get(position).getUserId().toString(), "0",
                                ShowingList_main.get(position).getUserName().toString()
                            )
                        } else {
                            showDialog(
                                ShowingList_main.get(position).getUserId().toString(), "1",
                                ShowingList_main.get(position).getUserName().toString()
                            )
                        }
                    }

                }

                override fun onLongClicked(position: Int) {
                }
            }, getPrefsString(Constants.loggeduser, ""))
            showDevicesRecyclerList.setAdapter(showDevicesadapter)
        } else {
            showDevicesRecyclerList.setVisibility(View.GONE)
        }
    }
    fun get_devices_list() {
showProgressDialog()
        TotalDevicesList.clear()
        var EndList = java.util.ArrayList<DeviceUserListModel>()
        val getDeviceslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("device_id",SelectedDeviceId)
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val devicesRequest = getDeviceslist.show_device_users(loginparameters)
        devicesRequest.enqueue(object : Callback<ArrayList<DeviceUserListModel>> {
            override fun onFailure(call: Call<ArrayList<DeviceUserListModel>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<DeviceUserListModel>>,
                response: Response<ArrayList<DeviceUserListModel>>
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
                                    txt_su_totaldevices.text=EndList.size.toString()
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
                }
            }
        })
    }

    fun integrateList(leadslist: ArrayList<DeviceUserListModel>, branch: String) {

        ShowingList.clear()
        ShowingList_main.clear()
        ShowingList.addAll(leadslist)
        ShowingList_main.addAll(leadslist)

        if (branch.equals("")) {
            spn_branch.setBackgroundColor(resources.getColor(R.color.grey_400))
            ShowingList_main.clear()
            ShowingList_main.addAll(ShowingList)
            println("showmain ${ShowingList_main.size}")
            populateList()
        } else {
            spn_branch.background=resources.getDrawable(R.drawable.spinner_box)
            var SelectedBranchList = ArrayList<DeviceUserListModel>()
            for (i in ShowingList.indices) {
                val c = ShowingList.get(i)
                val isActive: String = c.getBranchId().toString()
                if (isActive.equals(branch, ignoreCase = true)) {
                    SelectedBranchList.add(c)
                }
            }
            ShowingList_main.clear()
            ShowingList_main.addAll(SelectedBranchList)
            println("showmain ${ShowingList_main.size}")
            populateList()
        }

    }

    fun populateList() {
        //-------------------------------------Recycler Adapter=-------------------------------------
        println("showmain ${ShowingList_main.size}")
        showDevicesadapter = DeviceUsersAdapter(ShowingList_main, object : AdapterClickListener {
            override fun onPositionClicked(view: View, position: Int) {
                println("clicked pos ${position.toString()} view ${view.getTag()}")
                if (view.getTag().equals("switch")) {
                    if (ShowingList_main.get(position).getIsActive().equals("Yes")) {
                        showDialog(
                            ShowingList_main.get(position).getUserId().toString(), "0",
                            ShowingList_main.get(position).getUserName().toString()
                        )
                    } else {
                        showDialog(
                            ShowingList_main.get(position).getUserId().toString(), "1",
                            ShowingList_main.get(position).getUserName().toString()
                        )
                    }
                }
            }

            override fun onLongClicked(position: Int) {
            }
        }, getPrefsString(Constants.loggeduser, ""))

        showDevicesRecyclerList.setAdapter(showDevicesadapter)
        when {
            ShowingList.size > 0 -> {
                val lastposition = ShowingList.size - 1
                showDevicesRecyclerList.smoothScrollToPosition(lastposition)
                showDevicesRecyclerList.smoothScrollToPosition(0)
            }
            else -> {
            }
        }
    }

    fun set_device_status(deviceid: String, deviceStatus: String) {
        showProgressDialog()
        println("eviceid $deviceid devname $deviceStatus")
        val deviceStatusService = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("device_primary_id", SelectedDeviceId)
        loginparameters.put("device_login_status", deviceStatus)
        loginparameters.put("user_id", deviceid)
        loginparameters.put("db",getPrefsString(Constants.db,""))

        val setDeviceStatus = deviceStatusService.update_device_users_status(loginparameters)
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
                            toast(response.body()!!.getMsg()!!)
                        }
                    } else {
                        System.out.println("no show")
                    }
                } else { hideProgressDialog()
                }
            }
        })
    }

    fun showDialog(deviceid: String, deviceStatus: String, devicename: String) {
        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        if (deviceStatus.equals("1", ignoreCase = true)) {
            builder.setTitle("Activation ")
                .setMessage("Do you wish to Activate the user '$devicename'")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                    get_devices_list()
                }
                .setPositiveButton("OK") { dialog, which -> set_device_status(deviceid, deviceStatus) }
        } else {
            builder.setTitle("Deactivation ")
                .setMessage("Do you wish to Deactivate the user '$devicename'")
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
