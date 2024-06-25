package com.mazenet.mani.gurugubera.Retrofit

import com.mazenet.mani.gurugubera.Model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.QueryMap


interface ICallService {

    @POST("mobile-list-schemes")
    fun get_schemes(@QueryMap hashMap: java.util.HashMap<String, Any>): Call<Schemes_Response>

    @POST("mobile-store-customer")
    fun add_user(@QueryMap hashMap: java.util.HashMap<String, Any>): Call<successmsgmodel>


    @POST("check-login")
    fun check_login(@QueryMap hashMap: HashMap<String, String>): Call<checkloginmodel>

    @POST("mobile-add-lead-management")
    fun add_lead(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("profile")
    fun get_profile(@QueryMap hashMap: HashMap<String, String>): Call<Profilemodel>

    @POST("show-lead-details")
    fun get_lead_details(@QueryMap hashMap: HashMap<String, String>): Call<Leaddetailsmodel>

    @POST("reset-password")
    fun reset_password(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("update-lead-detail")
    fun add_followup(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("mobile-list-leads")
    fun show_leads(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<showleadmodel>>

    @POST("mobile-get-customer-wise-entrollments")
    fun get_enrollments(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<GroupdetailsModel>>

    @POST("entrollment-wise-group-details")
    fun get_individual_enrollment(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<GroupdetailsModel>>

    @POST("generated-receipt")
    fun get_generated_receipt(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<GeneratedReceiptModel>>

    @POST("update-device-status")
    fun update_device_status(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("update-device-login-status")
    fun update_device_users_status(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("printed-status-update")
    fun update_printed_status(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("add-cash-settlement")
    fun add_cash_settlement(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("store-remarks")
    fun store_remarks(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("mobile-add-receipt")
    fun add_receipt(@QueryMap hashMap: HashMap<String, String>): Call<Receiptsuccessmodel>

    @POST("mobile-store-advance-receipt")
    fun add_adv_receipt(@QueryMap hashMap: HashMap<String, String>): Call<Receiptsuccessmodel>

    @POST("store-customer-advance-receipt")
    fun before_enroll(@QueryMap hashMap: HashMap<String, String>): Call<Before_enroll_success_model>

    @POST("get-cash-in-hand")
    fun get_cashinhand(@QueryMap hashMap: HashMap<String, String>): Call<CashinhandModel>

    @POST("receipt-details")
    fun get_receipt_details(@QueryMap hashMap: HashMap<String, String>): Call<ReceiptDetailsModel>

    @POST("get-devices")
    fun show_devices(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<deviceListModel>>

    @POST("get-device-users")
    fun show_device_users(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<DeviceUserListModel>>

    @POST("mobile-get-collection-area-customers")
    fun show_collection_customers(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<collectioncustomermodel>>

    @POST("mobile-login")
    fun getdetails(@QueryMap hashMap: HashMap<String, String>): Call<loginmodel>

    @POST("otp-verified")
    fun verify_otp(@QueryMap hashMap: HashMap<String, String>): Call<otpverifymodel>

    @POST("get-admin-dashboard")
    fun get_admindashboard(@QueryMap hashMap: HashMap<String, String>): Call<AdminDashboardModel>

    @POST("get-business-agent-dashboard")
    fun get_businessdashboard(@QueryMap hashMap: HashMap<String, String>): Call<BusinessDashboardModel>

    @FormUrlEncoded
    @POST("get-device-counts")
    fun get_device_count(@Field("tenant_id") tenant_id: String): Call<deviceCountModel>

    @POST("mobile-list-branches")
    fun get_branches(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<BranchModel>>

    @POST("mobile-list-states")
    fun get_states(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<statesModel>>

    @POST("mobile-list-districts")
    fun get_districts(@QueryMap hashMap: HashMap<String, Any>): Call<ArrayList<districtModel>>

    @POST("mobile-list-cities")
    fun get_cities(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<citymodel>>



    @POST("list-followup-status-types")
    fun get_followupstatusType(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<followstatustypeModel>>

    @POST("list-customer-bank-names")
    fun get_customer_banks(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<BanksListModel>>

    @POST("mobile-list-payment-types")
    fun get_paymentType(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<PaymentTypeModel>>

    @POST("get-collection-agent-dashboard")
    fun get_collectiondashboard(@QueryMap hashMap: HashMap<String, String>): Call<CollectiondashboardModel>

    @POST("list-collection-remarks")
    fun get_remarks(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<RemarksListmodel>>

    @POST("store-collection-remarks")
    fun add_nrew_remark(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("mobile-get-employee-attendance")
    fun get_employee_attendence(@QueryMap hashMap: HashMap<String, String>): Call<successmsgmodel>

    @POST("mobile-store-employee-attendance")
    fun add_employee_attendance(@QueryMap hashMap: HashMap<String, String>):Call<successmsgmodel>


    //----------------------------------------------------------------------------------------------

    @POST("mobile-list-employees")
    fun get_employeelist(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<EmployeeModel>>

    @POST("mobile-list-customerss")
    fun get_customerlist(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<CustomerModel>>

    @POST("mobile-list-groups")
    fun get_groupslist(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<GroupsModel>>

    @POST("collection-reports")
    fun get_collection_reports(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<CollReportmodel>>

    @POST("outstanding-reports")
    fun get_outs_reports(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<OutstandingReportModel>>

    @POST("commitment-payment-reports")
    fun get_comtpayment_reports(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<ComtmntPaymentModel>>

    @POST("auction-reports")
    fun get_auction_reports(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<AuctionReportModel>>

    @POST("business-agent-report")
    fun get_businessagent_reports(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<BusinessAgentReportModel>>

    @POST("mobile-vacant-report")
    fun get_vacanr_report(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<VacantChitReportModel>>

    @POST("payment-details")
    fun get_auction_details(@QueryMap hashMap: HashMap<String, String>): Call<PaymentDetailModel>

    @POST("mobile-day-closing-reports")
    fun get_dayclosing(@QueryMap hashMap: HashMap<String, String>): Call<DayclosingModel>

    @POST("commitment-payment-details")
    fun get_comtpayment_details(@QueryMap hashMap: HashMap<String, String>): Call<CommitpayModel>

    @POST("agent-wise-customer-details")
    fun get_businsreport_details(@QueryMap hashMap: HashMap<String, String>): Call<BusinsReportDetailModel>

    @POST("mobile-list-collection-area")
    fun get_collection_area(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<CollAreaListmodel>>

    @POST("feedback-reports")
    fun get_feedback_report(@QueryMap hashMap: HashMap<String, String>): Call<ArrayList<GeneratedReceiptModel>>

}