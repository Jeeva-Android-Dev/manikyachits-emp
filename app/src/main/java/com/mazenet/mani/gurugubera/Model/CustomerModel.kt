package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CustomerModel {
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param phoneNo
     * @param customerName
     * @param tenantId
     * @param groupId
     * @param customerId
     * @param branchId
     * @param mobileNo
     */
    fun CustomerModel(
        tenantId: String,
        branchId: String,
        groupId: String,
        customerId: String,
        customerName: String,
        mobileNo: String,
        phoneNo: String
    ) {

        this.tenantId = tenantId
        this.branchId = branchId
        this.groupId = groupId
        this.customerId = customerId
        this.customerName = customerName
        this.mobileNo = mobileNo
        this.phoneNo = phoneNo
    }

    fun getTenantId(): String? {
        return tenantId
    }

    fun setTenantId(tenantId: String) {
        this.tenantId = tenantId
    }

    fun getBranchId(): String? {
        return branchId
    }

    fun setBranchId(branchId: String) {
        this.branchId = branchId
    }

    fun getGroupId(): String? {
        return groupId
    }

    fun setGroupId(groupId: String) {
        this.groupId = groupId
    }

    fun getCustomerId(): String? {
        return customerId
    }

    fun setCustomerId(customerId: String) {
        this.customerId = customerId
    }

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String) {
        this.customerName = customerName
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String) {
        this.mobileNo = mobileNo
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun setPhoneNo(phoneNo: String) {
        this.phoneNo = phoneNo
    }
    override fun toString(): String {
        return this.customerName.toString()
    }
}