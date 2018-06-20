package com.nverno.popularmovies.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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

    private static final String LOG_TAG = TrailerRepository.class.getSimpleName();

    private TrailerDatabase trailerDatabase;

    public TrailerRepository(Context context) {
        trailerDatabase = TrailerDatabase.getInstance(context);
    }

    public void loadMovieTrailers(final int movieId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDbApi movieDbApi = retrofit.create(MovieDbApi.class);

        Call<TrailerResult> call = movieDbApi.trailers(movieId);

        call.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResult> call,
                                   @NonNull Response<TrailerResult> response) {
                if (response.code() == 401 || response.code() == 404) {
                    Log.e(LOG_TAG, response.body().GetStatusMessage());
                } else if (response.code() == 200) {
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
                } else {
                    Log.e(LOG_TAG,
                            "Failed to retrieve Trailer data from the internet.");
                }
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to retrieve Trailer data from the internet.");
            }
        });

    }

}
