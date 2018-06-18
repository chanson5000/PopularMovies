package com.nverno.popularmovies.repository;

import com.nverno.popularmovies.database.PopularMovieDatabase;
import com.nverno.popularmovies.database.TopRatedMovieDatabase;
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

public class TopRatedMovieRepository {

    private MovieDbApi movieDbApi;

    public TopRatedMovieRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDbApi = retrofit.create(MovieDbApi.class);
    }

    public void loadTopRatedMovies(final TopRatedMovieDatabase topRatedMovieDatabase) {

        Call<MovieResult> call = movieDbApi.topRatedMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                final List<Movie> movies = response.body().GetMovies();

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        topRatedMovieDatabase.movieDao().insertMany(movies);
                    }
                });
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {

            }
        });
    }
}
