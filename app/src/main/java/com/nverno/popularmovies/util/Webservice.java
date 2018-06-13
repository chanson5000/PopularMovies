package com.nverno.popularmovies.util;

import com.nverno.popularmovies.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Webservice {

    @GET("3/movie/top_rated?api_key=" + MovieDbApi.API_KEY + "&page=1")
    Call<List<Movie>> topRatedMovies();

}
