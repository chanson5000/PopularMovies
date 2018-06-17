package com.nverno.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    // This is the base url we will use to fetch poster image urls.
    private static final String base_poster_url = "https://image.tmdb.org/t/p/w500";

    // Note: Serialization (Java to JSON), Deserialization (JSON to Java)
    // @SerializedName("field") sets the annotation of the JSON fields we fetch from the API.
    // With my fields being the same as my private variables, it is not needed.
    // With @Expose(serialization = false) we can also set whether we (de)serialize or not.

    // Serialize and Expose for use with retrofit2
    @Expose
    private int id;
    @Expose
    private String title;
    @Expose
    private String poster_path;
    @Expose
    private String overview;
    @Expose
    private double vote_average;
    @Expose
    private String release_date;

    // TODO: Implement these as properties.
    private List<String> trailers;
    private List<String> reviews;

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

    // Constructor for Parcelable, for passing object data through intents.
    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
        this.release_date = in.readString();

        // Need to create a new list before reading from the stream.
        // TODO: These currently have no data.
        this.trailers = new ArrayList<>();
        in.readStringList(this.trailers);
        this.reviews = new ArrayList<>();
        in.readStringList(this.reviews);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
        dest.writeString(this.release_date);
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

    // Our poster image url is not relying on any private variables.
    // API calls with retrofit2 did not seem to initialize constructor that would originally
    // assign the private variable that would have been accessed here. This works.
    public String getPosterImage() {
        return base_poster_url + poster_path;
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
    public int describeContents() {
        return 0;
    }

}
