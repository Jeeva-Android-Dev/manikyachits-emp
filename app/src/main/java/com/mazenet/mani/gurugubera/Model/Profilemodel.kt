package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Profilemodel {
    @SerializedName("user_id")
    @Expose
    private var userId: Int? = null
    @SerializedName("user_name")
    @Expose
    private var userName: String? = null
    @SerializedName("device_name")
    @Expose
    private var deviceName: String? = null
    @SerializedName("profile_photo")
    @Expose
    private var profilePhoto: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param deviceName
     * @param userId
     * @param userName
     * @param profilePhoto
     */
    fun Profilemodel(userId: Int?, userName: String, deviceName: String, profilePhoto: String) {

        this.userId = userId
        this.userName = userName
        this.deviceName = deviceName
        this.profilePhoto = profilePhoto
    }

    fun getUserId(): Int? {
        return userId
    }

    fun setUserId(userId: Int?) {
        this.userId = userId
    }

    fun getUserName(): String? {
        return userName
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }

    fun getDeviceName(): String? {
        return deviceName
    }

    fun setDeviceName(deviceName: String) {
        this.deviceName = deviceName
    }

    fun getProfilePhoto(): String? {
        return profilePhoto
    }

    fun setProfilePhoto(profilePhoto: String) {
        this.profilePhoto = profilePhoto
    }
}