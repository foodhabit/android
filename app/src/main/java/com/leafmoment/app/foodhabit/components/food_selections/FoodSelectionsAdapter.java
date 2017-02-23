package com.leafmoment.app.foodhabit.components.food_selections;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leafmoment.app.foodhabit.components.food_selection.FoodSelectionListener;
import com.leafmoment.app.foodhabit.components.food_selection.FoodSelectionViewHolder;
import com.leafmoment.app.foodhabit.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodSelectionsAdapter
        extends RecyclerView.Adapter<FoodSelectionViewHolder>
        implements FoodSelectionListener, ItemTouchHelperAdapter {
    private List<Food> foodList = new ArrayList<>();

    @Override
    public FoodSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater
                .from(parent.getContext())
                .inflate(FoodSelectionViewHolder.LAYOUT, parent, false);
        return new FoodSelectionViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(FoodSelectionViewHolder holder, int position) {
        holder.setFood(foodList.get(position), position);
    }

    @Override
    public void onFoodUpdated(Food food, int foodIndex) {
        foodList.set(foodIndex, food);
        notifyItemChanged(foodIndex);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    public void addFood(Food food) {
        foodList.add(food);
        notifyItemInserted(foodList.size() - 1);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Food food = foodList.remove(fromPosition);
        foodList.add(toPosition, food);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        foodList.remove(position);
        notifyItemRemoved(position);
    }
}
