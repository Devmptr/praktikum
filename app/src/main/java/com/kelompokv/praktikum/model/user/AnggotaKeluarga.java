package com.kelompokv.praktikum.model.user;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AnggotaKeluarga {
    private String emailuser;
    @SerializedName("id") @Expose private Integer id;
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
    @SerializedName("validated") @Expose private String validated;
    @SerializedName("id_keluarga") @Expose private Integer id_keluarga;

    public AnggotaKeluarga(Integer id, String nama, String nik, String jk, String tempat, String tanggal,
                           String agama, String pendidikan, String pekerjaan, String tipe, String ayah,
                           String ibu, String validated, Integer id_k){
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.id = id; this.nama = nama; this.nik = nik; this.jenis_kelamin = jk; this.tempat_lahir = tempat;
        this.tanggal_lahir = date; this.agama = agama; this.pendidikan = pendidikan; this.pekerjaan = pekerjaan;
        this.tipe = tipe; this.ayah = ayah; this.ibu = ibu; this.validated = validated; this.id_keluarga = id_k;
    }

    public Integer getId() {
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

    public String getStrTanggal(){
        long date = getTanggallahir().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strdate = dateFormat.format(date);
        return strdate;
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

    public String getValidated() {
        return validated;
    }

    public Integer getId_keluarga() {
        return id_keluarga;
    }
}
