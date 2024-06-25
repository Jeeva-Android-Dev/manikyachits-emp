package com.mazenet.mani.gurugubera.Services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import com.mazenet.mani.gurugubera.Databases.DatabaseOffline
import com.mazenet.mani.gurugubera.Model.GroupdetailsModel
import com.mazenet.mani.gurugubera.Model.collectioncustomermodel
import com.mazenet.mani.gurugubera.Retrofit.ICallService
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder
import com.mazenet.mani.gurugubera.Utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfflineService : Service() {
   lateinit var offlinedb:DatabaseOffline
    lateinit var offline_intent: Intent
    var BROADCAST_OFFLINE_DOWNLOAD = "buferedoffline"
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        offline_intent = Intent(BROADCAST_OFFLINE_DOWNLOAD)
        super.onCreate()
    }

    private fun sendAquringCustBroadcast() {
        offline_intent.putExtra("Acquiring customers", "0")
        sendBroadcast(offline_intent)
    }

    private fun sendAquiringCustCompletedBroadcast() {
        offline_intent.putExtra("Acquiring customers", "1")
        sendBroadcast(offline_intent)
    }

    private fun sendAquiringCustErrorBroadcast() {
        offline_intent.putExtra("Acquiring customers", "2")
        sendBroadcast(offline_intent)
    }

    private fun sendAquringEnrlBroadcast() {
        offline_intent.putExtra("Acquiring enrollments", "0")
        sendBroadcast(offline_intent)
    }

    private fun sendAquiringEnrlCompletedBroadcast() {
        offline_intent.putExtra("Acquiring enrollments", "1")
        sendBroadcast(offline_intent)
    }

    private fun sendAquiringEnrlErrorBroadcast() {
        offline_intent.putExtra("Acquiring enrollments", "2")
        sendBroadcast(offline_intent)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        offlinedb = DatabaseOffline(baseContext)
        pref = applicationContext.getSharedPreferences(Constants.UserPref, Context.MODE_PRIVATE)
        editor = pref.edit()
        val thread = object : Thread() {
            override fun run() {
                get_Collectionlist()
            }
        }

        thread.start()

        return START_STICKY
    }

    fun get_Collectionlist() {
        sendAquringCustBroadcast()
        var resultlist = java.util.ArrayList<collectioncustomermodel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        pref.getString(Constants.tenantid, "")?.let { loginparameters.put("tenant_id", it) }
        loginparameters.put("employee_id", pref.getInt(Constants.employee_id, 0).toString())
        val LeadListRequest = getleadslist.show_collection_customers(loginparameters)
        pref.getString(Constants.db,"")?.let { loginparameters.put("db", it) }
        LeadListRequest.enqueue(object : Callback<ArrayList<collectioncustomermodel>> {

            override fun onFailure(call: Call<ArrayList<collectioncustomermodel>>, t: Throwable) {
                sendAquiringCustErrorBroadcast()
                t.printStackTrace()

            }

            override fun onResponse(
                call: Call<ArrayList<collectioncustomermodel>>, response: Response<ArrayList<collectioncustomermodel>>
            ) {
                when {
                    response.isSuccessful -> {
                        sendAquiringCustCompletedBroadcast()
                        when {
                            response.code().equals(200) -> {
                                resultlist = response.body()!!
                                System.out.println("recycler ${response.body()}")
                                if (resultlist.size > 0) {
                                    offlinedb.deleteCustomersTable()
                                    offlinedb.addOfflineCustomerList(resultlist)
                                    offlinedb.deleteGroupDetailsTable()
                                    offlinedb.deleteInstallmentsDetailsTable()
                                    for (i in resultlist) {
                                        get_groups(i.getCustomerId().toString())
                                    }
                                } else {
                                }
                            }
                        }
                    }
                    else -> {
                        sendAquiringCustErrorBroadcast()
                    }
                }
            }
        })
    }

    private fun get_groups(custid: String) {

        var resultlist = java.util.ArrayList<GroupdetailsModel>()
        val getleadslist = RetrofitBuilder.buildservice(ICallService::class.java)
        val loginparameters = HashMap<String, String>()
        pref.getString(Constants.tenantid, "")?.let { loginparameters.put("tenant_id", it) }
        loginparameters.put("customer_id", custid)
        pref.getString(Constants.db,"")?.let { loginparameters.put("db", it) }
        println(custid)
        val LeadListRequest = getleadslist.get_enrollments(loginparameters)
        LeadListRequest.enqueue(object : Callback<ArrayList<GroupdetailsModel>> {
            override fun onFailure(call: Call<ArrayList<GroupdetailsModel>>, t: Throwable) {

                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<GroupdetailsModel>>, response: Response<ArrayList<GroupdetailsModel>>
            ) {

                when {
                    response.isSuccessful -> {

                        when {
                            response.code().equals(200) -> {
                                resultlist = response.body()!!

                                System.out.println("recycler ${response.body()}")
                                if (resultlist.size > 0) {
                                    offlinedb.addGroupDetails(resultlist)
                                    for (i in resultlist.indices) {
                                        val installlist = response.body()!!.get(i).getInstallmentsDet()!!
                                        if (!installlist.isNullOrEmpty()) {
                                            offlinedb.addInstallmentDetails(installlist)
                                        }
                                    }

                                } else {
                                }
                            }
                        }
                    }
                }
            }
        })
    }
}
