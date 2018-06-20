package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.PopularMovieRepository;

import java.util.List;

public class PopularMoviesViewModel extends AndroidViewModel {

    private PopularMovieRepository popularMovieRepository;

    private LiveData<List<Movie>> movies;
    private LiveData<Movie> selectedMovie;

    public PopularMoviesViewModel(@NonNull Application app) {
        super(app);

        popularMovieRepository = new PopularMovieRepository(this.getApplication());

        movies = popularMovieRepository.getPopularMoviesSorted();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return movies;
    }

    public LiveData<Movie> getMovieById(int movieId) {
        loadSelectedMovie(movieId);

        return selectedMovie;
    }

    private void loadSelectedMovie(int movieId) {
        selectedMovie = popularMovieRepository.getPopularMovieById(movieId);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
