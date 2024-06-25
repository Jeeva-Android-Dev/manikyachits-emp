package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CashinhandModel {
    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null
    @SerializedName("cash_in_hand")
    @Expose
    private var cashInHand: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param cashInHand
     * @param status
     * @param msg
     */
    fun CashinhandModel(status: String, msg: String, cashInHand: String){

        this.status = status
        this.msg = msg
        this.cashInHand = cashInHand
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

    fun getCashInHand(): String? {
        return cashInHand
    }

    fun setCashInHand(cashInHand: String) {
        this.cashInHand = cashInHand
    }


}