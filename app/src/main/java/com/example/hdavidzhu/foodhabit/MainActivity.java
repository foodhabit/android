package com.example.hdavidzhu.foodhabit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String currentPhotoPath;
    private File photoFile;
    private Uri photoURI;

    @BindView(R.id.picture)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    @OnClick(R.id.btn_take_picture)
    void onOpenCameraButtonClicked() {
        dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        // Create an photoFile file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photoFile = File.createTempFile(
                imageFileName, // Prefix
                ".jpg",        // Suffix
                storageDir     // Directory
        );
        currentPhotoPath = photoFile.getAbsolutePath();
        return photoFile;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.hdavidzhu.foodhabit.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void setPic() {
        // Get dimensions of the view
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmpOptions);
        int photoW = bmpOptions.outWidth;
        int photoH = bmpOptions.outHeight;

        // Determine how much to scale down the photoFile
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the photoFile file into a bitmap sized to fill the view
        bmpOptions.inJustDecodeBounds = false;
        bmpOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmpOptions);
        imageView.setImageBitmap(bitmap);

        // Send pic
        // TODO: These methods should be refactored to be single purpose
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(photoURI)), photoFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", photoFile.getName(), requestFile);
        BackendProvider.getInstance().getApi()
                .postFoodImage(body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    Timber.e(throwable.getMessage());
                    return null;
                })
                .subscribe(food -> {
                    Timber.e(String.valueOf(food.predictions));
                });
    }
}