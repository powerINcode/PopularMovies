package com.example.powerincode.popularmovies.screens.commonViews;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.powerincode.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by powerman23rus on 10.09.17.
 * Enjoy ;)
 */

public class LoadingImageView extends FrameLayout {
    ImageView mMainImage;
    ProgressBar mLoadingIndicator;

    public boolean mIsLoading;

    public ProgressBar getIsLoading() {
        return mLoadingIndicator;
    }

    public LoadingImageView(@NonNull Context context) {
        super(context);
        setup(context, null, 0);
    }

    public LoadingImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public LoadingImageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_loading_image_view, this, true);
        mMainImage = view.findViewById(R.id.iv_custom_main_image);
        mLoadingIndicator = view.findViewById(R.id.pb_custom_loading_indicator);
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            mMainImage.setVisibility(INVISIBLE);
            mLoadingIndicator.setVisibility(VISIBLE);
        } else {
            mLoadingIndicator.setVisibility(INVISIBLE);
            mMainImage.setVisibility(VISIBLE);
        }
    }

    public void load(String url) {
        setLoading(true);
        Picasso.with(getContext())
                .load(url)
                .into(mMainImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        setLoading(false);
                    }

                    @Override
                    public void onError() {
                        //TODO: Dummy to display the error
                        mMainImage.setImageResource(android.R.drawable.stat_notify_error);
                        setLoading(false);
                    }
                });
    }

}
