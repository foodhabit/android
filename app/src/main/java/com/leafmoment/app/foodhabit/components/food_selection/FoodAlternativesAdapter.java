package com.leafmoment.app.foodhabit.components.food_selection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leafmoment.app.foodhabit.components.food_alternative.FoodAlternativeListener;
import com.leafmoment.app.foodhabit.components.food_alternative.FoodAlternativeViewHolder;
import com.leafmoment.app.foodhabit.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAlternativesAdapter extends RecyclerView.Adapter<FoodAlternativeViewHolder> {

    private final List<Food> alternatives = new ArrayList<>();
    private FoodAlternativeListener listener;

    public FoodAlternativesAdapter(FoodAlternativeListener listener) {
        this.listener = listener;
    }

    @Override
    public FoodAlternativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(FoodAlternativeViewHolder.LAYOUT, parent, false);
        return new FoodAlternativeViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(FoodAlternativeViewHolder holder, int position) {
        holder.setAlternative(alternatives.get(position));
    }

    @Override
    public int getItemCount() {
        return alternatives.size();
    }

    public void setAlternatives(List<Food> alternatives) {
        this.alternatives.clear();
        this.alternatives.addAll(alternatives);
        notifyDataSetChanged();
    }
}
