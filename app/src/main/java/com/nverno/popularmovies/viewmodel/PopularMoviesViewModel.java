package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nverno.popularmovies.database.PopularMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.PopularMovieRepository;

import java.util.List;

public class PopularMoviesViewModel extends AndroidViewModel {

    private PopularMovieRepository popularMovieRepository;
    private MutableLiveData<Movie> selectedMovie;
    private LiveData<List<Movie>> movies;

    public PopularMoviesViewModel(@NonNull Application app) {
        super(app);

        popularMovieRepository = new PopularMovieRepository(this.getApplication());
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return popularMovieRepository.getPopularMoviesSorted();
    }

    public LiveData<Movie> getMovieById(int movieId) {
        return popularMovieRepository.getPopularMovieById(movieId);
    }
}
