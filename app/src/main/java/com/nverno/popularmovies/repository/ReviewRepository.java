package com.nverno.popularmovies.repository;

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

    private MovieDbApi movieDbApi;

    public ReviewRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDbApi = retrofit.create(MovieDbApi.class);
    }

    public void loadMovieReviews(final ReviewDatabase reviewDatabase, final int movieId) {

        Call<ReviewResult> call = movieDbApi.reviews(movieId);

        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                final List<Review> reviews = response.body().GetReviews();

                for (Review review : reviews) {
                    review.setMovieId(movieId);
                }

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        reviewDatabase.reviewDao().insertMany(reviews);
                    }
                });
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {

            }
        });
    }
}
