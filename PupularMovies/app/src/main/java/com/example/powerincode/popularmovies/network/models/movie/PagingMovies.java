package com.example.powerincode.popularmovies.network.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.powerincode.popularmovies.network.models.BaseModel;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class PagingMovies extends BaseModel implements Parcelable {
    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public ArrayList<Movie> results = null;

    public PagingMovies() {
    }

    protected PagingMovies(Parcel in) {
        page = in.readByte() == 0x00 ? null : in.readInt();
        totalResults = in.readByte() == 0x00 ? null : in.readInt();
        totalPages = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            results = new ArrayList<>();
            in.readList(results, Movie.class.getClassLoader());
        } else {
            results = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (page == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(page);
        }
        if (totalResults == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalResults);
        }
        if (totalPages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalPages);
        }
        if (results == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(results);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PagingMovies> CREATOR = new Parcelable.Creator<PagingMovies>() {
        @Override
        public PagingMovies createFromParcel(Parcel in) {
            return new PagingMovies(in);
        }

        @Override
        public PagingMovies[] newArray(int size) {
            return new PagingMovies[size];
        }
    };
}
