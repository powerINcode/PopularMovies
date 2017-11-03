package com.example.powerincode.popularmovies.screens.main;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.views.RatedMovieView;
import com.example.powerincode.popularmovies.common.views.SegmentView;
import com.example.powerincode.popularmovies.data.PMContract;
import com.example.powerincode.popularmovies.network.Networker;
import com.example.powerincode.popularmovies.network.models.movie.Movie;
import com.example.powerincode.popularmovies.network.models.movie.PlayingMoviesList;
import com.example.powerincode.popularmovies.network.models.movie.PagingMovies;
import com.example.powerincode.popularmovies.network.services.MovieService;
import com.example.powerincode.popularmovies.network.services.RequestCallback;
import com.example.powerincode.popularmovies.screens.BaseActivity;
import com.example.powerincode.popularmovies.screens.main.adapters.MoviesAdapter;
import com.example.powerincode.popularmovies.screens.movie_detail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SegmentView.SegmentAction,
        MoviesAdapter.MovieAdapterEvent,
        RatedMovieView.RatedMovieViewEvent,
        LoaderManager.LoaderCallbacks<Cursor>{
    public static final String BUNDLE_NOW_PLAYING_MOVIE = "BUNDLE_NOW_PLAYING_MOVIE";
    public static final String BUNDLE_SEGMENTED_MOVIES = "BUNDLE_SEGMENTED_MOVIES";
    public static final String BUNDLE_SEGMENT_POSITION = "BUNDLE_SEGMENT_POSITION";

    public static final int SEGMENT_TOP_RATED = 0;
    public static final int SEGMENT_POPULAR = 1;
    public static final int SEGMENT_FAVORITE = 2;

    public static final int LOADER_MAIN = 1000;

    @BindView(R.id.rv_movies)
    RecyclerView mMoviesRecyclerView;
    MoviesAdapter mMovieAdapter;

    @BindView(R.id.rm_now_playing)
    RatedMovieView mNowPlayingMovieView;

    @BindView(R.id.sv_movie_type_filter)
    SegmentView mMovieFilterSegment;

    MovieService mMovieService = Networker.shared.movieService;

    private Movie mNowPlayingMovie;

    private HashMap<Integer, PagingMovies> mSegmentMovies;
    private int mSelectedFilterPosition = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMovieFilterSegment.setActive(mSelectedFilterPosition);
        loadMovies(2);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMovieFilterSegment.setActive(mSelectedFilterPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLoaderManager().initLoader(LOADER_MAIN, null, this);

        if (getResources().getBoolean(R.bool.is_landscape_mode)) {
            mMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        mNowPlayingMovieView.setEventListener(this);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieFilterSegment.initialize(this, getString(R.string.top_rated_filter_label), getString(R.string.most_popular_filter_label), getString(R.string.favorite_filter_label));

        if (savedInstanceState != null) {
            mSelectedFilterPosition = savedInstanceState.getInt(BUNDLE_SEGMENT_POSITION);
            mSegmentMovies = (HashMap<Integer, PagingMovies>) savedInstanceState.getSerializable(BUNDLE_SEGMENTED_MOVIES);
            mNowPlayingMovie = (Movie) savedInstanceState.getSerializable(BUNDLE_NOW_PLAYING_MOVIE);

        }
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

        outState.putInt(BUNDLE_SEGMENT_POSITION, mSelectedFilterPosition);
        outState.putSerializable(BUNDLE_NOW_PLAYING_MOVIE, mNowPlayingMovie);
        outState.putSerializable(BUNDLE_SEGMENTED_MOVIES, mSegmentMovies);
    }

    @Override
    public void onSegmentSelected(int position) {
        mSelectedFilterPosition = position;
        loadData();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        goToDetailsActivity(movie);
    }

    private void goToDetailsActivity(Movie movie) {
        startActivity(MovieDetailActivity.getActivity(this, movie));
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mMoviesData;

            @Override
            protected void onStartLoading() {
                if (bundle == null) {
                    return;
                }

                if (mMoviesData != null) {
                    deliverResult(mMoviesData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContext().getContentResolver().query(PMContract.FavoriteMoviesEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                } catch(Exception e) {
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mMoviesData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            ArrayList<Movie> movies = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.id = cursor.getLong(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_ID_INDEX);
                    movie.title = cursor.getString(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_TITLE_INDEX);
                    movie.genres = cursor.getString(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_GENRES_INDEX);
                    movie.voteAverage = cursor.getFloat(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_VOTES_INDEX);
                    movie.overview = cursor.getString(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_DESCRIPTION_INDEX);
                    movie.posterPath = cursor.getString(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_POSTER_PATH_INDEX);
                    movie.isFavorite = true;

                    movies.add(movie);
                } while (cursor.moveToNext());
            }

            PagingMovies result = new PagingMovies();
            result.results = movies;

            if (mSegmentMovies == null) {
                mSegmentMovies = new HashMap<>();
            }

            mSegmentMovies.put(SEGMENT_FAVORITE, result);
            setMovies();
        } else {
            showMessage(getString(R.string.error_unable_to_load_favorite_movies));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
            mMovieService.listPlayingMovies(null).enqueue(new RequestCallback<PlayingMoviesList>() {
                @Override
                public void onComplete() {
                    super.onComplete();
                }

                @Override
                public void onSuccess(Response<PlayingMoviesList> response, PlayingMoviesList result) {
                    super.onSuccess(response, result);
                    int movieIndex = new Random().nextInt(result.results.size());

                    mNowPlayingMovie = result.results.get(movieIndex);
                    setNowPlayingMovie();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    showMessage(t.getMessage());
                }
            });
        }
    }

    private void loadMovies(int segmentPosition) {
        Call<PagingMovies> call = null;
        int segment;

        if (segmentPosition == SEGMENT_TOP_RATED) {
            call = mMovieService.listTopRated(null);
            segment = SEGMENT_TOP_RATED;
        } else if (segmentPosition == SEGMENT_POPULAR) {
            call = mMovieService.listPopularMovies(null);
            segment = SEGMENT_POPULAR;
        } else if (segmentPosition == 2){
            Bundle bundle = new Bundle();

            if(getLoaderManager().getLoader(LOADER_MAIN) == null) {
                getLoaderManager().initLoader(LOADER_MAIN, bundle, this);
            } else {
                getLoaderManager().restartLoader(LOADER_MAIN, bundle, this);
            }

            return;
        } else {
            throw new IllegalStateException("Only three type of filter is supported");
        }

        if (mSegmentMovies == null) {
            mSegmentMovies = new HashMap<>();
        }

        final int selectedSegment = segment;
        call.enqueue(new RequestCallback<PagingMovies>() {
            @Override
            public void onComplete() {
                super.onComplete();
            }

            @Override
            public void onSuccess(Response<PagingMovies> response, PagingMovies result) {
                super.onSuccess(response, result);

                mSegmentMovies.put(selectedSegment, result);
                setMovies();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                showMessage(t.getMessage());
            }
        });
    }


    private void setNowPlayingMovie() {
        mNowPlayingMovieView.initialize(mNowPlayingMovie);
    }

    private void setMovies() {
        mMovieAdapter = new MoviesAdapter(this, mSegmentMovies.get(mSelectedFilterPosition));
        mMovieAdapter.setEventListener(this);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
    }
}
