package com.example.android.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Movie implements Parcelable {


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    //Declare Variables
    @SerializedName("id")
    private Integer movieId;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalMovieTitle;

    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    private boolean isFavoritedMovie = false;
    String Poster_Base_Url = "http://image.tmdb.org/t/p/";
    String Poster_Size_Url = "w342";

    public Movie(Integer movieId, int voteCount, Boolean video, Double voteAverage, String movieTitle, Double popularity,
                 String posterPath, String originalLanguage, String originalMovieTitle, List<Integer> genreIds, String backdropPath,
                 boolean adult, String overview, String releaseDate, boolean isFavoritedMovie) {
        this.movieId = movieId;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.movieTitle = movieTitle;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalMovieTitle = originalMovieTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavoritedMovie = isFavoritedMovie;

    }

    public Movie() {

    }

    public static final Comparator<Movie> COMPARE_MOVIES_BY_NAME = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie movie2) {
            if (movie.originalMovieTitle!=null && movie2.originalMovieTitle!=null) {
                int compareResult = movie.originalMovieTitle.compareTo(movie2.originalMovieTitle);

                return compareResult;
            }
            return 0;
        }
    };


    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer id) {
        this.movieId = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String title) {
        this.movieTitle = title;
    }

    public String getOriginalMovieTitle() {
        return originalMovieTitle;
    }

    public void setOriginalMovieTitle(String originalTitle) {
        this.originalMovieTitle = originalTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }


    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }


    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.movieId);
        dest.writeString(this.movieTitle);
        dest.writeString(this.originalMovieTitle);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeValue(this.video);
        dest.writeList(this.genreIds);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);


    }


    private Movie(Parcel in) {

        this.movieId = in.readInt();
        this.movieTitle = in.readString();
        this.originalMovieTitle = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
        this.posterPath = in.readString();
        this.adult = in.readByte()!=0;
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.originalLanguage = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
    }


}
