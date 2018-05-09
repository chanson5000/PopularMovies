package com.nverno.popularmovies.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class Network {
    private final static String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3";

    public static String fetchHttp(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public static URL makeUrl(String toConvert) {
        try {
            URL url = new URL(toConvert);
            return url;
        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }
//
//    public static URI buildApiUri(String baseUrl, String... queries) {
//        URI newUri = Uri.parse(MOVIEDB_BASE_URL + baseUrl).buildUpon()
//                .appendQueryParameter()
//    }
}
