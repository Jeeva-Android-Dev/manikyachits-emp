package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class OutstandingReportModel {
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("customer_code")
    @Expose
    private var customerCode: String? = null
    @SerializedName("customer_mobile_no")
    @Expose
    private var customerMobileNo: String? = null
    @SerializedName("customer_phone_no")
    @Expose
    private var customerPhoneNo: String? = null
    @SerializedName("employee_id")
    @Expose
    private var employeeId: String? = null
    @SerializedName("employee_name")
    @Expose
    private var employeeName: String? = null
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
     * @param employeeId
     * @param customerName
     * @param customerId
     * @param customerCode
     * @param customerPhoneNo
     * @param totalPaid
     * @param totalPending
     * @param employeeName
     * @param customerMobileNo
     */
    fun OutstandingReportModel(
        customerId: String,
        customerName: String,
        customerCode: String,
        customerMobileNo: String,
        customerPhoneNo: String,
        employeeId: String,
        employeeName: String,
        totalPaid: String,
        totalPending: String
    ) {

        this.customerId = customerId
        this.customerName = customerName
        this.customerCode = customerCode
        this.customerMobileNo = customerMobileNo
        this.customerPhoneNo = customerPhoneNo
        this.employeeId = employeeId
        this.employeeName = employeeName
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

    fun getCustomerMobileNo(): String? {
        return customerMobileNo
    }

    fun setCustomerMobileNo(customerMobileNo: String) {
        this.customerMobileNo = customerMobileNo
    }

    fun getCustomerPhoneNo(): String? {
        return customerPhoneNo
    }

    fun setCustomerPhoneNo(customerPhoneNo: String) {
        this.customerPhoneNo = customerPhoneNo
    }

    fun getEmployeeId(): String? {
        return employeeId
    }

    fun setEmployeeId(employeeId: String) {
        this.employeeId = employeeId
    }

    fun getEmployeeName(): String? {
        return employeeName
    }

    fun setEmployeeName(employeeName: String) {
        this.employeeName = employeeName
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
}