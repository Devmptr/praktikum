package com.kelompokv.praktikum.model.user;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.Date;

public class AnggotaKeluarga {
    @SerializedName("id") @Expose private String id;
    @SerializedName("nama") @Expose private String nama;
    @SerializedName("nik") @Expose private String nik;
    @SerializedName("jenis_kelamin") @Expose private String jenis_kelamin;
    @SerializedName("tempat_lahir") @Expose private String tempat_lahir;
    @SerializedName("tanggal_lahir") @Expose private Date tanggal_lahir;
    @SerializedName("agama") @Expose private String agama;
    @SerializedName("pendidikan") @Expose private String pendidikan;
    @SerializedName("pekerjaan") @Expose private String pekerjaan;
    @SerializedName("tipe") @Expose private String tipe;
    @SerializedName("ayah") @Expose private String ayah;
    @SerializedName("ibu") @Expose private String ibu;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNik() {
        return nik;
    }

    public String getJeniskelamin() {
        return jenis_kelamin;
    }

    public String getTempatlahir() {
        return tempat_lahir;
    }

    public Date getTanggallahir() {
        return tanggal_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public String getTipe() {
        return tipe;
    }

    public String getAyah() {
        return ayah;
    }

    public String getIbu() {
        return ibu;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }
}
