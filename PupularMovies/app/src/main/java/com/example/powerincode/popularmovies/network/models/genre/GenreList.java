package com.example.powerincode.popularmovies.network.models.genre;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreList {
    @SerializedName("genres")
    public List<Genre> genres;

}