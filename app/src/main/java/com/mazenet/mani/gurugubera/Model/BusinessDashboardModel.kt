package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BusinessDashboardModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("target")
    @Expose
    private var target: String? = null
    @SerializedName("Acheived")
    @Expose
    private var acheived: String? = null
    @SerializedName("lead_generated")
    @Expose
    private var leadGenerated: String? = null
    @SerializedName("lead_enrolled")
    @Expose
    private var leadEnrolled: String? = null
    @SerializedName("total_vacanct")
    @Expose
    private var totalVacanct: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param totalVacanct
     * @param leadEnrolled
     * @param status
     * @param leadGenerated
     * @param target
     * @param acheived
     * @param msg
     */
    fun BusinessDashboardModel(
        status: String,
        msg: String,
        target: String,
        acheived: String,
        leadGenerated: String,
        leadEnrolled: String,
        totalVacanct: String
    ) {

        this.status = status
        this.msg = msg
        this.target = target
        this.acheived = acheived
        this.leadGenerated = leadGenerated
        this.leadEnrolled = leadEnrolled
        this.totalVacanct = totalVacanct
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun getTarget(): String? {
        return target
    }

    fun setTarget(target: String) {
        this.target = target
    }

    fun getAcheived(): String? {
        return acheived
    }

    fun setAcheived(acheived: String) {
        this.acheived = acheived
    }

    fun getLeadGenerated(): String? {
        return leadGenerated
    }

    fun setLeadGenerated(leadGenerated: String) {
        this.leadGenerated = leadGenerated
    }

    fun getLeadEnrolled(): String? {
        return leadEnrolled
    }

    fun setLeadEnrolled(leadEnrolled: String) {
        this.leadEnrolled = leadEnrolled
    }

    fun getTotalVacanct(): String? {
        return totalVacanct
    }

    fun setTotalVacanct(totalVacanct: String) {
        this.totalVacanct = totalVacanct
    }
}