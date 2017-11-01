package com.example.powerincode.popularmovies.network.services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public abstract class RequestCallback<T> implements Callback<T> {
    public void onComplete(){}
    public void onSuccess(Response<T> response, T result){
        onComplete();
    }
    public void onError(Throwable t){
        onComplete();
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onSuccess(response, response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(t);
    }
}
