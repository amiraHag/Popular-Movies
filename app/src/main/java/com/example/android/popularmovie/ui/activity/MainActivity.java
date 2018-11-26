package com.example.android.popularmovie.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovie.BuildConfig;
import com.example.android.popularmovie.R;
import com.example.android.popularmovie.ViewModel.MainViewModel;
import com.example.android.popularmovie.data.api.APIClient;
import com.example.android.popularmovie.data.api.APIInterfaceService;
import com.example.android.popularmovie.data.persistence.database.FavoriteMovieEntry;
import com.example.android.popularmovie.data.model.Movie;
import com.example.android.popularmovie.data.model.Movies;
import com.example.android.popularmovie.ui.adapter.MoviesAdapter;
import com.example.android.popularmovie.ui.listener.RecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //API variables
    final String API_SECRET_KEY = BuildConfig.THE_MOVIE_DB_API_SECRET_KEY;

    //Toast Messages Variables
    String EMPTY_APIKEY_MESSAGE = "API Key is empty, Please obtain API Key first";
    String Error_FETCHING_DATA_MESSAGE = "Error Fetching Data from api please try another time!";
    String MOVIES_DISPLAY_MESSAGE = "Movies will Display";

    //Persistence Variables
    private String RV_MAIN_LAYOUT = "rv_main_layout";
    private String MOVIES_LIST = "movies_list";
    private Parcelable mPersistenceMainRecyclerView;

    //Recycler View Variables
    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> mInstanceMoviesList = new ArrayList<>();
    List<Movie> mMovieList;
    int mMoviesPortrait = 2;
    int mMoviesLansdScape = 4;
    private RecyclerViewScrollListener recyclerViewScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkSavedState(savedInstanceState);
        setActivityLayout();
    }


    public void checkSavedState(Bundle savedState) {
        if (savedState!=null) {
            mInstanceMoviesList = savedState.getParcelableArrayList(MOVIES_LIST);
            mPersistenceMainRecyclerView = savedState.getParcelable(RV_MAIN_LAYOUT);
        }
    }


    private void setActivityLayout() {
        createRecyclerView();
        displayMovies();
    }

    private void createRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main_activity);
        mMoviesAdapter = new MoviesAdapter(this, mInstanceMoviesList);
        checkOrientation(this, mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMoviesAdapter);
        mMoviesAdapter.notifyDataSetChanged();
    }


    public void checkOrientation(Activity activity, RecyclerView recyclerView) {
        if (activity.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mMoviesPortrait));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, mMoviesLansdScape));
        }

    }


    private void displayMovies() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mMoviesSortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular)
        );
        String mostPopular = this.getString(R.string.pref_most_popular);
        String topRated = this.getString(R.string.pref_highest_rated);
        String favorite = this.getString(R.string.favorite);
        Boolean hasApiKey = API_SECRET_KEY.isEmpty();

        try {
            if (hasApiKey) {
                Toast.makeText(this, EMPTY_APIKEY_MESSAGE, Toast.LENGTH_SHORT).show();
                return;
            }
            if (mMoviesSortOrder.equals(mostPopular)) {
                displaySortingMovies("popular");
            } else if (mMoviesSortOrder.equals(favorite)) {
                displayFavoriteMovies();

            } else {
                displaySortingMovies("top rated");
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    private void displayFavoriteMovies() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getmLiveDataFavoriteMovieList().observe(this, new Observer<List<FavoriteMovieEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteMovieEntry> imageEntries) {
                MainViewModel.setMovieFavoriteList(imageEntries);
                mMovieList = MainViewModel.getmMovieFavoriteList();
                mMoviesAdapter.setmMovieList(mMovieList);
                mInstanceMoviesList.addAll(mMovieList);
            }
        });

    }


    private void displaySortingMovies(final String sortOrderChoosed) {

        APIClient APIClient = new APIClient();
        APIInterfaceService apiAPIInterfaceService =
                APIClient.getAPIClient().create(APIInterfaceService.class);
        Call<Movies> call;

        if (sortOrderChoosed=="popular") {
            call = apiAPIInterfaceService.getPopularMovies(API_SECRET_KEY);
        } else if (sortOrderChoosed=="top rated") {
            call = apiAPIInterfaceService.getTopRatedMovies(API_SECRET_KEY);
        } else {
            call = apiAPIInterfaceService.getTopRatedMovies(API_SECRET_KEY);
        }

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> callMovies, Response<Movies> responseMovies) {
                if (responseMovies.isSuccessful())

                {

                    Toast.makeText(MainActivity.this, sortOrderChoosed + MOVIES_DISPLAY_MESSAGE, Toast.LENGTH_SHORT).show();

                    if (responseMovies.body()!=null) {
                        mMovieList = responseMovies.body().getResults();
                        mInstanceMoviesList.addAll(mMovieList);
                        mMoviesAdapter.setmMovieList(mMovieList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MainActivity.this, Error_FETCHING_DATA_MESSAGE, Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(MOVIES_LIST, mInstanceMoviesList);
        savedInstanceState.putParcelable(RV_MAIN_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mInstanceMoviesList = savedInstanceState.getParcelableArrayList(MOVIES_LIST);
        mPersistenceMainRecyclerView = savedInstanceState.getParcelable(RV_MAIN_LAYOUT);
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_settings==item.getItemId()) {
            startSettingActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startSettingActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }
}