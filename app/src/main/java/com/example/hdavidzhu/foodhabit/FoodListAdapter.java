package com.example.hdavidzhu.foodhabit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hdavidzhu.foodhabit.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListViewHolder> {

    private List<Food> foodList = new ArrayList<>();

    @Override
    public FoodListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_food, parent, false);
        return new FoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodListViewHolder holder, int position) {
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
}
