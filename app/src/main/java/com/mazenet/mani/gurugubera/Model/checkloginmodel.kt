package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class checkloginmodel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("user_id")
    @Expose
    private var userId: Int? = null
    @SerializedName("device_primary_id")
    @Expose
    private var devicePrimaryId: Int? = null
    @SerializedName("otp")
    @Expose
    private var otp: Int? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("db")
    @Expose
    private var db: String? = null
    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param status
     * @param userId
     * @param devicePrimaryId
     * @param otp
     */
    fun Checkloginmodel(status: String, userId: Int?, devicePrimaryId: Int?, otp: Int?,msg: String,db:String?) {

        this.status = status
        this.userId = userId
        this.devicePrimaryId = devicePrimaryId
        this.otp = otp
        this.msg = msg
        this.db = db
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    fun getDevicePrimaryId(): Int? {
        return devicePrimaryId
    }

    fun setDevicePrimaryId(devicePrimaryId: Int?) {
        this.devicePrimaryId = devicePrimaryId
    }

    fun getOtp(): Int? {
        return otp
    }

    fun setOtp(otp: Int?) {
        this.otp = otp
    }
    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String?) {
        this.msg = msg
    }

    fun getDb(): String? {
        return db
    }

    fun setDb(db: String?) {
        this.db = db
    }
}