package com.mazenet.mani.gurugubera.Fragments.Collection


import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.AdapterClickListener
import com.mazenet.mani.gurugubera.Adapters.ShowCollectionList
import com.mazenet.mani.gurugubera.Fragments.BaseFragment
import com.mazenet.mani.gurugubera.Model.CashinhandModel
import com.mazenet.mani.gurugubera.Model.collectioncustomermodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Collections : BaseFragment() {
    var customerlist = ArrayList<collectioncustomermodel>()
    var customerlist_showing = ArrayList<collectioncustomermodel>()
    lateinit var collection_recyclerview: RecyclerView
    lateinit var edt_search: EditText
    lateinit var btn_col_settlement: Button
    lateinit var btn_col_viewreceipts: Button
    lateinit var txt_cl_totalcustomers: TextView
    lateinit var txt_cl_tobecollected: TextView
    lateinit var txt_cl_cashinhand: TextView
    lateinit var txt_cl_collected: TextView
    lateinit var imagerror: ImageView
    lateinit var shoeCollectionCustomerAdapter: ShowCollectionList
    val today_calender = Calendar.getInstance()
    val day_of_month = today_calender.get(Calendar.DAY_OF_MONTH)
    val week_of_year = today_calender.get(Calendar.WEEK_OF_YEAR)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
            menu.findItem(R.id.refresh).isVisible = true
        } else {
            menu.findItem(R.id.refresh).isVisible = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.filter -> {
                true
            }
            R.id.search -> {
                true
            }
            R.id.refresh -> {
                if (checkForInternet()) {
                    if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
                        get_Collectionlist()
                        get_cashinhand()
                    } else {
                        toast("You are in offline mode")
                    }

                } else {
                    toast("No Internet Connection")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View
        view = inflater.inflate(R.layout.fragment_collections, container, false)
        hideProgressDialog()
        (activity as HomeActivity).setActionBarTitle("Collection")
        collection_recyclerview = view.findViewById(R.id.collection_recyclerview) as RecyclerView
        edt_search = view.findViewById(R.id.edt_search) as EditText
        btn_col_settlement = view.findViewById(R.id.btn_col_settlement) as Button
        btn_col_viewreceipts = view.findViewById(R.id.btn_col_viewreceipts) as Button
        txt_cl_collected = view.findViewById(R.id.txt_cl_collected) as TextView
        txt_cl_tobecollected = view.findViewById(R.id.txt_cl_tobecollected) as TextView
        txt_cl_totalcustomers = view.findViewById(R.id.txt_cl_totalcustomers) as TextView
        txt_cl_cashinhand = view.findViewById(R.id.txt_cl_cashinhand) as TextView
        imagerror = view.findViewById(R.id.imagerror) as ImageView
        //---------_Recycler View --------------------------------------------------------
        collection_recyclerview.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(context)
        collection_recyclerview.setLayoutManager(mLayoutManager)
        collection_recyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        collection_recyclerview.setItemAnimator(DefaultItemAnimator())
        if (getPrefsBoolean(Constants.IS_ONLINE, true)) {
            try {
                if (day_of_month == getPrefsInt(
                        Constants.DAYCHECK_COLL,
                        0
                    ) && week_of_year == getPrefsInt(
                        Constants.WEEKCHECK_COLL,
                        0
                    ) && BaseUtils.offlinedb.OfflinecustomerSize() !== 0
                ) {
                    integrateList(BaseUtils.offlinedb.getOfflineCustomersList())
                } else {
                    if (checkForInternet()) {
                        get_Collectionlist()
                        get_cashinhand()
                    } else {
                        toast("No Internet Connection")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (checkForInternet()) {
                get_cashinhand()
            } else {
                toast("No Internet Connection")
            }

        } else {
            val offlinelist = BaseUtils.offlinedb.getOfflineCustomersList()
            if (offlinelist.size > 0) {
                integrateList(offlinelist)
                txt_cl_cashinhand.text = "Offline"
            } else {
                toast("No Customers Available")
            }

        }

        btn_col_viewreceipts.setOnClickListener {
            doFragmentTransaction(ViewReceiptsFragment(), Constants.VIEWRECEIPTS, true, "", "")
        }
        btn_col_settlement.setOnClickListener {
            doFragmentTransaction(CashinHand_Fragment(), Constants.VIEWRECEIPTS, true, "", "")
        }
        edt_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
                val text = edt_search.getText().toString()
                if (text == "" || text == null) {
                    customerlist_showing.clear()
                    customerlist_showing.addAll(customerlist)
                    if (customerlist_showing.size > 0) {
                        collection_recyclerview.setVisibility(View.VISIBLE)
                        shoeCollectionCustomerAdapter =
                            ShowCollectionList(customerlist_showing, object : AdapterClickListener {
                                override fun onPositionClicked(view: View, position: Int) {
                                    if (view.getTag().equals("listitem")) {
                                        val bundle: Bundle = Bundle()

                                        bundle.putString(
                                            "cust_id",
                                            customerlist_showing.get(position).getCustomerId().toString()
                                        )
                                        bundle.putString(
                                            "cust_name",
                                            customerlist_showing.get(position).getCustomerName().toString()
                                        )
                                        bundle.putString("cust_code", customerlist_showing.get(position).getCustomerCode().toString())
                                        bundle.putString("cust_mobile", customerlist_showing.get(position).getMobileNo().toString())
                                       // bundle.putString("cust_phone", customerlist_showing.get(position).getPhoneNo().toString())
                                        doFragmentTransactionWithBundle(
                                            GroupFragment(),
                                            Constants.GROUP_TAG,
                                            true, bundle
                                        )
                                    }
                                    else if (view.getTag().equals("custname")) {
                                    }
                                }

                                override fun onLongClicked(position: Int) {
                                }
                            })
                        collection_recyclerview.setAdapter(shoeCollectionCustomerAdapter)

                    } else {

                        collection_recyclerview.setVisibility(View.GONE)
                    }
                } else {
                    setnewadapter()
                    collection_recyclerview.setVisibility(View.VISIBLE)
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
                if (customerlist_showing.size > 0) {
                    collection_recyclerview.setVisibility(View.VISIBLE)
                } else {
                    collection_recyclerview.setVisibility(View.GONE)
                }
            }
        })
        return view
    }

    private fun setnewadapter() {
        customerlist_showing.clear()

        var text = edt_search.text.toString()
        text = text.toLowerCase(Locale.getDefault())
        if (text == null) {
            collection_recyclerview.setVisibility(View.GONE)
            return
        } else {
            for (i in customerlist.indices) {
                val schedte = customerlist.get(i)
                var name = schedte.getCustomerName()
//                var mobile = schedte.getMOBILE()
                name = name!!.toLowerCase(Locale.getDefault())
//                mobile = mobile.toLowerCase(Locale.getDefault())
                if (name.contains(text)) {
                    customerlist_showing.add(customerlist.get(i))
                }
            }
        }
        if (customerlist_showing.size > 0) {
            collection_recyclerview.setVisibility(View.VISIBLE)
            shoeCollectionCustomerAdapter = ShowCollectionList(customerlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("listitem")) {
                        val bundle: Bundle = Bundle()
                        bundle.putString("cust_id", customerlist_showing.get(position).getCustomerId().toString())
                        bundle.putString("cust_name", customerlist_showing.get(position).getCustomerName().toString())
                        bundle.putString("cust_code", customerlist_showing.get(position).getCustomerCode().toString())
                        bundle.putString("cust_mobile", customerlist_showing.get(position).getMobileNo().toString())
                      //  bundle.putString("cust_phone", customerlist_showing.get(position).getPhoneNo().toString())

                        doFragmentTransactionWithBundle(
                            GroupFragment(),
                            Constants.GROUP_TAG,
                            true, bundle
                        )
                    } else if (view.getTag().equals("custname")) {
                    }
                }

                override fun onLongClicked(position: Int) {
                }
            })
            collection_recyclerview.setAdapter(shoeCollectionCustomerAdapter)
        } else {
            collection_recyclerview.setVisibility(View.GONE)
        }
    }

    fun get_Collectionlist() {

            showProgressDialog()
            println("fetching")
            var resultlist = java.util.ArrayList<collectioncustomermodel>()
            val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, String>()
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("employee_id", getPrefsInt(Constants.employee_id, 0).toString())
            loginparameters.put("db",getPrefsString(Constants.db,""))
            val LeadListRequest = getleadslist.show_collection_customers(loginparameters)
            LeadListRequest.enqueue(object : Callback<ArrayList<collectioncustomermodel>> {

                override fun onFailure(call: Call<ArrayList<collectioncustomermodel>>, t: Throwable) {
                    hideProgressDialog()
                    collection_recyclerview.visibility = View.GONE
                    imagerror.visibility = View.VISIBLE
                    t.printStackTrace()

                }

                override fun onResponse(
                    call: Call<ArrayList<collectioncustomermodel>>,
                    response: Response<ArrayList<collectioncustomermodel>>
                ) {
                    when {
                        response.isSuccessful -> {
                            hideProgressDialog()
                            when {
                                response.code().equals(200) -> {
                                    resultlist = response.body()!!

                                    System.out.println("recycler ${response.body()}")
                                    //System.out.println("recycler ${response.body()!![0].getEntrollment()}")
                                    if (resultlist.size > 0) {
                                        BaseUtils.offlinedb.deleteCustomersTable()
                                        BaseUtils.offlinedb.addOfflineCustomerList(resultlist)
                                        setPrefsInt(Constants.DAYCHECK_COLL, day_of_month)
                                        setPrefsInt(Constants.WEEKCHECK_COLL, week_of_year)
                                        integrateList(response.body()!!)

                                    } else {
                                        collection_recyclerview.visibility = View.GONE
                                        imagerror.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                        else -> {
                            hideProgressDialog()
                            collection_recyclerview.visibility = View.GONE
                            imagerror.visibility = View.VISIBLE
                        }
                    }
                }
            })

    }

    fun get_cashinhand() {
        showProgressDialog()

        val cashinhand_serv = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        loginparameters.put("db",getPrefsString(Constants.db,""))
        val cashinhand_req = cashinhand_serv.get_cashinhand(loginparameters)
        cashinhand_req.enqueue(object : Callback<CashinhandModel> {
            override fun onFailure(call: Call<CashinhandModel>, t: Throwable) {
                toast(t.toString())
                hideProgressDialog()
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<CashinhandModel>,
                response: Response<CashinhandModel>
            ) {
                if (response.isSuccessful) {
                    hideProgressDialog()
                    if (response.code().equals(200)) {
                        if (response.body()!!.getStatus().equals("Success")) {
                            txt_cl_cashinhand.text = Constants.money_convertor(
                                Constants.isEmtytoZero(response.body()!!.getCashInHand().toString()),
                                false
                            )
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

    fun integrateList(leadslist: ArrayList<collectioncustomermodel>) {
        if (leadslist.size > 0) {
            customerlist.clear()
            customerlist_showing.clear()
            customerlist.addAll(leadslist)
            customerlist_showing.addAll(leadslist)
            imagerror.visibility = View.GONE
            collection_recyclerview.visibility = View.VISIBLE
            shoeCollectionCustomerAdapter = ShowCollectionList(customerlist_showing, object : AdapterClickListener {
                override fun onPositionClicked(view: View, position: Int) {
                    if (view.getTag().equals("listitem")) {
                        val bundle: Bundle = Bundle()
                       // bundle.putString("enroll",customerlist_showing.get(position).getEntrollment().toString())
                        println("custcode ${customerlist_showing.get(position).getCustomerCode().toString()}")
                        bundle.putString("cust_id", customerlist_showing.get(position).getCustomerId().toString())
                        bundle.putString("cust_name", customerlist_showing.get(position).getCustomerName().toString())
                        bundle.putString("cust_code", customerlist_showing.get(position).getCustomerCode().toString())
                        bundle.putString("cust_mobile", customerlist_showing.get(position).getMobileNo().toString())
                       // bundle.putString("cust_phone", customerlist_showing.get(position).getPhoneNo().toString())
                        doFragmentTransactionWithBundle(
                            GroupFragment(),
                            Constants.GROUP_TAG,
                            true, bundle
                        )
                    } else if (view.getTag().equals("custname")) {
                    }
                }

                override fun onLongClicked(position: Int) {
                }
            })
            var total_collected = 0
            var total_tobecollected = 0
            for (i in customerlist_showing) {
                total_collected = total_collected + i.getTotalPaid()!!.toInt()
                total_tobecollected = total_tobecollected + i.getTotalPending()!!.toInt()
            }
            txt_cl_collected.text = Constants.money_convertor(Constants.isEmtytoZero(total_collected.toString()), false)
            txt_cl_tobecollected.text =
                Constants.money_convertor(Constants.isEmtytoZero(total_tobecollected.toString()), false)
            txt_cl_totalcustomers.text =
                Constants.money_convertor(Constants.isEmtytoZero(customerlist_showing.size.toString()), false)
            collection_recyclerview.setAdapter(shoeCollectionCustomerAdapter)
        } else {
            imagerror.visibility = View.VISIBLE
            collection_recyclerview.visibility = View.GONE
            toast("No Customers Available")
        }
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
