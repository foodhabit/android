package com.leafmoment.app.foodhabit.components.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.leafmoment.app.foodhabit.R;
import com.leafmoment.app.foodhabit.components.food_display.FoodDisplayController;
import com.leafmoment.app.foodhabit.components.food_display.FoodDisplayControllerListener;
import com.leafmoment.app.foodhabit.components.food_selections.FoodSelectionsAdapter;
import com.leafmoment.app.foodhabit.components.food_selections.FoodSelectionsTouchHelperCallback;
import com.leafmoment.app.foodhabit.models.Food;
import com.leafmoment.app.foodhabit.providers.BackendProvider;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.text.DateFormat.getDateTimeInstance;

public class MainActivity extends AppCompatActivity implements FoodDisplayControllerListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private File photoFile;
    private Uri photoUri;
    private FoodDisplayController foodDisplayController;
    private FoodSelectionsAdapter foodSelectionsAdapter;

    @BindView(R.id.food_list)
    RecyclerView foodSelectionsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        foodDisplayController = new FoodDisplayController(this);
        foodDisplayController.setFoodSelectedListener(this);

        foodSelectionsAdapter = new FoodSelectionsAdapter();
        foodSelectionsView.setAdapter(foodSelectionsAdapter);
        ItemTouchHelper.Callback callback = new FoodSelectionsTouchHelperCallback(foodSelectionsAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(foodSelectionsView);
    }

    @OnClick(R.id.btn_take_picture)
    void onOpenCameraButtonClicked() {
        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            foodDisplayController.setPhotoUri(photoUri); // Update the food display
        }
    }

    @Override
    public void onFoodImageSelected(Bitmap foodBitmap) {
        try {
            BackendProvider.getInstance().analyzeFood(foodBitmap).subscribe(foodPrediction -> {
                Food food = foodPrediction.getFood();
                food.setSnapshot(foodBitmap);
                foodSelectionsAdapter.addFood(food);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createEmptyImageFile();
                photoUri = FileProvider.getUriForFile(
                        this,
                        "com.leafmoment.app.foodhabit.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createEmptyImageFile() throws IOException {
        // Create an photoFile file name
        String timeStamp = getDateTimeInstance().format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photoFile = File.createTempFile(
                imageFileName, // Prefix
                ".jpg",        // Suffix
                storageDir     // Directory
        );
        return photoFile;
    }
}