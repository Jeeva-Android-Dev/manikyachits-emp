package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class collectioncustomermodel {
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("customer_code")
    @Expose
    private var customerCode: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("total_installment")
    @Expose
    private var totalInstallment: String? = null
    @SerializedName("total_paid")
    @Expose
    private var totalPaid: String? = null
    @SerializedName("total_pending")
    @Expose
    private var totalPending: String? = null



    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param phoneNo
     * @param customerName
     * @param customerId
     * @param customerCode
     * @param totalInstallment
     * @param totalPaid
     * @param totalPending
     * @param mobileNo
     * @param entrolmentId
     */
    fun Collectioncustomermodel(
        customerId: String,
        customerName: String,
        customerCode: String,
        mobileNo: String,
        phoneNo: String,
        totalInstallment: String,
        totalPaid: String,
        totalPending: String
    ) {

        this.customerId = customerId
        this.customerName = customerName
        this.customerCode = customerCode
        this.mobileNo = mobileNo
        this.phoneNo = phoneNo
        this.totalInstallment = totalInstallment
        this.totalPaid = totalPaid
        this.totalPending = totalPending
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

    fun getCustomerCode(): String? {
        return customerCode
    }

    fun setCustomerCode(customerCode: String) {
        this.customerCode = customerCode
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

    fun getTotalInstallment(): String? {
        return totalInstallment
    }

    fun setTotalInstallment(totalInstallment: String) {
        this.totalInstallment = totalInstallment
    }

    fun getTotalPaid(): String? {
        return totalPaid
    }

    fun setTotalPaid(totalPaid: String) {
        this.totalPaid = totalPaid
    }

    fun getTotalPending(): String? {
        return totalPending
    }

    fun setTotalPending(totalPending: String) {
        this.totalPending = totalPending
    }



    override fun toString(): String {
        return "collectioncustomermodel(customerId=$customerId, customerName=$customerName, customerCode=$customerCode, mobileNo=$mobileNo, phoneNo=$phoneNo, totalInstallment=$totalInstallment, totalPaid=$totalPaid, totalPending=$totalPending)"
    }


}