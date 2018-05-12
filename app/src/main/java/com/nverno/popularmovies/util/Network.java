package com.nverno.popularmovies.util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Network {

    public static String fetchHttpsResponse(URL url) throws IOException {

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

    public static URL makeUrl(Uri toConvert) {
        return makeUrl(toConvert.toString());
    }
}
