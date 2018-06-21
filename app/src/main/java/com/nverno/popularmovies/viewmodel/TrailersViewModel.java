package com.nverno.popularmovies.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.nverno.popularmovies.model.Trailer;
import com.nverno.popularmovies.repository.TrailerRepository;

import java.util.ArrayList;
import java.util.List;

public class TrailersViewModel extends AndroidViewModel {

    private LiveData<List<Trailer>> allTrailers;
    private LiveData<List<Trailer>> selectedTrailers;

    public TrailersViewModel(@NonNull Application app) {
        super(app);

        TrailerRepository trailerRepository = new TrailerRepository(this.getApplication());

        allTrailers = trailerRepository.getAll();
    }

    private void setSelectedTrailers(final int movieId) {
        selectedTrailers = Transformations.map(allTrailers, new Function<List<Trailer>, List<Trailer>>() {
            @Override
            public List<Trailer> apply(List<Trailer> input) {
                List<Trailer> trailers = new ArrayList<>();

                for (Trailer trailer : input) {
                    if (trailer.getMovieId() == movieId) {
                        trailers.add(trailer);
                    }
                }

                return trailers;
            }
        });
    }

    public LiveData<List<Trailer>> getTrailers(int movieId) {
        setSelectedTrailers(movieId);
        return selectedTrailers;
    }
}
