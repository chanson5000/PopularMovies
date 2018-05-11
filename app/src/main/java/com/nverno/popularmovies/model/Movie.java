package com.nverno.popularmovies.model;

public class Movie {

    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private double vote_average;
    private String release_date;
    private String poster_image;
    private static final String poster_url = "https://image.tmdb.org/t/p/w500";

    public Movie(int id,
                 String title,
                 String poster_path,
                 String overview,
                 double vote_average,
                 String release_date) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.poster_image = poster_url + poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public String getPosterImage() {
        return poster_image;
    }

    public void setPosterImage(String poster_image) {
        this.poster_image = poster_image;
    }
}
