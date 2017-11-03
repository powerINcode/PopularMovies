package com.example.powerincode.popularmovies.network.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.powerincode.popularmovies.network.models.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review extends BaseModel implements Parcelable {
    @SerializedName("id")
    @Expose
    public final String id;
    @SerializedName("author")
    @Expose
    public final String author;
    @SerializedName("content")
    @Expose
    public final String content;
    @SerializedName("url")
    @Expose
    public final String url;

    protected Review(Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

}