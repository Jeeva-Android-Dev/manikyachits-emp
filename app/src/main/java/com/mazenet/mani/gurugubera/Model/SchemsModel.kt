package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class SchemsModel {

    @SerializedName("id")
    @Expose
    private var id: String? = null


    @SerializedName("scheme_format")
    @Expose
    private var scheme_format: String? = null


    @SerializedName("chit_value")
    @Expose
    private var chit_value: String? = null


    fun getid(): String? {
        return id
    }

    fun setid(id: String) {
        this.id = id
    }

    fun getscheme_format(): String? {
        return scheme_format
    }

    fun setscheme_format(scheme_format: String) {
        this.scheme_format = scheme_format
    }

    fun getchit_value(): String? {
        return chit_value
    }

    fun setchit_value(chit_value: String) {
        this.chit_value = chit_value
    }

}