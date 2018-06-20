package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.TopRatedMovieDatabase;
import com.nverno.popularmovies.model.Movie;

import java.util.List;

public class TopRatedMoviesViewModel extends AndroidViewModel {

    private TopRatedMovieDatabase database;

    public TopRatedMoviesViewModel(@NonNull Application app) {
        super(app);
        database = TopRatedMovieDatabase.getsInstance(this.getApplication());
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return database.movieDao().getByRating();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return database.movieDao().getMovieById(movieId);
    }
}
