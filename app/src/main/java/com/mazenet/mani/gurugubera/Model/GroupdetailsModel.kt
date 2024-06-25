package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GroupdetailsModel {
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("customer_code")
    @Expose
    private var customerCode: String? = null
    @SerializedName("customer_mobile_no")
    @Expose
    private var customerMobileNo: String? = null
    @SerializedName("customer_phone_no")
    @Expose
    private var customerPhoneNo: String? = null
    @SerializedName("entrollment_id")
    @Expose
    private var entrollmentId: String? = null
    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("chit_value")
    @Expose
    private var chitValue: String? = null
    @SerializedName("auction_date")
    @Expose
    private var auctionDate: String? = null
    @SerializedName("ticket_no")
    @Expose
    private var ticketNo: String? = null
    @SerializedName("install_amount")
    @Expose
    private var installAmount: String? = null
    @SerializedName("paid_amount")
    @Expose
    private var paidAmount: String? = null
    @SerializedName("pending_amount")
    @Expose
    private var pendingAmount: String? = null
    @SerializedName("advance_amount")
    @Expose
    private var advanceAmount: String? = null
    @SerializedName("pending_days")
    @Expose
    private var pendingDays: String? = null
    @SerializedName("bonus")
    @Expose
    private var bonus: String? = null
    @SerializedName("discountonpenalty")
    @Expose
    private var discountonpenalty: String? = null
    @SerializedName("discountondue")
    @Expose
    private var discountondue: String? = null
    @SerializedName("prized_status")
    @Expose
    private var prizedStatus: String? = null

    @SerializedName("due_no")
    @Expose
    private var due_no: String? = null

 @SerializedName("pending_due_no")
    @Expose
    private var pending_due_no: String? = null



    @SerializedName("installments_det")
    @Expose
    private var installmentsDet: ArrayList<InstallmentsDet>? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param prizedStatus
     * @param groupName
     * @param installmentsDet
     * @param pendingDays
     * @param customerPhoneNo
     * @param discountondue
     * @param discountonpenalty
     * @param groupId
     * @param ticketNo
     * @param customerId
     * @param chitValue
     * @param customerCode
     * @param entrollmentId
     * @param installAmount
     * @param paidAmount
     * @param bonus
     * @param pendingAmount
     * @param auctionDate
     * @param advanceAmount
     * @param customerMobileNo
     */
    fun GroupdetailsModel(
        customerId: String,
        customerCode: String,
        customerMobileNo: String,
        customerPhoneNo: String,
        entrollmentId: String,
        groupId: String,
        groupName: String,
        chitValue: String,
        auctionDate: String,
        ticketNo: String,
        installAmount: String,
        paidAmount: String,
        pendingAmount: String,
        advanceAmount: String,
        pendingDays: String,
        bonus: String,
        discountonpenalty: String,
        discountondue: String,
        prizedStatus: String,
        pending_due_no: String,
        due_no: String,
        installmentsDet: ArrayList<InstallmentsDet>
    ){

        this.customerId = customerId
        this.customerCode = customerCode
        this.customerMobileNo = customerMobileNo
        this.customerPhoneNo = customerPhoneNo
        this.entrollmentId = entrollmentId
        this.groupId = groupId
        this.groupName = groupName
        this.chitValue = chitValue
        this.auctionDate = auctionDate
        this.ticketNo = ticketNo
        this.installAmount = installAmount
        this.paidAmount = paidAmount
        this.pendingAmount = pendingAmount
        this.advanceAmount = advanceAmount
        this.pendingDays = pendingDays
        this.bonus = bonus
        this.discountonpenalty = discountonpenalty
        this.discountondue = discountondue
        this.prizedStatus = prizedStatus
        this.pending_due_no = pending_due_no
        this.due_no = due_no
        this.installmentsDet = installmentsDet
    }

    fun getpending_due_no(): String? {
        return pending_due_no
    }

    fun setpending_due_no(pending_due_no: String) {
        this.pending_due_no = pending_due_no
    }

    fun getdue_no(): String? {
        return due_no
    }

    fun setdue_no(due_no: String) {
        this.due_no = due_no
    }

    fun getCustomerId(): String? {
        return customerId
    }

    fun setCustomerId(customerId: String) {
        this.customerId = customerId
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

    fun getEntrollmentId(): String? {
        return entrollmentId
    }

    fun setEntrollmentId(entrollmentId: String) {
        this.entrollmentId = entrollmentId
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

    fun getChitValue(): String? {
        return chitValue
    }

    fun setChitValue(chitValue: String) {
        this.chitValue = chitValue
    }

    fun getAuctionDate(): String? {
        return auctionDate
    }

    fun setAuctionDate(auctionDate: String) {
        this.auctionDate = auctionDate
    }

    fun getTicketNo(): String? {
        return ticketNo
    }

    fun setTicketNo(ticketNo: String) {
        this.ticketNo = ticketNo
    }

    fun getInstallAmount(): String? {
        return installAmount
    }

    fun setInstallAmount(installAmount: String) {
        this.installAmount = installAmount
    }

    fun getPaidAmount(): String? {
        return paidAmount
    }

    fun setPaidAmount(paidAmount: String) {
        this.paidAmount = paidAmount
    }

    fun getPendingAmount(): String? {
        return pendingAmount
    }

    fun setPendingAmount(pendingAmount: String) {
        this.pendingAmount = pendingAmount
    }

    fun getAdvanceAmount(): String? {
        return advanceAmount
    }

    fun setAdvanceAmount(advanceAmount: String) {
        this.advanceAmount = advanceAmount
    }

    fun getPendingDays(): String? {
        return pendingDays
    }

    fun setPendingDays(pendingDays: String) {
        this.pendingDays = pendingDays
    }

    fun getBonus(): String? {
        return bonus
    }

    fun setBonus(bonus: String) {
        this.bonus = bonus
    }

    fun getDiscountonpenalty(): String? {
        return discountonpenalty
    }

    fun setDiscountonpenalty(discountonpenalty: String) {
        this.discountonpenalty = discountonpenalty
    }

    fun getDiscountondue(): String? {
        return discountondue
    }

    fun setDiscountondue(discountondue: String) {
        this.discountondue = discountondue
    }

    fun getPrizedStatus(): String? {
        return prizedStatus
    }

    fun setPrizedStatus(prizedStatus: String) {
        this.prizedStatus = prizedStatus
    }

    fun getInstallmentsDet(): ArrayList<InstallmentsDet>? {
        return installmentsDet
    }

    fun setInstallmentsDet(installmentsDet: ArrayList<InstallmentsDet>) {
        this.installmentsDet = installmentsDet
    }

}