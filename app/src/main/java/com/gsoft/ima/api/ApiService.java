package com.gsoft.ima.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.gsoft.ima.constants.main.FormConstants.*;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("users/")
    Call<ResponseBody> getUser(
            @Query(FIRSTNAME) String firstname
    );

    @FormUrlEncoded
    @POST("users/create")
    Call<ResponseBody> createUser(
            @Field(LASTNAME) String lastname,
            @Field(FIRSTNAME) String firstname,
            @Field(GENDER) String gender,
            @Field(BIRTHDAY) String birthday,
            @Field(BIRTHPLACE) String birthplace,
            @Field(ID_CARD) String id_card,
            @Field(COUNTRY) String country,
            @Field(CITY) String city,
            @Field(PHONE) String phone,
            @Field(EMAIL) String email,
            @Field(PASSWORD) String password
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<ResponseBody> login(
            @Field(PHONE) String phone,
            @Field(PASSWORD) String password
    );

    @FormUrlEncoded
    @POST("users/forgot")
    Call<ResponseBody> forgotPassword(
            @Field(EMAIL) String email
    );

    @FormUrlEncoded
    @POST("users/check_code")
    Call<ResponseBody> checkCode(
            @Field(EMAIL) String email,
            @Field(CODE) String code
    );

}