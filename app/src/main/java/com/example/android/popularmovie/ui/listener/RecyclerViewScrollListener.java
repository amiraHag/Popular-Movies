package com.example.android.popularmovie.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLayoutManager;
    private int totalCount;
    private int lastVisibleItemIndex;
    private int mPreviousItemsCount;
    private int page;
    private boolean loadProgress;

    public RecyclerViewScrollListener(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        this.totalCount = mLayoutManager.getItemCount();
        this.lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
        this.mPreviousItemsCount = 12;
        this.page = 1;
        this.loadProgress = true;

    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        if (getLoadProgress()) {
            if (totalCount > mPreviousItemsCount) {
                setLoadProgress(false);
                mPreviousItemsCount = totalCount;
            }

        } else {
            int lastVisibleItem = lastVisibleItemIndex + 1;
            if (lastVisibleItem >= totalCount) {
                setLoadProgress(true);
                setPagesCount(page++);
            }

        }

    }

    public void setPagesCount(int page) {
        this.page = page;

    }

    ;

    public int getPageCount() {
        return page;
    }

    ;

    public void setLoadProgress(boolean loadProgress) {
        this.loadProgress = loadProgress;
    }

    public boolean getLoadProgress() {
        return loadProgress;
    }

    ;

}
