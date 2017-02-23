package com.leafmoment.app.foodhabit.components.food_selection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leafmoment.app.foodhabit.R;
import com.leafmoment.app.foodhabit.components.food_search.FoodSearchDialog;
import com.leafmoment.app.foodhabit.components.food_search.FoodSearchDialogListener;
import com.leafmoment.app.foodhabit.models.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;
import timber.log.Timber;

public class FoodSelectionViewHolder extends RecyclerView.ViewHolder implements FoodSearchDialogListener {

    public static final int LAYOUT = R.layout.food_selection;

    private Food food;
    private boolean isExpanded;

    private FoodSelectionListener listener;
    private FoodAlternativesAdapter adapter;
    private FoodSearchDialog searchDialog;

    @BindView(R.id.food_snapshot)
    ImageView foodSnapshotView;

    @BindView(R.id.food_name)
    TextView foodNameView;

    @BindView(R.id.food_selection_expanded)
    LinearLayout foodSelectionExpandedView;

    @BindView(R.id.food_queries)
    TagGroup foodQueriesView;

    @BindView(R.id.food_alternatives)
    RecyclerView foodAlternativesView;

    public FoodSelectionViewHolder(View itemView, FoodSelectionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
        setIsExpanded(false);

        adapter = new FoodAlternativesAdapter();
        foodAlternativesView.setAdapter(adapter);
        foodQueriesView.setOnTagClickListener(tag -> Timber.d(tag));

        searchDialog = new FoodSearchDialog(itemView.getContext(), this);
    }

    @OnClick(R.id.food_selection)
    public void onFoodSelectionClicked() {
        toggleIsExpanded();
        if (listener != null) {
            listener.onFoodSelected(food);
        }
    }

    @OnClick(R.id.btn_food_search)
    public void onFoodSearchButtonClicked() {
        searchDialog.onSearchButtonClicked();
    }


    @Override
    public void onSearchTermAdded(String searchTerm) {
        foodQueriesView.setTags(searchTerm);
    }

    public void setFood(Food food) {
        this.food = food;
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
}
