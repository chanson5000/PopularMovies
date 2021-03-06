package com.nverno.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nverno.popularmovies.model.Trailer;

import java.util.List;

@Dao
public interface TrailerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Trailer> trailers);

    @Query("SELECT * FROM trailer")
    LiveData<List<Trailer>> getAll();

    @Delete
    void delete(Trailer trailer);
}
