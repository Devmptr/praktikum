package com.kelompokv.praktikum.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("success")
    @Expose
    private Token success;

    public  Token getSuccess(){
        return success;
    }
}
