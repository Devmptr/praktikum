package com.kelompokv.praktikum.model.admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keluarga {
    @SerializedName("id") @Expose private Integer id;
    @SerializedName("alamat") @Expose private String alamat;
    @SerializedName("rtrw") @Expose private String rtrw;
    @SerializedName("kodepos") @Expose private String kodepos;
    @SerializedName("kelurahan") @Expose private String kelurahan;
    @SerializedName("kecamatan") @Expose private String kecamatan;
    @SerializedName("kabupaten") @Expose private String kabupaten;
    @SerializedName("provinsi") @Expose private String provinsi;

    public Integer getId() {
        return id;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getRtrw() {
        return rtrw;
    }

    public String getKodepos() {
        return kodepos;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }
}
