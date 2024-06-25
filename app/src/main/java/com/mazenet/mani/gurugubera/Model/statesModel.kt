package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class statesModel {
    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("state_id")
    @Expose
    private var stateId: Int? = null
    @SerializedName("country_id")
    @Expose
    private var countryId: Int? = null
    @SerializedName("state_name")
    @Expose
    private var stateName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param countryId
     * @param sno
     * @param stateId
     * @param stateName
     */
    fun StatesModel(sno: Int?, stateId: Int?, countryId: Int?, stateName: String) {

        this.sno = sno
        this.stateId = stateId
        this.countryId = countryId
        this.stateName = stateName
    }

    fun getSno(): Int? {
        return sno
    }

    fun setSno(sno: Int?) {
        this.sno = sno
    }

    fun getStateId(): Int? {
        return stateId
    }

    fun setStateId(stateId: Int?) {
        this.stateId = stateId
    }

    fun getCountryId(): Int? {
        return countryId
    }

    fun setCountryId(countryId: Int?) {
        this.countryId = countryId
    }

    fun getStateName(): String? {
        return stateName
    }

    fun setStateName(stateName: String) {
        this.stateName = stateName
    }

}