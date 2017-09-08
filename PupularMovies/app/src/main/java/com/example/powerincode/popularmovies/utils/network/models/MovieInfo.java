package com.example.powerincode.popularmovies.utils.network.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class MovieInfo extends BaseModel {
    private final String mClassName = MovieInfo.class.getSimpleName();

    public long id;
    public String title;
    public boolean video;
    public long voteCount;
    public double voteAverage;
    public double popularity;
    public String posterPath;
    public String originalLanguage;
    public String originalTitle;
    public ArrayList<Integer> genreIds;
    public String backdropPath;
    public boolean adult;
    public String overview;
    public String releaseDate;

    MovieInfo() {
        super();
    }

    MovieInfo(String json) throws JSONException {
        super(json);
    }

    @Override
    public void parse(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            id = jsonObject.getLong("id");
            title = jsonObject.getString("title");
            video = jsonObject.getBoolean("video");
            voteCount = jsonObject.getLong("vote_count");
            voteAverage = jsonObject.getLong("vote_average");
            popularity = jsonObject.getDouble("popularity");
            posterPath = jsonObject.getString("poster_path").replace("/","");
            originalLanguage = jsonObject.getString("original_language");
            originalTitle = jsonObject.getString("original_title");
            backdropPath = jsonObject.getString("backdrop_path");
            adult = jsonObject.getBoolean("adult");
            overview = jsonObject.getString("overview");
            releaseDate = jsonObject.getString("release_date");
            genreIds = new ArrayList<>();

            JSONArray genreIdsJsonArray = jsonObject.getJSONArray("genre_ids");
            for (int i = 0; i < genreIdsJsonArray.length(); i++) {
                this.genreIds.add(genreIdsJsonArray.getInt(i));
            }

        } catch (JSONException e) {
            Log.e(getClassName(), e.getMessage());
        }

    }
}
