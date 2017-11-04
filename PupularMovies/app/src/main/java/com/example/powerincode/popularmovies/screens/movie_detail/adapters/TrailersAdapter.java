package com.example.powerincode.popularmovies.screens.movie_detail.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.adapters.BaseViewHolder;
import com.example.powerincode.popularmovies.common.views.ImageLoader;
import com.example.powerincode.popularmovies.network.models.movie.Video;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    public interface TrailersAdapterEvent {
        void onTrailerClicked(String key);
    }

    private final Context mContext;
    private final ArrayList<Video> videos;
    private TrailersAdapterEvent mEventListener;

    public TrailersAdapter(Context context, ArrayList<Video> videos) {
        this.mContext = context;
        this.videos = videos;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_trailer, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setEventListener(TrailersAdapterEvent e) {
        mEventListener = e;
    }

    class TrailerViewHolder extends BaseViewHolder {
        @BindView(R.id.il_trailer_container)
        ImageLoader mTrailerImageLoader;
        private Video video;

        TrailerViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mEventListener != null) {
                        mEventListener.onTrailerClicked(video.getYoutubeVideoURL());
                    }
                }
            });
        }

        void bind(Video video) {
            this.video = video;
            mTrailerImageLoader.initialization(video.getYoutubePreviewURL());
        }
    }
}
