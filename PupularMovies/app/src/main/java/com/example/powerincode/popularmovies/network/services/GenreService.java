package com.example.powerincode.popularmovies.network.services;

import com.example.powerincode.popularmovies.network.models.genre.GenreList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by powerman23rus on 31.10.17.
 * Enjoy ;)
 */

public interface GenreService {
    @GET("genre/movie/list")
    Call<GenreList> listGenres();
}
