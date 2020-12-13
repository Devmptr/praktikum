package com.kelompokv.praktikum.network.service;

import com.kelompokv.praktikum.Endpoint;
import com.kelompokv.praktikum.model.admin.KeluargaList;
import com.kelompokv.praktikum.model.admin.UserList;
import com.kelompokv.praktikum.model.user.AnggotaKeluargaResult;
import com.kelompokv.praktikum.network.response.CUDAnggota;
import com.kelompokv.praktikum.network.response.CUDKeluarga;
import com.kelompokv.praktikum.network.response.CUDUser;
import com.kelompokv.praktikum.network.response.SERAnggota;
import com.kelompokv.praktikum.network.response.SERKeluarga;
import com.kelompokv.praktikum.network.response.SERUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdminService {
    @GET(Endpoint.ADMIN_USER_ALL)
    Call<UserList> allUser();

    @GET(Endpoint.ADMIN_USER_SHOW)
    Call<SERUser> showUser(@Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.ADMIN_USER_DELETE)
    Call<CUDUser> deleteUser(@Path(value = "id", encoded = true) Integer id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.ADMIN_USER_CREATE)
    Call<CUDUser> createUser(@Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("role") String role);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.ADMIN_USER_UPDATE)
    Call<CUDUser> updateUser(@Path(value = "id", encoded = true) Integer id,
                             @Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("role") String role);

    @GET(Endpoint.ADMIN_KELUARGA_ALL)
    Call<KeluargaList> allKeluarga();

    @GET(Endpoint.ADMIN_KELUARGA_SHOW)
    Call<SERKeluarga> showKeluarga(@Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.ADMIN_KELUARGA_DELETE)
    Call<CUDKeluarga> deleteKeluarga(@Path(value = "id", encoded = true) Integer id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.ADMIN_KELUARGA_CREATE)
    Call<CUDKeluarga> createKeluarga(@Field("alamat") String alamat,
                                     @Field("rtrw") String rtrw,
                                     @Field("kodepos") String kodepos,
                                     @Field("kelurahan") String kelurahan,
                                     @Field("kecamatan") String kecamatan,
                                     @Field("kabupaten") String kabupaten,
                                     @Field("provinsi") String provinsi);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.ADMIN_KELUARGA_UPDATE)
    Call<CUDKeluarga> updateKeluarga(@Path(value = "id", encoded = true) Integer id,
                                     @Field("alamat") String alamat,
                                     @Field("rtrw") String rtrw,
                                     @Field("kodepos") String kodepos,
                                     @Field("kelurahan") String kelurahan,
                                     @Field("kecamatan") String kecamatan,
                                     @Field("kabupaten") String kabupaten,
                                     @Field("provinsi") String provinsi);

    @GET(Endpoint.ADMIN_ANGGOTA_KELUARGA)
    Call<AnggotaKeluargaResult> anggotaKeluarga(@Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.ADMIN_ANGGOTA_SHOW)
    Call<SERAnggota> showAnggota(@Path(value = "id", encoded = true) Integer id);

    @GET(Endpoint.ADMIN_ANGGOTA_DELETE)
    Call<CUDAnggota> deleteAnggota(@Path(value = "id", encoded = true) Integer id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.ADMIN_ANGGOTA_CREATE)
    Call<CUDAnggota> createAnggota(@Field("nama") String nama,
                                   @Field("jenis_kelamin") String jenis_kelamin,
                                   @Field("tempat_lahir") String tempat_lahir,
                                   @Field("tanggal_lahir") String tanggal_lahir,
                                   @Field("agama") String agama,
                                   @Field("pendidikan") String pendidikan,
                                   @Field("pekerjaan") String pekerjaan,
                                   @Field("tipe") String tipe,
                                   @Field("ayah") String ayah,
                                   @Field("ibu") String ibu,
                                   @Field("id_keluarga") Integer id_keluarga,
                                   @Field("email_user") String email_user,
                                   @Field("validasi") String validasi);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.ADMIN_ANGGOTA_UPDATE)
    Call<CUDAnggota> updateAnggota(@Path(value = "id", encoded = true) Integer id,
                                   @Field("nama") String nama,
                                   @Field("jenis_kelamin") String jenis_kelamin,
                                   @Field("tempat_lahir") String tempat_lahir,
                                   @Field("tanggal_lahir") String tanggal_lahir,
                                   @Field("agama") String agama,
                                   @Field("pendidikan") String pendidikan,
                                   @Field("pekerjaan") String pekerjaan,
                                   @Field("tipe") String tipe,
                                   @Field("ayah") String ayah,
                                   @Field("ibu") String ibu,
                                   @Field("id_keluarga") Integer id_keluarga,
                                   @Field("validasi") String validasi);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.FIREBASE_SEND_NOTIF)
    Call<CUDAnggota> sendNotifUpdate(@Field("title") String title,
                                     @Field("message") String msg,
                                     @Field("token") String token);
}
