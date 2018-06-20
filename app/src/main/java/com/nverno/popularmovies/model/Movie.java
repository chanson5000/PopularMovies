package com.nverno.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "movie")
public class Movie {

    // This is the base url we will use to fetch poster image urls.
    private static final String base_poster_url = "https://image.tmdb.org/t/p/w500";

    // Expose for use with retrofit2
    @Expose
    @PrimaryKey
    private int id;

    @Expose
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @Expose
    private String overview;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("release_date")
    private String releaseDate;

    @Expose
    private double popularity;

    // TODO: Implement these as properties.
    @Ignore
    private List<Trailer> trailers;
    @Ignore
    private List<Review> reviews;

    // Constructor for object creation.
    public Movie() {
    }

    private String ConvertDateFormat(String oldFormat) {

        // Check to see if the date format needs to be converted.
        if (oldFormat.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(oldFormat);
                return new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return oldFormat;
            }
        } else {
            return oldFormat;
        }
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
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return ConvertDateFormat(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    // Our poster image url is not relying on any private variables.
    // API calls with retrofit2 did not seem to initialize constructor that would originally
    // assign the private variable that would have been accessed here. This works.
    public String getPosterImage() {
        return base_poster_url + posterPath;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}
