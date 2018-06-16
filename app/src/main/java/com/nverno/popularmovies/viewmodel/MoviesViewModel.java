package com.nverno.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.model.Results;
import com.nverno.popularmovies.moviedb.MovieDbApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesViewModel extends ViewModel {

    // Constant for logging
    private static final String LOG_TAG = MoviesViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> mMovies;

    public LiveData<List<Movie>> getTopRatedMovies() {
        if (mMovies == null) {
            mMovies = new MutableLiveData<>();

            loadTopRatedMovies();
        }

        return mMovies;
    }

    private void loadTopRatedMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDbApi movieDb = retrofit.create(MovieDbApi.class);

        Call<Results> call = movieDb.topRatedMovies();

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                // Our results object contains a list of movie objects.
                mMovies.setValue(response.body().GetMovies());
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.e(LOG_TAG, "loadTopRatedMovies failed.");
            }
        });
    }
}
