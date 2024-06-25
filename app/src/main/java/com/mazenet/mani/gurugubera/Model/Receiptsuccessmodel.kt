package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Receiptsuccessmodel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("receipt_no")
    @Expose
    private var receiptNo: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param receiptNo
     * @param status
     * @param msg
     */
    fun Receiptsuccessmodel(status: String, msg: String, receiptNo: String){

        this.status = status
        this.msg = msg
        this.receiptNo = receiptNo
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

    fun getReceiptNo(): String? {
        return receiptNo
    }

    fun setReceiptNo(receiptNo: String) {
        this.receiptNo = receiptNo
    }
}