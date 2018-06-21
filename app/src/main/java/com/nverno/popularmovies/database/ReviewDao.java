package com.nverno.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nverno.popularmovies.model.Review;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Review> reviews);

    @Query("SELECT * FROM review")
    LiveData<List<Review>> getAll();

    @Delete
    void delete(Review review);
}
