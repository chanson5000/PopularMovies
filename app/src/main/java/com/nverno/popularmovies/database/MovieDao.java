package com.nverno.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.nverno.popularmovies.model.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);

}
