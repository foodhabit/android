package com.leafmoment.app.foodhabit.components.food_search;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;

import com.leafmoment.app.foodhabit.R;

public class FoodSearchDialog {

    private AlertDialog.Builder builder;

    public FoodSearchDialog(Context context, FoodSearchDialogListener listener) {

        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.search);

        // Set up the input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.ok, (dialogInterface, which) -> {
            String searchTerm = input.getText().toString();
            listener.onSearchTermAdded(searchTerm);
        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, which) -> dialogInterface.cancel());
    }

    public void onSearchButtonClicked() {
        builder.show();
    }
}
