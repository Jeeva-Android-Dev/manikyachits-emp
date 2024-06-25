package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class EnrolledDetail {
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("total_received")
    @Expose
    private var totalReceived: String? = null
    @SerializedName("pending_amount")
    @Expose
    private var pendingAmount: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param groupName
     * @param pendingAmount
     * @param totalReceived
     */
    fun EnrolledDetail(groupName: String, totalReceived: String, pendingAmount: String){

        this.groupName = groupName
        this.totalReceived = totalReceived
        this.pendingAmount = pendingAmount
    }

    fun getGroupName(): String? {
        return groupName
    }

    fun setGroupName(groupName: String) {
        this.groupName = groupName
    }

    fun getTotalReceived(): String? {
        return totalReceived
    }

    fun setTotalReceived(totalReceived: String) {
        this.totalReceived = totalReceived
    }

    fun getPendingAmount(): String? {
        return pendingAmount
    }

    fun setPendingAmount(pendingAmount: String) {
        this.pendingAmount = pendingAmount
    }
}