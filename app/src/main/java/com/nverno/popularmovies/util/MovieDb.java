package com.nverno.popularmovies.util;

import android.net.Uri;

import com.nverno.popularmovies.model.Movie;

import java.net.URL;
import java.util.List;

public class MovieDb implements MovieDbApi {

    private final static String BASE_URL = "https://api.themoviedb.org/3/";
    private final static String TOP_RATED = "movie/top_rated";
    private final static String POPULAR = "movie/popular";
    private final static String AUTH_STRING = "?api_key=";
    private final static String PAGE_ONE = "&page=1";
    private final static String AUTH = AUTH_STRING + API_KEY;

    private final static String API_PARAM = "api_key";
    private final static String PAGE_PARAM = "page";

    // I only really need these two for now...
    public static URL getTopRatedUrl() {
        return Network.makeUrl(BASE_URL + TOP_RATED + AUTH + PAGE_ONE);
    }

    public static URL getPopularUrl() {
        return Network.makeUrl(BASE_URL + POPULAR + AUTH + PAGE_ONE);
    }

    // I may want to implement this URI builder in the future...
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
}
