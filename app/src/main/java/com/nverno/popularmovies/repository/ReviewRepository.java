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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository extends Repository {

    private static final String LOG_TAG = ReviewRepository.class.getSimpleName();

    private final ReviewDatabase reviewDatabase;

    private final Context mContext;

    private static final List<Integer> retrievedReviews = new ArrayList<>();

    public ReviewRepository(Context context) {
        reviewDatabase = ReviewDatabase.getInstance(context);

        mContext = context;
    }

    public void fetchMovieReviewsFromWeb(final int movieId) {

        if (networkNotAvailable(mContext)) {
            Log.d(LOG_TAG, "Skipping internet data fetch, network not available.");
            return;
        }

        if (retrievedReviews.contains(movieId)) {
            Log.d(LOG_TAG, "Skipped fetching internet data for: " + movieId);
            return;
        }

        MovieDbApi movieDbApi = getMovieDbApi(MovieDbApi.class);

        Call<ReviewResult> call = movieDbApi.reviews(movieId);

        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResult> call,
                                   @NonNull Response<ReviewResult> response) {

                switch (response.code()) {
                    case 401:
                        Log.e(LOG_TAG, "Authentication failed. Please check your API key.");
                        break;
                    case 404:
                        Log.e(LOG_TAG, "Server returned \"Not Found\" error.");
                        break;
                    case 200:
                        final List<Review> reviews = response.body().GetReviews();

                        Log.d(LOG_TAG, "Fetched internet data for: " + movieId);

                        for (Review review : reviews) {
                            review.setMovieId(movieId);
                        }

                        retrievedReviews.add(movieId);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                reviewDatabase.reviewDao().insertMany(reviews);
                            }
                        });
                        break;
                    default:
                        Log.e(LOG_TAG,
                                "Failed to fetch internet data for: " + movieId);
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to fetch internet data for: " + movieId);
            }
        });
    }

    public LiveData<List<Review>> getAll() {
        return reviewDatabase.reviewDao().getAll();
    }
}
