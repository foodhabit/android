package com.leafmoment.app.foodhabit.components.food_selection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leafmoment.app.foodhabit.R;
import com.leafmoment.app.foodhabit.models.Food;
import com.leafmoment.app.foodhabit.providers.BackendProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;

public class FoodSelectionViewHolder extends RecyclerView.ViewHolder {

    public static final int LAYOUT = R.layout.food_selection;

    private int foodIndex;
    private boolean isExpanded;

    private FoodSelectionListener listener;
    private FoodAlternativesAdapter adapter;

    @BindView(R.id.food_snapshot)
    ImageView foodSnapshotView;

    @BindView(R.id.food_name)
    TextView foodNameView;

    @BindView(R.id.food_selection_expanded)
    LinearLayout foodSelectionExpandedView;

    @BindView(R.id.food_queries)
    TagGroup foodQueriesView;

    @BindView(R.id.input_food_search)
    EditText foodSearchInputView;

    @BindView(R.id.food_alternatives)
    RecyclerView foodAlternativesView;

    public FoodSelectionViewHolder(View itemView, FoodSelectionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
        setIsExpanded(false);

        adapter = new FoodAlternativesAdapter();
        foodAlternativesView.setAdapter(adapter);
        foodQueriesView.setOnTagClickListener(this::updateFood);
    }

    @OnClick(R.id.food_selection)
    public void onFoodSelectionClicked() {
        toggleIsExpanded();
    }

    @OnClick(R.id.btn_food_search)
    public void onFoodSearchButtonClicked() {
        updateFood(foodSearchInputView.getText().toString());
    }

    public void setFood(Food food, int index) {
        this.foodIndex = index;
        adapter.setAlternatives(food.getAlternatives());
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

    private void updateFood(String searchTerm) {
        BackendProvider.getInstance().searchFood(searchTerm).subscribe(foodSearchResponse -> {
            listener.onFoodUpdated(foodSearchResponse.getFood(), foodIndex);
        });
    }
}
