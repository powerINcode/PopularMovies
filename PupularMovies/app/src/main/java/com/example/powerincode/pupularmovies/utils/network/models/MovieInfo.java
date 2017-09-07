package com.example.powerincode.pupularmovies.utils.network.models;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class MovieInfo extends BaseModel {
    public Integer id;
    public String title;
    public Boolean video;
    public Integer voteCount;
    public Double voteAverage;
    public Double popularity;
    public String posterPath;
    public String originalLanguage;
    public String originalTitle;
    public ArrayList<Integer> genreIds = new ArrayList<>();
    public String backdropPath;
    public Boolean adult;
    public String overview;
    public String releaseDate;

    @Override
    public void parse(String json) {
    }
}
