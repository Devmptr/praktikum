package com.kelompokv.praktikum.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelompokv.praktikum.model.admin.Keluarga;

public class SERKeluarga {
    @SerializedName("keluarga")
    @Expose
    private Keluarga keluarga;
    public Keluarga getKeluarga() {
        return keluarga;
    }
}
