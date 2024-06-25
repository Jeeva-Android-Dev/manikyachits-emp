package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class District_array_Model {
    @SerializedName("id")
    @Expose
    private String district_id;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("district_name")
    @Expose
    private String district_name;

    public String getdistrict_id() {
        return district_id;
    }

    public void setdistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getstate_id() {
        return state_id;
    }

    public void setstate_id(String state_id) {
        this.state_id = state_id;
    }

    public String getdistrict_name() {
        return district_name;
    }

    public void setdistrict_name(String district_name) {
        this.district_name = district_name;
    }
}