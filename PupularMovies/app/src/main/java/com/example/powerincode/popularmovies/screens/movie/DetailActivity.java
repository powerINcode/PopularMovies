package com.example.powerincode.popularmovies.screens.movie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.screens.BaseActivity;
import com.example.powerincode.popularmovies.screens.commonViews.LoadingImageView;
import com.example.powerincode.popularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.popularmovies.utils.network.services.Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends BaseActivity {
    public static final String EXTRA_MOVIE_INFO = "EXTRA_MOVIE_INFO";
    private LoadingImageView mPosterImageView;
    private TextView mRatingTextView;
    private TextView mReleaseDateTextView;
    private TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent homeActivityIntent = getIntent();
        if (homeActivityIntent.hasExtra(EXTRA_MOVIE_INFO)) {
            MovieInfo mMovie = homeActivityIntent.getParcelableExtra(EXTRA_MOVIE_INFO);
            setTitle(mMovie.title);

            mRatingTextView = (TextView) findViewById(R.id.tv_rating);
            mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
            mOverviewTextView = (TextView) findViewById(R.id.tv_overview_text);
            mPosterImageView = (LoadingImageView) findViewById(R.id.iv_detail_poster);

            mRatingTextView.setText(getResources().getString(R.string.movie_rating, mMovie.voteAverage));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date convertedDate;
            try {
                convertedDate = dateFormat.parse(mMovie.releaseDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(convertedDate);
                mReleaseDateTextView.setText(getResources().getString(R.string.movie_release_date, String.valueOf(calendar.get(Calendar.YEAR))));

            } catch (ParseException e) {
                Log.e(getClassName(), e.getMessage());
            }

            mOverviewTextView.setText(mMovie.overview);
            mPosterImageView.load(Common.shared.getPosterPath(mMovie.posterPath, "w320"));
        }
    }
}
