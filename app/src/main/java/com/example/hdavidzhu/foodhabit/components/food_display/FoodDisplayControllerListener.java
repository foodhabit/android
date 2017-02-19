package com.example.hdavidzhu.foodhabit.components.food_display;

import android.graphics.Bitmap;

import com.example.hdavidzhu.foodhabit.models.Food;

import java.util.List;

public interface FoodDisplayControllerListener {
    void onFoodImageSelected(Bitmap foodBitmap);
    void onFoodPredictionsReceived(List<Food> predictions);
}
