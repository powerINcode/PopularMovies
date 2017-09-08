package com.example.powerincode.pupularmovies.utils.network.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public abstract class BaseModel {
    String getClassName() {
        return this.getClass().getSimpleName();
    }

    BaseModel() {}

    BaseModel(String json) throws JSONException {
        parse(json);
    }

    public abstract void parse(String json) throws JSONException;
}
