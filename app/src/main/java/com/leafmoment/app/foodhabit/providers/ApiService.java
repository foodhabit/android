package com.leafmoment.app.foodhabit.providers;

import com.leafmoment.app.foodhabit.models.FoodSearchResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @GET("food")
    Observable<FoodSearchResponse> search(@Query("search") String searchTerm);

    // Following this tutorial:
    // https://futurestud.io/tutorials/retrofit-2-how-to-upload-files-to-server
    @Multipart
    @POST("food")
    Observable<FoodSearchResponse> postFoodImage(@Part MultipartBody.Part image);
}
