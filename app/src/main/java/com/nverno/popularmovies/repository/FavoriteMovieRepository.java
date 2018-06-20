package com.nverno.popularmovies.repository;

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.util.AppExecutors;

public class FavoriteMovieRepository {

    public void removeFavoriteMovie(final FavoriteMovieDatabase favoriteMovieDatabase, final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteMovieDatabase.movieDao().delete(movie);
            }
        });
    }

    public void addFavoriteMovie(final FavoriteMovieDatabase favoriteMovieDatabase, final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteMovieDatabase.movieDao().insert(movie);
            }
        });
    }

}
