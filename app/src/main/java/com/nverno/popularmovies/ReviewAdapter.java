package com.nverno.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nverno.popularmovies.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private Context mContext;
    private List<Review> reviews;

    public ReviewAdapter(Context context) {
        mContext = context;
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView author;
        final TextView content;

        ReviewAdapterViewHolder(View view) {
            super(view);

            author = view.findViewById(R.id.review_author);
            content = view.findViewById(R.id.review_content);
        }
    }

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.review_item;

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, viewGroup, false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {

        String quoteReview = "\"" + reviews.get(position).getContent() + "\"";
        String dashAuthor = "— " + reviews.get(position).getAuthor();

        reviewAdapterViewHolder.content.setText(quoteReview);
        reviewAdapterViewHolder.author.setText(dashAuthor);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) return 0;
        return reviews.size();
    }

    public void setReviewsData(List<Review> data) {
        reviews = data;
        notifyDataSetChanged();
    }

}
