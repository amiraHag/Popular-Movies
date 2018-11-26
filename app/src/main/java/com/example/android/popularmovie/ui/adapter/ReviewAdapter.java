package com.example.android.popularmovie.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovie.R;
import com.example.android.popularmovie.data.model.Review;

import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Review> mReviewList;

    public ReviewAdapter(Context mContext, List<Review> mReviewList) {
        this.mContext = mContext;
        this.mReviewList = mReviewList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layouInflator = LayoutInflater.from(viewGroup.getContext());
        View view = layouInflator.inflate(R.layout.item_review, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        Review currentReview = mReviewList.get(i);
        viewHolder.bind(currentReview, i);
    }

    public List<Review> getmReviewList() {
        return mReviewList;
    }

    public void setmReviewList(List<Review> mReviewList) {
        this.mReviewList = mReviewList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mReviewList==null) {
            return 0;
        }
        return mReviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mNumberTextView;
        public TextView mAuthorTextView;
        public TextView mContentTextView;
        public TextView mURLTextView;

        public MyViewHolder(View view) {
            super(view);
            mNumberTextView = (TextView) view.findViewById(R.id.tv_review_no);
            mAuthorTextView = (TextView) view.findViewById(R.id.tv_review_author);
            mContentTextView = (TextView) view.findViewById(R.id.tv_review_content);
            mURLTextView = (TextView) view.findViewById(R.id.tv_review_link);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Toast.makeText(mContext, "You Clicked on the Review No " + (position + 1), Toast.LENGTH_SHORT).show();
                }

            });

        }

        public void bind(Review review, int i) {
            String mNoLabel = mContext.getString(R.string.review_no_lb);
            String content = review.getContent();
            int maxLength = (content.length() < 35) ? content.length() : 35;
            content = content.substring(0, maxLength) + "........";
            int currentIndex = i + 1;

            mNumberTextView.setText(mNoLabel + currentIndex);
            mAuthorTextView.setText(review.getAuthor());
            mContentTextView.setText(content);
            mURLTextView.setText(review.getUrl());
            Linkify.addLinks(mURLTextView, Linkify.WEB_URLS);

        }
    }

}
