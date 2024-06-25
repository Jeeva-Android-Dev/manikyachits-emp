package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PaymentDetailModel {
    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("no_of_members")
    @Expose
    private var noOfMembers: String? = null
    @SerializedName("chit_value")
    @Expose
    private var chitValue: String? = null
    @SerializedName("no_of_months")
    @Expose
    private var noOfMonths: String? = null
    @SerializedName("auction_details")
    @Expose
    private var auctionDetails: ArrayList<AuctionDetail>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    /**
     *
     * @param groupId
     * @param groupName
     * @param chitValue
     * @param noOfMembers
     * @param auctionDetails
     * @param noOfMonths
     */
    fun PaymentDetailModel(
        groupId: String,
        groupName: String,
        noOfMembers: String,
        chitValue: String,
        noOfMonths: String,
        auctionDetails: ArrayList<AuctionDetail>
    ) {

        this.groupId = groupId
        this.groupName = groupName
        this.noOfMembers = noOfMembers
        this.chitValue = chitValue
        this.noOfMonths = noOfMonths
        this.auctionDetails = auctionDetails
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

    fun getNoOfMembers(): String? {
        return noOfMembers
    }

    fun setNoOfMembers(noOfMembers: String) {
        this.noOfMembers = noOfMembers
    }

    fun getChitValue(): String? {
        return chitValue
    }

    fun setChitValue(chitValue: String) {
        this.chitValue = chitValue
    }

    fun getNoOfMonths(): String? {
        return noOfMonths
    }

    fun setNoOfMonths(noOfMonths: String) {
        this.noOfMonths = noOfMonths
    }

    fun getAuctionDetails(): ArrayList<AuctionDetail>? {
        return auctionDetails
    }

    fun setAuctionDetails(auctionDetails: ArrayList<AuctionDetail>) {
        this.auctionDetails = auctionDetails
    }
}