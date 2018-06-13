package com.nverno.popularmovies.util;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.MovieDbApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Webservice {

    @GET("3/movie/top_rated?api_key=" + MovieDbApi.API_KEY + "&page=1")
    Call<List<Movie>> topRatedMovies();

}
