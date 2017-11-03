package com.example.powerincode.popularmovies.network.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.powerincode.popularmovies.network.models.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class Video extends BaseModel implements Parcelable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("iso_639_1")
    @Expose
    public String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    public String iso31661;
    @SerializedName("key")
    @Expose
    public String key;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("site")
    @Expose
    public String site;
    @SerializedName("size")
    @Expose
    public Integer size;
    @SerializedName("type")
    @Expose
    public String type;

    protected Video(Parcel in) {
        id = in.readString();
        iso6391 = in.readString();
        iso31661 = in.readString();
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readByte() == 0x00 ? null : in.readInt();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(iso6391);
        dest.writeString(iso31661);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        if (size == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(size);
        }
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public String getYoutubePreviewURL() {
        return "https://img.youtube.com/vi/" + key + "/0.jpg";
    }
    public String getYoutubeVideoURL() {
        return "https://www.youtube.com/watch?v=" + key;
    }
}
