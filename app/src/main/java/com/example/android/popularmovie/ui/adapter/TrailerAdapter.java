package com.example.android.popularmovie.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovie.R;
import com.example.android.popularmovie.data.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Trailer> mTrailerList;

    public TrailerAdapter(Context mContext, List<Trailer> mTrailerList) {
        this.mContext = mContext;
        this.mTrailerList = mTrailerList;

    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layouInflator = LayoutInflater.from(viewGroup.getContext());
        View view = layouInflator.inflate(R.layout.item_trailer, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.MyViewHolder viewHolder, int i) {
        Trailer currentTrailer = mTrailerList.get(i);
        viewHolder.bind(currentTrailer, i);
    }

    public List<Trailer> getmTrailerList() {
        return mTrailerList;
    }

    public void setmTrailerList(List<Trailer> mTrailerList) {
        this.mTrailerList = mTrailerList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTrailerList==null) {
            return 0;
        }
        return mTrailerList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mNumberTextView;
        public TextView mNameTextView;

        public MyViewHolder(View view) {
            super(view);
            mNumberTextView = (TextView) view.findViewById(R.id.tv_trailer_no);
            mNameTextView = (TextView) view.findViewById(R.id.tv_trailer_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int currentPosition = getAdapterPosition();
                    int emptyPosiion = RecyclerView.NO_POSITION;

                    if (currentPosition!=emptyPosiion) {

                        Trailer currentTrailer = mTrailerList.get(currentPosition);
                        String videoId = currentTrailer.getKey();
                        String videoBaseUrl = "https://www.youtube.com/watch?v=";
                        String videoFullUrl = videoBaseUrl + videoId;
                        Uri uri = Uri.parse(videoFullUrl);
                        showWebPage(uri);
                        Toast.makeText(v.getContext(), "You will watch Trailer No " + (currentPosition + 1), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        public void bind(Trailer trailer, int i) {
            String mNoLabel = mContext.getString(R.string.trailer_no_lb);
            String name = trailer.getName();
            int currentIndex = i + 1;

            mNumberTextView.setText(mNoLabel + currentIndex);
            mNameTextView.setText(name);
        }

        public void showWebPage(Uri uri) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);

        }

    }
}
