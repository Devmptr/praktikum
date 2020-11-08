package com.kelompokv.praktikum.api;

import com.kelompokv.praktikum.model.auth.Login;
import com.kelompokv.praktikum.model.auth.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Interface {
    @FormUrlEncoded
    @POST("login")
    Call<Login> postLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<Register> postRegister(@Field("name") String name, @Field("email") String email,
                                @Field("password") String password, @Field("confirm") String confirm);
}
