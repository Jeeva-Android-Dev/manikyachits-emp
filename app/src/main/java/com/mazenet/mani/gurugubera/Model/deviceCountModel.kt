package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class deviceCountModel {

    @SerializedName("Total Devices")
    @Expose
    private var totalDevices: Int? = null
    @SerializedName("Active Devices")
    @Expose
    private var activeDevices: Int? = null
    @SerializedName("DeActive Devices")
    @Expose
    private var deActiveDevices: Int? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param activeDevices
     * @param totalDevices
     * @param deActiveDevices
     */
    fun DeviceCountModel(totalDevices: Int?, activeDevices: Int?, deActiveDevices: Int?) {

        this.totalDevices = totalDevices
        this.activeDevices = activeDevices
        this.deActiveDevices = deActiveDevices
    }

    fun getTotalDevices(): Int? {
        return totalDevices
    }

    fun setTotalDevices(totalDevices: Int?) {
        this.totalDevices = totalDevices
    }

    fun getActiveDevices(): Int? {
        return activeDevices
    }

    fun setActiveDevices(activeDevices: Int?) {
        this.activeDevices = activeDevices
    }

    fun getDeActiveDevices(): Int? {
        return deActiveDevices
    }

    fun setDeActiveDevices(deActiveDevices: Int?) {
        this.deActiveDevices = deActiveDevices
    }

}