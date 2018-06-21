package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.FavoriteMovieRepository;

import java.util.List;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private FavoriteMovieRepository favoriteMovieRepository;

    private LiveData<List<Movie>> movies;
    private LiveData<Movie> selectedMovie;

    public FavoriteMoviesViewModel(@NonNull Application app) {
        super(app);

        favoriteMovieRepository = new FavoriteMovieRepository(this.getApplication());

        movies = favoriteMovieRepository.getFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return movies;
    }

    public LiveData<Movie> getMovieById(int movieId) {
        loadSelectedMovie(movieId);

        return selectedMovie;
    }

    private void loadSelectedMovie(int movieId) {
        selectedMovie = favoriteMovieRepository.getMovieById(movieId);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
