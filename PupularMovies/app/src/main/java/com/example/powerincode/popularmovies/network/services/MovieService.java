package com.example.powerincode.popularmovies.network.services;

import com.example.powerincode.popularmovies.network.models.PagingMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public interface MovieService {
    @GET("movie/popular")
    Call<PagingMovies> listPopularMovies(@Query("page") Integer page);
}
