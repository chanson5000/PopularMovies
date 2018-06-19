package com.nverno.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "trailer")
public class Trailer {

    private static final String base_youtube_url = "https://www.youtube.com/watch?v=";

    @PrimaryKey
    @Expose
    private String id;

    @Expose
    private int movieId;

    @Expose
    private String name;

    @Expose
    private String key;

    @Expose
    private String type;

    @Expose
    private String site;

    public Trailer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTrailer() {
        return base_youtube_url + key;
    }
}
