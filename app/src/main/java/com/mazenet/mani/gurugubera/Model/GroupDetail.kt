package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GroupDetail {
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("ticket_no")
    @Expose
    private var ticketNo: String? = null
    @SerializedName("chit_value")
    @Expose
    private var chitValue: String? = null
    @SerializedName("payment_paid")
    @Expose
    private var paymentPaid: String? = null
    @SerializedName("payment_pending")
    @Expose
    private var paymentPending: String? = null
    @SerializedName("total_payment")
    @Expose
    private var totalPayment: String? = null
    @SerializedName("payment_status")
    @Expose
    private var payment_status: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param ticketNo
     * @param groupName
     * @param chitValue
     * @param paymentPaid
     * @param paymentPending
     * @param totalPayment
     */
    fun GroupDetail(
        groupName: String,
        ticketNo: String,
        chitValue: String,
        paymentPaid: String,
        paymentPending: String,
        totalPayment: String,
        payment_status: String
    ) {

        this.groupName = groupName
        this.ticketNo = ticketNo
        this.chitValue = chitValue
        this.paymentPaid = paymentPaid
        this.paymentPending = paymentPending
        this.totalPayment = totalPayment
        this.payment_status = payment_status
    }

    fun getGroupName(): String? {
        return groupName
    }

    fun setGroupName(groupName: String) {
        this.groupName = groupName
    }

    fun getTicketNo(): String? {
        return ticketNo
    }

    fun setTicketNo(ticketNo: String) {
        this.ticketNo = ticketNo
    }

    fun getChitValue(): String? {
        return chitValue
    }

    fun setChitValue(chitValue: String) {
        this.chitValue = chitValue
    }

    fun getPaymentPaid(): String? {
        return paymentPaid
    }

    fun setPaymentPaid(paymentPaid: String) {
        this.paymentPaid = paymentPaid
    }

    fun getPaymentPending(): String? {
        return paymentPending
    }

    fun setPaymentPending(paymentPending: String) {
        this.paymentPending = paymentPending
    }

    fun getTotalPayment(): String? {
        return totalPayment
    }

    fun setTotalPayment(totalPayment: String) {
        this.totalPayment = totalPayment
    }
    fun getTpayment_status(): String? {
        return payment_status
    }

    fun setpayment_status(payment_status: String) {
        this.payment_status = payment_status
    }

}