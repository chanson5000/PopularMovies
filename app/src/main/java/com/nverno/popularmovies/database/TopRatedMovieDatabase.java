package com.nverno.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.nverno.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class TopRatedMovieDatabase extends RoomDatabase {

    private static final String LOG_TAG = TopRatedMovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static TopRatedMovieDatabase sInstance;

    public static TopRatedMovieDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Top Rated Movies database instance");
                sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                        TopRatedMovieDatabase.class)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting Top Rated Movies database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
