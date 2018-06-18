package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class TrailerResult {

    @Expose
    private List<Trailer> results;

    public List<Trailer> GetTrailers() {

        if (results == null) {
            results = new ArrayList<>();
        }

        return results;
    }
}
