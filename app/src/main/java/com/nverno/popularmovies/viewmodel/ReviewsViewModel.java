package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.ReviewDatabase;
import com.nverno.popularmovies.model.Review;

import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {

    private ReviewDatabase database;

    public ReviewsViewModel(@NonNull Application app) {
        super(app);
        database = ReviewDatabase.getInstance(this.getApplication());
    }

    public LiveData<List<Review>> getReviews() {
        return database.reviewDao().getAll();
    }

    public LiveData<List<Review>> getReviewsByMovieId(int movieId) {
        return database.reviewDao().getByMovieId(movieId);
    }
}
