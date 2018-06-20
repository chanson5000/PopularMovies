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

    public void removeFavoriteMovie(final Movie movie) {
        favoriteMovieDatabase.movieDao().delete(movie);
    }

    public void addFavoriteMovie(final Movie movie) {
        favoriteMovieDatabase.movieDao().insert(movie);
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovieDatabase.movieDao().getAll();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return favoriteMovieDatabase.movieDao().getMovieById(movieId);
    }
}
