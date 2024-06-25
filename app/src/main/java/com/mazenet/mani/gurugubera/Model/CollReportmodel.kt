package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CollReportmodel {
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("employee_name")
    @Expose
    private var employeeName: String? = null
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("receipt_no")
    @Expose
    private var receiptNo: String? = null
    @SerializedName("receipt_date")
    @Expose
    private var receiptDate: String? = null
    @SerializedName("received_amount")
    @Expose
    private var receivedAmount: String? = null
    @SerializedName("payment_mode")
    @Expose
    private var paymentMode: String? = null
    @SerializedName("cheque_details")
    @Expose
    private var chequeDetails: String? = null
    @SerializedName("receipt_type")
    @Expose
    private var receiptType: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param customerName
     * @param tenantId
     * @param paymentMode
     * @param receiptDate
     * @param customerId
     * @param receiptType
     * @param receiptNo
     * @param branchId
     * @param chequeDetails
     * @param receivedAmount
     * @param employeeName
     */
    fun CollReportmodel(
        tenantId: String,
        branchId: String,
        employeeName: String,
        customerId: String,
        customerName: String,
        receiptNo: String,
        receiptDate: String,
        receivedAmount: String,
        paymentMode: String,
        chequeDetails: String,
        receiptType: String
    ) {

        this.tenantId = tenantId
        this.branchId = branchId
        this.employeeName = employeeName
        this.customerId = customerId
        this.customerName = customerName
        this.receiptNo = receiptNo
        this.receiptDate = receiptDate
        this.receivedAmount = receivedAmount
        this.paymentMode = paymentMode
        this.chequeDetails = chequeDetails
        this.receiptType = receiptType
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

    fun getEmployeeName(): String? {
        return employeeName
    }

    fun setEmployeeName(employeeName: String) {
        this.employeeName = employeeName
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

    fun getReceiptNo(): String? {
        return receiptNo
    }

    fun setReceiptNo(receiptNo: String) {
        this.receiptNo = receiptNo
    }

    fun getReceiptDate(): String? {
        return receiptDate
    }

    fun setReceiptDate(receiptDate: String) {
        this.receiptDate = receiptDate
    }

    fun getReceivedAmount(): String? {
        return receivedAmount
    }

    fun setReceivedAmount(receivedAmount: String) {
        this.receivedAmount = receivedAmount
    }

    fun getPaymentMode(): String? {
        return paymentMode
    }

    fun setPaymentMode(paymentMode: String) {
        this.paymentMode = paymentMode
    }

    fun getChequeDetails(): String? {
        return chequeDetails
    }

    fun setChequeDetails(chequeDetails: String) {
        this.chequeDetails = chequeDetails
    }

    fun getReceiptType(): String? {
        return receiptType
    }

    fun setReceiptType(receiptType: String) {
        this.receiptType = receiptType
    }

}