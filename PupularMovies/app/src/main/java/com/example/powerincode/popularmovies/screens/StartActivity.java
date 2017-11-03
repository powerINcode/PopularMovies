package com.example.powerincode.popularmovies.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.powerincode.popularmovies.PopularMovieApplication;
import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.network.Networker;
import com.example.powerincode.popularmovies.network.models.genre.GenreList;
import com.example.powerincode.popularmovies.network.services.RequestCallback;
import com.example.powerincode.popularmovies.screens.main.MainActivity;

import butterknife.BindView;
import retrofit2.Response;

public class StartActivity extends BaseActivity {

    @BindView(R.id.iv_app_loading)
    ImageView mLoadingImageView;

    @Override
    public int getLayout() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

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
                showMessage(t.getMessage());
            }
        });
    }
}
