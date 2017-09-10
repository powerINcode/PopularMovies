package com.example.powerincode.popularmovies.screens.home.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.screens.commonViews.LoadingImageView;
import com.example.powerincode.popularmovies.utils.network.models.DiscoverMovie;
import com.example.powerincode.popularmovies.utils.network.models.MovieInfo;
import com.example.powerincode.popularmovies.utils.network.services.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private final String mPosterResolution = "w185_and_h278_bestv2";

    public interface OnMovieClickListener {
        void onClick(MovieInfo movieInfo);
    }

    private int moviesPerPage = 20;
    private SparseArray<ArrayList<MovieInfo>> mMovies;
    private OnMovieClickListener mOnClickListener;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        int pageIndex = position / moviesPerPage;
        int movieIndex = position % moviesPerPage;
        ArrayList<MovieInfo> movies = mMovies.get(pageIndex);
        MovieInfo movie = movies.get(movieIndex);

        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return mMovies.size() * moviesPerPage;
    }

    public MoviesAdapter(OnMovieClickListener listener, DiscoverMovie... movies) {
        setMovies(movies);
        mOnClickListener = listener;
    }

    private void setMovies(DiscoverMovie... movies) {
        mMovies = new SparseArray<>();
        for (int i = 0; i < movies.length; i++) {
            mMovies.put(i, movies[i].results);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LoadingImageView mPosterImageView;
        private MovieInfo mMovie;

        MovieViewHolder(View itemView) {
            super(itemView);

            mPosterImageView = itemView.findViewById(R.id.iv_movie_poster_image);
            itemView.setOnClickListener(this);
        }

        void bind(MovieInfo movieInfo) {
            mMovie = movieInfo;
            String posterFullUrl = Common.shared.getPosterPath(mMovie.posterPath, mPosterResolution);

//            Picasso.with(itemView.getContext())
//                    .load(posterFullUrl)
//                    .into(mPosterImageView);

            mPosterImageView.load(posterFullUrl);
        }

        @Override
        public void onClick(View view) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(mMovie);
            }
        }
    }
}
