package com.nverno.popularmovies.moviedb;

import com.nverno.popularmovies.model.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDbApi {

    @GET("3/movie/top_rated?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<MovieResult> topRatedMovies();

    @GET("3/movie/popular?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<MovieResult> popularMovies();

    @GET("2/movie/{movie_id}/reviews?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<MovieResult> reviews(
            @Path("movie_id") int movieId
    );
}
