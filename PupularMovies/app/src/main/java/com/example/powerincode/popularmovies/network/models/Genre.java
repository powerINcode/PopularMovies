package com.example.powerincode.popularmovies.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by powerman23rus on 01.11.17.
 * Enjoy ;)
 */

public class Genre {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
}
