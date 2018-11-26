package com.example.android.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable {

    public static final Parcelable.Creator<Cast> CREATOR = new Parcelable.Creator<Cast>() {


        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        public Cast[] newArray(int size) {
            return (new Cast[size]);
        }

    };

    @SerializedName("key")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("character")
    private String character;

    public Cast(int id, String name, String character) {
        this.id = id;
        this.name = name;
        this.character = character;
    }

    public Cast() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(character);

    }

    private Cast(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.character = in.readString();

    }

}
