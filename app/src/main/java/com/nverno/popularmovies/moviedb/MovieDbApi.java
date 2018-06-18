package com.nverno.popularmovies.moviedb;

import com.nverno.popularmovies.model.MovieResult;
import com.nverno.popularmovies.model.ReviewResult;
import com.nverno.popularmovies.model.TrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieDbApi {

    @GET("3/movie/top_rated?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<MovieResult> topRatedMovies();

    @GET("3/movie/popular?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<MovieResult> popularMovies();

    @GET("3/movie/{movie_id}/reviews?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<ReviewResult> reviews(
            @Path("movie_id") int movieId
    );

    @GET("3/movie/{movie_id}/videos?api_key=" + MovieDbSecrets.API_KEY + "&page=1")
    Call<TrailerResult> trailers(
            @Path("movie_id") int movieId
    );
}
