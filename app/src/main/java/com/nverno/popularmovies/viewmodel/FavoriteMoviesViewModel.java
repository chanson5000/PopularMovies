package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private FavoriteMovieDatabase database;

    public FavoriteMoviesViewModel(@NonNull Application app) {
        super(app);
        database = FavoriteMovieDatabase.getInstance(this.getApplication());
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return database.movieDao().getAll();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return database.movieDao().getMovieById(movieId);
    }
}
