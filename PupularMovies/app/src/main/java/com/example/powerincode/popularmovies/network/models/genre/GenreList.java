package com.example.powerincode.popularmovies.network.models.genre;

import com.example.powerincode.popularmovies.network.models.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreList extends BaseModel {
    @SerializedName("genres")
    public List<Genre> genres;

}