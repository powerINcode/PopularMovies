package com.example.powerincode.popularmovies.utils.network.routes;

import android.net.Uri;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public class RouterDiscover extends Router {

    public static final RouterDiscover shared = new RouterDiscover();

    @Override
    public String getRoute() {
        return "discover/movie";
    }

    public Uri getMovies() {
        return getMainUri();
    }

    public Uri getPopularMovies() {
        return getMovies()
                .buildUpon()
                .appendQueryParameter(PARAM.SORT_BY, PARAM_VALUE.POPULARITY)
                .build();
    }

    private static class PARAM {
        static final String SORT_BY = "sot_by";
    }

    private static class PARAM_VALUE {
        static final String POPULARITY = "popularity.desc";
    }
}
