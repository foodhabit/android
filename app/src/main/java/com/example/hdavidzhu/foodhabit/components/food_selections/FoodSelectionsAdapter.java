package com.example.hdavidzhu.foodhabit.components.food_selections;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hdavidzhu.foodhabit.components.food_selection.FoodSelectionListener;
import com.example.hdavidzhu.foodhabit.components.food_selection.FoodSelectionViewHolder;
import com.example.hdavidzhu.foodhabit.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodSelectionsAdapter extends RecyclerView.Adapter<FoodSelectionViewHolder> {

    private List<Food> foodList = new ArrayList<>();
    private FoodSelectionListener foodItemListener;

    @Override
    public FoodSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(FoodSelectionViewHolder.LAYOUT, parent, false);
        return new FoodSelectionViewHolder(view, foodItemListener);
    }

    @Override
    public void onBindViewHolder(FoodSelectionViewHolder holder, int position) {
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

    public void setFoodItemListener(FoodSelectionListener listener) {
        foodItemListener = listener;
    }
}
