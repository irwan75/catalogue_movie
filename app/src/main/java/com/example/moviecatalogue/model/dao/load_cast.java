package com.example.moviecatalogue.model.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class load_cast{

    @SerializedName("cast_id")
    private String cast_id;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profile_path;

    public load_cast(String cast_id, String name, String profile_path) {
        this.cast_id = cast_id;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCast_id() {
        return cast_id;
    }

    public void setCast_id(String cast_id) {
        this.cast_id = cast_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
