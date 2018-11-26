package com.example.android.popularmovie.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popularmovie.data.persistence.database.AppDatabase;
import com.example.android.popularmovie.data.persistence.database.FavoriteMovieEntry;
import com.example.android.popularmovie.data.persistence.AppExecutors;
import com.example.android.popularmovie.data.persistence.dbhelper.FavoriteDbHelper;
import com.example.android.popularmovie.ui.adapter.ReviewAdapter;
import com.example.android.popularmovie.ui.adapter.TrailerAdapter;
import com.example.android.popularmovie.BuildConfig;
import com.example.android.popularmovie.R;
import com.example.android.popularmovie.data.api.APIClient;
import com.example.android.popularmovie.data.api.APIInterfaceService;
import com.example.android.popularmovie.data.model.Movie;
import com.example.android.popularmovie.data.model.Review;
import com.example.android.popularmovie.data.model.Reviews;
import com.example.android.popularmovie.data.model.Trailer;
import com.example.android.popularmovie.data.model.Trailers;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {


    //API variables
    final String API_SECRET_KEY = BuildConfig.THE_MOVIE_DB_API_SECRET_KEY;

    //Toast Messages Variables
    String EMPTY_APIKEY_MESSAGE = "API Key is empty, Please obtain API Key first";
    String ERROR_FETCHING_DATA_MESSAGE = "Error Fetching Data from api please try another time!";
    String FAV_MOVIE_ADDED_MESSAGE = "Movie added to Favorite Movie List";
    String FAV_MOVIE_DELETED_MESSAGE = "Movie deleted from Favorite Movie List";

    //DataBase
    FavoriteDbHelper favoriteDbHelper;
    AppDatabase mDataBase;

    //Views
    MultiSnapRecyclerView mTrailerRecyclerView;
    RecyclerView.LayoutManager mTrailerRecyclerViewLayoutManager;
    MultiSnapRecyclerView mReviewRecyclerView;
    RecyclerView.LayoutManager mReviewRecyclerViewLayoutManager;

    TextView mMovieOriginalTitleTextView;
    TextView mMovieUserRateTextView;
    TextView mMovieReleaseDateTextView;
    TextView mMoviePopularityTextView;
    TextView mMovieOverviewTextView;
    ImageView mMoviePosterImageView;

    // Movie Variables
    Movie mMovie;
    List<FavoriteMovieEntry> currentFavoriteMovie = new ArrayList<>();

    int mMovieID;
    String mMovieTitle;
    String mMovieOriginalTitle;
    String mMovieUserRating;
    String mMovieReleaseDate;
    String mMoviePopularity;
    String mMovieOverview;
    String mMoviePoster;
    String mMoviePosterBaseUrl = "https://image.tmdb.org/t/p/w500";
    String mMoviePosterFullUrl;

    TrailerAdapter mTrailerAdapter;
    List<Trailer> mTrailerList;
    ReviewAdapter mReviewAdapter;
    List<Review> mReviewList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mDataBase = AppDatabase.getInstance(getApplicationContext());
        favoriteDbHelper = new FavoriteDbHelper(this);

        setDetailedActivityLayout();

    }

    private void setDetailedActivityLayout() {

        initActivityLayout();

        Intent intentStartedActivity = getIntent();
        if (intentStartedActivity.hasExtra("currentmovie")) {

            mMovie = getIntent().getParcelableExtra("currentmovie");
            setMovieVariables(mMovie);
            displayMovieData();


            try {
                if (API_SECRET_KEY.isEmpty()) {

                    Toast.makeText(getApplicationContext(), EMPTY_APIKEY_MESSAGE, Toast.LENGTH_SHORT).show();
                    return;

                } else {

                    APIClient APIClient = new APIClient();
                    APIInterfaceService apiAPIInterfaceService = APIClient.getAPIClient().create(APIInterfaceService.class);
                    displayTrailers(apiAPIInterfaceService);
                    displayReviews(apiAPIInterfaceService);

                }

            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, ERROR_FETCHING_DATA_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private void initActivityLayout() {
        mMoviePosterImageView = (ImageView) findViewById(R.id.iv_detailed_movie_poster);
        mMovieOriginalTitleTextView = (TextView) findViewById(R.id.tv_detailed_movie_original_title);
        mMovieUserRateTextView = (TextView) findViewById(R.id.tv_detailed_user_rate);
        mMoviePopularityTextView = (TextView) findViewById(R.id.tv_detailed_movie_popularity);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_detailed_movie_releasedate);
        mMovieOverviewTextView = (TextView) findViewById(R.id.tv_detailed_movie_overview);

        mTrailerList = new ArrayList<>();
        mTrailerAdapter = new TrailerAdapter(this, mTrailerList);
        mTrailerRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.rv_detailed_movie_trailer);
        mTrailerRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mTrailerRecyclerView.setLayoutManager(mTrailerRecyclerViewLayoutManager);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mTrailerAdapter.notifyDataSetChanged();

        mReviewList = new ArrayList<>();
        mReviewAdapter = new ReviewAdapter(this, mReviewList);
        mReviewRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.rv_detailed_movie_review);
        mReviewRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerView.setLayoutManager(mReviewRecyclerViewLayoutManager);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        mReviewAdapter.notifyDataSetChanged();
    }

    private void setMovieVariables(Movie currentMovie) {

        this.mMovieID = currentMovie.getMovieId();
        this.mMovieTitle = currentMovie.getMovieTitle();
        this.mMoviePoster = currentMovie.getPosterPath();
        this.mMoviePosterFullUrl = mMoviePosterBaseUrl + mMoviePoster;
        this.mMovieOriginalTitle = currentMovie.getOriginalMovieTitle();
        this.mMovieUserRating = currentMovie.getVoteAverage() + "";
        this.mMovieReleaseDate = currentMovie.getReleaseDate();
        this.mMoviePopularity = currentMovie.getPopularity() + "";
        this.mMovieOverview = currentMovie.getOverview();


    }

    private void displayMovieData() {
        checkFavoriteMovie(mMovieID);
        this.getSupportActionBar().setTitle("Movie : " + mMovieTitle);
        Glide.with(this)
                .load(mMoviePosterFullUrl)
                .into(mMoviePosterImageView);
        mMovieOriginalTitleTextView.setText(mMovieOriginalTitle);
        mMovieUserRateTextView.setText(mMovieUserRating);
        mMoviePopularityTextView.setText(mMoviePopularity);
        mMovieReleaseDateTextView.setText(mMovieReleaseDate);
        mMovieOverviewTextView.setText(mMovieOverview);

    }

    private void displayTrailers(APIInterfaceService apiAPIInterfaceService) {
        Call<Trailers> call = apiAPIInterfaceService.getMovieTrailer(mMovieID, API_SECRET_KEY);
        call.enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        mTrailerList = response.body().getResults();
                        mTrailerAdapter.setmTrailerList(mTrailerList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                Toast.makeText(DetailActivity.this, ERROR_FETCHING_DATA_MESSAGE, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void displayReviews(APIInterfaceService apiAPIInterfaceService) {
        Call<Reviews> call = apiAPIInterfaceService.getReview(mMovieID, API_SECRET_KEY);

        call.enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null) {
                        mReviewList = response.body().getResults();
                        mReviewAdapter.setmReviewList(mReviewList);

                    }
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Toast.makeText(DetailActivity.this, ERROR_FETCHING_DATA_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    private void checkFavoriteMovie(final int movieId) {
        final MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.bv_favorite_movie);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                currentFavoriteMovie = mDataBase.favoriteMovieDao().getFavoriteMovieById(movieId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!currentFavoriteMovie.isEmpty()) {
                    materialFavoriteButton.setFavorite(true);
                }
                materialFavoriteButton.setOnFavoriteChangeListener(
                        new MaterialFavoriteButton.OnFavoriteChangeListener() {
                            @Override
                            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                if (favorite==true) {
                                    addFavoriteMovie(buttonView);

                                } else {
                                    deleteFavoriteMovie(mMovieID, buttonView);
                                }
                            }
                        });
            }

        }.execute();
    }

    public void addFavoriteMovie(final MaterialFavoriteButton favButtonView) {

        final FavoriteMovieEntry favoriteMovieEntry = new FavoriteMovieEntry(mMovieID, mMovieTitle, mMovieOriginalTitle,
                mMovieReleaseDate, mMovie.getPopularity(), mMovie.getVoteAverage(), mMoviePoster, mMovieOverview);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataBase.favoriteMovieDao().insertFavoriteMovie(favoriteMovieEntry);
                Snackbar.make(favButtonView, FAV_MOVIE_ADDED_MESSAGE, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteFavoriteMovie(final int movie_id, final MaterialFavoriteButton favButtonView) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDataBase.favoriteMovieDao().deleteFavoriteMovieWithId(movie_id);
                Snackbar.make(favButtonView, FAV_MOVIE_DELETED_MESSAGE, Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}