package com.example.powerincode.popularmovies.utils.network.services;

import android.net.Uri;

import com.example.powerincode.popularmovies.utils.Configuration;

/**
 * Created by powerman23rus on 09.09.17.
 * Enjoy ;)
 */

public class Common {
    public static final Common shared = new Common();

    public String getPosterPath(String posterPath, String resolution) {
        return Uri.parse(Configuration.api.BASE_POSTERS_URL)
                .buildUpon()
                .appendPath(resolution)
                .appendPath(posterPath)
                .build()
                .toString();
    }
}
