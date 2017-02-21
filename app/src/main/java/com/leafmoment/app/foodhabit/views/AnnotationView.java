package com.leafmoment.app.foodhabit.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class AnnotationView extends SubsamplingScaleImageView {

    private PointF corner1;
    private PointF corner2;

    private final Paint paint = new Paint();

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    public AnnotationView(Context context) {
        super(context, null);
    }

    public AnnotationView(Context context, AttributeSet attr) {
        super(context, attr);
        initialize();
    }

    private void initialize() {
    }

    public void setCorner1(PointF corner1) {
        this.corner1 = corner1;
    }

    public void setCorner2(PointF corner2) {
        this.corner2 = corner2;
        invalidate();
    }

    public PointF getUpperLeft() {
        return new PointF(Math.min(corner1.x, corner2.x), Math.min(corner1.y, corner2.y));
    }

    public PointF getLowerRight() {
        return new PointF(Math.max(corner1.x, corner2.x), Math.max(corner1.y, corner2.y));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isReady()) {
            return;
        }
        if (corner1 == null || corner2 == null) {
            return;
        }
        PointF upperLeft = sourceToViewCoord(getUpperLeft());
        PointF lowerRight = sourceToViewCoord(getLowerRight());
        canvas.drawRect(upperLeft.x, upperLeft.y, lowerRight.x, lowerRight.y, paint);
    }
}
