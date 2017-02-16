package com.example.hdavidzhu.foodhabit;

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
import android.widget.ImageView;

import com.example.hdavidzhu.foodhabit.models.Food;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.text.DateFormat.getDateTimeInstance;

public class MainActivity extends AppCompatActivity implements FoodDisplayControllerListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private File photoFile;
    private Uri photoUri;
    private FoodDisplayController foodDisplayController;
    private FoodListAdapter foodListAdapter;

    @BindView(R.id.cropped_food)
    ImageView croppedFoodImageView;

    @BindView(R.id.food_list)
    RecyclerView foodListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        foodDisplayController = new FoodDisplayController(this);
        foodDisplayController.setFoodSelectedListener(this);

        foodListAdapter = new FoodListAdapter();
        foodListView.setAdapter(foodListAdapter);
    }

    @OnClick(R.id.btn_take_picture)
    void onOpenCameraButtonClicked() {
        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            // Update the view
            foodDisplayController.setPhotoUri(photoUri);
            foodDisplayController.updateView();

            assert photoFile != null;

            // Get food analysis
            BackendProvider
                    .getInstance()
                    .analyzeFood(photoFile)
                    .subscribe(food -> {
                        foodListAdapter.setFoodList(food.predictions);
                    });
        }
    }

    @Override
    public void onFoodImageSelected(Bitmap foodBitmap) {
        croppedFoodImageView.setImageBitmap(foodBitmap);
    }

    @Override
    public void onFoodPredictionsReceived(List<Food> predictions) {
        foodListAdapter.setFoodList(predictions);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createEmptyImageFile();
                photoUri = FileProvider.getUriForFile(
                        this,
                        "com.example.hdavidzhu.foodhabit.fileprovider",
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