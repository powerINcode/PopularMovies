package com.example.powerincode.popularmovies.screens.movie_detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.views.RatedMovieView;
import com.example.powerincode.popularmovies.data.PMContract;
import com.example.powerincode.popularmovies.network.Networker;
import com.example.powerincode.popularmovies.network.models.movie.Movie;
import com.example.powerincode.popularmovies.network.models.movie.Review;
import com.example.powerincode.popularmovies.network.models.movie.ReviewList;
import com.example.powerincode.popularmovies.network.models.movie.Video;
import com.example.powerincode.popularmovies.network.models.movie.VideoList;
import com.example.powerincode.popularmovies.network.services.MovieService;
import com.example.powerincode.popularmovies.network.services.RequestCallback;
import com.example.powerincode.popularmovies.screens.BaseActivity;
import com.example.powerincode.popularmovies.screens.movie_detail.adapters.ReviewsAdapter;
import com.example.powerincode.popularmovies.screens.movie_detail.adapters.TrailersAdapter;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import retrofit2.Response;

public class MovieDetailActivity extends BaseActivity implements TrailersAdapter.TrailersAdapterEvent,
        LoaderManager.LoaderCallbacks<Cursor>{
    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    public static final String BUNDLE_MOVIE = "BUNDLE_MOVIE";
    public static final String BUNDLE_TRAILERS = "BUNDLE_TRAILERS";
    public static final String BUNDLE_REVIEWS = "BUNDLE_REVIEWS";
    public static final int LOADER_FAVORITE_MOVIE = 2000;

    public static Intent getActivity(Activity from, Movie movie) {
        Intent intent = new Intent(from, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, (Parcelable) movie);

        return intent;
    }

    @BindView(R.id.pv_selected_movie)
    RatedMovieView mSelectedMovieView;

    @BindView(R.id.tv_movie_description)
    TextView mMovieDescriptionTextView;

    @BindView(R.id.tv_trailers_empty)
    TextView mEmptyTrailersTextView;

    @BindView(R.id.tv_reviews_empty)
    TextView mEmptyReviewsTextView;

    @BindView(R.id.rv_trailers)
    RecyclerView mTrailersRecyclerView;
    TrailersAdapter mTrailerAdapter;

    @BindView(R.id.rv_movie_reviews)
    RecyclerView mReviewsRecyclerView;

    @BindView(R.id.fb_movie_favorite)
    FloatingActionButton mFavoriteButton;

    ReviewsAdapter mReviewsAdapter;

    private final MovieService mMovieService = Networker.shared.movieService;
    private ArrayList<Video> mTrailers;
    private ArrayList<Review> mReviews;
    private Movie mMovie;
    private Cursor mMovieData;

    @Override
    public int getLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTrailersRecyclerView.setHasFixedSize(true);

        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReviewsRecyclerView.setHasFixedSize(true);

        Bundle favoriteMovieBundle = new Bundle();
        if (getLoaderManager().getLoader(LOADER_FAVORITE_MOVIE) == null) {
            getLoaderManager().initLoader(LOADER_FAVORITE_MOVIE, favoriteMovieBundle, this);
        } else {
            getLoaderManager().restartLoader(LOADER_FAVORITE_MOVIE, favoriteMovieBundle, this);
        }

        if(getIntent() != null) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            mSelectedMovieView.initialize(mMovie);
            mMovieDescriptionTextView.setText(mMovie.overview);
        }

        if (savedInstanceState != null) {
            mTrailers = (ArrayList<Video>) savedInstanceState.getSerializable(BUNDLE_TRAILERS);
            mReviews = (ArrayList<Review>) savedInstanceState.getSerializable(BUNDLE_REVIEWS);
            mMovie = (Movie) savedInstanceState.getSerializable(BUNDLE_MOVIE);
        }

        setFavoriteFabImage();

        loadData();
    }

    private void setFavoriteFabImage() {
        if (mMovie.isFavorite) {
            mFavoriteButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_favorite, null));
        } else {
            mFavoriteButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star, null));
        }
    }

    private void loadData() {
        if (mTrailers != null) {
            setTrailers();
        } else {
            mMovieService.listVideos(mMovie.id).enqueue(new RequestCallback<VideoList>() {
                @Override
                public void onSuccess(Response<VideoList> response, VideoList result) {
                    super.onSuccess(response, result);

                    mTrailers = result.videos;
                    setTrailers();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    showMessage(t.getMessage());
                }
            });
        }

        if (mReviews != null) {
            setReviews();
        } else {
            mMovieService.listReviews(mMovie.id).enqueue(new RequestCallback<ReviewList>() {
                @Override
                public void onSuccess(Response<ReviewList> response, ReviewList result) {
                    super.onSuccess(response, result);
                    mReviews = result.reviews;
                    setReviews();
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    showMessage(t.getMessage());
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(BUNDLE_TRAILERS, mTrailers);
        outState.putSerializable(BUNDLE_REVIEWS, mReviews);
        outState.putSerializable(BUNDLE_MOVIE, mMovie);
    }

    @Override
    public void onTrailerClicked(String key) {
        Uri urlToTrailer = Uri.parse(key);
        Intent trailerIntent = new Intent(Intent.ACTION_VIEW, urlToTrailer);

        if (trailerIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(trailerIntent);
        }
    }

    public void addToFavorite(View view) {
        ContentResolver contentResolver = getContentResolver();
        if (!mMovie.isFavorite) {
            ContentValues cv = new ContentValues();
            cv.put(PMContract.FavoriteMoviesEntry._ID, mMovie.id);
            cv.put(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_TITLE, mMovie.title);
            cv.put(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_POSTER_PATH, mMovie.posterPath);
            cv.put(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_GENRES, mMovie.getGenres());
            cv.put(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_VOTES, mMovie.voteAverage);
            cv.put(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_DESCRIPTION, mMovie.overview);
            cv.put(PMContract.FavoriteMoviesEntry.TABLE_COLUMN_ADD_DATE, new Date().getTime());

            Uri result = contentResolver.insert(PMContract.FavoriteMoviesEntry.CONTENT_URI, cv);
        } else {
            contentResolver.delete(PMContract.FavoriteMoviesEntry.CONTENT_URI
                    .buildUpon().appendPath(String.valueOf(mMovie.id)).build(), null, null);
        }

        mMovie.isFavorite = !mMovie.isFavorite;
        setFavoriteFabImage();
    }

    private void setTrailers() {
        if (mTrailers.size() > 0) {
            mEmptyTrailersTextView.setVisibility(View.INVISIBLE);
            mTrailerAdapter = new TrailersAdapter(MovieDetailActivity.this, mTrailers);
            mTrailerAdapter.setEventListener(MovieDetailActivity.this);
            mTrailersRecyclerView.setAdapter(mTrailerAdapter);
        } else {
            mEmptyTrailersTextView.setVisibility(View.VISIBLE);
        }
    }
    private void setReviews() {
        if (mReviews.size() > 0) {
            mEmptyReviewsTextView.setVisibility(View.INVISIBLE);
            mReviewsAdapter = new ReviewsAdapter(MovieDetailActivity.this, mReviews);
            mReviewsRecyclerView.setAdapter(mReviewsAdapter);
        } else {
            mEmptyReviewsTextView.setVisibility(View.VISIBLE);
        }
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (bundle == null) {
                    return;
                }

                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                return getContentResolver().query(PMContract.FavoriteMoviesEntry.CONTENT_URI
                        .buildUpon().appendPath(String.valueOf(mMovie.id)).build(), null, null, null, null);
            }

            @Override
            public void deliverResult(Cursor data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            return;
        }

        if (cursor.moveToFirst()) {
            mMovie.isFavorite = true;
            setFavoriteFabImage();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
