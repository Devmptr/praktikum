package com.kelompokv.praktikum.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("token")
    @Expose
    private String token;

    public String getToken(){
        return token;
    }
}
