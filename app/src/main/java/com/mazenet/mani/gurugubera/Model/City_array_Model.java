package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City_array_Model {
    @SerializedName("id")
    @Expose
    private String city_id;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("district_id")
    @Expose
    private String district_id;
    @SerializedName("city_name")
    @Expose
    private String city_name;

    public String getcity_id() {
        return city_id;
    }

    public void setcity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getstate_id() {
        return state_id;
    }

    public void setstate_id(String state_id) {
        this.state_id = state_id;
    }

    public String getcity_name() {
        return city_name;
    }

    public void setcity_name(String city_name) {
        this.city_name = city_name;
    }
    public String getdistrict_id() {
        return district_id;
    }

    public void setdistrict_id(String district_id) {
        this.district_id = district_id;
    }
}