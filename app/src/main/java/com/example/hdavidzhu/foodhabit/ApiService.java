package com.example.hdavidzhu.foodhabit;

import com.example.hdavidzhu.foodhabit.models.Food;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {
    @POST("food")
    Observable<Food> postFoodImage();
}
