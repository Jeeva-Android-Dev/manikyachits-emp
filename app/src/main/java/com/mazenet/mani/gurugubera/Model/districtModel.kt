package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class districtModel {
    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("district_id")
    @Expose
    private var districtId: Int? = null
    @SerializedName("state_id")
    @Expose
    private var stateId: Int? = null
    @SerializedName("district_name")
    @Expose
    private var districtName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param districtName
     * @param districtId
     * @param sno
     * @param stateId
     */
    fun DistrictModel(sno: Int?, districtId: Int?, stateId: Int?, districtName: String) {

        this.sno = sno
        this.districtId = districtId
        this.stateId = stateId
        this.districtName = districtName
    }

    fun getSno(): Int? {
        return sno
    }

    fun setSno(sno: Int?) {
        this.sno = sno
    }

    fun getDistrictId(): Int? {
        return districtId
    }

    fun setDistrictId(districtId: Int?) {
        this.districtId = districtId
    }

    fun getStateId(): Int? {
        return stateId
    }

    fun setStateId(stateId: Int?) {
        this.stateId = stateId
    }

    fun getDistrictName(): String? {
        return districtName
    }

    fun setDistrictName(districtName: String) {
        this.districtName = districtName
    }
}