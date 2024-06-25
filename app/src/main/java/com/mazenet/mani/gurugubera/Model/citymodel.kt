package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class citymodel {
    @SerializedName("sno")
    @Expose
    private var sno: Int? = null
    @SerializedName("state_id")
    @Expose
    private var stateId: Int? = null
    @SerializedName("district_id")
    @Expose
    private var districtId: Int? = null
    @SerializedName("city_id")
    @Expose
    private var cityId: Int? = null
    @SerializedName("city_name")
    @Expose
    private var cityName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param districtId
     * @param sno
     * @param cityId
     * @param stateId
     * @param cityName
     */
    fun Citymodel(sno: Int?, stateId: Int?, districtId: Int?, cityId: Int?, cityName: String){

        this.sno = sno
        this.stateId = stateId
        this.districtId = districtId
        this.cityId = cityId
        this.cityName = cityName
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

    fun getDistrictId(): Int? {
        return districtId
    }

    fun setDistrictId(districtId: Int?) {
        this.districtId = districtId
    }

    fun getCityId(): Int? {
        return cityId
    }

    fun setCityId(cityId: Int?) {
        this.cityId = cityId
    }

    fun getCityName(): String? {
        return cityName
    }

    fun setCityName(cityName: String) {
        this.cityName = cityName
    }

}