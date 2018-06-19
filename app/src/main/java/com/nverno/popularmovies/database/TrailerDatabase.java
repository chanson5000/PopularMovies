package com.nverno.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.nverno.popularmovies.model.Trailer;

@Database(entities = {Trailer.class}, version = 1, exportSchema = false)
public abstract class TrailerDatabase extends RoomDatabase {

    private static final String LOG_TAG = TrailerDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "trailers";
    private static TrailerDatabase sInstance;

    public static TrailerDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Trailers database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        TrailerDatabase.class, TrailerDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting Trailers database instance");
        return sInstance;
    }

    public abstract TrailerDao trailerDao();
}
