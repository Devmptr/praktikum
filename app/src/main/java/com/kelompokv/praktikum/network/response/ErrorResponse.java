package com.kelompokv.praktikum.network.response;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("message")
    private String msg;

    @SerializedName("error")
    private Error error;

    public String getMsg() {
        return msg;
    }

    public Error getError() {
        return error;
    }
}
