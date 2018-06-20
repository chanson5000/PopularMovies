package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class TrailerResult {

    @Expose
    private int id;

    @Expose
    private List<Trailer> results;

    @Expose
    private String status_message;

    public int getMovieId() {
        return id;
    }

    public List<Trailer> GetTrailers() {

        if (results == null) {
            results = new ArrayList<>();
        }

        return results;
    }

    public String GetStatusMessage() {
        return status_message;
    }
}
