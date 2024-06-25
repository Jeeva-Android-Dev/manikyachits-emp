package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class States_Response {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<StateModel> state_array1 = null;
    private List<State_array_Model> state_array = null;

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

    public List<State_array_Model> getstate_array() {
        return state_array;
    }

    public void setstate_array(List<State_array_Model> state_array) {
        this.state_array = state_array;
    }
    public List<StateModel> getstate_array1() {
        return state_array1;
    }

    public void setstate_array1(List<StateModel> state_array1) {
        this.state_array1 = state_array1;
    }


}