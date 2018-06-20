package com.nverno.popularmovies.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nverno.popularmovies.database.PopularMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.model.MovieResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;
import com.nverno.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularMovieRepository {

    private static final String LOG_TAG = PopularMovieRepository.class.getSimpleName();

    private PopularMovieDatabase popularMovieDatabase;

    public PopularMovieRepository(Context context) {
        popularMovieDatabase = PopularMovieDatabase.getInstance(context);
    }

    // Fetch popular movies from TheMovieDb.Org for cache into our database.
    public void fetchPopularMoviesFromWeb() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDbApi movieDbApi = retrofit.create(MovieDbApi.class);

        Call<MovieResult> call = movieDbApi.popularMovies();

        // Save to the database
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
                            popularMovieDatabase.movieDao().insertMany(movies);
                        }
                    });
                } else {
                    Log.e(LOG_TAG,
                            "Failed to retrieve Popular Movie data from the internet.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to retrieve Popular Movie data from the internet.");
            }
        });
    }
}
