package com.mazenet.mani.gurugubera.Fragments


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.mazenet.mani.gurugubera.Activities.HomeActivity
import com.mazenet.mani.gurugubera.Adapters.ShowCollectionList
import com.mazenet.mani.gurugubera.Databases.DatabaseOffline
import com.mazenet.mani.gurugubera.Model.*
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Spinners.*
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import com.mazenet.mani.gurugubera.Model.SchemsModel
import com.mazenet.mani.gurugubera.Utilities.BaseActivity
import com.mazenet.mani.gurugubera.Utilities.BaseUtils.offlinedb

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log


class Add_customer : BaseFragment() {

    lateinit var  cus_name:EditText
    lateinit var  branch:Button
    lateinit var  empBranch:Button
    lateinit var  mobile:EditText
    lateinit var  email:EditText
    lateinit var  date_of_birth:TextView
    lateinit var  date_of_joning:TextView
    lateinit var  address:EditText
    lateinit var  state:Button
    lateinit var  state_spinner:Spinner
    lateinit var  agent_spiner:Spinner
    lateinit var  schemes_spinner:Spinner
    lateinit var  district_spinner:Spinner
    lateinit var  city_spinner:Spinner
    lateinit var  district:Button
    lateinit var  city:Button
    lateinit var  pincode:EditText
    lateinit var  submit:TextView
    var do_birth: String = ""
    var do_join: String = ""
    var brancharray = ArrayList<BranchModel>()
    var statearray = ArrayList<StateModel>()
    var State_Array = ArrayList<State_array_Model>()
    var District_Array = ArrayList<District_array_Model>()
    var City_Array = ArrayList<City_array_Model>()
    var selected_state_id = ""
    var selected_state_name = ""
    var selected_agent_id = ""
    var selected_agent_name = ""
    var selected_scheme_id = ""
    var selected_scheme_name = ""
    private val schemes: MutableList<SchemsModel>? = mutableListOf()
    private val states: MutableList<statesModel>? = mutableListOf()
    private val agent: MutableList<BusinessAgentReportModel>? = mutableListOf()

    private val shemesMap: java.util.HashMap<Int, SchemsModel>? = null
    private val statesMap: java.util.HashMap<Int, statesModel>? = null
    private val agentMap: java.util.HashMap<Int, BusinessAgentReportModel>? = null

    private val districts: MutableList<districtModel>? = mutableListOf()
    private val distrctMap: java.util.HashMap<Int, districtModel>? = null

    private val cities: MutableList<citymodel>? = mutableListOf()
    private val cityMap: java.util.HashMap<Int, citymodel>? = null
    var selected_district_id = ""
    var selected_district_name = ""
    var selected_city_id = ""
    var selected_city_name = ""
    var SelectedBranch = ""
    var SelectedBranchName = ""
    var gender = "1"

    lateinit var BranchSpinner: BranchSpinnerdilog
    lateinit var StateSpinner: StateListSpinnerDialog
    lateinit var DistrictSpinner: DistrictSpinnerdilog
    lateinit var CitySpinner: CitySpinnerdilog

    lateinit var  male : RadioButton
    lateinit var  female : RadioButton
    var PaymentTypeList = java.util.ArrayList<PaymentTypeModel>()
    lateinit var PaymentSpinner: PaymentTypeSpinnerdilog
    lateinit var edt_paymenttype: Button
    var SelectedPaymenttypeid: String = ""
    var SelectedPaymenttypename: String = ""


    lateinit var df : SimpleDateFormat


    var customerlist = ArrayList<collectioncustomermodel>()
    var customerlist_showing = ArrayList<collectioncustomermodel>()
    lateinit var collection_recyclerview : RecyclerView
    lateinit var edt_search: EditText
    lateinit var btn_col_settlement : Button
    lateinit var btn_col_viewreceipts : Button
    lateinit var txt_cl_totalcustomers : TextView
    lateinit var txt_cl_tobecollected : TextView
    lateinit var txt_cl_cashinhand : TextView
    lateinit var txt_cl_collected : TextView
    lateinit var imagerror : ImageView
    lateinit var shoeCollectionCustomerAdapter : ShowCollectionList
    val today_calender = Calendar.getInstance()

