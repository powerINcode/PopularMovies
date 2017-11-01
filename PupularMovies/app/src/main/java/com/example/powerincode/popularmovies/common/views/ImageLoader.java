package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.powerincode.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class ImageLoader extends CustomView {
    public interface ImageLoaderEvent {
        void onComplete();
    }

    @BindView(R.id.iv_image)
    ImageViewPoster mImageView;

    @BindView(R.id.pb_image_loader)
    ProgressBar mImageLoader;

    private ImageLoaderEvent imageLoaderEventListener;


    @Override
    protected int getLayoutId() {
        return R.layout.view_image_loader;
    }

    public ImageLoader(@NonNull Context context) {
        super(context);
    }

    public ImageLoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageLoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(ImageLoaderEvent e) {
        imageLoaderEventListener = e;
    }

    public void initialization(String url) {
        mImageView.setVisibility(INVISIBLE);
        mImageLoader.setVisibility(VISIBLE);
        Picasso.with(getContext()).load(url).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                loadingFinish();
            }

            @Override
            public void onError() {
                loadingFinish();
            }
        });
    }

    private void loadingFinish() {
        mImageLoader.setVisibility(INVISIBLE);
        mImageView.setVisibility(VISIBLE);

        if (imageLoaderEventListener != null) {
            imageLoaderEventListener.onComplete();
        }
    }

    @Override
    protected int[] getStyledAttrs() {
        return R.styleable.ImageLoader;
    }

    @Override
    protected void setAttr(TypedArray attrs) {
        int scale = attrs.getInt(R.styleable.ImageLoader_scale, 0);

        if(scale == 1) {
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else if(scale == 2) {
            mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        }
    }
}
