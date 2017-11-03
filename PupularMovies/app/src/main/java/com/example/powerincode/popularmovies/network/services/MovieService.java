package com.example.powerincode.popularmovies.network.services;

import com.example.powerincode.popularmovies.network.models.movie.PlayingMoviesList;
import com.example.powerincode.popularmovies.network.models.movie.PagingMovies;
import com.example.powerincode.popularmovies.network.models.movie.ReviewList;
import com.example.powerincode.popularmovies.network.models.movie.VideoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
    Call<PlayingMoviesList> listPlayingMovies(@Query("page") Integer page);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewList> listReviews(@Path("movie_id") long movieId);

    @GET("movie/{movie_id}/videos")
    Call<VideoList> listVideos(@Path("movie_id") long movieId);
}
