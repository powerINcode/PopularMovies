package com.example.powerincode.popularmovies.network;

import android.support.annotation.NonNull;

import com.example.powerincode.popularmovies.network.services.GenreService;
import com.example.powerincode.popularmovies.network.services.MovieService;
import com.example.powerincode.popularmovies.utils.Configuration;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class Networker {
    public static final Networker shared = new Networker(Configuration.API_KEY, Configuration.API_DOMAIN_URL);
    private final String domainUrl;
    private final String apiKey;

    public final MovieService movieService;
    public final GenreService genreService;

    public Networker(String apiKey, String domainUrl) {
        this.domainUrl = domainUrl;
        this.apiKey = apiKey;

        movieService = getBuilder(MovieService.class);
        genreService = getBuilder(GenreService.class);
    }

    private <T> T getBuilder(Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.domainUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        return retrofit.create(service);
    }

    @NonNull
    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addEncodedQueryParameter("api_key", apiKey)
                                .build();

                        Request newRequest = request.newBuilder()
                                .url(url)
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();
    }
}
