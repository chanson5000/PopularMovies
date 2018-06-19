package com.nverno.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nverno.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAll();

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    LiveData<List<Movie>> getByRating();

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    LiveData<List<Movie>> getByPopularity();

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Movie> movies);

    @Delete
    void delete(Movie movie);

}
