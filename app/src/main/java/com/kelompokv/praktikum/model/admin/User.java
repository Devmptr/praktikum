package com.kelompokv.praktikum.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("name") @Expose private String name;
    @SerializedName("email") @Expose private String email;
    @SerializedName("role") @Expose private String role;

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
