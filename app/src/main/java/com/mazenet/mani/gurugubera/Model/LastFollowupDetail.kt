package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class LastFollowupDetail {
    @SerializedName("followup_status_type_name")
    @Expose
    private var followupStatusTypeName: String? = null
    @SerializedName("followup_date")
    @Expose
    private var followupDate: String? = null
    @SerializedName("followup_remarks")
    @Expose
    private var followupRemarks: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param followupDate
     * @param followupRemarks
     * @param followupStatusTypeName
     */
    fun LastFollowupDetail(followupStatusTypeName: String, followupDate: String, followupRemarks: String) {

        this.followupStatusTypeName = followupStatusTypeName
        this.followupDate = followupDate
        this.followupRemarks = followupRemarks
    }

    fun getFollowupStatusTypeName(): String? {
        return followupStatusTypeName
    }

    fun setFollowupStatusTypeName(followupStatusTypeName: String) {
        this.followupStatusTypeName = followupStatusTypeName
    }

    fun getFollowupDate(): String? {
        return followupDate
    }

    fun setFollowupDate(followupDate: String) {
        this.followupDate = followupDate
    }

    fun getFollowupRemarks(): String? {
        return followupRemarks
    }

    fun setFollowupRemarks(followupRemarks: String) {
        this.followupRemarks = followupRemarks
    }
}