package com.example.hdavidzhu.foodhabit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO: Potentially change the name of this class
public class BackendProvider {

    private static BackendProvider instance;

    public static BackendProvider getInstance() {
        if (instance == null) {
            instance = new BackendProvider();
        }
        return instance;
    }

    private ApiService apiService;

    private BackendProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://92fc9bdb.ngrok.io/") // TODO: Change this to a permanent endpoint.
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApi() {
        return apiService;
    }
}
