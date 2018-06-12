package com.nverno.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private double vote_average;
    private String release_date;
    private String poster_image;
    private List<String> trailers;
    private List<String> reviews;
    private static final String poster_url = "https://image.tmdb.org/t/p/w500";

    // Constructor for object creation.
    public Movie(int id,
                 String title,
                 String poster_path,
                 String overview,
                 double vote_average,
                 String release_date,
                 List<String> trailers,
                 List<String> reviews) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.poster_image = poster_url + poster_path;
        this.trailers = trailers;
        this.reviews = reviews;
    }

    // Constructor for Parcelable, for passing object data through intents.
    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
        this.release_date = in.readString();
        this.poster_image = in.readString();
        // Need to create a new list before reading from the stream.
        this.trailers = new ArrayList<>();
        in.readStringList(this.trailers);
        this.reviews = new ArrayList<>();
        in.readStringList(this.reviews);
    }

    private String ConvertDateFormat(String oldFormat) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(oldFormat);
            return new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldFormat;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_image);
        dest.writeStringList(this.trailers);
        dest.writeStringList(this.reviews);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
        return ConvertDateFormat(release_date);
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

    public List<String> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<String> trailers) {
        this.trailers = trailers;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    // Need to override this if you are implementing Parcelable.
    @Override
    public int describeContents(){
        return 0;
    }

}
