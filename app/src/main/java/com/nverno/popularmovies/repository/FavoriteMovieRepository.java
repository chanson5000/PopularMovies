package com.nverno.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.database.MovieDao;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.util.AppExecutors;

import java.util.List;

public class FavoriteMovieRepository {

    private static final String LOG_TAG = FavoriteMovieRepository.class.getSimpleName();

    private FavoriteMovieDatabase favoriteMovieDatabase;

    public FavoriteMovieRepository(Context context) {
        favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(context);
    }

    public void remove(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteMovieDatabase.movieDao().delete(movie);
            }
        });
    }

    public void add(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favoriteMovieDatabase.movieDao().insert(movie);
            }
        });
    }

    public LiveData<List<Movie>> getAll() {
        return favoriteMovieDatabase.movieDao().getAll();
    }
}
