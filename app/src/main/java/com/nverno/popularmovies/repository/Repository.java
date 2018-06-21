package com.nverno.popularmovies.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

abstract class Repository {

    boolean networkNotAvailable(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
