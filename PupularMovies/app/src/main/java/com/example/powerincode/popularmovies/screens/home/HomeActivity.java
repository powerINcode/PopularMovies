package com.example.powerincode.popularmovies.screens.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.screens.home.adapters.MoviesAdapter;
import com.example.powerincode.popularmovies.utils.network.models.DiscoverMovie;
import com.example.powerincode.popularmovies.utils.network.services.Actions.ActionItem;
import com.example.powerincode.popularmovies.utils.network.services.DiscoverService;

public class HomeActivity extends AppCompatActivity {
    private DiscoverService service = DiscoverService.shared;

    RecyclerView mRecyclerView;
    MoviesAdapter mMovieAdapter;
    TextView mErrorMessage;
    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mErrorMessage = (TextView) findViewById(R.id.tv_movies_error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_movies_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_movies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true); //TODO: delete when multipage is added

        loadData();
    }

    private void loadData() {
        service.getPopularMovies(new ActionItem<DiscoverMovie>() {
            @Override
            public void start() {
                super.start();
                mErrorMessage.setVisibility(View.INVISIBLE);
                mLoadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void complete(String response, DiscoverMovie result) {
                super.complete(response, result);
                mMovieAdapter = new MoviesAdapter(result);
                mRecyclerView.setAdapter(mMovieAdapter);
            }

            @Override
            public void done() {
                super.done();

                mLoadingIndicator.setVisibility(View.INVISIBLE);
            }

            @Override
            public void error(Exception error) {
                mErrorMessage.setText(error.getMessage());
                mErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
