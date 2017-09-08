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
        return "movie";
    }

    public Uri getMovies() {
        return getMainUri();
    }

    public Uri getPopularMovies() {
        return getMovies()
                .buildUpon()
                .appendEncodedPath(PATHS.POPULARITY)
                .build();
    }

    public Uri getTopRatedMovies() {
        return getMovies()
                .buildUpon()
                .appendEncodedPath(PATHS.RATING)
                .build();
    }

    private static class PATHS {
        static final String POPULARITY = "popular";
        static final String RATING = "top_rated";
    }
}
