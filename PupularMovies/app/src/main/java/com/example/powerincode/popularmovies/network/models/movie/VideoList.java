package com.example.powerincode.popularmovies.network.models.movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.powerincode.popularmovies.network.models.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoList extends BaseModel implements Parcelable {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("results")
    @Expose
    public ArrayList<Video> videos = null;

    protected VideoList(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            videos = new ArrayList<Video>();
            in.readList(videos, Video.class.getClassLoader());
        } else {
            videos = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (videos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(videos);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VideoList> CREATOR = new Parcelable.Creator<VideoList>() {
        @Override
        public VideoList createFromParcel(Parcel in) {
            return new VideoList(in);
        }

        @Override
        public VideoList[] newArray(int size) {
            return new VideoList[size];
        }
    };
}