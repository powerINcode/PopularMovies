package com.example.powerincode.popularmovies.screens;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SegmentView.SegmentAction {
    public static final String BUNDLE_NOW_PLAYING_MOVIE = "BUNDLE_NOW_PLAYING_MOVIE";
    public static final String BUNDLE_SEGMENTED_MOVIES = "BUNDLE_SEGMENTED_MOVIES";
    public static final String BUNDLE_SEGMENTE_POSITION = "BUNDLE_SEGMENTE_POSITION";


    @BindView(R.id.rv_movies)
    RecyclerView mMoviesRecyclerView;
    MoviesAdapter mMovieAdapter;

    @BindView(R.id.rm_now_playing)
    RatedMovieView mNowPlayingMovieView;

    @BindView(R.id.sv_movie_type_filter)
    SegmentView mMovieFilterSegment;

    MovieService mMovieService = Networker.shared.movieService;

    private Toast mToast;
    private Movie mNowPlayingMovie;

    private HashMap<Integer, PagingMovies> mSegmentMovies;
    private int mSelectedFilterPosition = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.is_landscape_mode)) {
            mMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieFilterSegment.initialize(this, getString(R.string.top_rated_filter_label), getString(R.string.most_popular_filter_label), getString(R.string.favorite_filter_label));

        if (savedInstanceState != null) {
            mSelectedFilterPosition = savedInstanceState.getInt(BUNDLE_SEGMENTE_POSITION);
            mSegmentMovies = (HashMap<Integer, PagingMovies>) savedInstanceState.getSerializable(BUNDLE_SEGMENTED_MOVIES);
            mNowPlayingMovie = (Movie) savedInstanceState.getSerializable(BUNDLE_NOW_PLAYING_MOVIE);

        }

        mMovieFilterSegment.setActive(mSelectedFilterPosition);
        loadData();
    }

    private void loadData() {
        if (mSegmentMovies != null && mSegmentMovies.get(mSelectedFilterPosition) != null) {
            setMovies();
        } else {
            loadMovies(mSelectedFilterPosition);
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

    private void loadMovies(int segmentPosition) {
        Call<PagingMovies> call = null;

        if (segmentPosition == 0) {
            call = mMovieService.listTopRated(null);
        } else if (segmentPosition == 1) {
            call = mMovieService.listPopularMovies(null);
        } else if (segmentPosition == 2){
            call = mMovieService.listTopRated(null);
        } else {
            throw new IllegalStateException("Only three type of filter is supported");
        }

        call.enqueue(new RequestCallback<PagingMovies>() {
                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onSuccess(Response<PagingMovies> response, PagingMovies result) {
                        super.onSuccess(response, result);

                        if (mSegmentMovies == null) {
                            mSegmentMovies = new HashMap<>();
                        }

                        mSegmentMovies.put(mSelectedFilterPosition, result);
                        setMovies();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        showError(t.getMessage());
                    }
                });
    }


    private void setNowPlayingMovie() {
        mNowPlayingMovieView.initialize(mNowPlayingMovie.title, GenreUtils.shared.getGenres(mNowPlayingMovie.genreIds, 2),
                mNowPlayingMovie.posterPath, mNowPlayingMovie.voteAverage);
    }

    private void setMovies() {
        mMovieAdapter = new MoviesAdapter(this, mSegmentMovies.get(mSelectedFilterPosition));
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

        outState.putInt(BUNDLE_SEGMENTE_POSITION, mSelectedFilterPosition);
        outState.putSerializable(BUNDLE_NOW_PLAYING_MOVIE, mNowPlayingMovie);
        outState.putSerializable(BUNDLE_SEGMENTED_MOVIES, mSegmentMovies);
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
        loadData();
    }
}
