package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.ReviewDatabase;
import com.nverno.popularmovies.model.Review;
import com.nverno.popularmovies.repository.ReviewRepository;

import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;

    public ReviewsViewModel(@NonNull Application app) {
        super(app);

        reviewRepository = new ReviewRepository(this.getApplication());
    }

    public LiveData<List<Review>> getReviewsByMovieId(int movieId) {
        return reviewRepository.getReviewsByMovieId(movieId);
    }
}
