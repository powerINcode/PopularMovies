package com.example.powerincode.popularmovies.screens;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.views.RatedMovieView;
import com.example.powerincode.popularmovies.common.views.SegmentView;
import com.example.powerincode.popularmovies.network.Networker;
import com.example.powerincode.popularmovies.network.models.movie.Movie;
import com.example.powerincode.popularmovies.network.models.movie.MoviePlayingList;
import com.example.powerincode.popularmovies.network.models.movie.PagingMovies;
import com.example.powerincode.popularmovies.network.services.MovieService;
import com.example.powerincode.popularmovies.network.services.RequestCallback;
import com.example.powerincode.popularmovies.screens.adapters.MoviesAdapter;
import com.example.powerincode.popularmovies.utils.GenreUtils;

import java.util.Random;

import butterknife.BindView;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SegmentView.SegmentAction {
    public static final String BUNDLE_NOW_PLAYING_MOVIE = "BUNDLE_NOW_PLAYING_MOVIE";
    public static final String BUNDLE_MOVIES = "BUNDLE_MOVIES";
    public static final String BUNDLE_SELECTED_FILTER_POSITION = "BUNDLE_SELECTED_FILTER_POSITION";


    @BindView(R.id.rv_movies)
    RecyclerView mMoviesRecyclerView;
    MoviesAdapter mMovieAdapter;

    @BindView(R.id.rm_now_playing)
    RatedMovieView mNowPlayingMovieView;

    @BindView(R.id.sv_movie_type_filter)
    SegmentView mMovieFilterSegment;

    MovieService mMovieService = Networker.shared.movieService;

    private Toast mToast;
    private int mSelectedFilterPosition;
    private Movie mNowPlayingMovie;
    private PagingMovies mMovies;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieFilterSegment.initialize(this, getString(R.string.most_rated_filter_label), getString(R.string.most_popular_filter_label), getString(R.string.favorite_filter_label));
        mMovieFilterSegment.setActive(1);

        if (savedInstanceState != null) {
            mMovies = (PagingMovies) savedInstanceState.getSerializable(BUNDLE_MOVIES);
            mNowPlayingMovie = (Movie) savedInstanceState.getSerializable(BUNDLE_NOW_PLAYING_MOVIE);
            mSelectedFilterPosition = savedInstanceState.getInt(BUNDLE_SELECTED_FILTER_POSITION);
            mMovieFilterSegment.setActive(mSelectedFilterPosition);
        }

        loadData();
    }

    private void loadData() {
        if (mMovies != null) {
            setMovies();
        } else {
            mMovieService.listPopularMovies(null)
                    .enqueue(new RequestCallback<PagingMovies>() {
                        @Override
                        public void onComplete() {
                            super.onComplete();
                        }

                        @Override
                        public void onSuccess(Response<PagingMovies> response, PagingMovies result) {
                            super.onSuccess(response, result);
                            mMovies = result;
                            setMovies();
                        }

                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            showError(t.getMessage());
                        }
                    });
        }

        if (mNowPlayingMovie != null) {
            setNowPlayingMovie();
        } else {
            mMovieService.listPlayingMovies(null).enqueue(new RequestCallback<MoviePlayingList>() {
                @Override
                public void onComplete() {
                    super.onComplete();
                }

                @Override
                public void onSuccess(Response<MoviePlayingList> response, MoviePlayingList result) {
                    super.onSuccess(response, result);
                    int movieIndex = new Random().nextInt(result.results.size());

                    mNowPlayingMovie = result.results.get(movieIndex);
                    setNowPlayingMovie();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    showError(t.getMessage());
                }
            });
        }
    }


    private void setNowPlayingMovie() {
        mNowPlayingMovieView.initialize(mNowPlayingMovie.title, GenreUtils.shared.getGenres(mNowPlayingMovie.genreIds, 2),
                mNowPlayingMovie.posterPath, mNowPlayingMovie.voteAverage);
    }

    private void setMovies() {

        mMovieAdapter = new MoviesAdapter(this, mMovies);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BUNDLE_NOW_PLAYING_MOVIE, mNowPlayingMovie);
        outState.putSerializable(BUNDLE_MOVIES, mMovies);
        outState.putInt(BUNDLE_SELECTED_FILTER_POSITION, mSelectedFilterPosition);
    }

    private void showError(String message) {
        if (mToast == null) {
            mToast = new Toast(this);
            mToast.setDuration(Toast.LENGTH_LONG);
        } else {
            mToast.cancel();
        }

        mToast.setText(message);
        mToast.show();

    }

    @Override
    public void onSelected(int position) {
        mSelectedFilterPosition = position;
    }
}
