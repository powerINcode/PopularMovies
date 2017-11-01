package com.example.powerincode.popularmovies.network.models.movie;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class PagingMovies {
    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public ArrayList<Movie> results = null;
}
