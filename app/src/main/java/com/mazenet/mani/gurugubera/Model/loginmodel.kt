package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class loginmodel {


    @SerializedName("success")
    @Expose
    private var success: Boolean? = null
    @SerializedName("token")
    @Expose
    private var token: String? = null
    @SerializedName("logged_user_id")
    @Expose
    private var loggedUserId: Int? = null
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: Int? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: Int? = null
    @SerializedName("role_id")
    @Expose
    private var roleId: Int? = null
    @SerializedName("logged_user_name")
    @Expose
    private var loggedUserName: String? = null
    @SerializedName("role_name")
    @Expose
    private var roleName: String? = null
    @SerializedName("tenant_name")
    @Expose
    private var tenantName: String? = null
    @SerializedName("tenant_gst_no")
    @Expose
    private var tenantGstNo: String? = null
    @SerializedName("tenant_address_1")
    @Expose
    private var tenantAddress1: String? = null
    @SerializedName("tenant_address_2")
    @Expose
    private var tenantAddress2: String? = null
    @SerializedName("state_name")
    @Expose
    private var stateName: String? = null
    @SerializedName("district_name")
    @Expose
    private var districtName: String? = null
    @SerializedName("city_name")
    @Expose
    private var cityName: String? = null
    @SerializedName("tenant_mobile")
    @Expose
    private var tenantMobile: String? = null
    @SerializedName("tenant_phone")
    @Expose
    private var tenantPhone: String? = null
    @SerializedName("role_type")
    @Expose
    private var role_type: String? = null
    @SerializedName("db")
    @Expose
    private var db: String? = null
    @SerializedName("employee_id")
    @Expose
    private var employee_id: Int? = null

    @SerializedName("profile_pic")
    @Expose
    private var profile_pic: String? = null

    @SerializedName("branch_name")
    @Expose
    private var branch_name: String? = null


    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param tenantId
     * @param tenantGstNo
     * @param cityName
     * @param loggedUserName
     * @param roleName
     * @param tenantName
     * @param tenantMobile
     * @param districtName
     * @param tenantAddress1
     * @param token
     * @param branchId
     * @param stateName
     * @param loggedUserId
     * @param success
     * @param tenantAddress2
     * @param tenantPhone
     * @param db
     * @param roleId
     * @param profile_pic
     * @param branch_name
     */
    fun Loginmodel(
        success: Boolean?,
        token: String,
        loggedUserId: Int?,
        tenantId: Int?,
        branchId: Int?,
        roleId: Int?,
        loggedUserName: String,
        roleName: String,
        tenantName: String,
        tenantGstNo: String,
        tenantAddress1: String,
        tenantAddress2: String,
        stateName: String,
        districtName: String,
        cityName: String,
        tenantMobile: String,
        tenantPhone: String,
        role_type: String,
        db : String,
        employee_id:Int,
        profile_pic : String,
        branch_name: String
    ) {

        this.success = success
        this.token = token
        this.loggedUserId = loggedUserId
        this.tenantId = tenantId
        this.branchId = branchId
        this.roleId = roleId
        this.loggedUserName = loggedUserName
        this.roleName = roleName
        this.tenantName = tenantName
        this.tenantGstNo = tenantGstNo
        this.tenantAddress1 = tenantAddress1
        this.tenantAddress2 = tenantAddress2
        this.stateName = stateName
        this.districtName = districtName
        this.cityName = cityName
        this.tenantMobile = tenantMobile
        this.tenantPhone = tenantPhone
        this.role_type = role_type
        this.db = db
        this.employee_id = employee_id
        this.profile_pic = profile_pic
        this.branch_name = branch_name
    }

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun getLoggedUserId(): Int? {
        return loggedUserId
    }

    fun setLoggedUserId(loggedUserId: Int?) {
        this.loggedUserId = loggedUserId
    }

    fun getTenantId(): Int? {
        return tenantId
    }

    fun setTenantId(tenantId: Int?) {
        this.tenantId = tenantId
    }

    fun getBranchId(): Int? {
        return branchId
    }

    fun setBranchId(branchId: Int?) {
        this.branchId = branchId
    }

    fun getRoleId(): Int? {
        return roleId
    }

    fun setRoleId(roleId: Int?) {
        this.roleId = roleId
    }

    fun getLoggedUserName(): String? {
        return loggedUserName
    }

    fun setLoggedUserName(loggedUserName: String) {
        this.loggedUserName = loggedUserName
    }

    fun getRoleName(): String? {
        return roleName
    }

    fun setRoleName(roleName: String) {
        this.roleName = roleName
    }

    fun getTenantName(): String? {
        return tenantName
    }

    fun setTenantName(tenantName: String) {
        this.tenantName = tenantName
    }

    fun getTenantGstNo(): String? {
        return tenantGstNo
    }

    fun setTenantGstNo(tenantGstNo: String) {
        this.tenantGstNo = tenantGstNo
    }

    fun getTenantAddress1(): String? {
        return tenantAddress1
    }

    fun setTenantAddress1(tenantAddress1: String) {
        this.tenantAddress1 = tenantAddress1
    }

    fun getTenantAddress2(): String? {
        return tenantAddress2
    }

    fun setTenantAddress2(tenantAddress2: String) {
        this.tenantAddress2 = tenantAddress2
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

    fun getTenantMobile(): String? {
        return tenantMobile
    }

    fun setTenantMobile(tenantMobile: String) {
        this.tenantMobile = tenantMobile
    }

    fun getTenantPhone(): String? {
        return tenantPhone
    }

    fun setTenantPhone(tenantPhone: String) {
        this.tenantPhone = tenantPhone
    }

    fun getRoleType(): String? {
        return role_type
    }

    fun setRoleType(role_type: String) {
        this.role_type = role_type
    }

    fun getDb(): String? {
        return db
    }

    fun setDb(db: String) {
        this.db = db
    }

    fun getEmployeeId(): Int? {
        return employee_id
    }

    fun setEmployeeId(employee_id: Int?) {
        this.employee_id = employee_id
    }

    fun setProfilePic(profile_pic: String?){
        this.profile_pic = profile_pic
    }

    fun getProfilePic() : String? {
        return profile_pic
    }

    fun setBranchname(branch_name: String){
        this.branch_name = branch_name
    }

    fun getBranchName() : String? {
        return branch_name
    }
}