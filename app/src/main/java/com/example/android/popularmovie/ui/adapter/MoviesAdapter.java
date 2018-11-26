package com.example.android.popularmovie.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.popularmovie.ui.activity.DetailActivity;
import com.example.android.popularmovie.R;
import com.example.android.popularmovie.data.model.Movie;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;


    public MoviesAdapter(Context mContext, List<Movie> mMovieList) {
        this.mContext = mContext;
        this.mMovieList = mMovieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layouInflator = LayoutInflater.from(viewGroup.getContext());
        View view = layouInflator.inflate(R.layout.item_movie, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder viewHolder, int i) {
        Movie currentMovie = mMovieList.get(i);
        viewHolder.bind(currentMovie, i);
    }


    public void setmMovieList(List<Movie> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    public List<Movie> getmMovieList() {
        return this.mMovieList;
    }

    @Override
    public int getItemCount() {
        if (mMovieList==null) {
            return 0;
        }
        return mMovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mUserRateingTextView;
        public ImageView mPosterImageView;

        public MyViewHolder(View view) {
            super(view);
            mPosterImageView = (ImageView) view.findViewById(R.id.iv_detailed_movie_poster);
            mTitleTextView = (TextView) view.findViewById(R.id.tv_movie_title);
            mUserRateingTextView = (TextView) view.findViewById(R.id.tv_user_rate);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = getAdapterPosition();
                    int emptyPosiion = RecyclerView.NO_POSITION;

                    if (currentPosition!=emptyPosiion) {
                        Movie currentMovie = mMovieList.get(currentPosition);
                        String movieName = currentMovie.getOriginalMovieTitle();
                        startActivity(currentMovie);
                        Toast.makeText(v.getContext(), "You clicked " + movieName, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void startActivity(Movie movie) {

            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("currentmovie", movie);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

        public void bind(Movie movie, int i) {
            String mMovieTitle = movie.getOriginalMovieTitle();
            String mUserRate = Double.toString(movie.getVoteAverage());
            String BaseUrl = "https://image.tmdb.org/t/p/w500";
            String mPosterURL = BaseUrl + movie.getPosterPath();

            mTitleTextView.setText(mMovieTitle);
            mUserRateingTextView.setText(mUserRate);
            Glide.with(mContext)
                    .load(mPosterURL)
                    .into(mPosterImageView);
        }
    }
}
