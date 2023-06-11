package com.gsoft.ima.api;


import com.gsoft.ima.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("users/")
    Call<ResponseBody> getUser(
            @Query("firstname") String firstname
    );

    @FormUrlEncoded
    @POST("users/create")
    Call<ResponseBody> createUser(
            @Field("lastname") String lastname,
            @Field("firstname") String firstname,
            @Field("gender") String gender,
            @Field("birthday") String birthday,
            @Field("birthplace") String birthplace,
            @Field("id_card") String id_card,
            @Field("country") String country,
            @Field("city") String city,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("password") String password
    );

}