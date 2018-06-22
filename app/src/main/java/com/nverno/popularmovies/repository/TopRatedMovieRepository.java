package com.nverno.popularmovies.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nverno.popularmovies.database.TopRatedMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.model.MovieResult;
import com.nverno.popularmovies.moviedb.MovieDbApi;
import com.nverno.popularmovies.util.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedMovieRepository extends Repository {

    private static final String LOG_TAG = TopRatedMovieRepository.class.getSimpleName();

    private final TopRatedMovieDatabase topRatedMovieDatabase;

    private final Context mContext;

    private static boolean databaseUpdated = false;

    public TopRatedMovieRepository(Context context) {
        topRatedMovieDatabase = TopRatedMovieDatabase.getsInstance(context);

        mContext = context;

        cacheWebData();
    }

    private void cacheWebData() {

        if (networkNotAvailable(mContext)) {
            Log.d(LOG_TAG, "Skipping internet data fetch, network not available.");
            return;
        }

        if (databaseUpdated) {
            Log.d(LOG_TAG, "Skipped fetching internet data.");
            return;
        }

        MovieDbApi movieDbApi = getMovieDbApi(MovieDbApi.class);

        Call<MovieResult> call = movieDbApi.topRatedMovies();

        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call,
                                   @NonNull Response<MovieResult> response) {

                switch (response.code()) {
                    case 401:
                        Log.e(LOG_TAG, "Authentication failed. Please check your API key.");
                        break;
                    case 404:
                        Log.e(LOG_TAG, "Server returned \"Not Found\" error.");
                        break;
                    case 200:
                        final List<Movie> movies = response.body().GetMovies();

                        Log.d(LOG_TAG, "Fetched internet data.");
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                topRatedMovieDatabase.movieDao().insertMany(movies);
                            }
                        });

                        databaseUpdated = true;
                        break;
                    default:
                        Log.e(LOG_TAG,
                                "Failed to fetch internet data.");
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(LOG_TAG,
                        "Failed to fetch internet data.");
            }
        });
    }

    public LiveData<List<Movie>> getAllSorted() {
        return topRatedMovieDatabase.movieDao().getAllSortedByRating();
    }
}
