package com.example.hdavidzhu.foodhabit.components.food_selection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hdavidzhu.foodhabit.R;
import com.example.hdavidzhu.foodhabit.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAlternativeViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.food_alternative;

    @BindView(R.id.food_alternative_name)
    TextView nameView;

    public FoodAlternativeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setAlternative(Food food) {
        nameView.setText(food.name);
    }
}
