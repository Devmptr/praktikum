package com.kelompokv.praktikum.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FBToken {
    @SerializedName("success")
    @Expose
    private String success;

    public  String getSuccess(){
        return success;
    }
}
