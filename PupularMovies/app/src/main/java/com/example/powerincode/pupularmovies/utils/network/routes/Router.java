package com.example.powerincode.pupularmovies.utils.network.routes;

import android.net.Uri;

import com.example.powerincode.pupularmovies.utils.Configuration;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

abstract class Router {
    protected abstract String getRoute();

    Uri getMainUri() {
        return Uri.parse(Configuration.api.BASE_URL)
                .buildUpon()
                .appendEncodedPath(getRoute())
                .appendQueryParameter("api_key", Configuration.api.MOVIE_DB_API_KEY)
                .build();
    }
}
