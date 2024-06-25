package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StateModel {

    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("country_id")
    @Expose
    private var country_id: String? = null
    @SerializedName("state_name")
    @Expose
    private var state_name: String? = null


    fun getid(): String? {
        return id
    }

    fun setid(id: String) {
        this.id = id
    }

    fun getcountryid(): String? {
        return country_id
    }

    fun setcountryid(country_id: String) {
        this.country_id = country_id
    }

    fun getstatename(): String? {
        return state_name
    }

    fun setstatename(state_name: String) {
        this.state_name = state_name
    }

}