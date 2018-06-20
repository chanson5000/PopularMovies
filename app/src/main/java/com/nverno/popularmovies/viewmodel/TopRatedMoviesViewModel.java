package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.TopRatedMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.TopRatedMovieRepository;

import java.util.List;

public class TopRatedMoviesViewModel extends AndroidViewModel {

    private TopRatedMovieRepository topRatedMovieRepository;
    private LiveData<List<Movie>> movies;
    private LiveData<Movie> selectedMovie;

    public TopRatedMoviesViewModel(@NonNull Application app) {
        super(app);

        topRatedMovieRepository = new TopRatedMovieRepository(this.getApplication());

        movies = topRatedMovieRepository.getTopRatedMoviesSorted();
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return movies;
    }

    public LiveData<Movie> getMovieById(int movieId) {
        loadSelectedMovie(movieId);

        return selectedMovie;
    }

    private void loadSelectedMovie(int movieId) {
        selectedMovie = topRatedMovieRepository.getTopRatedMovieById(movieId);
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }
}
