package com.nverno.popularmovies.util;

import android.net.Uri;

import com.nverno.popularmovies.model.Movie;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MovieDb implements MovieDbApi {

    final static String BASE_URL = "https://api.themoviedb.org/3/";
    final static String TOP_RATED = "movie/top_rated";
    final static String AUTH_STRING = "?api_key=";
    final static String PAGE_ONE = "&page=1";
    final static String AUTH = AUTH_STRING + API_KEY;

    final static String API_PARAM = "api_key";
    final static String PAGE_PARAM = "page";

    public static List<Movie> getTopRated() {

        Uri requestUri = Uri.parse(BASE_URL + TOP_RATED).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(1))
                .build();

        URL requestUrl = Network.makeUrl(requestUri);

        try {
            String jsonResponseString = Network.fetchHttpsResponse(requestUrl);

            return JsonParse.topRatedResults(jsonResponseString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URL getTopRatedUrl() {
        return Network.makeUrl(BASE_URL + TOP_RATED + AUTH + PAGE_ONE);
    }
}
