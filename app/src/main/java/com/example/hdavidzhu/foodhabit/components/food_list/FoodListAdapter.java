package com.example.hdavidzhu.foodhabit.components.food_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hdavidzhu.foodhabit.components.food_item.FoodItemListener;
import com.example.hdavidzhu.foodhabit.components.food_item.FoodItemViewHolder;
import com.example.hdavidzhu.foodhabit.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodItemViewHolder> {

    private List<Food> foodList = new ArrayList<>();
    private FoodItemListener foodItemListener;

    @Override
    public FoodItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(FoodItemViewHolder.LAYOUT, parent, false);
        return new FoodItemViewHolder(view, foodItemListener);
    }

    @Override
    public void onBindViewHolder(FoodItemViewHolder holder, int position) {
        holder.setFood(foodList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    public void setFoodItemListener(FoodItemListener listener) {
        foodItemListener = listener;
    }
}
