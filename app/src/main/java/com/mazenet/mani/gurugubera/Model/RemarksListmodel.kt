package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class RemarksListmodel {

    @SerializedName("collection_remarks_id")
    @Expose
    private var collectionRemarksId: String? = null
    @SerializedName("tenant_id")
    @Expose
    private var tenantId: String? = null
    @SerializedName("remark_name")
    @Expose
    private var remarkName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    /**
     *
     * @param tenantId
     * @param remarkName
     * @param collectionRemarksId
     */
    fun RemarksListmodel(collectionRemarksId: String, tenantId: String, remarkName: String) {

        this.collectionRemarksId = collectionRemarksId
        this.tenantId = tenantId
        this.remarkName = remarkName
    }

    fun getCollectionRemarksId(): String? {
        return collectionRemarksId
    }

    fun setCollectionRemarksId(collectionRemarksId: String) {
        this.collectionRemarksId = collectionRemarksId
    }

    fun getTenantId(): String? {
        return tenantId
    }

    fun setTenantId(tenantId: String) {
        this.tenantId = tenantId
    }

    fun getRemarkName(): String? {
        return remarkName
    }

    fun setRemarkName(remarkName: String) {
        this.remarkName = remarkName
    }
    override fun toString(): String {
        return this.remarkName.toString()
    }
}