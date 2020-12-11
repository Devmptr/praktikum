package com.kelompokv.praktikum.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("success")
    @Expose
    private Token success;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("is_profile")
    @Expose
    private Boolean is_profile;

    @SerializedName("id")
    @Expose
    private Integer id;

    public Token getSuccess(){
        return success;
    }

    public String getRole(){ return role; }

    public Boolean getIs_profile() {
        return is_profile;
    }

    public Integer getId() {
        return id;
    }
}
