package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class followstatustypeModel {

    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("followup_status_id")
    @Expose
    private var followupStatusId: Int? = null
    @SerializedName("followup_status_name")
    @Expose
    private var followupStatusName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param sno
     * @param followupStatusName
     * @param followupStatusId
     */
    fun FollowstatustypeModel(sno: Int?, followupStatusId: Int?, followupStatusName: String) {

        this.sno = sno
        this.followupStatusId = followupStatusId
        this.followupStatusName = followupStatusName
    }

    fun getSno(): Int? {
        return sno
    }

    fun setSno(sno: Int?) {
        this.sno = sno
    }

    fun getFollowupStatusId(): Int? {
        return followupStatusId
    }

    fun setFollowupStatusId(followupStatusId: Int?) {
        this.followupStatusId = followupStatusId
    }

    fun getFollowupStatusName(): String? {
        return followupStatusName
    }

    fun setFollowupStatusName(followupStatusName: String) {
        this.followupStatusName = followupStatusName
    }
}