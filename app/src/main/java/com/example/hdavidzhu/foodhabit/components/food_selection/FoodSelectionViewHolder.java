package com.example.hdavidzhu.foodhabit.components.food_selection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hdavidzhu.foodhabit.R;
import com.example.hdavidzhu.foodhabit.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

public class FoodSelectionViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.food_selection;

    private FoodSelectionListener listener;
    private Food food;
    private boolean isExpanded;


    @BindView(R.id.food_snapshot)
    ImageView foodSnapshotView;

    @BindView(R.id.food_name)
    TextView foodNameView;

    @BindView(R.id.food_selection_expanded)
    LinearLayout foodSelectionExpandedView;

    @BindView(R.id.food_queries)
    TagGroup foodQueriesView;

    public FoodSelectionViewHolder(View itemView, FoodSelectionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
        setIsExpanded(false);
    }

    @OnClick(R.id.food_selection)
    public void onFoodSelectionClicked() {
        toggleIsExpanded();
        if (listener != null) {
            listener.onFoodSelected(food);
        }
    }

    public void setFood(Food food) {
        this.food = food;
        foodSnapshotView.setImageBitmap(food.getSnapshot());
        foodNameView.setText(food.getName());
        foodQueriesView.setTags(food.queries);
    }

    private void setIsExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
        foodSelectionExpandedView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    private void toggleIsExpanded() {
        setIsExpanded(!isExpanded);
    }
}
