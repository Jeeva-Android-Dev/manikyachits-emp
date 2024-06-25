package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class DeviceUserListModel {
    @SerializedName("user_id")
    @Expose
    private var userId: Int? = null
    @SerializedName("device_name")
    @Expose
    private var deviceName: String? = null
    @SerializedName("user_name")
    @Expose
    private var userName: String? = null
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
     * @param userId
     * @param userName
     */
    fun DeviceUserListModel(userId: Int?, deviceName: String, userName: String, branchId: Int?, isActive: String) {

        this.userId = userId
        this.deviceName = deviceName
        this.userName = userName
        this.branchId = branchId
        this.isActive = isActive
    }

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    fun getDeviceName(): String? {
        return deviceName
    }

    fun setDeviceName(deviceName: String) {
        this.deviceName = deviceName
    }

    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String) {
        this.userName = userName
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