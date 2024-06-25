package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GeneratedReceiptModel {
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
    @SerializedName("receipt_type")
    @Expose
    private var Receipt_type: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param customerName
     * @param paymentMode
     * @param receiptDate
     * @param customerId
     * @param receiptNo
     * @param receivedAmount
     */
    fun GeneratedReceiptModel(
        customerId: String,
        customerName: String,
        receiptNo: String,
        receiptDate: String,
        receivedAmount: String,
        paymentMode: String,
        Receipt_type:String
    ) {

        this.customerId = customerId
        this.customerName = customerName
        this.receiptNo = receiptNo
        this.receiptDate = receiptDate
        this.receivedAmount = receivedAmount
        this.paymentMode = paymentMode
        this.Receipt_type = Receipt_type
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
    fun getReceipt_type(): String? {
        return Receipt_type
    }

    fun setReceipt_typee(Receipt_type: String) {
        this.Receipt_type = Receipt_type
    }
}