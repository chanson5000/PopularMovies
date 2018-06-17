package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

// This class is purely for returning results from our retrofit2 API calls.
public class Results {

    @SerializedName("results")
    @Expose
    private List<Movie> results;

    public List<Movie> GetMovies() {

        if (results == null) {
            results = new ArrayList<>();
        }

        return results;
    }
}
