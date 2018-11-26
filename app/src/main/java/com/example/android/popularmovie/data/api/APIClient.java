package com.example.android.popularmovie.data.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class APIClient {

    static String MOVIE_DB_API_URL = "https://api.themoviedb.org/3/";
    static Retrofit API_Retrofit = null;


    public static Retrofit getAPIClient() {

        if (API_Retrofit==null) {

            setNewBaseUrl(MOVIE_DB_API_URL);
        }

        return API_Retrofit;
    }


    public static void setNewBaseUrl(String url) {

        if (url!=null) {
            API_Retrofit = new Retrofit.Builder()
                    .baseUrl(MOVIE_DB_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }


}
