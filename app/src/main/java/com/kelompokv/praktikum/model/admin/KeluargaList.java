package com.kelompokv.praktikum.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeluargaList {
    @SerializedName("keluarga")
    @Expose
    private List<Keluarga> keluargas;
    public List<Keluarga> getKeluargas() {
        return keluargas;
    }
}
