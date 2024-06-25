package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class otpverifymodel {

    @SerializedName("status")
    @Expose
    private var status: String? = null
    @SerializedName("msg")
    @Expose
    private var msg: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param status
     * @param msg
     */
    fun Otpverifymodel(status: String, msg: String){

        this.status = status
        this.msg = msg
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
}