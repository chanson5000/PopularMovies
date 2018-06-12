package com.nverno.popularmovies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nverno.popularmovies.database.MoviePopularDatabase;
import com.nverno.popularmovies.model.Movie;

public class PopularMovieViewModel extends ViewModel {
    private LiveData<Movie> movie;

    public PopularMovieViewModel(MoviePopularDatabase database, int id) {
        movie = database.movieDao().loadMovieById(id);
    }

    public LiveData<Movie> getMovie() { return movie; }
}
