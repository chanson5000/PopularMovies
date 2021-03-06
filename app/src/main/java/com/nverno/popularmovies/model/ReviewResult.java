package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ReviewResult {

    @Expose
    private int id;

    @Expose
    private List<Review> results;

    public int getMovieId() {
        return id;
    }

    public List<Review> GetReviews() {
        if (results == null) {
            results = new ArrayList<>();
        }

        return results;
    }
}
