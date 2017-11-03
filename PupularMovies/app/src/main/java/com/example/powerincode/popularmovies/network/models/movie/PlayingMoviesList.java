package com.example.powerincode.popularmovies.network.models.movie;

import java.util.List;

import com.example.powerincode.popularmovies.network.models.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayingMoviesList extends BaseModel {

    @SerializedName("results")
    @Expose
    public final List<Movie> results = null;
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    @SerializedName("dates")
    @Expose
    public Dates dates;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

}