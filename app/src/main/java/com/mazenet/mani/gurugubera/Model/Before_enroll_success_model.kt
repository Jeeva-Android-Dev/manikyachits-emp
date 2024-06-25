package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Before_enroll_success_model {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("receipt_no")
    @Expose
    private var receipt_no: String? = null




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
    fun Before_enroll_success_model(status: String, msg: String,receipt_no:String){

        this.status = status
        this.msg = msg
        this.receipt_no = receipt_no

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

    fun getReceipt(): String? {
        return receipt_no
    }

    fun setReceipt(receipt_no: String) {
        this.receipt_no = receipt_no
    }


}