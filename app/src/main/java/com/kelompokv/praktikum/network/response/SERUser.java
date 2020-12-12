package com.kelompokv.praktikum.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelompokv.praktikum.model.admin.User;

public class SERUser {
    @SerializedName("user")
    @Expose
    private User user;
    public User getUser() {
        return user;
    }
}
