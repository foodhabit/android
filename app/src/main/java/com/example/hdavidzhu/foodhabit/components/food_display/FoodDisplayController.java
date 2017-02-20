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
import com.example.hdavidzhu.foodhabit.views.AnnotationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FoodDisplayController {

    private Uri photoUri;
    private PointF downSCoord;
    private PointF upSCoord;

    private FoodDisplayControllerListener listener;

    @BindView(R.id.picture)
    AnnotationView imageView;

    public FoodDisplayController(Context context) {
        ButterKnife.bind(this, (Activity) context);
        imageView.setOnTouchListener((view, motionEvent) -> {

            // Prevents the parent from capturing any events.
            // This way, the touch events won't cause the parent to scroll.
            view.getParent().requestDisallowInterceptTouchEvent(true);

            int action = MotionEventCompat.getActionMasked(motionEvent);
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    downSCoord = imageView.viewToSourceCoord(motionEvent.getX(), motionEvent.getY());
                    imageView.setCorner1(downSCoord);
                    return true;

                case (MotionEvent.ACTION_MOVE):
                    upSCoord = imageView.viewToSourceCoord(motionEvent.getX(), motionEvent.getY());
                    imageView.setCorner2(upSCoord);
                    return true;

                case (MotionEvent.ACTION_UP):
                    upSCoord = imageView.viewToSourceCoord(motionEvent.getX(), motionEvent.getY());
                    imageView.setCorner2(upSCoord);
                    if (imageView.isReady()) {
                        Observable.fromCallable(() -> {
                            Bitmap sourceBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
                            // TODO: Choose a good crop size.
                            PointF upperLeft = imageView.getUpperLeft();
                            PointF lowerRight = imageView.getLowerRight();
                            return Bitmap.createBitmap(
                                    sourceBitmap,
                                    (int) upperLeft.x,
                                    (int) upperLeft.y,
                                    (int) (lowerRight.x - upperLeft.x),
                                    (int) (lowerRight.y - upperLeft.y));
                        }).subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(croppedImage -> listener.onFoodImageSelected(croppedImage));
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
        imageView.setImage(ImageSource.uri(photoUri));
    }
}
