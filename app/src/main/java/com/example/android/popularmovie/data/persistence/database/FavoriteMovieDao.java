package com.example.android.popularmovie.data.persistence.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface FavoriteMovieDao {


    @Query("SELECT * FROM favoritemovietable WHERE movieid = :movie_id")
    List<FavoriteMovieEntry> getFavoriteMovieById(int movie_id);


    @Query("SELECT * FROM favoritemovietable WHERE title = :title")
    List<FavoriteMovieEntry> getFavoriteMovieByTitle(String title);

    @Query("SELECT * FROM favoritemovietable")
    LiveData<List<FavoriteMovieEntry>> getAllFavoriteMovies();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(FavoriteMovieEntry favoriteMovieEntry);


    @Query("DELETE FROM favoritemovietable WHERE movieid = :movie_id")
    void deleteFavoriteMovieWithId(int movie_id);

    @Query("DELETE FROM favoritemovietable WHERE title = :title")
    void deleteFavoriteMovieWithTitle(String title);


}
