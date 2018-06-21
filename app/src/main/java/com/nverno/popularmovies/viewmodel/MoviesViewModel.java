package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.FavoriteMovieRepository;
import com.nverno.popularmovies.repository.PopularMovieRepository;
import com.nverno.popularmovies.repository.TopRatedMovieRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private PopularMovieRepository popularMovieRepository;
    private TopRatedMovieRepository topRatedMovieRepository;
    private FavoriteMovieRepository favoriteMovieRepository;

    private LiveData<List<Movie>> popularMovies;
    private LiveData<List<Movie>> topRatedMovies;
    private LiveData<List<Movie>> favoriteMovies;

    private LiveData<List<Movie>> selectedMovies;
    private MutableLiveData<Movie> selectedMovie;


    public MoviesViewModel(@NonNull Application app) {
        super(app);

        popularMovieRepository = new PopularMovieRepository(this.getApplication());


    }

}
