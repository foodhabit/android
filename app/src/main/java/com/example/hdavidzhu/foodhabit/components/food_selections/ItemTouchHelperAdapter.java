package com.example.hdavidzhu.foodhabit.components.food_selections;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
