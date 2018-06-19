package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.PopularMovieDatabase;
import com.nverno.popularmovies.model.Movie;

import java.util.List;

public class PopularMoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> popularMovies;

    public PopularMoviesViewModel(@NonNull Application app) {
        super(app);
        PopularMovieDatabase database = PopularMovieDatabase
                .getInstance(this.getApplication());

        popularMovies = database.movieDao().getByPopularity();
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovies;
    }
}
