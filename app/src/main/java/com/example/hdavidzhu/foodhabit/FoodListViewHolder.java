package com.example.hdavidzhu.foodhabit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hdavidzhu.foodhabit.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodListViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.list_item_food;

    private FoodItemListener listener;
    private Food food;


    @BindView(R.id.food_name)
    TextView foodNameView;

    public FoodListViewHolder(View itemView, FoodItemListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    @OnClick(R.id.list_item_food)
    public void onListItemClicked() {
        listener.onFoodSelected(food);
    }

    public void setFood(Food food) {
        this.food = food;
        foodNameView.setText(food.name);
    }
}
