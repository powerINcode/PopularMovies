package com.example.powerincode.pupularmovies.utils.network.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class DiscoverMovie extends BaseModel {
    public int page;
    public long totalResults;
    public int totalPages;
    public ArrayList<MovieInfo> results = new ArrayList<>();

    DiscoverMovie() {
        super();
    }

    public DiscoverMovie(String json) throws JSONException {
        super(json);
    }

    @Override
    public void parse(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        page = jsonObject.getInt("page");
        totalResults = jsonObject.getLong("total_results");
        totalPages = jsonObject.getInt("total_pages");
        JSONArray resultsJsonArray = jsonObject.getJSONArray("results");

        for (int i = 0; i < resultsJsonArray.length(); i++) {
            results.add(new MovieInfo(resultsJsonArray
                    .getJSONObject(i)
                    .toString())
            );
        }
    }
}
