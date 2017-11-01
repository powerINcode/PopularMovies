package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.powerincode.popularmovies.R;

import butterknife.ButterKnife;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public abstract class CustomView extends FrameLayout {

    protected abstract int getLayoutId();

    public CustomView(@NonNull Context context) {
        super(context);
        setup(context, null, 0);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs, defStyleAttr);
    }

    private void setup(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(getLayoutId(), this, true);
        ButterKnife.bind(view);

        if(getStyledAttrs() != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, getStyledAttrs());

            try {
                setAttr(ta);
            } catch(Exception e) {
                ta.recycle();
            }
        }
    }

    protected @StyleableRes int[] getStyledAttrs() {
        return null;
    }

    protected void setAttr(TypedArray attrs){
    }

}
