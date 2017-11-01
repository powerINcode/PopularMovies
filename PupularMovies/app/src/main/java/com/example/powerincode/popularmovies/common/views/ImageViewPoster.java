package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

public class ImageViewPoster extends android.support.v7.widget.AppCompatImageView {
    public ImageViewPoster(Context context) {
        super(context);
    }

    public ImageViewPoster(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewPoster(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        setMatrix();
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setMatrix();
    }

    private void setup() {
        setScaleType(ScaleType.MATRIX);
    }

    private void setMatrix() {
        if (getDrawable() == null) {
            return;
        }

        if (getScaleType() == ScaleType.MATRIX) {
            Matrix matrix = getImageMatrix();
            float scaleFactor = getWidth() / (float) getDrawable().getIntrinsicWidth();
            matrix.setScale(scaleFactor, scaleFactor, 0, 0);
            setImageMatrix(matrix);
        }
    }
}