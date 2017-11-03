package com.example.powerincode.popularmovies.screens.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.adapters.BaseViewHolder;
import com.example.powerincode.popularmovies.common.views.ImageLoader;
import com.example.powerincode.popularmovies.network.models.movie.Movie;
import com.example.powerincode.popularmovies.network.models.movie.PagingMovies;
import com.example.powerincode.popularmovies.utils.UriHelper;

import butterknife.BindView;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public interface MovieAdapterEvent {
        void onMovieClicked(Movie movie);
    }

    private final Context mContext;
    private PagingMovies mPagingMovies;
    private MovieAdapterEvent mEventListener;

    public MoviesAdapter(Context context, PagingMovies mPagingMovies) {
        this.mPagingMovies = mPagingMovies;
        this.mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mPagingMovies.results.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (mPagingMovies == null || mPagingMovies.results == null) {
            return 0;
        }

        return mPagingMovies.results.size();
    }

    public void setEventListener(MovieAdapterEvent e) {
        mEventListener = e;
    }

    class MovieViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageLoader mPosterImageLoader;

        @BindView(R.id.tv_movie_name)
        TextView mMovieNameTextView;

        private Movie mMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEventListener != null) {
                        mEventListener.onMovieClicked(mMovie);
                    }
                }
            });
        }

        public void bind(Movie movie) {
            mMovie = movie;
            mMovieNameTextView.setText(movie.title);
            mPosterImageLoader.initialization(UriHelper.shared.buildPosterUrl(mContext, movie.posterPath));
        }
    }
}
