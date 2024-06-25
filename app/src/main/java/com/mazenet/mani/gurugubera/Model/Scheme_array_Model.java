package com.mazenet.mani.gurugubera.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scheme_array_Model {
    @SerializedName("id")
    @Expose
    private String scheme_id;
    @SerializedName("scheme_format")
    @Expose
    private String scheme_format;
    @SerializedName("chit_value")
    @Expose
    private String chit_value;

    public Scheme_array_Model(String scheme_id, String scheme_format, String chit_value) {
        this.scheme_id = scheme_id;
        this.scheme_format = scheme_format;
        this.chit_value = chit_value;
    }




    public String getscheme_id() {
        return scheme_id;
    }

    public void setscheme_id(String scheme_id) {
        this.scheme_id = scheme_id;
    }

    public String getscheme_format() {
        return scheme_format;
    }

    public void setscheme_format(String scheme_format) {
        this.scheme_format = scheme_format;
    }

    public String getchit_value() {
        return chit_value;
    }

    public void setchit_value(String chit_value) {
        this.chit_value = chit_value;
    }
}