package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CustomerDetail {
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("ticket_no")
    @Expose
    private var ticketNo: String? = null
    @SerializedName("total_commission_amount")
    @Expose
    private var totalCommissionAmount: String? = null
    @SerializedName("paid_commission_amount")
    @Expose
    private var paidCommissionAmount: String? = null
    @SerializedName("pending_commission_amount")
    @Expose
    private var pendingCommissionAmount: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param customerName
     * @param groupId
     * @param totalCommissionAmount
     * @param ticketNo
     * @param groupName
     * @param customerId
     * @param paidCommissionAmount
     * @param pendingCommissionAmount
     */
    fun CustomerDetail(
        customerId: String,
        customerName: String,
        groupId: String,
        groupName: String,
        ticketNo: String,
        totalCommissionAmount: String,
        paidCommissionAmount: String,
        pendingCommissionAmount: String
    ){

        this.customerId = customerId
        this.customerName = customerName
        this.groupId = groupId
        this.groupName = groupName
        this.ticketNo = ticketNo
        this.totalCommissionAmount = totalCommissionAmount
        this.paidCommissionAmount = paidCommissionAmount
        this.pendingCommissionAmount = pendingCommissionAmount
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

    fun getGroupId(): String? {
        return groupId
    }

    fun setGroupId(groupId: String) {
        this.groupId = groupId
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

    fun getTotalCommissionAmount(): String? {
        return totalCommissionAmount
    }

    fun setTotalCommissionAmount(totalCommissionAmount: String) {
        this.totalCommissionAmount = totalCommissionAmount
    }

    fun getPaidCommissionAmount(): String? {
        return paidCommissionAmount
    }

    fun setPaidCommissionAmount(paidCommissionAmount: String) {
        this.paidCommissionAmount = paidCommissionAmount
    }

    fun getPendingCommissionAmount(): String? {
        return pendingCommissionAmount
    }

    fun setPendingCommissionAmount(pendingCommissionAmount: String) {
        this.pendingCommissionAmount = pendingCommissionAmount
    }
}