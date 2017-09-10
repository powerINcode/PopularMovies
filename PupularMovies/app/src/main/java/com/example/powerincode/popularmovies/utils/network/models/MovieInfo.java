package com.example.powerincode.popularmovies.utils.network.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class MovieInfo extends BaseModel implements Parcelable {
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

    public MovieInfo(Parcel parcel) {
        id = parcel.readLong();
        title = parcel.readString();
        video = getBooleanRepresentation(parcel.readByte());
        voteCount = parcel.readLong();
        voteAverage = parcel.readDouble();
        popularity = parcel.readDouble();
        posterPath = parcel.readString();
        originalLanguage = parcel.readString();
        originalTitle = parcel.readString();
        backdropPath = parcel.readString();
        adult = getBooleanRepresentation(parcel.readByte());
        overview = parcel.readString();
        releaseDate = parcel.readString();
        genreIds = (ArrayList<Integer>) parcel.readSerializable();
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

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeByte(getBooleanRepresentation(video));
        parcel.writeLong(voteCount);
        parcel.writeDouble(voteAverage);
        parcel.writeDouble(popularity);
        parcel.writeString(posterPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(backdropPath);
        parcel.writeByte(getBooleanRepresentation(adult));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeSerializable(genreIds);
    }

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel parcel) {
            return new MovieInfo(parcel);
        }

        @Override
        public MovieInfo[] newArray(int i) {
            return new MovieInfo[i];
        }
    };
    //endregion

    private byte getBooleanRepresentation(boolean value) {
        return (byte)(value ? 1 : 0);
    }

    private boolean getBooleanRepresentation(byte value) {
        return value == 1;
    }
}
