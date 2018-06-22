package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.FavoriteMovieRepository;
import com.nverno.popularmovies.repository.PopularMovieRepository;
import com.nverno.popularmovies.repository.TopRatedMovieRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> popularMovies;
    private LiveData<List<Movie>> topRatedMovies;
    private LiveData<List<Movie>> favoriteMovies;

    private MediatorLiveData<List<Movie>> selectedMovies;
    private LiveData<Movie> selectedMovie;

    private boolean isFavoriteMoviesList = false;

    private static final int SORT_POPULAR = 0;
    private static final int SORT_TOP_RATED = 1;
    private static final int SHOW_FAVORITES = 2;

    public MoviesViewModel(@NonNull Application app) {
        super(app);

        PopularMovieRepository popularMovieRepository = new PopularMovieRepository(this.getApplication());
        TopRatedMovieRepository topRatedMovieRepository = new TopRatedMovieRepository(this.getApplication());
        FavoriteMovieRepository favoriteMovieRepository = new FavoriteMovieRepository(this.getApplication());

        popularMovies = popularMovieRepository.getAllSorted();
        topRatedMovies = topRatedMovieRepository.getAllSorted();
        favoriteMovies = favoriteMovieRepository.getAll();

        selectedMovies = new MediatorLiveData<>();
        selectedMovie = new MutableLiveData<>();
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return selectedMovies;
    }

    private void removeAllSources() {
        selectedMovies.removeSource(popularMovies);
        selectedMovies.removeSource(topRatedMovies);
        selectedMovies.removeSource(favoriteMovies);
    }

    public void loadPopularMovies() {
        removeAllSources();

        if (isFavoriteMoviesList) {
            isFavoriteMoviesList = false;
        }

        selectedMovies.addSource(popularMovies, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                selectedMovies.setValue(movies);
            }
        });
    }

    public void loadTopRatedMovies() {
        removeAllSources();

        if (isFavoriteMoviesList) {
            isFavoriteMoviesList = false;
        }

        selectedMovies.addSource(topRatedMovies, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                selectedMovies.setValue(movies);
            }
        });
    }

    public void loadFavoriteMovies() {
        removeAllSources();

        if (!isFavoriteMoviesList) {
            isFavoriteMoviesList = true;
        }

        selectedMovies.addSource(favoriteMovies, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                selectedMovies.setValue(movies);
            }
        });
    }

    public LiveData<Movie> getSelectedMovie() {
        return selectedMovie;
    }

    public boolean isFavoriteMoviesList() {
        return isFavoriteMoviesList;
    }

    public LiveData<Boolean> isFavoriteMovie(final int movieId) {
        return Transformations.map(favoriteMovies, new Function<List<Movie>, Boolean>() {
            @Override
            public Boolean apply(List<Movie> movies) {
                for (Movie movie : movies) {
                    if (movie.getId() == movieId) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void setSelectedMovie(int sortType, final int movieId) {
        switch (sortType) {
            case SORT_POPULAR:
                selectedMovie = Transformations.map(popularMovies, new Function<List<Movie>, Movie>() {
                    @Override
                    public Movie apply(List<Movie> movies) {
                        for (Movie movie : movies) {
                            if (movie.getId() == movieId) {
                                return movie;
                            }
                        }
                        return null;
                    }
                });
                break;

            case SORT_TOP_RATED:
                selectedMovie = Transformations.map(topRatedMovies, new Function<List<Movie>, Movie>() {
                    @Override
                    public Movie apply(List<Movie> movies) {
                        for (Movie movie : movies) {
                            if (movie.getId() == movieId) {
                                return movie;
                            }
                        }
                        return null;
                    }
                });
                break;

            case SHOW_FAVORITES:
                selectedMovie = Transformations.map(favoriteMovies, new Function<List<Movie>, Movie>() {
                    @Override
                    public Movie apply(List<Movie> movies) {
                        for (Movie movie : movies) {
                            if (movie.getId() == movieId) {
                                return movie;
                            }
                        }
                        return null;
                    }
                });

            default:
                break;
        }
    }
}
