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
import static com.gsoft.ima.constants.main.TransactionConstants.*;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("users/")
    Call<ResponseBody> getUser(
            @Query(PHONE) String phone
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

    @FormUrlEncoded
    @POST("users/reset_password")
    Call<ResponseBody> resetPassword(
            @Field(EMAIL) String email,
            @Field(PASSWORD) String password
    );


    @FormUrlEncoded
    @POST("transactions/create")
    Call<ResponseBody> createTransaction(
            @Field(NUM_SENDER) String lastname,
            @Field(NUM_RECEIVER) String firstname,
            @Field(AMOUNT) int gender
    );

    @Headers("Content-Type: application/json")
    @GET("transactions/")
    Call<ResponseBody> getTotalSentPending(
            @Query(TOTAL) String phone
    );

    @Headers("Content-Type: application/json")
    @GET("transactions/")
    Call<ResponseBody> getPendingSender(
            @Query(PHONE) String phone
    );

    @FormUrlEncoded
    @POST("transactions/validate")
    Call<ResponseBody> confirmTransaction(
            @Field(ID) int id,
            @Field(AMOUNT) int amount,
            @Field(PHONE) String phone
    );

    @FormUrlEncoded
    @POST("users/update")
    Call<ResponseBody> updateBalance(
            @Field(UPDATE_AMOUNT) int amount,
            @Field(PHONE) String phone
    );
}