package com.nverno.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.nverno.popularmovies.model.Review;

@Database(entities = {Review.class}, version = 1, exportSchema = false)
public abstract class ReviewDatabase extends RoomDatabase {

    private static final String LOG_TAG = ReviewDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "reviews";
    private static ReviewDatabase sInstance;

    public static ReviewDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Reviews database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        ReviewDatabase.class, ReviewDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting Reviews database instance");
        return sInstance;
    }

    public abstract ReviewDao reviewDao();
}
