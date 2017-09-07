package com.example.powerincode.pupularmovies.utils.network.models;

import android.net.Uri;

import com.example.powerincode.pupularmovies.utils.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by powerman23rus on 07.09.17.
 * Enjoy ;)
 */

public class Request {
    public final String PARAM_API_KEY = "api_key";

    Uri.Builder builder;

    public URL getURL() throws MalformedURLException {
        return new URL(builder.build().buildUpon().toString());
    }

    public Request(String route) {
        builder = Uri.parse(Configuration.api.BASE_URL)
                .buildUpon()
                .appendEncodedPath(route)
                .appendQueryParameter(PARAM_API_KEY, Configuration.api.MOVIE_DB_API_KEY);
    }

    public Uri.Builder addQueryParameter(String param, String value) {
        builder.appendQueryParameter(param, value);

        return builder;
    }
}
