package com.example.powerincode.popularmovies.common.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.network.models.movie.Movie;
import com.example.powerincode.popularmovies.utils.UriHelper;

import butterknife.BindView;

/**
 * Created by powerman23rus on 31.10.17.
 * Enjoy ;)
 */

public class RatedMovieView extends CustomView implements View.OnClickListener {
    public interface RatedMovieViewEvent {
        void onMovieClicked(Movie movie);
    }

    @BindView(R.id.il_rated_movie_poster)
    ImageLoader mPosterImageLoader;

    @BindView(R.id.rtv_rating)
    RatingView mRatingView;

    @BindView(R.id.v_movie_description_container)
    View mMovieDescriptionContainer;

    @BindView(R.id.tv_movie_name)
    TextView mMovieName;

    @BindView(R.id.tv_movie_genre)
    TextView mMovieGenre;

    private RatedMovieViewEvent mEventListener;
    private Movie mMovie;

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

    public void initialize(final Movie movie) {
        mMovie = movie;
        mPosterImageLoader.setListener(new ImageLoader.ImageLoaderEvent() {
            @Override
            public void onComplete() {
                mMovieDescriptionContainer.setVisibility(VISIBLE);

                mMovieName.setText(movie.title);
                mMovieName.setVisibility(VISIBLE);

                mMovieGenre.setText(movie.getGenres());
                mMovieGenre.setVisibility(VISIBLE);

                mRatingView.initialize(movie.voteAverage / 2);
                mRatingView.setVisibility(VISIBLE);
            }
        });
        mPosterImageLoader.initialization(UriHelper.shared.buildPosterUrl(getContext(), movie.posterPath, 780));

        setOnClickListener(this);
    }

    public void setEventListener(RatedMovieViewEvent e) {
        mEventListener = e;
    }

    @Override
    public void onClick(View view) {
        if (mEventListener != null) {
            mEventListener.onMovieClicked(mMovie);
        }
    }
}
