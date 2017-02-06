package com.example.hdavidzhu.foodhabit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
                .baseUrl("https://google.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApi() {
        return apiService;
    }
}
