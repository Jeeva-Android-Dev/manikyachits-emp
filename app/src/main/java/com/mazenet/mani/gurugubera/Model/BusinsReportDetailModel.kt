package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BusinsReportDetailModel {
    @SerializedName("agent_id")
    @Expose
    private var agentId: String? = null
    @SerializedName("agent_name")
    @Expose
    private var agentName: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("profile_photo")
    @Expose
    private var profilePhoto: String? = null
    @SerializedName("customer_details")
    @Expose
    private var customerDetails: ArrayList<CustomerDetail>? = null

    /**
     * No args constructor for use in serialization
     *
     */
  

    /**
     *
     * @param phoneNo
     * @param agentName
     * @param customerDetails
     * @param agentId
     * @param profilePhoto
     * @param mobileNo
     */
    fun BusinsReportDetailModel(
        agentId: String,
        agentName: String,
        phoneNo: String,
        mobileNo: String,
        profilePhoto: String,
        customerDetails: ArrayList<CustomerDetail>
    ) {
       
        this.agentId = agentId
        this.agentName = agentName
        this.phoneNo = phoneNo
        this.mobileNo = mobileNo
        this.profilePhoto = profilePhoto
        this.customerDetails = customerDetails
    }

    fun getAgentId(): String? {
        return agentId
    }

    fun setAgentId(agentId: String) {
        this.agentId = agentId
    }

    fun getAgentName(): String? {
        return agentName
    }

    fun setAgentName(agentName: String) {
        this.agentName = agentName
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun setPhoneNo(phoneNo: String) {
        this.phoneNo = phoneNo
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String) {
        this.mobileNo = mobileNo
    }

    fun getProfilePhoto(): String? {
        return profilePhoto
    }

    fun setProfilePhoto(profilePhoto: String) {
        this.profilePhoto = profilePhoto
    }

    fun getCustomerDetails(): ArrayList<CustomerDetail>? {
        return customerDetails
    }

    fun setCustomerDetails(customerDetails: ArrayList<CustomerDetail>) {
        this.customerDetails = customerDetails
    }
}