package com.nverno.popularmovies.util;

import com.nverno.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParse {
    // Just parsing our Json string into a movie object here...
    public static List<Movie> topRatedResults(String jsonString) throws JSONException {

        JSONObject jsonMoviesObject = new JSONObject(jsonString);
        JSONArray moviesResultsArray = jsonMoviesObject.getJSONArray("results");

        List<Movie> movies = new ArrayList<>();

        int len = moviesResultsArray.length();
        for (int i=0; i<len; i++)
        {
            JSONObject movie = moviesResultsArray.getJSONObject(i);

            movies.add(new Movie(movie.getInt("id"),
                    movie.getString("title"),
                    movie.getString("poster_path"),
                    movie.getString("overview"),
                    movie.getDouble("vote_average"),
                    movie.getString("release_date")));
        }

        return movies;
    }
}
