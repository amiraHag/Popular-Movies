package com.example.android.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Trailers implements Parcelable {
    public static final Parcelable.Creator<Trailers> CREATOR = new Parcelable.Creator<Trailers>() {


        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        public Trailers[] newArray(int size) {
            return (new Trailers[size]);
        }

    };

    @SerializedName("id")
    private int id_trailer;

    @SerializedName("results")
    private List<Trailer> results;

    public Trailers(int id_trailer, List<Trailer> results) {
        this.id_trailer = id_trailer;
        this.results = results;
    }

    public Trailers() {
    }

    public int getId_trailer() {
        return id_trailer;
    }

    public void setId_trailer(int id_trailer) {
        this.id_trailer = id_trailer;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_trailer);
        dest.writeList(this.results);

    }

    private Trailers(Parcel in) {
        this.id_trailer = in.readInt();
        this.results = new ArrayList<Trailer>();
        in.readList(this.results, Trailer.class.getClassLoader());

    }
}
