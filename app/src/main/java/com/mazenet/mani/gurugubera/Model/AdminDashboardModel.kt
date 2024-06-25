package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AdminDashboardModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("target")
    @Expose
    private var target: String? = null
    @SerializedName("Acheived")
    @Expose
    private var acheived: String? = null
    @SerializedName("Total Devices")
    @Expose
    private var totalDevices: String? = null
    @SerializedName("Active Devices")
    @Expose
    private var activeDevices: String? = null
    @SerializedName("DeActive Devices")
    @Expose
    private var deActiveDevices: String? = null
    @SerializedName("dayclosing_cash")
    @Expose
    private var dayclosingCash: String? = null
    @SerializedName("dayclosing_cheque")
    @Expose
    private var dayclosingCheque: String? = null
    @SerializedName("dayclosing_dd")
    @Expose
    private var dayclosingDd: String? = null
    @SerializedName("dayclosing_rtgs")
    @Expose
    private var dayclosingRtgs: String? = null
    @SerializedName("dayclosing_card")
    @Expose
    private var dayclosingCard: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param dayclosingDd
     * @param dayclosingCash
     * @param activeDevices
     * @param dayclosingCheque
     * @param dayclosingCard
     * @param dayclosingRtgs
     * @param status
     * @param target
     * @param acheived
     * @param msg
     * @param totalDevices
     * @param deActiveDevices
     */
    fun AdminDashboardModel(
        status: String,
        target: String,
        acheived: String,
        totalDevices: String,
        activeDevices: String,
        deActiveDevices: String,
        dayclosingCash: String,
        dayclosingCheque: String,
        dayclosingDd: String,
        dayclosingRtgs: String,
        dayclosingCard: String,
        msg: String
    ) {

        this.status = status
        this.target = target
        this.acheived = acheived
        this.totalDevices = totalDevices
        this.activeDevices = activeDevices
        this.deActiveDevices = deActiveDevices
        this.dayclosingCash = dayclosingCash
        this.dayclosingCheque = dayclosingCheque
        this.dayclosingDd = dayclosingDd
        this.dayclosingRtgs = dayclosingRtgs
        this.dayclosingCard = dayclosingCard
        this.msg = msg
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
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

    fun getTotalDevices(): String? {
        return totalDevices
    }

    fun setTotalDevices(totalDevices: String) {
        this.totalDevices = totalDevices
    }

    fun getActiveDevices(): String? {
        return activeDevices
    }

    fun setActiveDevices(activeDevices: String) {
        this.activeDevices = activeDevices
    }

    fun getDeActiveDevices(): String? {
        return deActiveDevices
    }

    fun setDeActiveDevices(deActiveDevices: String) {
        this.deActiveDevices = deActiveDevices
    }

    fun getDayclosingCash(): String? {
        return dayclosingCash
    }

    fun setDayclosingCash(dayclosingCash: String) {
        this.dayclosingCash = dayclosingCash
    }

    fun getDayclosingCheque(): String? {
        return dayclosingCheque
    }

    fun setDayclosingCheque(dayclosingCheque: String) {
        this.dayclosingCheque = dayclosingCheque
    }

    fun getDayclosingDd(): String? {
        return dayclosingDd
    }

    fun setDayclosingDd(dayclosingDd: String) {
        this.dayclosingDd = dayclosingDd
    }

    fun getDayclosingRtgs(): String? {
        return dayclosingRtgs
    }

    fun setDayclosingRtgs(dayclosingRtgs: String) {
        this.dayclosingRtgs = dayclosingRtgs
    }

    fun getDayclosingCard(): String? {
        return dayclosingCard
    }

    fun setDayclosingCard(dayclosingCard: String) {
        this.dayclosingCard = dayclosingCard
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }
}