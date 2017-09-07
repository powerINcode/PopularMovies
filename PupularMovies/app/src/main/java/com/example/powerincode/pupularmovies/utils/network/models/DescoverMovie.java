package com.example.powerincode.pupularmovies.utils.network.models;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class DescoverMovie extends BaseModel {
    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public ArrayList<MovieInfo> results = new ArrayList<>();

    @Override
    public void parse(String json) {
    }
}
