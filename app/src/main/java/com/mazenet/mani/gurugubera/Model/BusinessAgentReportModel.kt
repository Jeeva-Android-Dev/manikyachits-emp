package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BusinessAgentReportModel {
    @SerializedName("agent_id")
    @Expose
    private var agentId: String? = null
    @SerializedName("agent_name")
    @Expose
    private var agentName: String? = null
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
     * @param totalCommissionAmount
     * @param paidCommissionAmount
     * @param agentName
     * @param agentId
     * @param pendingCommissionAmount
     */
    fun BusinessAgentReportModel(
        agentId: String,
        agentName: String,
        totalCommissionAmount: String,
        paidCommissionAmount: String,
        pendingCommissionAmount: String
    ) {

        this.agentId = agentId
        this.agentName = agentName
        this.totalCommissionAmount = totalCommissionAmount
        this.paidCommissionAmount = paidCommissionAmount
        this.pendingCommissionAmount = pendingCommissionAmount
    }

    fun getAgentId(): String? {
        return agentId
    }

    fun setAgentId(agentId: String) {
        this.agentId = agentId
    }

    fun getAgentName(): String? {
        return agentName
    }

    fun setAgentName(agentName: String) {
        this.agentName = agentName
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