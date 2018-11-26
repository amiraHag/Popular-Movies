package com.example.android.popularmovie.data.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Reviews implements Parcelable {


    public final static Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {

        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        public Reviews[] newArray(int size) {
            return (new Reviews[size]);
        }

    };

    @SerializedName("id")
    private Integer id;
    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<Review> results;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;


    private Reviews(Parcel in) {
        this.id = in.readInt();
        this.page = in.readInt();
        in.readList(this.results, Review.class.getClassLoader());
        this.totalPages = in.readInt();
        this.totalResults = in.readInt();
    }


    public Reviews() {
    }


    public Reviews(Integer id, Integer page, List<Review> results, Integer totalPages, Integer totalResults) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(page);
        parcel.writeList(results);
        parcel.writeInt(totalPages);
        parcel.writeInt(totalResults);
    }

    public int describeContents() {
        return 0;
    }

}