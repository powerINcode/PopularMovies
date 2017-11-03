package com.example.powerincode.popularmovies.screens;

import android.content.Intent;
import android.os.Bundle;

import com.example.powerincode.popularmovies.PopularMovieApplication;
import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.network.Networker;
import com.example.powerincode.popularmovies.network.models.genre.GenreList;
import com.example.powerincode.popularmovies.network.services.RequestCallback;
import com.example.powerincode.popularmovies.screens.main.MainActivity;

import retrofit2.Response;

public class StartActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Networker.shared.genreService.listGenres().enqueue(new RequestCallback<GenreList>() {
            @Override
            public void onSuccess(Response<GenreList> response, GenreList result) {
                super.onSuccess(response, result);

                PopularMovieApplication.sGenres = result.genres;

                Intent mainActivityIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        });
    }
}
