package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.utils.UriHelper;

import butterknife.BindView;

/**
 * Created by powerman23rus on 31.10.17.
 * Enjoy ;)
 */

public class RatedMovieView extends CustomView {
    @BindView(R.id.il_rated_movie_poster)
    ImageLoader mPosterImageLoader;

    @BindView(R.id.rtv_rating)
    RatingView mRatingView;

    @BindView(R.id.tv_movie_name)
    TextView mMovieName;

    @BindView(R.id.tv_movie_genre)
    TextView mMovieGenre;

    public RatedMovieView(@NonNull Context context) {
        super(context);
    }

    public RatedMovieView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatedMovieView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_rated_movie;
    }

    public void initialize(String name, String genre, String posterPath, float rating) {
        mMovieName.setText(name);
        mMovieGenre.setText(genre);
        mPosterImageLoader.initialization(UriHelper.shared.buildPosterUrl(getContext(), posterPath));
        mRatingView.initialize(rating / 2);
    }
}
