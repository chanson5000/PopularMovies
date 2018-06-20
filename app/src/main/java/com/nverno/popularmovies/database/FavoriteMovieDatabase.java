package com.nverno.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.nverno.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class  FavoriteMovieDatabase extends RoomDatabase {

    private static final String LOG_TAG = FavoriteMovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoritemovies";
    private static FavoriteMovieDatabase sInstance;

    public static FavoriteMovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Favorite Movies database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteMovieDatabase.class, FavoriteMovieDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting Favorite Movies database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
