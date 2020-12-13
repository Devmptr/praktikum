package com.kelompokv.praktikum.network.service;

import com.kelompokv.praktikum.Endpoint;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.response.SERUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET(Endpoint.ADMIN_USER_SHOW)
    Call<SERUser> showUser(@Path(value = "id", encoded = true) Integer id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.USER_PROFILE_UPDATE)
    Call<CUDUser> updateUser(@Path(value = "id", encoded = true) Integer id,
                             @Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password);

    @GET(Endpoint.USER_KELUARGA_GET)
    Call<SERKeluarga> showKeluarga(@Path(value = "id", encoded = true) Integer id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.USER_KELUARGA_UPDATE)
    Call<CUDKeluarga> updateKeluarga(@Path(value = "id", encoded = true) Integer id,
                                     @Field("alamat") String alamat,
                                     @Field("rtrw") String rtrw,
                                     @Field("kodepos") String kodepos,
                                     @Field("kelurahan") String kelurahan,
                                     @Field("kecamatan") String kecamatan,
                                     @Field("kabupaten") String kabupaten,
                                     @Field("provinsi") String provinsi);
}
