package com.example.hdavidzhu.foodhabit.providers;

import com.example.hdavidzhu.foodhabit.models.FoodPrediction;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    // Following this tutorial:
    // https://futurestud.io/tutorials/retrofit-2-how-to-upload-files-to-server

    @Multipart
    @POST("food")
    Observable<FoodPrediction> postFoodImage(@Part MultipartBody.Part image);
}
