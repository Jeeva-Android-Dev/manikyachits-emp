package com.mazenet.mani.gurugubera.Model

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class DayclosingModel {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null

    @SerializedName("data")
    @Expose
    var data: JsonElement? = null



    /**
     * @param status
     * @param msg
     * @param data
     */
    constructor(status: String?, msg: String?, data: JsonElement?) : super()  {

        this.status = status
        this.msg = msg
        this.data = data
    }





}