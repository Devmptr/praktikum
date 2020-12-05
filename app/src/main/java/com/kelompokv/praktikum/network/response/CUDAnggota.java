package com.kelompokv.praktikum.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelompokv.praktikum.model.auth.Token;

public class CUDAnggota {
    @SerializedName("success")
    @Expose
    private String success;
    public  String getSuccess(){
        return success;
    }
}
