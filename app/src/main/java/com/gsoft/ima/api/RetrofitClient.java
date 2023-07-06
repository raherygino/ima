package com.gsoft.ima.api;

import com.gsoft.ima.model.models.Transaction;
import com.gsoft.ima.model.models.User;

import static com.gsoft.ima.constants.network.NetworkConstants.BASE_URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    public static Call<ResponseBody> getUsers(String firstname) {
        return  getApiService().getUser(firstname);
    }

    public static Call<ResponseBody> login(String phone, String password) {
        return  getApiService().login(phone, password);
    }

    public static Call<ResponseBody> forgotPassword(String email) {
        return  getApiService().forgotPassword(email);
    }

    public static Call<ResponseBody> checkCode(String email, String code) {
        return  getApiService().checkCode(email, code);
    }

    public static Call<ResponseBody> resetPassword(String email, String password) {
        return getApiService().resetPassword(email, password);
    }

    public static Call<ResponseBody> createUser(User user) {

        return getApiService().createUser(
                user.lastname,
                user.firstname,
                user.gender,
                user.birthday,
                user.birthplace,
                user.id_card,
                user.country,
                user.city,
                user.phone,
                user.email,
                user.password
        );
    }

    public static Call<ResponseBody> createTransaction(Transaction transaction) {
        return getApiService().createTransaction(
                transaction.numSender,
                transaction.numReceiver,
                transaction.amount
        );
    }


    public static Call<ResponseBody> totalPending(String phone) {
        return getApiService().getTotalSentPending(phone);
    }

}
