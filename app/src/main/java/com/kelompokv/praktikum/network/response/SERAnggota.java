package com.kelompokv.praktikum.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kelompokv.praktikum.model.user.AnggotaKeluarga;


public class SERAnggota {
    @SerializedName("anggota")
    @Expose
    private AnggotaKeluarga anggotas;
    public AnggotaKeluarga getAnggota() {
        return anggotas;
    }

    @SerializedName("token_fb")
    @Expose
    private String token_fb;
    public String getToken_fb(){
        return token_fb;
    }
}
