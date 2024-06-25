package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TenantModel {

    @SerializedName("tenant_id")
    @Expose
    private var tenantId: Int? = null
    @SerializedName("tenant_name")
    @Expose
    private var tenantName: String? = null

    fun TenantModel(tenantId: Int?, tenantName: String) {

        this.tenantId = tenantId
        this.tenantName = tenantName
    }

    fun getTenantId(): Int? {
        return tenantId
    }

    fun setTenantId(tenantId: Int?) {
        this.tenantId = tenantId
    }

    fun getTenantName(): String? {
        return tenantName
    }

    fun setTenantName(tenantName: String) {
        this.tenantName = tenantName
    }
}