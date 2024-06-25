package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ComtmntPaymentModel {
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("enrollment_id")
    @Expose
    private var enrollmentId: String? = null
    @SerializedName("due_amount")
    @Expose
    private var dueAmount: String? = null
    @SerializedName("payment_type_name")
    @Expose
    private var payment_type_name: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param customerName
     * @param enrollmentId
     * @param customerId
     * @param dueAmount
     */
    fun ComtmntPaymentModel(customerId: String, customerName: String, enrollmentId: String, dueAmount: String,payment_type_name:String){

        this.customerId = customerId
        this.customerName = customerName
        this.enrollmentId = enrollmentId
        this.dueAmount = dueAmount
        this.payment_type_name = payment_type_name
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

    fun getEnrollmentId(): String? {
        return enrollmentId
    }

    fun setEnrollmentId(enrollmentId: String) {
        this.enrollmentId = enrollmentId
    }

    fun getDueAmount(): String? {
        return dueAmount
    }

    fun setDueAmount(dueAmount: String) {
        this.dueAmount = dueAmount
    }
    fun getpayment_type_name(): String? {
        return payment_type_name
    }

    fun setpayment_type_name(payment_type_name: String) {
        this.payment_type_name = payment_type_name
    }
}