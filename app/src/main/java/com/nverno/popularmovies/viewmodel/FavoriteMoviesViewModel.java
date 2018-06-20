package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private FavoriteMovieRepository favoriteMovieRepository;

    public FavoriteMoviesViewModel(@NonNull Application app) {
        super(app);

        favoriteMovieRepository = new FavoriteMovieRepository(this.getApplication());
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return favoriteMovieRepository.getFavoriteMovies();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return favoriteMovieRepository.getMovieById(movieId);
    }
}
