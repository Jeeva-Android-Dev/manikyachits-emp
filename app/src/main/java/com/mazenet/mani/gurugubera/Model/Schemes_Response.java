package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Schemes_Response {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<SchemsModel> scheme_array1 = null;
    private List<Scheme_array_Model> scheme_array = null;

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

    public List<Scheme_array_Model> getscheme_array() {
        return scheme_array;
    }

    public void setscheme_array(List<Scheme_array_Model> scheme_array) {
        this.scheme_array = scheme_array;
    }
    public List<SchemsModel> getscheme_array1() {
        return scheme_array1;
    }

    public void setscheme_array1(List<SchemsModel> scheme_array1) {
        this.scheme_array1 = scheme_array1;
    }


}