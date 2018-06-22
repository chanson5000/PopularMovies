package com.nverno.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.nverno.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class PopularMovieDatabase extends RoomDatabase {

    private static final String LOG_TAG = PopularMovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popularMovies";
    private static PopularMovieDatabase sInstance;

    public static PopularMovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Popular Movies database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PopularMovieDatabase.class, PopularMovieDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting Popular Movies database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
