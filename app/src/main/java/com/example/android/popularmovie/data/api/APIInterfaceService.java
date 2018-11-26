package com.example.android.popularmovie.data.api;

import com.example.android.popularmovie.data.model.Movie;
import com.example.android.popularmovie.data.model.Movies;
import com.example.android.popularmovie.data.model.Reviews;
import com.example.android.popularmovie.data.model.Trailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterfaceService {

    @GET("movie/popular")
    Call<Movies> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<Movies> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<Movies> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<Movies> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    //Trailers
    @GET("movie/{id}/videos")
    Call<Trailers> getMovieTrailer(@Path("id") int id, @Query("api_key") String apiKey);

    //Reviews
    @GET("movie/{id}/reviews")
    Call<Reviews> getReview(@Path("id") int id, @Query("api_key") String apiKey);

    //Casts
    @GET("/movie/{id}/credits")
    Call<Movie> getMovieCasts(@Path("id") int id, @Query("api_key") String apiKey);

}
