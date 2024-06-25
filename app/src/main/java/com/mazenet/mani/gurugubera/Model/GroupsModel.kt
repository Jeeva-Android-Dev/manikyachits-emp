package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class GroupsModel {
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param tenantId
     * @param groupId
     * @param groupName
     * @param branchId
     */
    fun GroupsModel(tenantId: String, branchId: String, groupId: String, groupName: String){

        this.tenantId = tenantId
        this.branchId = branchId
        this.groupId = groupId
        this.groupName = groupName
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

    fun getGroupId(): String? {
        return groupId
    }

    fun setGroupId(groupId: String) {
        this.groupId = groupId
    }

    fun getGroupName(): String? {
        return groupName
    }

    fun setGroupName(groupName: String) {
        this.groupName = groupName
    }
    override fun toString(): String {
        return this.groupName.toString()
    }
}