package com.nverno.popularmovies.util;

import android.net.Uri;

import com.nverno.popularmovies.model.Movie;

import java.net.URL;
import java.util.List;

public class MovieDb implements MovieDBApi {

    final static String BASE_URL = "https://api.themoviedb.org/3/";
    final static String TOP_RATED = "movie/top_rated";
    final static String AUTH_STRING = "?api_key=";
    final static String PAGE_ONE = "&page=1";
    final static String AUTH = AUTH_STRING + KEY;

    public static List<Movie> getTopRated() {
        try {
            URL url = Network.makeUrl(BASE_URL + TOP_RATED + AUTH + PAGE_ONE);
            String jsonResponseString = Network.fetchHttp(url);

            return JsonParse.topRatedResults(jsonResponseString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static URL getTopRatedUrl() {
        return Network.makeUrl(BASE_URL + TOP_RATED + AUTH + PAGE_ONE);
    }

    public static Uri getTopRatedUri() {
        String base = BASE_URL + TOP_RATED + AUTH + PAGE_ONE;

            Uri uri = Uri.parse(base).buildUpon().build();

            return uri;
    }

}
