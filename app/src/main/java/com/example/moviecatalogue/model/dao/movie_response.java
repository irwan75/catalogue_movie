package com.example.moviecatalogue.model.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class movie_response implements Parcelable {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<load_movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<load_movie> getResults() {
        return results;
    }

    public List<load_movie> getMovies() {
        return results;
    }

    public void setResults(List<load_movie> results) {
        this.results = results;
    }

    public void setMovies(List<load_movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
    }

    public movie_response() {
    }

    protected movie_response(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(load_movie.CREATOR);
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Creator<movie_response> CREATOR = new Creator<movie_response>() {
        @Override
        public movie_response createFromParcel(Parcel source) {
            return new movie_response(source);
        }

        @Override
        public movie_response[] newArray(int size) {
            return new movie_response[size];
        }
    };

}
