package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.ReviewDatabase;
import com.nverno.popularmovies.model.Review;

import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {

    private LiveData<List<Review>> reviews;

    public ReviewsViewModel(@NonNull Application app) {
        super(app);
        ReviewDatabase database = ReviewDatabase
                .getInstance(this.getApplication());

        reviews = database.reviewDao().getAll();
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }
}
