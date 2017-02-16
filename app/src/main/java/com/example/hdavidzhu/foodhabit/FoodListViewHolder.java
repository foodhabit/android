package com.example.hdavidzhu.foodhabit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hdavidzhu.foodhabit.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodListViewHolder extends RecyclerView.ViewHolder {

    private Food food;

    @BindView(R.id.food_name)
    TextView foodNameView;

    public FoodListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setFood(Food food) {
        this.food = food;
        foodNameView.setText(food.name);
    }
}
