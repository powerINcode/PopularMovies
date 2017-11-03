package com.example.powerincode.popularmovies.screens.movie_detail.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.powerincode.popularmovies.R;
import com.example.powerincode.popularmovies.common.adapters.BaseViewHolder;
import com.example.powerincode.popularmovies.network.models.movie.Review;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by powerman23rus on 02.11.17.
 * Enjoy ;)
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private final Context mContext;
    private final ArrayList<Review> mReviews;

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        mContext = context;
        this.mReviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_review, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.bind(review.author, review.content);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_review_author)
        TextView mAuthorTextView;

        @BindView(R.id.tv_review_content)
        TextView mReviewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
        }

        void bind(String author, String content) {
            mAuthorTextView.setText(author);
            mReviewContent.setText(content);
        }
    }
}
