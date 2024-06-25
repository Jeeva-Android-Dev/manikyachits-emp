package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Leaddetailsmodel {
    @SerializedName("lead_name")
    @Expose
    private var leadName: String? = null
    @SerializedName("lead_mobile")
    @Expose
    private var leadMobile: String? = null
    @SerializedName("lead_phone")
    @Expose
    private var leadPhone: String? = null
    @SerializedName("lead_email")
    @Expose
    private var leadEmail: String? = null
    @SerializedName("lead_address_1")
    @Expose
    private var leadAddress1: String? = null
    @SerializedName("lead_address_2")
    @Expose
    private var leadAddress2: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: Int? = null
    @SerializedName("generated_on")
    @Expose
    private var generatedOn: String? = null
    @SerializedName("followup_status_type_id")
    @Expose
    private var followupStatusTypeId: Int? = null
    @SerializedName("next_followup_date")
    @Expose
    private var nextFollowupDate: String? = null
    @SerializedName("last_followup_details")
    @Expose
    private var lastFollowupDetails: ArrayList<LastFollowupDetail>? = null
    @SerializedName("enrolled_details")
    @Expose
    private var enrolledDetails: ArrayList<EnrolledDetail>? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param leadMobile
     * @param branchId
     * @param leadAddress2
     * @param enrolledDetails
     * @param leadAddress1
     * @param leadPhone
     * @param leadName
     * @param lastFollowupDetails
     * @param leadEmail
     * @param followupStatusTypeId
     * @param nextFollowupDate
     * @param generatedOn
     */
    fun Leaddetailsmodel(
        leadName: String,
        leadMobile: String,
        leadPhone: String,
        leadEmail: String,
        leadAddress1: String,
        leadAddress2: String,
        branchId: Int?,
        generatedOn: String,
        followupStatusTypeId: Int?,
        nextFollowupDate: String,
        lastFollowupDetails: ArrayList<LastFollowupDetail>,
        enrolledDetails: ArrayList<EnrolledDetail>
    ) {

        this.leadName = leadName
        this.leadMobile = leadMobile
        this.leadPhone = leadPhone
        this.leadEmail = leadEmail
        this.leadAddress1 = leadAddress1
        this.leadAddress2 = leadAddress2
        this.branchId = branchId
        this.generatedOn = generatedOn
        this.followupStatusTypeId = followupStatusTypeId
        this.nextFollowupDate = nextFollowupDate
        this.lastFollowupDetails = lastFollowupDetails
        this.enrolledDetails = enrolledDetails
    }

    fun getLeadName(): String? {
        return leadName
    }

    fun setLeadName(leadName: String) {
        this.leadName = leadName
    }

    fun getLeadMobile(): String? {
        return leadMobile
    }

    fun setLeadMobile(leadMobile: String) {
        this.leadMobile = leadMobile
    }

    fun getLeadPhone(): String? {
        return leadPhone
    }

    fun setLeadPhone(leadPhone: String) {
        this.leadPhone = leadPhone
    }

    fun getLeadEmail(): String? {
        return leadEmail
    }

    fun setLeadEmail(leadEmail: String) {
        this.leadEmail = leadEmail
    }

    fun getLeadAddress1(): String? {
        return leadAddress1
    }

    fun setLeadAddress1(leadAddress1: String) {
        this.leadAddress1 = leadAddress1
    }

    fun getLeadAddress2(): String? {
        return leadAddress2
    }

    fun setLeadAddress2(leadAddress2: String) {
        this.leadAddress2 = leadAddress2
    }

    fun getBranchId(): Int? {
        return branchId
    }

    fun setBranchId(branchId: Int?) {
        this.branchId = branchId
    }

    fun getGeneratedOn(): String? {
        return generatedOn
    }

    fun setGeneratedOn(generatedOn: String) {
        this.generatedOn = generatedOn
    }

    fun getFollowupStatusTypeId(): Int? {
        return followupStatusTypeId
    }

    fun setFollowupStatusTypeId(followupStatusTypeId: Int?) {
        this.followupStatusTypeId = followupStatusTypeId
    }

    fun getNextFollowupDate(): String? {
        return nextFollowupDate
    }

    fun setNextFollowupDate(nextFollowupDate: String) {
        this.nextFollowupDate = nextFollowupDate
    }

    fun getLastFollowupDetails(): ArrayList<LastFollowupDetail>? {
        return lastFollowupDetails
    }

    fun setLastFollowupDetails(lastFollowupDetails: ArrayList<LastFollowupDetail>) {
        this.lastFollowupDetails = lastFollowupDetails
    }

    fun getEnrolledDetails(): ArrayList<EnrolledDetail>? {
        return enrolledDetails
    }

    fun setEnrolledDetails(enrolledDetails: ArrayList<EnrolledDetail>) {
        this.enrolledDetails = enrolledDetails
    }

}