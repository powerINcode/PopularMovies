package com.example.powerincode.popularmovies.screens.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.adapters.BaseViewHolder;
import com.example.powerincode.popularmovies.common.views.ImageLoader;
import com.example.powerincode.popularmovies.network.models.Movie;
import com.example.powerincode.popularmovies.network.models.PagingMovies;
import com.example.powerincode.popularmovies.utils.UriHelper;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by powerman23rus on 26.10.17.
 * Enjoy ;)
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private final Context mContext;
    private PagingMovies mPagingMovies;

    public MoviesAdapter(Context context, PagingMovies mPagingMovies) {
        this.mPagingMovies = mPagingMovies;
        this.mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_movie_v2, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mPagingMovies.results.get(position);
        holder.bind(movie.posterPath, movie.originalTitle);
    }

    @Override
    public int getItemCount() {
        return mPagingMovies.results.size();
    }

    class MovieViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageLoader mPosterImageLoader;

        @BindView(R.id.tv_movie_name)
        TextView mMovieNameTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(String posterPath, String movieName) {
            mMovieNameTextView.setText(movieName);
            mPosterImageLoader.initialization(UriHelper.shared.buildPosterUrl(mContext, posterPath));
        }
    }
}
