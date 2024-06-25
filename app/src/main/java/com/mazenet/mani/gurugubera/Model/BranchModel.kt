package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class BranchModel {

    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: Int? = null
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: Int? = null
    @SerializedName("head_office_id")
    @Expose
    private var headOfficeId: Int? = null
    @SerializedName("branch_name")
    @Expose
    private var branchName: String? = null
    @SerializedName("branch_code")
    @Expose
    private var branchCode: String? = null
    @SerializedName("email_id")
    @Expose
    private var emailId: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("address_line_1")
    @Expose
    private var addressLine1: String? = null
    @SerializedName("address_line_2")
    @Expose
    private var addressLine2: String? = null
    @SerializedName("landmark")
    @Expose
    private var landmark: String? = null
    @SerializedName("state_name")
    @Expose
    private var stateName: String? = null
    @SerializedName("district_name")
    @Expose
    private var districtName: String? = null
    @SerializedName("city_name")
    @Expose
    private var cityName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param tenantId
     * @param sno
     * @param cityName
     * @param branchCode
     * @param addressLine2
     * @param addressLine1
     * @param phoneNo
     * @param landmark
     * @param districtName
     * @param emailId
     * @param branchId
     * @param stateName
     * @param branchName
     * @param headOfficeId
     * @param mobileNo
     */
    fun BranchModel(
        sno: Int?,
        branchId: Int?,
        tenantId: Int?,
        headOfficeId: Int?,
        branchName: String,
        branchCode: String,
        emailId: String,
        phoneNo: String,
        mobileNo: String,
        addressLine1: String,
        addressLine2: String,
        landmark: String,
        stateName: String,
        districtName: String,
        cityName: String
    ) {
        this.sno = sno
        this.branchId = branchId
        this.tenantId = tenantId
        this.headOfficeId = headOfficeId
        this.branchName = branchName
        this.branchCode = branchCode
        this.emailId = emailId
        this.phoneNo = phoneNo
        this.mobileNo = mobileNo
        this.addressLine1 = addressLine1
        this.addressLine2 = addressLine2
        this.landmark = landmark
        this.stateName = stateName
        this.districtName = districtName
        this.cityName = cityName
    }

    fun getSno(): Int? {
        return sno
    }

    fun setSno(sno: Int?) {
        this.sno = sno
    }

    fun getBranchId(): Int? {
        return branchId
    }

    fun setBranchId(branchId: Int?) {
        this.branchId = branchId
    }

    fun getTenantId(): Int? {
        return tenantId
    }

    fun setTenantId(tenantId: Int?) {
        this.tenantId = tenantId
    }

    fun getHeadOfficeId(): Int? {
        return headOfficeId
    }

    fun setHeadOfficeId(headOfficeId: Int?) {
        this.headOfficeId = headOfficeId
    }

    fun getBranchName(): String? {
        return branchName
    }

    fun setBranchName(branchName: String) {
        this.branchName = branchName
    }

    fun getBranchCode(): String? {
        return branchCode
    }

    fun setBranchCode(branchCode: String) {
        this.branchCode = branchCode
    }

    fun getEmailId(): String? {
        return emailId
    }

    fun setEmailId(emailId: String) {
        this.emailId = emailId
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

    fun getAddressLine1(): String? {
        return addressLine1
    }

    fun setAddressLine1(addressLine1: String) {
        this.addressLine1 = addressLine1
    }

    fun getAddressLine2(): String? {
        return addressLine2
    }

    fun setAddressLine2(addressLine2: String) {
        this.addressLine2 = addressLine2
    }

    fun getLandmark(): String? {
        return landmark
    }

    fun setLandmark(landmark: String) {
        this.landmark = landmark
    }

    fun getStateName(): String? {
        return stateName
    }

    fun setStateName(stateName: String) {
        this.stateName = stateName
    }

    fun getDistrictName(): String? {
        return districtName
    }

    fun setDistrictName(districtName: String) {
        this.districtName = districtName
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String) {
        this.cityName = cityName
    }

    override fun toString(): String {
        return this.branchName.toString()
    }
}