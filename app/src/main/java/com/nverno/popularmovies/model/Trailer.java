package com.nverno.popularmovies.model;

import com.google.gson.annotations.Expose;

public class Trailer {

    private static final String base_youtube_url = "https://www.youtube.com/watch?v=";

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String key;
    @Expose
    private String site;

    public String getTrailer() {
        return base_youtube_url + key;
    }
}
