package com.leafmoment.app.foodhabit.components.food_alternative;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leafmoment.app.foodhabit.R;
import com.leafmoment.app.foodhabit.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodAlternativeViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.food_alternative;

    private Food food;
    private FoodAlternativeListener listener;

    @BindView(R.id.food_alternative_name)
    TextView nameView;

    public FoodAlternativeViewHolder(View itemView, FoodAlternativeListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    @OnClick(R.id.food_alternative)
    public void onClicked() {
        listener.onAlternativeClicked(food);
    }

    public void setAlternative(Food food) {
        this.food = food;
        nameView.setText(food.name);
    }
}
