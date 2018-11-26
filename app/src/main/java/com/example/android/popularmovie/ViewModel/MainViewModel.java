package com.example.android.popularmovie.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.popularmovie.data.model.Movie;
import com.example.android.popularmovie.data.persistence.database.AppDatabase;
import com.example.android.popularmovie.data.persistence.database.FavoriteMovieEntry;

import java.util.ArrayList;
import java.util.List;


public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMovieEntry>> mLiveDataFavoriteMovieList;
    private AppDatabase instanceDB;
    private static List<Movie> mMovieFavoriteList;

    public MainViewModel(Application application) {
        super(application);
        instanceDB = AppDatabase.getInstance(this.getApplication());
        mLiveDataFavoriteMovieList = instanceDB.favoriteMovieDao().getAllFavoriteMovies();
    }

    public void setmLiveDataFavoriteMovieList(LiveData<List<FavoriteMovieEntry>> mLiveDataFavoriteMovieList) {
        this.mLiveDataFavoriteMovieList = mLiveDataFavoriteMovieList;
    }

    public LiveData<List<FavoriteMovieEntry>> getmLiveDataFavoriteMovieList() {

        return mLiveDataFavoriteMovieList;
    }


    public static void setMovieFavoriteList(List<FavoriteMovieEntry> entries) {

        List<Movie> movies = new ArrayList<>();
        for (FavoriteMovieEntry entry : entries) {
            Movie movie = new Movie();
            movie.setMovieId(entry.getMovieid());
            movie.setMovieTitle(entry.getTitle());
            movie.setOriginalMovieTitle(entry.getOriginaltitle());
            movie.setVoteAverage(entry.getUserrating());
            movie.setPopularity(entry.getPopularity());
            movie.setReleaseDate(entry.getReleasedate());
            movie.setOverview(entry.getOverview());
            movie.setPosterPath(entry.getPosterpath());

            movies.add(movie);
        }
        mMovieFavoriteList = movies;

    }

    public static List<Movie> getmMovieFavoriteList() {

        return mMovieFavoriteList;
    }


}
