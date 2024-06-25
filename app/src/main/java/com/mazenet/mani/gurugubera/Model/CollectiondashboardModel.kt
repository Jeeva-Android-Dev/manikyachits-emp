package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CollectiondashboardModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("target")
    @Expose
    private var target: String? = null
    @SerializedName("Acheived")
    @Expose
    private var acheived: String? = null
    @SerializedName("cash_in_hand")
    @Expose
    private var cashInHand: String? = null
    @SerializedName("no_of_receipt_generated")
    @Expose
    private var noOfReceiptGenerated: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param cashInHand
     * @param noOfReceiptGenerated
     * @param status
     * @param target
     * @param acheived
     * @param msg
     */
    fun CollectiondashboardModel(
        status: String,
        msg: String,
        target: String,
        acheived: String,
        cashInHand: String,
        noOfReceiptGenerated: String
    ) {

        this.status = status
        this.msg = msg
        this.target = target
        this.acheived = acheived
        this.cashInHand = cashInHand
        this.noOfReceiptGenerated = noOfReceiptGenerated
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String) {
        this.msg = msg
    }

    fun getTarget(): String? {
        return target
    }

    fun setTarget(target: String) {
        this.target = target
    }

    fun getAcheived(): String? {
        return acheived
    }

    fun setAcheived(acheived: String) {
        this.acheived = acheived
    }

    fun getCashInHand(): String? {
        return cashInHand
    }

    fun setCashInHand(cashInHand: String) {
        this.cashInHand = cashInHand
    }

    fun getNoOfReceiptGenerated(): String? {
        return noOfReceiptGenerated
    }

    fun setNoOfReceiptGenerated(noOfReceiptGenerated: String) {
        this.noOfReceiptGenerated = noOfReceiptGenerated
    }

}