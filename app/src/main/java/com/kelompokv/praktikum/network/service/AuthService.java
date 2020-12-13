package com.kelompokv.praktikum.network.service;

import com.kelompokv.praktikum.Endpoint;
import com.kelompokv.praktikum.model.auth.FBToken;
import com.kelompokv.praktikum.model.auth.Login;
import com.kelompokv.praktikum.model.auth.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {
    @FormUrlEncoded
    @POST("login")
    Call<Login> postLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<Register> postRegister(@Field("name") String name, @Field("email") String email,
                                @Field("password") String password, @Field("confirm") String confirm);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.FIREBASE_SET_TOKEN)
    Call<FBToken> setFBToken(@Path(value = "id", encoded = true) Integer id, @Field("token") String token);

    @GET(Endpoint.FIREBASE_DELETE_TOKEN)
    Call<FBToken> deleteFBToken(@Path(value = "id", encoded = true) Integer id);
}
