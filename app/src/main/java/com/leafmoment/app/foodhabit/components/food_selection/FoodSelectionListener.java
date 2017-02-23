package com.leafmoment.app.foodhabit.components.food_selection;

import com.leafmoment.app.foodhabit.models.Food;

public interface FoodSelectionListener {
    void onFoodUpdated(Food food, int foodIndex);
}
