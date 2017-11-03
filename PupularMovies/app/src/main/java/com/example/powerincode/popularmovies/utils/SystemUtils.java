package com.example.powerincode.popularmovies.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class SystemUtils {
    public static final SystemUtils shared = new SystemUtils();

    public int pxToDp(int pixels) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        return (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, pixels, displaymetrics );
    }
}
