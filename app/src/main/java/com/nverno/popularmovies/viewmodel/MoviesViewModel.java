package com.nverno.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;


import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.model.MovieResult;
import com.nverno.popularmovies.model.Review;
import com.nverno.popularmovies.model.ReviewResult;
import com.nverno.popularmovies.model.TrailerResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;

import java.util.List;

import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = MoviesViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> mTopRatedMovies;
    private MutableLiveData<List<Movie>> mPopularMovies;

    private Retrofit retrofit;
    private MovieDbApi movieDb;

    public MoviesViewModel() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDb = retrofit.create(MovieDbApi.class);
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        if (mTopRatedMovies == null) {
            mTopRatedMovies = new MutableLiveData<>();

            loadTopRatedMovies();
        }

        return mTopRatedMovies;
    }

    public LiveData<List<Movie>> getPopularMovies() {
        if (mPopularMovies == null) {
            mPopularMovies = new MutableLiveData<>();

            loadPopularMovies();
        }

        return mPopularMovies;
    }

    private void loadTopRatedMovies() {

        Call<MovieResult> call = movieDb.topRatedMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                // Our results object contains a list of movie objects.

                List<Movie> movies = response.body().GetMovies();

                for (final Movie currentMovie : movies) {
                    Call<ReviewResult> reviewCall = movieDb.reviews(currentMovie.getId());
                    Call<TrailerResult> trailerCall = movieDb.trailers(currentMovie.getId());


                    reviewCall.enqueue(new Callback<ReviewResult>() {
                        @Override
                        public void onResponse(Call<ReviewResult> reviewCall, Response<ReviewResult> response) {
                            currentMovie.setReviews(response.body().GetReviews());
                        }

                        @Override
                        public void onFailure(Call<ReviewResult> reviewCall, Throwable t) {

                        }
                    });

                    trailerCall.enqueue(new Callback<TrailerResult>() {
                        @Override
                        public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                            currentMovie.setTrailers(response.body().GetTrailers());
                        }

                        @Override
                        public void onFailure(Call<TrailerResult> call, Throwable t) {

                        }
                    });
                }

                mTopRatedMovies.setValue(movies);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e(LOG_TAG, "loadTopRatedMovies failed.");
            }
        });
    }

    private void loadPopularMovies() {

        Call<MovieResult> call = movieDb.popularMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                // Our results object contains a list of movie objects.

                List<Movie> movies = response.body().GetMovies();

                for (final Movie currentMovie : movies) {
                    Call<ReviewResult> reviewCall = movieDb.reviews(currentMovie.getId());
                    Call<TrailerResult> trailerCall = movieDb.trailers(currentMovie.getId());

                    reviewCall.enqueue(new Callback<ReviewResult>() {
                        @Override
                        public void onResponse(Call<ReviewResult> reviewCall, Response<ReviewResult> response) {
                            currentMovie.setReviews(response.body().GetReviews());
                        }

                        @Override
                        public void onFailure(Call<ReviewResult> reviewCall, Throwable t) {

                        }
                    });

                    trailerCall.enqueue(new Callback<TrailerResult>() {
                        @Override
                        public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                            currentMovie.setTrailers(response.body().GetTrailers());
                        }

                        @Override
                        public void onFailure(Call<TrailerResult> call, Throwable t) {

                        }
                    });
                }

                mPopularMovies.setValue(movies);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e(LOG_TAG, "loadPopularMovies failed.");
            }
        });
    }
}
