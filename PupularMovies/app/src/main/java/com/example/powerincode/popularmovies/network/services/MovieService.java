package com.example.powerincode.popularmovies.network.services;

import com.example.powerincode.popularmovies.network.models.movie.MoviePlayingList;
import com.example.powerincode.popularmovies.network.models.movie.PagingMovies;

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

    @GET("movie/top_rated")
    Call<PagingMovies> listTopRated(@Query("page") Integer page);

    @GET("movie/now_playing")
    Call<MoviePlayingList> listPlayingMovies(@Query("page") Integer page);
}
