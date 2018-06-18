package com.nverno.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "movie")
public class Movie {

    // This is the base url we will use to fetch poster image urls.
    private static final String base_poster_url = "https://image.tmdb.org/t/p/w500";

    // Note: Serialization (Java to JSON), Deserialization (JSON to Java)
    // @SerializedName("field") sets the annotation of the JSON fields we fetch from the API.
    // With my fields being the same as my private variables, it is not needed.
    // With @Expose(serialization = false) we can also set whether we (de)serialize or not.

    // Serialize and Expose for use with retrofit2
    @Expose
    @PrimaryKey
    private int id;
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Expose
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @Expose
    @ColumnInfo(name = "overview")
    private String overview;
    @Expose
    @ColumnInfo(name = "vote_average")
    private double vote_average;
    @Expose
    @ColumnInfo(name = "release_date")
    private String release_date;

    // TODO: Implement these as properties.
    @Ignore
    private List<Trailer> trailers;
    @Ignore
    private List<Review> reviews;

    // Constructor for object creation.
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
    }



    private String ConvertDateFormat(String oldFormat) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(oldFormat);
            return new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Just return us the old format if it doesn't work out!
        return oldFormat;
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

//    public String getPosterPath() {
//        return poster_path;
//    }
//
//    public void setPosterPath(String poster_path) {
//        this.poster_path = poster_path;
//    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
//
//    public double getVoteAverage() {
//        return vote_average;
//    }
//
//    public void setVoteAverage(double vote_average) {
//        this.vote_average = vote_average;
//    }
//
//    public String getReleaseDate() {
//        return ConvertDateFormat(release_date);
//    }
//
//    public void setReleaseDate(String release_date) {
//        this.release_date = release_date;
//    }

    // Our poster image url is not relying on any private variables.
    // API calls with retrofit2 did not seem to initialize constructor that would originally
    // assign the private variable that would have been accessed here. This works.
    public String getPosterImage() {
        return base_poster_url + poster_path;
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
