package com.example.powerincode.popularmovies.screens.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.popularmovies.utils.network.services.Common;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_INFO = "EXTRA_MOVIE_INFO";
    private MovieInfo mMovie;
    ImageView mPosterImageView;
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
            mPosterImageView = (ImageView) findViewById(R.id.iv_detail_poster);

            mRatingTextView.setText(String.valueOf(mMovie.voteAverage));
            mReleaseDateTextView.setText(mMovie.releaseDate);
            mOverviewTextView.setText(mMovie.overview);
            Picasso.with(this)
                    .load(Common.shared.getPosterPath(mMovie.posterPath, "w320"))
                    .into(mPosterImageView);
        }
    }
}
