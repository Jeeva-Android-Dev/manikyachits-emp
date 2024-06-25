package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class deviceListModel {
    @SerializedName("device_id")
    @Expose
    private var deviceId: Int? = null
    @SerializedName("device_name")
    @Expose
    private var deviceName: String? = null
    @SerializedName("android_id")
    @Expose
    private var androidId: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: Int? = null
    @SerializedName("isActive")
    @Expose
    private var isActive: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param isActive
     * @param deviceName
     * @param branchId
     * @param androidId
     * @param deviceId
     */
    fun DeviceListModel(deviceId: Int?, deviceName: String, androidId: String, branchId: Int?, isActive: String) {
        this.deviceId = deviceId
        this.deviceName = deviceName
        this.androidId = androidId
        this.branchId = branchId
        this.isActive = isActive
    }

    fun getDeviceId(): Int? {
        return deviceId
    }

    fun setDeviceId(deviceId: Int?) {
        this.deviceId = deviceId
    }

    fun getDeviceName(): String? {
        return deviceName
    }

    fun setDeviceName(deviceName: String) {
        this.deviceName = deviceName
    }

    fun getAndroidId(): String? {
        return androidId
    }

    fun setAndroidId(androidId: String) {
        this.androidId = androidId
    }

    fun getBranchId(): Int? {
        return branchId
    }

    fun setBranchId(branchId: Int?) {
        this.branchId = branchId
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String) {
        this.isActive = isActive
    }

}