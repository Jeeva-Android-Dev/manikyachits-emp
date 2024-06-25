package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State_array_Model {
    @SerializedName("id")
    @Expose
    private String state_id;
    @SerializedName("country_id")
    @Expose
    private String country_id;
    @SerializedName("state_name")
    @Expose
    private String state_name;

    public State_array_Model(String state_id, String country_id, String state_name) {
        this.state_id = state_id;
        this.country_id = country_id;
        this.state_name = state_name;
    }




    public String getstate_id() {
        return state_id;
    }

    public void setstate_id(String state_id) {
        this.state_id = state_id;
    }

    public String getcountry_id() {
        return country_id;
    }

    public void setcountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getstate_name() {
        return state_name;
    }

    public void setstate_name(String state_name) {
        this.state_name = state_name;
    }
}