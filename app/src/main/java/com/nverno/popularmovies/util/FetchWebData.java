package com.nverno.popularmovies.util;

import android.os.AsyncTask;

import com.nverno.popularmovies.model.Movie;

import java.io.IOException;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchWebData {

    public static void fetchData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Webservice request = retrofit.create(Webservice.class);

        Call<List<Movie>> call = request.topRatedMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                System.out.print(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                System.err.print("An error occurred with retrofit");
            }
        });
    }
}
