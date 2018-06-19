package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;

public class Review {

    @Expose
    private String id;
    @Expose
    private String author;
    @Expose
    private String content;

    public Review() {
    }


    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return content;
    }
}
