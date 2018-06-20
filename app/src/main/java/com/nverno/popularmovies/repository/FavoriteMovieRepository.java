package com.nverno.popularmovies.repository;

import android.content.Context;

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.util.AppExecutors;

public class FavoriteMovieRepository {

    private static final String LOG_TAG = FavoriteMovieRepository.class.getSimpleName();

    private FavoriteMovieDatabase favoriteMovieDatabase;

    public FavoriteMovieRepository(Context context) {
        favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(context);
    }

    public void removeFavoriteMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteMovieDatabase.movieDao().delete(movie);
            }
        });
    }

    public void addFavoriteMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteMovieDatabase.movieDao().insert(movie);
            }
        });
    }

}
