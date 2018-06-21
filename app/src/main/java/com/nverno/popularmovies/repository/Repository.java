package com.nverno.popularmovies.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nverno.popularmovies.moviedb.MovieDbApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

abstract class Repository {

    boolean networkNotAvailable(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    MovieDbApi getMovieDbApi(Class<MovieDbApi> apiClass) {
        return buildRetrofit().create(apiClass);
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
