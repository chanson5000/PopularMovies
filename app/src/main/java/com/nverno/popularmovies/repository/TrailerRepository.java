package com.nverno.popularmovies.repository;

import com.nverno.popularmovies.database.TrailerDatabase;
import com.nverno.popularmovies.model.Trailer;
import com.nverno.popularmovies.model.TrailerResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;
import com.nverno.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrailerRepository {

    private MovieDbApi movieDbApi;

    public TrailerRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDbApi = retrofit.create(MovieDbApi.class);
    }

    public void loadMovieTrailers(final TrailerDatabase trailerDatabase, final int movieId) {

        Call<TrailerResult> call = movieDbApi.trailers(movieId);

        call.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                final List<Trailer> trailers = response.body().GetTrailers();

                for (Trailer trailer : trailers) {
                    trailer.setMovieId(movieId);
                }

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        trailerDatabase.trailerDao().insertMany(trailers);
                    }
                });
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {

            }
        });

    }

}
