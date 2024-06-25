package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AuctionReportModel {
    @SerializedName("bid_primary_id")
    @Expose
    private var bidPrimaryId: String? = null
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
    @SerializedName("total_release_amount")
    @Expose
    private var totalReleaseAmount: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param customerName
     * @param groupId
     * @param bidPrimaryId
     * @param ticketNo
     * @param groupName
     * @param customerId
     * @param totalReleaseAmount
     */
    fun AuctionReportModel(
        bidPrimaryId: String,
        customerId: String,
        customerName: String,
        groupId: String,
        groupName: String,
        ticketNo: String,
        totalReleaseAmount: String
    ) {

        this.bidPrimaryId = bidPrimaryId
        this.customerId = customerId
        this.customerName = customerName
        this.groupId = groupId
        this.groupName = groupName
        this.ticketNo = ticketNo
        this.totalReleaseAmount = totalReleaseAmount
    }

    fun getBidPrimaryId(): String? {
        return bidPrimaryId
    }

    fun setBidPrimaryId(bidPrimaryId: String) {
        this.bidPrimaryId = bidPrimaryId
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

    fun getTotalReleaseAmount(): String? {
        return totalReleaseAmount
    }

    fun setTotalReleaseAmount(totalReleaseAmount: String) {
        this.totalReleaseAmount = totalReleaseAmount
    }

}