    val day_of_month = today_calender.get(Calendar.DAY_OF_MONTH)
    val week_of_year = today_calender.get(Calendar.WEEK_OF_YEAR)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as HomeActivity).getPAymeType()
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View
        view = inflater.inflate(R.layout.add_customer_fragment, container, false)
        (activity as HomeActivity).setActionBarTitle("Add Users")
        if (BaseUtils.masterdb.PaymentTypeSize() > 0) {
            PaymentTypeList = BaseUtils.masterdb.getPaymentType()
        }
        //offlinedb.deleteAddCustomer()
        Log.d("Customer_list",offlinedb.getOfflineAddCustomer().toString())
        hideProgressDialog()
        df = SimpleDateFormat("yyyy-MM-dd")


        (activity as HomeActivity).setActionBarTitle("Add Customer")
        cus_name = view.findViewById(R.id.cus_name) as EditText
        male = view.findViewById(R.id.male) as RadioButton
        female = view.findViewById(R.id.female) as RadioButton
        branch = view.findViewById(R.id.branch) as Button
        empBranch = view.findViewById(R.id.emp_branch) as Button
        mobile = view.findViewById(R.id.mobile) as EditText
        email = view.findViewById(R.id.email) as EditText
        date_of_birth = view.findViewById(R.id.date_of_birth) as TextView
        date_of_joning = view.findViewById(R.id.date_of_joning) as TextView
        address = view.findViewById(R.id.address) as EditText
        state = view.findViewById(R.id.state) as Button
        state_spinner = view.findViewById(R.id.state_spinner) as Spinner
        agent_spiner = view.findViewById(R.id.agent_spinner) as Spinner
        schemes_spinner = view.findViewById(R.id.schemes_spinner) as Spinner
        district_spinner = view.findViewById(R.id.district_spinner) as Spinner
        city_spinner = view.findViewById(R.id.city_spinner) as Spinner
        district = view.findViewById(R.id.district) as Button
        city = view.findViewById(R.id.district) as Button
        pincode = view.findViewById(R.id.pincode) as EditText
        submit = view.findViewById(R.id.submit) as TextView
        edt_paymenttype = view.findViewById(R.id.edt_paymenttype) as Button

        ///
        getStates()
        getScheme()
      //  getAgents()
        val agent_list= ArrayList<String>()
        agent_list.add(getPrefsString(Constants.username,""))
        agent_spiner.adapter = context?.let { ArrayAdapter<String>(it,  android.R.layout.simple_spinner_dropdown_item, agent_list) }
       //agent_spiner.onItemSelectedListener = agetSpinner

        PaymentSpinner = PaymentTypeSpinnerdilog(
            activity, PaymentTypeList,
            "Select Payment Type"
        )
        edt_paymenttype.setOnClickListener {
            PaymentSpinner.showSpinerDialog()
        }
        PaymentSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, paytype: String) {
                SelectedPaymenttypeid = position.toString()
                SelectedPaymenttypename = paytype
                edt_paymenttype.setText(paytype)
                println("paytype $paytype")
            }
        })

        empBranch.text=getPrefsString(Constants.branchName,"")


        submit.setOnClickListener {

            if(female.isChecked){
                gender="2"
            }
            if(male.isChecked){
                gender="1"
            }


            if(cus_name.text.toString().isEmpty() ||mobile.text.toString().isEmpty() ||email.text.toString().isEmpty()||date_of_birth.text.toString().isEmpty()
                || date_of_joning.text.toString().isEmpty()||address.text.toString().isEmpty()||pincode.text.toString().isEmpty()
                ||selected_state_id.equals("")||selected_district_id.equals("")||selected_city_id.equals("")) {


                if (cus_name.text.toString().isEmpty()) {
                    cus_name.setError("Enter Customer Name")
                } else {
                    cus_name.setError(null)

                }

                if (mobile.text.toString().isEmpty()) {
                    mobile.setError("Enter Mobile Number")
                } else {
                    mobile.setError(null)

                }
                if (email.text.toString().isEmpty()) {
                    email.setError("Enter Email ID")
                } else {
                    email.setError(null)

                }
                if (date_of_birth.text.toString().isEmpty()) {
                    date_of_birth.setError("Enter DOB")
                } else {
                    date_of_birth.setError(null)
                }

                if (date_of_joning.text.toString().isEmpty()) {
                    date_of_joning.setError("Enter DOJ")
                } else {
                    date_of_joning.setError(null)
                }
                if (address.text.toString().isEmpty()) {
                    address.setError("Enter Address")
                } else {
                    address.setError(null)
                }

                if (pincode.text.toString().isEmpty()) {
                    pincode.setError("Enter Pincode")
                } else {
                    pincode.setError(null)
                }
                if (selected_state_id.equals("")) {

                    Toast.makeText(context, "Select State", Toast.LENGTH_SHORT).show()

                }
                if (selected_district_id.equals("")) {

                    Toast.makeText(context, "Select District", Toast.LENGTH_SHORT).show()

                }
                if (selected_city_id.equals("")) {

                    Toast.makeText(context, "Select City", Toast.LENGTH_SHORT).show()
                }
                if (SelectedBranch.equals("")) {

                    Toast.makeText(context, "Select City", Toast.LENGTH_SHORT).show()
                }

            }else{

                add_customer(Constants.tenantid,SelectedBranch,cus_name.text.toString(),mobile.text.toString(),gender,
                    email.text.toString(),address.text.toString(),selected_state_id,selected_district_id,
                    selected_city_id,pincode.text.toString(),date_of_birth.text.toString(),date_of_joning.text.toString())

            }

        }

        
        if (BaseUtils.masterdb.BranchTableSize() > 0) {
            brancharray = BaseUtils.masterdb.getBranches()
            System.out.println("branch_array $brancharray")
        }

        //ithu corrct ah working.. itha reference ah vachu tha panne
        BranchSpinner = BranchSpinnerdilog(
            activity, brancharray,
            "Select Branch"
        )

        branch.setOnClickListener {
            System.out.println("spn lay clicked ")
            BranchSpinner.showSpinerDialog()
        }
        BranchSpinner.bindOnSpinerListener(object : OnSpinnerItemClick {
            override fun onClick(item: String, position: Int, grpname: String) {

                SelectedBranch = position.toString()
                SelectedBranchName = grpname
                branch.setText(grpname)
                setPrefsString(Constants.selectedBranchid, SelectedBranch)
                setPrefsString(Constants.selectedBranchName, grpname)

                System.out.println("branchid $SelectedBranch branchname $grpname")

                //getAgents(SelectedBranch)
            }
        })



        ////
        date_of_birth.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1, R.style.MyDialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val newDate = Calendar.getInstance()
                        newDate.set(year, monthOfYear, dayOfMonth)

                        try {

                            do_birth = df.format(newDate.time)
                            date_of_birth.text = do_birth
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH)
                )
            }!!
            fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis())
            fromDatePickerDialog.setTitle("Date of Birth")
            fromDatePickerDialog.show()
        }
        date_of_joning.setOnClickListener {
            val newCalendar = Calendar.getInstance()
            var fromDatePickerDialog: DatePickerDialog
            fromDatePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    it1, R.style.MyDialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        val newDate = Calendar.getInstance()
                        newDate.set(year, monthOfYear, dayOfMonth)

                        try {

                            do_join = df.format(newDate.time)
                            date_of_joning.text = do_join
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, newCalendar.get(Calendar.YEAR),
                    newCalendar.get(Calendar.MONTH),
                    newCalendar.get(Calendar.DAY_OF_MONTH)
                )
            }!!
            fromDatePickerDialog.getDatePicker().maxDate=System.currentTimeMillis()
            fromDatePickerDialog.setTitle("Date of Joining")
            fromDatePickerDialog.show()
        }


        return view
    }


    fun getScheme() {

        var schemeslist = ArrayList<SchemsModel>()
        val getSates = RetrofitBuilder.buildservice(ICallService::class.java)


        val map = HashMap<String,Any>()
        map["db"] = getPrefsString(Constants.db,"")

        val ShemeRequest = getSates.get_schemes(map)
        ShemeRequest.enqueue(object : Callback<Schemes_Response> {
            override fun onFailure(call: Call<Schemes_Response>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Schemes_Response>, response: Response<Schemes_Response>) {
                when {
                    response.isSuccessful -> when {
                        response.code().equals(200) -> {
                            schemeslist = response.body()!!.getscheme_array1()!! as ArrayList<SchemsModel>

                            schemes?.clear()
                            schemes?.addAll(schemeslist)
                            val list = mutableListOf<String>()
                            if (schemes != null) {
                                for (i in 0..schemes.size - 1) {
                                    shemesMap?.put(i, schemes[i])

                                    list.add(schemes[i].getscheme_format()!!)
                                }
                            }

                            schemes_spinner.adapter = context?.let { ArrayAdapter<String>(it,  android.R.layout.simple_spinner_dropdown_item, list) }
                            schemes_spinner.onItemSelectedListener = schemeSpinner

                        }
                    }
                }
            }
        })
    }


    fun getAgents(SelectedBranch:String) {

        showProgressDialog()
        println("fetching")
        var agentList = java.util.ArrayList<BusinessAgentReportModel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        val mmxz=getPrefsString(Constants.tenantid, "");
        val mbranchcsmxz=getPrefsString(Constants.branches, "");
        loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
        loginparameters.put("branch_id", SelectedBranch)
        loginparameters.put("db",getPrefsString(Constants.db,""))

        //  loginparameters.put("user_id", getPrefsString(Constants.loggeduser, ""))
        val LeadListRequest = getleadslist.get_businessagent_reports(loginparameters)
        LeadListRequest.enqueue(object : Callback<ArrayList<BusinessAgentReportModel>> {

            override fun onFailure(call: Call<ArrayList<BusinessAgentReportModel>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()

            }

            override fun onResponse(
                call: Call<ArrayList<BusinessAgentReportModel>>,
                response: Response<ArrayList<BusinessAgentReportModel>>
            ) {
                hideProgressDialog()
                when {
                    response.isSuccessful -> {

                        when {
                            response.code().equals(200) -> {
                                agentList = response.body()!!

                                agent?.clear()
                                agent?.addAll(agentList)

                                val list = mutableListOf<String>()
                                if (agent != null) {
                                    for (i in 0..agent.size - 1) {
                                        agentMap?.put(i, agent[i])

                                        list.add(agent[i].getAgentName()!!)
                                    }
                                }

                                agent_spiner.adapter = context?.let { ArrayAdapter<String>(it,  android.R.layout.simple_spinner_dropdown_item, list) }
                                agent_spiner.onItemSelectedListener = agetSpinner

                            }

                        }

                    }

                }
            }
        })
    }
    fun getStates() {

        var stateslist = ArrayList<statesModel>()
        val getSates = RetrofitBuilder.buildservice(ICallService::class.java)
        val login = HashMap<String,String>()
        login.put("db",getPrefsString(Constants.db,""))
        val StateRequest = getSates.get_states(login)
        StateRequest.enqueue(object : Callback<ArrayList<statesModel>>{
            override fun onResponse(call: Call<ArrayList<statesModel>>, response: Response<ArrayList<statesModel>>) {
                when {
                    response.isSuccessful -> when {
                        response.code().equals(200) -> {
                            stateslist = response.body()!! as ArrayList<statesModel>



                            states?.clear()
                            states?.addAll(stateslist)
                            val list = mutableListOf<String>()
                            if (states != null) {
                                for (i in 0..states.size - 1) {
                                    statesMap?.put(i, states[i])

                                    list.add(states[i].getStateName()!!)
                                }
                            }

                            state_spinner.adapter = context?.let { ArrayAdapter<String>(it,  android.R.layout.simple_spinner_dropdown_item, list) }
                            state_spinner.onItemSelectedListener = stateSpinner

                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<statesModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
       /* StateRequest.enqueue(object : Callback<States_Response> {
            override fun onFailure(call: Call<States_Response>, t: Throwable) {

            }

            override fun onResponse(call: Call<States_Response>, response: Response<States_Response>) {
                when {
                    response.isSuccessful -> when {
                        response.code().equals(200) -> {
                            stateslist = response.body()!!.getstate_array1()!! as ArrayList<StateModel>



                            states?.clear()
                            states?.addAll(stateslist)
                            val list = mutableListOf<String>()
                            if (states != null) {
                                for (i in 0..states.size - 1) {
                                    statesMap?.put(i, states[i])

                                    list.add(states[i].getstatename()!!)
                                }
                            }

                            state_spinner.adapter = ArrayAdapter<String>(context,  android.R.layout.simple_spinner_dropdown_item, list)
                            state_spinner.onItemSelectedListener = stateSpinner

                        }
                    }
                }
            }
        })*/
    }

    val stateSpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            //do nothing
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            selected_state_id = states?.get(p2)?.getStateId().toString() ?: ""
            selected_state_name = states?.get(p2)?.getStateName() ?: ""
            Log.d("stateidcheck",selected_state_id)
            getDistrict()



        }

    }
    val agetSpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            //do nothing
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            /*selected_agent_id = agent?.get(p2)?.getAgentId() ?: ""
            selected_agent_name = agent?.get(p2)?.getAgentName() ?: ""*/



        }

    }
    val schemeSpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            //do nothing
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            selected_scheme_id = schemes?.get(p2)?.getid() ?: ""
            selected_scheme_name = schemes?.get(p2)?.getscheme_format() ?: ""

            getDistrict()



        }

    }


    fun getDistrict() {
        var districtlist = ArrayList<districtModel>()
        val getDistrict = RetrofitBuilder.buildservice(ICallService::class.java)
        val login = HashMap<String,Any>()
        login.put("db",getPrefsString(Constants.db,""))
        val DistrictRequest = getDistrict.get_districts(login)
        DistrictRequest.enqueue(object : Callback<ArrayList<districtModel>> {
            override fun onFailure(call: Call<ArrayList<districtModel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<districtModel>>,
                response: Response<ArrayList<districtModel>>
            ) {
                when {
                    response.isSuccessful -> when {
                        response.code().equals(200) -> {


                          //  districtlist = response.body()!!.getdistrict_array() as ArrayList<District_array_Model>

                            if(response.body()!!.size>0){

                                for(k in 0..response.body()!!.size-1){

                                    if(response.body()!!.get(k).getStateId().toString()!!.equals(selected_state_id)){
                                        Log.d("stateidcheck",selected_state_id+
                                                "  "+response.body()!![k].getStateId())
                                        districtlist.add(response.body()!!.get(k))

                                    }

                                }
                            }


                            districts?.clear()
                            districts?.addAll(districtlist)
                            val list = mutableListOf<String>()
                            if (districts != null) {
                                for (i in 0..districts.size - 1) {
                                    distrctMap?.put(i, districts[i])
                                        list.add(districts[i].getDistrictName()!!)

                                }
                            }

                            district_spinner.adapter = context?.let { ArrayAdapter<String>(it,  android.R.layout.simple_spinner_dropdown_item, list) }
                            district_spinner.onItemSelectedListener = districtSpinner

                        }
                    }
                }
            }
        })
    }

    val districtSpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            //do nothing
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            selected_district_id = districts?.get(p2)?.getDistrictId().toString() ?: ""
            selected_district_name = districts?.get(p2)?.getDistrictName() ?: ""
            getCities()
        }

    }



    fun getCities() {
        var Citylist = ArrayList<citymodel>()
        val getCities = RetrofitBuilder.buildservice(ICallService::class.java)
        val login = HashMap<String,String>()
        login.put("db",getPrefsString(Constants.db,""))
        val CityRequest = getCities.get_cities(login)
        CityRequest.enqueue(object : Callback<ArrayList<citymodel>> {
            override fun onFailure(call: Call<ArrayList<citymodel>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<citymodel>>, response: Response<ArrayList<citymodel>>) {
                when {
                    response.isSuccessful -> when {
                        response.code().equals(200) -> {



                            if(response.body()!!.size>0){

                                for(k in 0..response.body()!!.size-1){
                                    if(response.body()!!.get(k).getDistrictId().toString().equals(selected_district_id)){
                                        Citylist.add(response.body()!!.get(k))

                                    }

                                }
                            }

                            cities?.clear()
                            cities?.addAll(Citylist)
                            val list = mutableListOf<String>()
                            if (cities != null) {
                                for (i in 0..cities.size - 1) {
                                    cityMap?.put(i, cities[i])

                                    var dsvxc=cities[i]
                                    val vdd=selected_district_id

                                    list.add(cities[i].getCityName()!!)


                                }
                            }

                            city_spinner.adapter = context?.let { ArrayAdapter<String>(it,  android.R.layout.simple_spinner_dropdown_item, list) }
                            city_spinner.onItemSelectedListener = citySpinner
                        }
                    }
                }
            }
        })
    }
    val citySpinner = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            //do nothing
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            selected_city_id = cities?.get(p2)?.getCityId().toString() ?: ""
            selected_city_name = cities?.get(p2)?.getCityName() ?: ""


        }

    }


    private fun add_customer(
        tenant_id: String,
        branch_id: String,
        name: String,
        mobile_no: String,
        gender: String,
        email_id: String,
        address_line_1: String,
        state_id: String,
        district_id: String,
        city_id: String,
        pincode: String,
        dob: String,
        doj: String
        ) {
        showProgressDialog()
        if(!getPrefsBoolean(Constants.IS_ONLINE,true)){
            offlinedb = DatabaseOffline(this.requireContext())
            offlinedb.addOfflineAddCustomer(getPrefsString(Constants.tenantid, "")
            ,branch_id,name,mobile_no,gender,email_id,address_line_1,state_id,district_id,city_id,
                pincode,dob,doj,selected_scheme_id,SelectedPaymenttypeid,selected_agent_id,
                getPrefsString(Constants.loggeduser, ""))
            hideProgressDialog()
            Log.d("Customer_list",offlinedb.getOfflineAddCustomer().toString())

            Toast.makeText(this.requireContext(), "offline customer created Successfully", Toast.LENGTH_SHORT).show()
            activity?.onBackPressed()
        }
        else {

            val addleadservice = RetrofitBuilder.buildservice(ICallService::class.java)
            val loginparameters = HashMap<String, Any>()
            loginparameters.put("tenant_id", getPrefsString(Constants.tenantid, ""))
            loginparameters.put("branch_id", branch_id)
            loginparameters.put("name", name)
            loginparameters.put("mobile_no", mobile_no)
            loginparameters.put("gender", gender)
            loginparameters.put("email_id", email_id)
            loginparameters.put("address_line_1", address_line_1)
            loginparameters.put("state_id", state_id)
            loginparameters.put("district_id", district_id)
            loginparameters.put("city_id", city_id)
            loginparameters.put("pincode", pincode)
            loginparameters.put("dob", dob)
            loginparameters.put("doj", doj)
            loginparameters.put("scheme_id", selected_scheme_id)
            loginparameters.put("payment_type_id", SelectedPaymenttypeid)
            loginparameters.put("agent_id", getPrefsString(Constants.loggeduser, ""))
            loginparameters.put("created_by", getPrefsString(Constants.loggeduser, ""))
            loginparameters.put("db",getPrefsString(Constants.db,""))


            System.out.println("user param" + loginparameters)
            val addlead_req = addleadservice.add_user(loginparameters)
            addlead_req.enqueue(object : Callback<successmsgmodel> {
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
                                toast("Customer Added Successfully")
                                //doFragmentTransaction(BaseFragment(), Constants.SHOW_LEAD_TAG, false, "", "")
                                activity?.let {
                                    val intent = Intent(it, HomeActivity::class.java)
                                    it.startActivity(intent)
                                }
                            } else {
                                toast(response.body()!!.getMsg()!!)
                                activity?.let {
                                    val intent = Intent(it, HomeActivity::class.java)
                                    it.startActivity(intent)
                                }
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
    }




    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }
}
