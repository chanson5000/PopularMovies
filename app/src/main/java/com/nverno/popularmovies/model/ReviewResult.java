package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ReviewResult {

    @Expose
    private int id;

    @Expose
    private List<Review> results;

    @Expose
    private String status_message;

    public int getMovieId() {
        return id;
    }

    public List<Review> GetReviews() {

        if (results == null) {
            results = new ArrayList<>();
        }

        return results;
    }

    public String getStatusMessage() {
        return status_message;
    }
}
