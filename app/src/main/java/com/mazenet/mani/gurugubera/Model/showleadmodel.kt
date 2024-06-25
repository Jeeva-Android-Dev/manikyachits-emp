package com.mazenet.mani.gurugubera.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class showleadmodel() : Parcelable {


    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("lead_id")
    @Expose
    private var leadId: Int? = null
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: Int? = null
    @SerializedName("lead_customer_name")
    @Expose
    private var leadCustomerName: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("lead_generated_on")
    @Expose
    private var leadGeneratedOn: String? = null
    @SerializedName("lead_last_followupdates")
    @Expose
    private var leadLastFollowupdates: String? = null
    @SerializedName("last_followup")
    @Expose
    private var lastFollowup: String? = null
    @SerializedName("next_followup_date")
    @Expose
    private var nextFollowupDate: String? = null
    @SerializedName("lead_last_followupstatus")
    @Expose
    private var leadLastFollowupstatus: String? = null
    @SerializedName("lead_entrolled")
    @Expose
    private var leadEntrolled: String? = null

    constructor(parcel: Parcel) : this() {
        sno = parcel.readValue(Int::class.java.classLoader) as? Int
        leadId = parcel.readValue(Int::class.java.classLoader) as? Int
        tenantId = parcel.readValue(Int::class.java.classLoader) as? Int
        leadCustomerName = parcel.readString()
        phoneNo = parcel.readString()
        mobileNo = parcel.readString()
        leadGeneratedOn = parcel.readString()
        leadLastFollowupdates = parcel.readString()
        lastFollowup = parcel.readString()
        nextFollowupDate = parcel.readString()
        leadLastFollowupstatus = parcel.readString()
        leadEntrolled = parcel.readString()
    }
//    val CREATOR: Parcelable.Creator<showleadmodel> = object : Parcelable.Creator<showleadmodel>() {
//
//
//        fun createFromParcel(`in`: Parcel): showleadmodel {
//            return Showleadmodel(`in`)
//        }
//
//        fun newArray(size: Int): Array<showleadmodel> {
//            return arrayOfNulls<showleadmodel>(size)
//        }
//
//    }

    protected fun Showleadmodel(`in`: Parcel) {
        this.sno = `in`.readValue(Int::class.java.classLoader) as Int
        this.leadId = `in`.readValue(Int::class.java.classLoader) as Int
        this.tenantId = `in`.readValue(Int::class.java.classLoader) as Int
        this.leadCustomerName = `in`.readValue(String::class.java.classLoader) as String
        this.phoneNo = `in`.readValue(String::class.java.classLoader) as String
        this.mobileNo = `in`.readValue(String::class.java.classLoader) as String
        this.leadGeneratedOn = `in`.readValue(String::class.java.classLoader) as String
        this.leadLastFollowupdates = `in`.readValue(String::class.java.classLoader) as String
        this.lastFollowup = `in`.readValue(String::class.java.classLoader) as String
        this.nextFollowupDate = `in`.readValue(String::class.java.classLoader) as String
        this.leadLastFollowupstatus = `in`.readValue(String::class.java.classLoader) as String
        this.leadEntrolled = `in`.readValue(String::class.java.classLoader) as String
    }

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param phoneNo
     * @param tenantId
     * @param leadGeneratedOn
     * @param sno
     * @param leadCustomerName
     * @param leadId
     * @param leadEntrolled
     * @param leadLastFollowupstatus
     * @param nextFollowupDate
     * @param leadLastFollowupdates
     * @param mobileNo
     * @param lastFollowup
     */
    fun Showleadmodel(
        sno: Int?,
        leadId: Int?,
        tenantId: Int?,
        leadCustomerName: String,
        phoneNo: String,
        mobileNo: String,
        leadGeneratedOn: String,
        leadLastFollowupdates: String,
        lastFollowup: String,
        nextFollowupDate: String,
        leadLastFollowupstatus: String,
        leadEntrolled: String
    ) {

        this.sno = sno
        this.leadId = leadId
        this.tenantId = tenantId
        this.leadCustomerName = leadCustomerName
        this.phoneNo = phoneNo
        this.mobileNo = mobileNo
        this.leadGeneratedOn = leadGeneratedOn
        this.leadLastFollowupdates = leadLastFollowupdates
        this.lastFollowup = lastFollowup
        this.nextFollowupDate = nextFollowupDate
        this.leadLastFollowupstatus = leadLastFollowupstatus
        this.leadEntrolled = leadEntrolled
    }

    fun getSno(): Int? {
        return sno
    }

    fun setSno(sno: Int?) {
        this.sno = sno
    }

    fun getLeadId(): Int? {
        return leadId
    }

    fun setLeadId(leadId: Int?) {
        this.leadId = leadId
    }

    fun getTenantId(): Int? {
        return tenantId
    }

    fun setTenantId(tenantId: Int?) {
        this.tenantId = tenantId
    }

    fun getLeadCustomerName(): String? {
        return leadCustomerName
    }

    fun setLeadCustomerName(leadCustomerName: String) {
        this.leadCustomerName = leadCustomerName
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun setPhoneNo(phoneNo: String) {
        this.phoneNo = phoneNo
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String) {
        this.mobileNo = mobileNo
    }

    fun getLeadGeneratedOn(): String? {
        return leadGeneratedOn
    }

    fun setLeadGeneratedOn(leadGeneratedOn: String) {
        this.leadGeneratedOn = leadGeneratedOn
    }

    fun getLeadLastFollowupdates(): String? {
        return leadLastFollowupdates
    }

    fun setLeadLastFollowupdates(leadLastFollowupdates: String) {
        this.leadLastFollowupdates = leadLastFollowupdates
    }

    fun getLastFollowup(): String? {
        return lastFollowup
    }

    fun setLastFollowup(lastFollowup: String) {
        this.lastFollowup = lastFollowup
    }

    fun getNextFollowupDate(): String? {
        return nextFollowupDate
    }

    fun setNextFollowupDate(nextFollowupDate: String) {
        this.nextFollowupDate = nextFollowupDate
    }

    fun getLeadLastFollowupstatus(): String? {
        return leadLastFollowupstatus
    }

    fun setLeadLastFollowupstatus(leadLastFollowupstatus: String) {
        this.leadLastFollowupstatus = leadLastFollowupstatus
    }

    fun getLeadEntrolled(): String? {
        return leadEntrolled
    }

    fun setLeadEntrolled(leadEntrolled: String) {
        this.leadEntrolled = leadEntrolled
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(sno)
        dest.writeValue(leadId)
        dest.writeValue(tenantId)
        dest.writeValue(leadCustomerName)
        dest.writeValue(phoneNo)
        dest.writeValue(mobileNo)
        dest.writeValue(leadGeneratedOn)
        dest.writeValue(leadLastFollowupdates)
        dest.writeValue(lastFollowup)
        dest.writeValue(nextFollowupDate)
        dest.writeValue(leadLastFollowupstatus)
        dest.writeValue(leadEntrolled)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<showleadmodel> {
        override fun createFromParcel(parcel: Parcel): showleadmodel {
            return showleadmodel(parcel)
        }

        override fun newArray(size: Int): Array<showleadmodel?> {
            return arrayOfNulls(size)
        }
    }

}