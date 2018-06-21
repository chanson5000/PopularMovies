package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.model.Review;
import com.nverno.popularmovies.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {

    private LiveData<List<Review>> allReviews;
    private LiveData<List<Review>> selectedReviews;

    private ReviewRepository reviewRepository;

    public ReviewsViewModel(@NonNull Application app) {
        super(app);

        reviewRepository = new ReviewRepository(this.getApplication());

        allReviews = reviewRepository.getAllReviews();
    }

    private void setSelectedReviews(final int movieId) {
        selectedReviews = Transformations.map(allReviews, new Function<List<Review>, List<Review>>() {
            @Override
            public List<Review> apply(List<Review> input) {
                List<Review> reviews = new ArrayList<>();

                for (Review review : input) {
                    if (review.getMovieId() == movieId) {
                        reviews.add(review);
                    }
                }

                return reviews;
            }
        });
    }

    public LiveData<List<Review>> getReviews(int movieId) {
        loadReviewData(movieId);
        setSelectedReviews(movieId);
        return selectedReviews;
    }

    private void loadReviewData(int movieId) {
        reviewRepository.fetchMovieReviewsFromWeb(movieId);
    }
}
