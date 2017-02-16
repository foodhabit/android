package com.example.hdavidzhu.foodhabit;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.example.hdavidzhu.foodhabit.models.FoodPrediction;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static java.text.DateFormat.getDateTimeInstance;

// TODO: Potentially change the name of this class
public class BackendProvider {

    private static BackendProvider instance;

    public static BackendProvider getInstance() {
        if (instance == null) {
            instance = new BackendProvider();
        }
        return instance;
    }

    private Context context;
    private ApiService apiService;

    private BackendProvider() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://86ab3d08.ngrok.io") // TODO: Change this to a permanent endpoint.
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ApiService getApi() {
        return apiService;
    }

    public Observable<FoodPrediction> analyzeFood(Bitmap foodBitmap) throws IOException {
        return Observable.fromCallable(() -> {
            String timeStamp = getDateTimeInstance().format(new Date());
            String foodImageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File foodImageFile = File.createTempFile(
                    foodImageFileName,
                    ".jpg",
                    storageDir);
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(foodImageFile);
                foodBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return foodImageFile;
        }).concatMap(this::analyzeFood);
    }

    public Observable<FoodPrediction> analyzeFood(File foodImageFile) {
        String uriString = Uri.fromFile(foodImageFile).toString();
        return analyzeFood(
                foodImageFile,
                MediaType.parse(uriString.substring(uriString.lastIndexOf("."))));
    }

    public Observable<FoodPrediction> analyzeFood(File foodImageFile, MediaType mediaType) {
        RequestBody requestFile = RequestBody.create(mediaType, foodImageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", foodImageFile.getName(), requestFile);
        return BackendProvider.getInstance().getApi()
                .postFoodImage(body)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Timber.e(throwable.getMessage()));
    }
}
