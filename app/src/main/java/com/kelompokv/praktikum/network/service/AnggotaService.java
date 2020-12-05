package com.kelompokv.praktikum.network.service;

import com.kelompokv.praktikum.Endpoint;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.SERAnggota;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AnggotaService {
    @GET(Endpoint.USER_ANGGOTA_BASE)
    Call<AnggotaKeluargaResult> getAnggota();

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.USER_ANGGOTA_BASE)
    Call<CUDAnggota> storeAnggota(@Field("nama") String nama,
                                  @Field("jenis_kelamin") String jenis_kelamin,
                                  @Field("tempat_lahir") String tempat_lahir,
                                  @Field("tanggal_lahir") String tanggal_lahir,
                                  @Field("agama") String agama,
                                  @Field("pendidikan") String pendidikan,
                                  @Field("pekerjaan") String pekerjaan,
                                  @Field("tipe") String tipe,
                                  @Field("ayah") String ayah,
                                  @Field("ibu") String ibu);

    @GET(Endpoint.USER_ANGGOTA_WITH_ID)
    Call<SERAnggota> editAnggota(@Path(value = "anggota", encoded = true) String anggota);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT(Endpoint.USER_ANGGOTA_WITH_ID)
    Call<CUDAnggota> updateAnggota(@Path(value = "anggota", encoded = true) String anggota,
                                   @Field("nama") String nama,
                                   @Field("jenis_kelamin") String jenis_kelamin,
                                   @Field("tempat_lahir") String tempat_lahir,
                                   @Field("tanggal_lahir") String tanggal_lahir,
                                   @Field("agama") String agama,
                                   @Field("pendidikan") String pendidikan,
                                   @Field("pekerjaan") String pekerjaan,
                                   @Field("tipe") String tipe,
                                   @Field("ayah") String ayah,
                                   @Field("ibu") String ibu);

    @DELETE(Endpoint.USER_ANGGOTA_WITH_ID)
    Call<CUDAnggota> deleteAnggota(@Path(value = "anggota", encoded = true) String anggota);
}
