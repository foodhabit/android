package com.example.hdavidzhu.foodhabit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.davemorrissey.labs.subscaleview.ImageSource;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FoodDisplayController {

    private Context context;
    private GestureDetector gestureDetector;

    @BindView(R.id.picture)
    PinView imageView;

    private Uri photoUri;

    public FoodDisplayController(Context context) {
        this.context = context;
        ButterKnife.bind(this, (Activity) context);
        gestureDetector = createGestureDetector();
        imageView.setOnTouchListener((view, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public void updateView() {
        imageView.setImage(ImageSource.uri(photoUri));
    }

    private GestureDetector createGestureDetector() {
        return new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (imageView.isReady()) {
                    PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());
                    Timber.d(sCoord.toString()); // TODO: Remove
                    try {
                        Bitmap sourceBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                        // TODO: Choose a good crop size.
                        Bitmap croppedImage = Bitmap.createBitmap(sourceBitmap, (int) e.getX(), (int) e.getY(), 200, 200);
                        BackendProvider.getInstance().analyzeFood(croppedImage).subscribe(food -> {
                            Timber.i(food.predictions.toString());
                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    imageView.setPin(sCoord);
                }
                return true;
            }
        });
    }
}
