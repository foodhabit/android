package com.example.hdavidzhu.foodhabit.components.food_display;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.hdavidzhu.foodhabit.R;
import com.example.hdavidzhu.foodhabit.providers.BackendProvider;
import com.example.hdavidzhu.foodhabit.views.AnnotationView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FoodDisplayController {

    private FoodDisplayControllerListener listener;

    @BindView(R.id.picture)
    AnnotationView imageView;

    private Uri photoUri;

    private PointF downSCoord;
    private PointF upSCoord;

    public FoodDisplayController(Context context) {
        ButterKnife.bind(this, (Activity) context);
        imageView.setOnTouchListener((view, motionEvent) -> {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            int action = MotionEventCompat.getActionMasked(motionEvent);
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    Timber.d("Action was DOWN");

                    downSCoord = imageView.viewToSourceCoord(motionEvent.getX(), motionEvent.getY());
                    Timber.d(downSCoord.toString());
                    imageView.setCorner1(downSCoord);

                    return true;
                case (MotionEvent.ACTION_MOVE):
                    Timber.d("Action was MOVE");

                    upSCoord = imageView.viewToSourceCoord(motionEvent.getX(), motionEvent.getY());
                    Timber.d(upSCoord.toString());
                    imageView.setCorner2(upSCoord);

                    return true;
                case (MotionEvent.ACTION_UP):
                    Timber.d("Action was UP");

                    upSCoord = imageView.viewToSourceCoord(motionEvent.getX(), motionEvent.getY());
                    Timber.d(upSCoord.toString());
                    imageView.setCorner2(upSCoord);

                    if (imageView.isReady()) {
                        try {
                            Bitmap sourceBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                            // TODO: Choose a good crop size.
                            Bitmap croppedImage = Bitmap.createBitmap(
                                    sourceBitmap,
                                    (int) downSCoord.x,
                                    (int) downSCoord.y,
                                    (int) (upSCoord.x - downSCoord.x),
                                    (int) (upSCoord.y - downSCoord.y));
                            listener.onFoodImageSelected(croppedImage);
                            BackendProvider.getInstance().analyzeFood(croppedImage).subscribe(food -> {
                                listener.onFoodPredictionsReceived(food.predictions);
                            });
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    return true;
                default:
                    return true;
            }
        });
    }

    public void setFoodSelectedListener(FoodDisplayControllerListener foodDisplayControllerListener) {
        listener = foodDisplayControllerListener;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public void updateView() {
        imageView.setImage(ImageSource.uri(photoUri));
    }
}
