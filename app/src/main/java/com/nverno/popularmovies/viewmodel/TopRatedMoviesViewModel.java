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

    public TopRatedMoviesViewModel(@NonNull Application app) {
        super(app);

        topRatedMovieRepository = new TopRatedMovieRepository(this.getApplication());
    }

    public LiveData<List<Movie>> getTopRatedMovies() {
        return topRatedMovieRepository.getTopRatedMoviesSorted();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return topRatedMovieRepository.getTopRatedMovieById(movieId);
    }
}
