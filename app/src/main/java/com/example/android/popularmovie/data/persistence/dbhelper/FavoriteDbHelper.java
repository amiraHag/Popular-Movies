package com.example.android.popularmovie.data.persistence.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.android.popularmovie.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {


    // Database Info
    private static final String DATABASE_NAME = "favoritemoviedatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_FAVORITE_MOVIE = "favoritemovie";

    // Post Table Columns
    private static final String KEY_MOVIE_ID = "movieid";
    private static final String KEY_MOVIE_TITLE = "title";
    private static final String KEY_MOVIE_ORIGINAL_TITLE = "originalTitle";
    private static final String KEY_MOVIE_USER_RATING = "userrating";
    private static final String KEY_MOVIE_RELEASE_DATE = "releaseDate";
    private static final String KEY_MOVIE_POPULARITY = "popularity";
    private static final String KEY_MOVIE_PLOT_SYNOPSIS = "overview";
    private static final String KEY_MOVIE_POSTER_PATH = "posterPath";


    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE_MOVIE + " (" +
                KEY_MOVIE_ID + " INTEGER PRIMARY KEY," +
                KEY_MOVIE_TITLE + " TEXT NOT NULL, " +
                KEY_MOVIE_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                KEY_MOVIE_USER_RATING + " REAL NOT NULL, " +
                KEY_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                KEY_MOVIE_POPULARITY + " REAL NOT NULL, " +
                KEY_MOVIE_PLOT_SYNOPSIS + " TEXT NOT NULL, " +
                KEY_MOVIE_POSTER_PATH + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion!=newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_MOVIE);
            onCreate(db);
        }
    }


    public void addFavoriteMovie(Movie movie) {

        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_MOVIE_ID, movie.getMovieId());
            values.put(KEY_MOVIE_TITLE, movie.getMovieTitle());
            values.put(KEY_MOVIE_ORIGINAL_TITLE, movie.getOriginalMovieTitle());
            values.put(KEY_MOVIE_USER_RATING, movie.getVoteAverage());
            values.put(KEY_MOVIE_POPULARITY, movie.getPopularity());
            values.put(KEY_MOVIE_PLOT_SYNOPSIS, movie.getOverview());
            values.put(KEY_MOVIE_RELEASE_DATE, movie.getReleaseDate());
            values.put(KEY_MOVIE_POSTER_PATH, movie.getPosterPath());


            db.insertOrThrow(TABLE_FAVORITE_MOVIE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Error", "Error add movie to database");
        } finally {
            db.endTransaction();
            db.close();
        }


    }


    public List<Movie> getAllFavoriteMovie() {

        List<Movie> favoriteList = new ArrayList<>();

        String[] mTableColumns = {
                KEY_MOVIE_ID,
                KEY_MOVIE_TITLE,
                KEY_MOVIE_ORIGINAL_TITLE,
                KEY_MOVIE_USER_RATING,
                KEY_MOVIE_RELEASE_DATE,
                KEY_MOVIE_POPULARITY,
                KEY_MOVIE_PLOT_SYNOPSIS,
                KEY_MOVIE_POSTER_PATH

        };


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVORITE_MOVIE,
                mTableColumns,
                null,
                null,
                null,
                null,
                KEY_MOVIE_ID);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.setMovieId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_ID))));
                    movie.setMovieTitle(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_TITLE)));
                    movie.setOriginalMovieTitle(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_ORIGINAL_TITLE)));
                    movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_USER_RATING))));
                    movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_RELEASE_DATE)));
                    movie.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_POPULARITY))));
                    movie.setPosterPath(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_POSTER_PATH)));
                    movie.setOverview(cursor.getString(cursor.getColumnIndex(KEY_MOVIE_PLOT_SYNOPSIS)));

                    favoriteList.add(movie);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Error", "Error get movies from database");
        } finally {
            if (cursor!=null && !cursor.isClosed()) {
                cursor.close();
                db.close();
            }
        }
        return favoriteList;
    }

    public void deleteFavoriteMovieById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_FAVORITE_MOVIE, KEY_MOVIE_ID + "=" + id, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Error", "Error delete favorite movie");
        } finally {
            db.endTransaction();
            db.close();
        }


    }

    public void deleteAllFavoriteMovies() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {

            db.delete(TABLE_FAVORITE_MOVIE, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Error", "Error delete all favorite movies");
        } finally {
            db.endTransaction();
            db.close();
        }


    }
}
