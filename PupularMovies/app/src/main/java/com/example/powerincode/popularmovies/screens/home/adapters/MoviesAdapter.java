package com.example.powerincode.popularmovies.screens.home.adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.utils.Configuration;
import com.example.powerincode.popularmovies.utils.network.models.DiscoverMovie;
import com.example.powerincode.popularmovies.utils.network.models.MovieInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by powerman23rus on 08.09.17.
 * Enjoy ;)
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private int moviesPerPage = 20;
    private SparseArray<ArrayList<MovieInfo>> mMovies;

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

        holder.bind(movie.posterPath);
    }

    @Override
    public int getItemCount() {
        return mMovies.size() * moviesPerPage;
    }

    public MoviesAdapter(DiscoverMovie... movies) {
        setMovies(movies);
    }

    private void setMovies(DiscoverMovie... movies) {
        mMovies = new SparseArray<>();
        for (int i = 0; i < movies.length; i++) {
            mMovies.put(i, movies[i].results);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mPosterImageView;

        MovieViewHolder(View itemView) {
            super(itemView);

            mPosterImageView = itemView.findViewById(R.id.iv_movie_poster_image);
        }

        void bind(String posterUrl) {
            String posterFullUrl = Uri.parse(Configuration.api.BASE_POSTERS_URL)
                    .buildUpon()
                    .appendPath(posterUrl)
                    .build()
                    .toString();

            Picasso.with(itemView.getContext()).load(posterFullUrl).into(mPosterImageView);
        }
    }
}
