package com.example.powerincode.popularmovies.screens.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.screens.home.adapters.MoviesAdapter;
import com.example.powerincode.popularmovies.screens.movie.DetailActivity;
import com.example.powerincode.popularmovies.utils.network.models.DiscoverMovie;
import com.example.powerincode.popularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.popularmovies.utils.network.services.Actions.ActionItem;
import com.example.powerincode.popularmovies.utils.network.services.DiscoverService;

public class HomeActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickListener {
    private final int MOST_POPULAR = 0;
    private final int MOST_RATED = 1;
    private int mCurrentSortingType = MOST_POPULAR;

    private DiscoverService service = DiscoverService.shared;

    RecyclerView mRecyclerView;
    MoviesAdapter mMovieAdapter;
    TextView mErrorMessage;
    ProgressBar mLoadingIndicator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_most_popular:
                changeSortType(MOST_POPULAR);
                return true;
            case R.id.opt_most_rated:
                changeSortType(MOST_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mErrorMessage = (TextView) findViewById(R.id.tv_movies_error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_movies_loading_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.rc_movies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true); //TODO: delete when multipage is added
        changeSortType(MOST_RATED);
    }

    private void changeSortType(int sortType) {
        if (mCurrentSortingType == sortType) {
            return;
        }

        mCurrentSortingType = sortType;
        switch (mCurrentSortingType) {
            case MOST_POPULAR:
                setTitle(getResources().getString(R.string.option_most_popular));
                service.getPopularMovies(mLoadingCallback);
                break;
            case MOST_RATED:
                setTitle(getResources().getString(R.string.option_most_rated));
                service.getTopRatedMovies(mLoadingCallback);
                break;
            default:
                setErrorState(getResources().getString(R.string.error_type_unsupported, getResources().getString(R.string.sort)));
                break;
        }
    }

    private void setErrorState(String message) {
        mErrorMessage.setText(message);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private ActionItem<DiscoverMovie> mLoadingCallback = new ActionItem<DiscoverMovie>() {
        @Override
        public void start() {
            super.start();
            mErrorMessage.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        public void complete(String response, DiscoverMovie result) {
            super.complete(response, result);
            mMovieAdapter = new MoviesAdapter(HomeActivity.this, result);
            mRecyclerView.setAdapter(mMovieAdapter);
        }

        @Override
        public void done() {
            super.done();
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }

        @Override
        public void error(Exception error) {
            super.error(error);
            setErrorState(error.getMessage());
        }
    };

    @Override
    public void onClick(MovieInfo movieInfo) {
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra(DetailActivity.EXTRA_MOVIE_INFO, movieInfo);
        startActivity(detailActivity);
    }
}
