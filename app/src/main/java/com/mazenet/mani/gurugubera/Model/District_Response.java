package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class District_Response {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<District_array_Model> district_array = null;

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public List<District_array_Model> getdistrict_array() {
        return district_array;
    }

    public void setdistrict_array(List<District_array_Model> district_array) {
        this.district_array = district_array;
    }

}