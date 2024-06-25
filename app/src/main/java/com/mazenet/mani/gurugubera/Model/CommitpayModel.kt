package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CommitpayModel {
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("customer_code")
    @Expose
    private var customerCode: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("profile_photo")
    @Expose
    private var profilePhoto: String? = null
    @SerializedName("group_details")
    @Expose
    private var groupDetails: ArrayList<GroupDetail>? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param phoneNo
     * @param customerName
     * @param groupDetails
     * @param customerCode
     * @param profilePhoto
     * @param mobileNo
     */
    fun CommitpayModel(
        customerName: String,
        customerCode: String,
        phoneNo: String,
        mobileNo: String,
        profilePhoto: String,
        groupDetails: ArrayList<GroupDetail>
    ) {
       
        this.customerName = customerName
        this.customerCode = customerCode
        this.phoneNo = phoneNo
        this.mobileNo = mobileNo
        this.profilePhoto = profilePhoto
        this.groupDetails = groupDetails
    }

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String) {
        this.customerName = customerName
    }

    fun getCustomerCode(): String? {
        return customerCode
    }

    fun setCustomerCode(customerCode: String) {
        this.customerCode = customerCode
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

    fun getGroupDetails(): ArrayList<GroupDetail>? {
        return groupDetails
    }

    fun setGroupDetails(groupDetails: ArrayList<GroupDetail>) {
        this.groupDetails = groupDetails
    }
}