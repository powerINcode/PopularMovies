package com.example.powerincode.popularmovies.utils;

import android.content.Context;

import com.example.powerincode.popularmovies.R;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class UriHelper {
    public static final UriHelper shared = new UriHelper();

    public String buildPosterUrl(Context context, String postPath) {
        return Configuration.DOMAIN_URL + "w" + context.getResources().getInteger(R.integer.poster_size) + postPath;
    }
}
