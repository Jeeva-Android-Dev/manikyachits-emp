package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AuctionDetail {
    @SerializedName("bid_primary_id")
    @Expose
    private var bidPrimaryId: String? = null
    @SerializedName("auction_no")
    @Expose
    private var auctionNo: String? = null
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
    @SerializedName("bidding_amount")
    @Expose
    private var biddingAmount: String? = null
    @SerializedName("commission_amount")
    @Expose
    private var commissionAmount: String? = null
    @SerializedName("divident_amount")
    @Expose
    private var dividentAmount: String? = null
    @SerializedName("installment_amount")
    @Expose
    private var installmentAmount: String? = null
    @SerializedName("gst")
    @Expose
    private var gst: String? = null
    @SerializedName("cgst")
    @Expose
    private var cgst: String? = null
    @SerializedName("sgst")
    @Expose
    private var sgst: String? = null
    @SerializedName("customer_released")
    @Expose
    private var customerReleased: String? = null
    @SerializedName("pending_release")
    @Expose
    private var pendingRelease: String? = null
    @SerializedName("payment_status")
    @Expose
    private var payment_status: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
       /**
     *
     * @param customerName
     * @param bidPrimaryId
     * @param gst
     * @param groupName
     * @param biddingAmount
     * @param sgst
     * @param commissionAmount
     * @param customerReleased
     * @param groupId
     * @param ticketNo
     * @param dividentAmount
     * @param customerId
     * @param installmentAmount
     * @param cgst
     * @param auctionNo
     * @param pendingRelease
     * @param totalReleaseAmount
     */
    fun AuctionDetail(
        bidPrimaryId: String,
        auctionNo: String,
        customerId: String,
        customerName: String,
        groupId: String,
        groupName: String,
        ticketNo: String,
        totalReleaseAmount: String,
        biddingAmount: String,
        commissionAmount: String,
        dividentAmount: String,
        installmentAmount: String,
        gst: String,
        cgst: String,
        sgst: String,
        customerReleased: String,
        pendingRelease: String,
        payment_status:String
    ){

        this.bidPrimaryId = bidPrimaryId
        this.auctionNo = auctionNo
        this.customerId = customerId
        this.customerName = customerName
        this.groupId = groupId
        this.groupName = groupName
        this.ticketNo = ticketNo
        this.totalReleaseAmount = totalReleaseAmount
        this.biddingAmount = biddingAmount
        this.commissionAmount = commissionAmount
        this.dividentAmount = dividentAmount
        this.installmentAmount = installmentAmount
        this.gst = gst
        this.cgst = cgst
        this.sgst = sgst
        this.customerReleased = customerReleased
        this.pendingRelease = pendingRelease
        this.payment_status = payment_status
    }

    fun getBidPrimaryId(): String? {
        return bidPrimaryId
    }

    fun setBidPrimaryId(bidPrimaryId: String) {
        this.bidPrimaryId = bidPrimaryId
    }

    fun getAuctionNo(): String? {
        return auctionNo
    }

    fun setAuctionNo(auctionNo: String) {
        this.auctionNo = auctionNo
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

    fun getBiddingAmount(): String? {
        return biddingAmount
    }

    fun setBiddingAmount(biddingAmount: String) {
        this.biddingAmount = biddingAmount
    }

    fun getCommissionAmount(): String? {
        return commissionAmount
    }

    fun setCommissionAmount(commissionAmount: String) {
        this.commissionAmount = commissionAmount
    }

    fun getDividentAmount(): String? {
        return dividentAmount
    }

    fun setDividentAmount(dividentAmount: String) {
        this.dividentAmount = dividentAmount
    }

    fun getInstallmentAmount(): String? {
        return installmentAmount
    }

    fun setInstallmentAmount(installmentAmount: String) {
        this.installmentAmount = installmentAmount
    }

    fun getGst(): String? {
        return gst
    }

    fun setGst(gst: String) {
        this.gst = gst
    }

    fun getCgst(): String? {
        return cgst
    }

    fun setCgst(cgst: String) {
        this.cgst = cgst
    }

    fun getSgst(): String? {
        return sgst
    }

    fun setSgst(sgst: String) {
        this.sgst = sgst
    }

    fun getCustomerReleased(): String? {
        return customerReleased
    }

    fun setCustomerReleased(customerReleased: String) {
        this.customerReleased = customerReleased
    }

    fun getPendingRelease(): String? {
        return pendingRelease
    }

    fun setPendingRelease(pendingRelease: String) {
        this.pendingRelease = pendingRelease
    }
    fun getpayment_status(): String? {
        return payment_status
    }

    fun setpayment_status(payment_status: String) {
        this.payment_status = payment_status
    }
}