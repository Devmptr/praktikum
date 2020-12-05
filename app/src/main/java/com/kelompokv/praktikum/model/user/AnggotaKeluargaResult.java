package com.kelompokv.praktikum.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelompokv.praktikum.model.auth.Token;

import java.util.List;

public class AnggotaKeluargaResult {
    @SerializedName("anggota")
    @Expose
    private List<AnggotaKeluarga> anggotas;
    public List<AnggotaKeluarga> getAnggota() {
        return anggotas;
    }
}
