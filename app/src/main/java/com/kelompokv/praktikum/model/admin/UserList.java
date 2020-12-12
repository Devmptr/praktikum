package com.kelompokv.praktikum.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {
    @SerializedName("user")
    @Expose
    private List<User> users;
    public List<User> getUsers() {
        return users;
    }
}
