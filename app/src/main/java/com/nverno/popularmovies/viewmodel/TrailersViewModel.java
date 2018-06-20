package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.TrailerDatabase;
import com.nverno.popularmovies.model.Trailer;
import com.nverno.popularmovies.repository.TrailerRepository;

import java.util.List;

public class TrailersViewModel extends AndroidViewModel {

    private TrailerRepository trailerRepository;

    public TrailersViewModel(@NonNull Application app) {
        super(app);

        trailerRepository = new TrailerRepository(this.getApplication());
    }

    public LiveData<List<Trailer>> getTrailersByMovieId(int movieId) {
        return trailerRepository.getTrailersByMovieId(movieId);
    }
}
