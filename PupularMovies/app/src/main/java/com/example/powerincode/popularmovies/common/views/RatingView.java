package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.powerincode.popularmovies.R;

import java.util.List;

import butterknife.BindViews;

/**
 * Created by powerman23rus on 31.10.17.
 * Enjoy ;)
 */

public class RatingView extends CustomView {
    private final int MAX_RATING = 5;

    @BindViews({ R.id.iv_one_rating, R.id.iv_two_rating, R.id.iv_three_rating, R.id.iv_four_rating, R.id.iv_five_rating })
    List<ImageView> mImageViews;


    @Override
    protected int getLayoutId() {
        return R.layout.view_rating;
    }

    public RatingView(@NonNull Context context) {
        super(context);
    }

    public RatingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initialize(float rating) {
        int i = 0;
        while (i < rating && i < mImageViews.size()) {
            mImageViews.get(i++).setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_active, null));
        }
    }
}
