package com.nverno.popularmovies.moviedb;

import com.nverno.popularmovies.model.Results;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieDbApi {

    @GET("3/movie/top_rated?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<Results> topRatedMovies();

    @GET("3/movie/popular?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<Results> popularMovies();

}
