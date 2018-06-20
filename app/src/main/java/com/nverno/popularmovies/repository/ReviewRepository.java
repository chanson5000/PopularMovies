package com.nverno.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nverno.popularmovies.database.ReviewDatabase;
import com.nverno.popularmovies.model.Review;
import com.nverno.popularmovies.model.ReviewResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;
import com.nverno.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewRepository {

    private static final String LOG_TAG = ReviewRepository.class.getSimpleName();

    private ReviewDatabase reviewDatabase;

    public ReviewRepository(Context context) {
        reviewDatabase = ReviewDatabase.getInstance(context);
    }

    public void fetchMovieReviewsFromWeb(final int movieId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDbApi movieDbApi = retrofit.create(MovieDbApi.class);

        Call<ReviewResult> call = movieDbApi.reviews(movieId);

        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResult> call,
                                   @NonNull Response<ReviewResult> response) {

                if (response.code() == 401 || response.code() == 404) {
                    Log.e(LOG_TAG, response.body().GetStatusMessage());
                } else if (response.code() == 200) {
                    final List<Review> reviews = response.body().GetReviews();

                    Log.d(LOG_TAG, "Loading Review Data from web for: " + movieId);

                    for (Review review : reviews) {
                        review.setMovieId(movieId);
                    }

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            reviewDatabase.reviewDao().insertMany(reviews);
                        }
                    });
                } else {
                    Log.e(LOG_TAG,
                            "Failed to retrieve Review data from the internet.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to retrieve Review data from the internet.");
            }
        });
    }

    public LiveData<List<Review>> getReviewsByMovieId(int movieId) {
        return reviewDatabase.reviewDao().getByMovieId(movieId);
    }
}
