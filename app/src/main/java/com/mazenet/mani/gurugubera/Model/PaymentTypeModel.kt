package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PaymentTypeModel {
    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("payment_type_id")
    @Expose
    private var payment_type_id: Int? = null
    @SerializedName("payment_name")
    @Expose
    private var paymentName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param paymentName
     * @param tenantId
     * @param sno
     */
    fun PaymentTypeModel(sno: Int?, tenantId: Int?, paymentName: String) {

        this.sno = sno
        this.payment_type_id = tenantId
        this.paymentName = paymentName
    }

    fun getSno(): Int? {
        return sno
    }

    fun setSno(sno: Int?) {
        this.sno = sno
    }

    fun getTenantId(): Int? {
        return payment_type_id
    }

    fun setTenantId(tenantId: Int?) {
        this.payment_type_id = tenantId
    }

    fun getPaymentName(): String? {
        return paymentName
    }

    fun setPaymentName(paymentName: String) {
        this.paymentName = paymentName
    }
    override fun toString(): String {
        return this.paymentName.toString()
    }
}