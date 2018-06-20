package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.database.TrailerDatabase;
import com.nverno.popularmovies.model.Trailer;

import java.util.List;

public class TrailersViewModel extends AndroidViewModel {

    private TrailerDatabase database;

    public TrailersViewModel(@NonNull Application app) {
        super(app);
        database = TrailerDatabase.getInstance(this.getApplication());
    }

    public LiveData<List<Trailer>> getTrailers() {
        return database.trailerDao().getAll();
    }

    public LiveData<List<Trailer>> getTrailersByMovieId(int movieId) {
        return database.trailerDao().getByMovieId(movieId);
    }
}
