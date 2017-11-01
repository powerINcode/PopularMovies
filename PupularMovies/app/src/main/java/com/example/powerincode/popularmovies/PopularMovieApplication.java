package com.example.powerincode.popularmovies;

import android.app.Application;

import com.example.powerincode.popularmovies.network.models.genre.Genre;

import java.util.List;

/**
 * Created by powerman23rus on 31.10.17.
 * Enjoy ;)
 */

public class PopularMovieApplication extends Application {
    public static List<Genre> sGenres;
}
