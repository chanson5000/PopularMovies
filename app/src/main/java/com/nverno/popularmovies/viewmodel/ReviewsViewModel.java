package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.model.Review;
import com.nverno.popularmovies.repository.ReviewRepository;

import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;

    private LiveData<List<Review>> reviews;

    public ReviewsViewModel(@NonNull Application app) {
        super(app);

        reviewRepository = new ReviewRepository(this.getApplication());
    }

    public LiveData<List<Review>> getReviewsByMovieId(int movieId) {
        loadReviews(movieId);

        return reviews;
    }

    private void loadReviews(int movieId) {
        reviews = reviewRepository.getReviewsByMovieId(movieId);
    }
}
