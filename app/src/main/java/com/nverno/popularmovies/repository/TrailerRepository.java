package com.nverno.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nverno.popularmovies.database.TrailerDatabase;
import com.nverno.popularmovies.model.Trailer;
import com.nverno.popularmovies.model.TrailerResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;
import com.nverno.popularmovies.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerRepository extends Repository {

    private static final String LOG_TAG = TrailerRepository.class.getSimpleName();

    private TrailerDatabase trailerDatabase;

    private Context mContext;

    private static List<Integer> retrievedTrailers = new ArrayList<>();

    public TrailerRepository(Context context) {
        trailerDatabase = TrailerDatabase.getInstance(context);

        mContext = context;
    }

    public void fetchMovieTrailersFromWeb(final int movieId) {

        if (networkNotAvailable(mContext)) {
            Log.d(LOG_TAG, "Skipping internet data fetch, network not available.");
            return;
        }

        if (retrievedTrailers.contains(movieId)) {
            Log.d(LOG_TAG, "Skipped fetching internet data for: " + movieId);
            return;
        }

        MovieDbApi movieDbApi = getMovieDbApi(MovieDbApi.class);

        Call<TrailerResult> call = movieDbApi.trailers(movieId);

        call.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResult> call,
                                   @NonNull Response<TrailerResult> response) {

                if (response.code() == 401) {
                    Log.e(LOG_TAG, "Authentication failed. Please check your API key.");
                } else if (response.code() == 404) {
                    Log.e(LOG_TAG, "Server returned \"Not Found\" error.");
                } else if (response.code() == 200) {
                    final List<Trailer> trailers = response.body().GetTrailers();

                    Log.d(LOG_TAG, "Fetched internet data for: " + movieId);

                    for (Trailer trailer : trailers) {
                        trailer.setMovieId(movieId);
                    }

                    retrievedTrailers.add(movieId);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            trailerDatabase.trailerDao().insertMany(trailers);
                        }
                    });
                } else {
                    Log.e(LOG_TAG,
                            "Failed to fetch internet data for: " + movieId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to fetch internet data for: " + movieId);
            }
        });
    }

    public LiveData<List<Trailer>> getAll() {
        return trailerDatabase.trailerDao().getAll();
    }
}
