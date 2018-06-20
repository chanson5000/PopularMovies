package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.TopRatedMovieDatabase;
import com.nverno.popularmovies.model.Movie;

import java.util.List;

public class TopRatedMoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> topRatedMovies;
    private TopRatedMovieDatabase database;

    public TopRatedMoviesViewModel(@NonNull Application app) {
        super(app);
        database = TopRatedMovieDatabase.getsInstance(this.getApplication());

        topRatedMovies = database.movieDao().getByRating();
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovies;
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return database.movieDao().getMovieById(movieId);
    }
}
