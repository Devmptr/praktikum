package com.kelompokv.praktikum.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CUDAnggota {
    @SerializedName("success")
    @Expose
    private String success;
    public  String getSuccess(){
        return success;
    }

    @SerializedName("error")
    @Expose
    private String error;
    public  String getError(){
        return error;
    }
}
