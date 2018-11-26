package com.example.android.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Casts implements Parcelable {

    public static final Parcelable.Creator<Casts> CREATOR = new Parcelable.Creator<Casts>() {


        public Casts createFromParcel(Parcel in) {
            return new Casts(in);
        }

        public Casts[] newArray(int size) {
            return (new Casts[size]);
        }

    };

    @SerializedName("id")
    private int id;

    @SerializedName("casts")
    private List<Cast> casts;


    public Casts() {
    }

    public Casts(int id, List<Cast> casts) {
        this.id = id;
        this.casts = casts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeList(this.casts);

    }

    private Casts(Parcel in) {
        this.id = in.readInt();
        this.casts = new ArrayList<Cast>();
        in.readList(this.casts, Cast.class.getClassLoader());

    }


}
