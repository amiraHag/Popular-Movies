package com.example.android.popularmovie.data.persistence.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "favoritemovietable")
public class FavoriteMovieEntry {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieid")
    private int movieid;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "originaltitle")
    private String originaltitle;

    @NonNull
    @ColumnInfo(name = "releasedate")
    private String releasedate;

    @ColumnInfo(name = "popularity")
    private Double popularity;

    @NonNull
    @ColumnInfo(name = "userrating")
    private Double userrating;

    @NonNull
    @ColumnInfo(name = "posterpath")
    private String posterpath;

    @NonNull
    @ColumnInfo(name = "overview")
    private String overview;


    public FavoriteMovieEntry(int movieid, String title, String originaltitle, String releasedate, Double popularity, Double userrating, String posterpath, String overview) {
        this.movieid = movieid;
        this.title = title;
        this.originaltitle = originaltitle;
        this.releasedate = releasedate;
        this.popularity = popularity;
        this.userrating = userrating;
        this.posterpath = posterpath;
        this.overview = overview;
    }


    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginaltitle() {
        return originaltitle;
    }

    public void setOriginaltitle(String originaltitle) {
        this.originaltitle = originaltitle;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getUserrating() {
        return userrating;
    }

    public void setUserrating(Double userrating) {
        this.userrating = userrating;
    }

    public void setPosterpath(String posterpath){ this.posterpath = posterpath; }

    public String getPosterpath() { return posterpath; }

    public void setOverview(String image) { this.overview = overview; }

    public String getOverview() { return overview; }

}