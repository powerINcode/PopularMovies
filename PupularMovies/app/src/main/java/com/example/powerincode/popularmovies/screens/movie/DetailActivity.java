package com.example.powerincode.popularmovies.screens.movie;

import android.content.Intent;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.screens.commonViews.LoadingImageView;
import com.example.powerincode.popularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.popularmovies.utils.network.services.Common;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_INFO = "EXTRA_MOVIE_INFO";
    private MovieInfo mMovie;
    LoadingImageView mPosterImageView;
    TextView mRatingTextView;
    TextView mReleaseDateTextView;
    TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent homeActivityIntent = getIntent();
        if (homeActivityIntent.hasExtra(EXTRA_MOVIE_INFO)) {
            mMovie = homeActivityIntent.getParcelableExtra(EXTRA_MOVIE_INFO);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            mOverviewTextView.setText(mMovie.overview);
            mPosterImageView.load(Common.shared.getPosterPath(mMovie.posterPath, "w320"));
        }
    }
}
