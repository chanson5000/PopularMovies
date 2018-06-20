package com.nverno.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nverno.popularmovies.adapter.ReviewAdapter;
import com.nverno.popularmovies.model.Review;
import com.nverno.popularmovies.viewmodel.ReviewsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity {

    private ReviewAdapter mReviewAdapter;

    @BindView(R.id.recycler_reviews)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_reviews)
    TextView mNoReviews;
    @BindView(R.id.review_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.review_header)
    TextView mReviewHeader;

    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_TITLE = "MOVIE_NAME_EXTRA";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter(this);

        mRecyclerView.setAdapter(mReviewAdapter);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null
                && intentThatStartedThisActivity.hasExtra(MOVIE_ID)
                && intentThatStartedThisActivity.hasExtra(MOVIE_TITLE)) {

            Bundle bundle = intentThatStartedThisActivity.getExtras();

            if (bundle != null) {
                mMovieTitle.setText(bundle.getString(MOVIE_TITLE));
                setReviewsView(bundle.getInt(MOVIE_ID));
            }
        }
    }

    private void setReviewsView(final int movieId) {
        ReviewsViewModel reviewsViewModel = ViewModelProviders.of(this)
                .get(ReviewsViewModel.class);

        reviewsViewModel.getReviewsByMovieId(movieId)
                .observe(this, new Observer<List<Review>>() {
                    @Override
                    public void onChanged(@Nullable List<Review> reviews) {
                        if (reviews == null || reviews.isEmpty()) {
                            mReviewHeader.setVisibility(View.INVISIBLE);
                            mNoReviews.setVisibility(View.VISIBLE);
                        } else {
                            mReviewAdapter.setReviewsData(reviews);
                        }
                    }
                });
    }
}
