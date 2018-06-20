package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.model.Movie;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> favoriteMovies;

    public FavoriteMoviesViewModel(@NonNull Application app) {
        super(app);
        FavoriteMovieDatabase database = FavoriteMovieDatabase
                .getInstance(this.getApplication());

        favoriteMovies = database.movieDao().getAll();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovies;
    }
}
