package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class EmployeeModel {

    @SerializedName("tenant_id")
    @Expose
    private var tenantId: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("employee_id")
    @Expose
    private var employeeId: String? = null
    @SerializedName("employee_name")
    @Expose
    private var employeeName: String? = null
    @SerializedName("employee_type")
    @Expose
    private var employeeType: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param employeeType
     * @param employeeId
     * @param tenantId
     * @param branchId
     * @param employeeName
     */
    fun EmployeeModel(
        tenantId: String,
        branchId: String,
        employeeId: String,
        employeeName: String,
        employeeType: String
    ) {

        this.tenantId = tenantId
        this.branchId = branchId
        this.employeeId = employeeId
        this.employeeName = employeeName
        this.employeeType = employeeType
    }

    fun getTenantId(): String? {
        return tenantId
    }

    fun setTenantId(tenantId: String) {
        this.tenantId = tenantId
    }

    fun getBranchId(): String? {
        return branchId
    }

    fun setBranchId(branchId: String) {
        this.branchId = branchId
    }

    fun getEmployeeId(): String? {
        return employeeId
    }

    fun setEmployeeId(employeeId: String) {
        this.employeeId = employeeId
    }

    fun getEmployeeName(): String? {
        return employeeName
    }

    fun setEmployeeName(employeeName: String) {
        this.employeeName = employeeName
    }

    fun getEmployeeType(): String? {
        return employeeType
    }

    fun setEmployeeType(employeeType: String) {
        this.employeeType = employeeType
    }
    override fun toString(): String {
        return this.employeeName.toString()
    }
}