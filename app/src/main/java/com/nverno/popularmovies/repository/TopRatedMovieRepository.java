package com.nverno.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nverno.popularmovies.database.TopRatedMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.model.MovieResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;
import com.nverno.popularmovies.util.AppExecutors;
import com.nverno.popularmovies.viewmodel.PopularMoviesViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopRatedMovieRepository {

    private static final String LOG_TAG = TopRatedMovieRepository.class.getSimpleName();

    private TopRatedMovieDatabase topRatedMovieDatabase;

    private static boolean databaseUpdated = false;

    public TopRatedMovieRepository(Context context) {
        topRatedMovieDatabase = TopRatedMovieDatabase.getsInstance(context);

        cacheWebData();
    }

    public void cacheWebData() {

        if (databaseUpdated) {
            Log.d(LOG_TAG, "Skipped fecthing Top Rated Movie data from the internet");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDbApi movieDbApi = retrofit.create(MovieDbApi.class);

        Call<MovieResult> call = movieDbApi.topRatedMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,
                                   @NonNull Response<MovieResult> response) {

                if (response.code() == 401 || response.code() == 404) {
                    Log.e(LOG_TAG, response.body().GetStatusMessage());
                } else if (response.code() == 200) {
                    final List<Movie> movies = response.body().GetMovies();

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            topRatedMovieDatabase.movieDao().insertMany(movies);
                        }
                    });
                } else {
                    Log.e(LOG_TAG,
                            "Failed to retrieve Top Rated Movie data from the internet.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to retrieve Top Rated Movie data from the internet.");
            }
        });
    }

    public LiveData<List<Movie>> getTopRatedMoviesSorted() {
        return topRatedMovieDatabase.movieDao().getByRating();
    }

    public LiveData<Movie> getTopRatedMovieById(int movieId) {
        return topRatedMovieDatabase.movieDao().getMovieById(movieId);
    }
}
