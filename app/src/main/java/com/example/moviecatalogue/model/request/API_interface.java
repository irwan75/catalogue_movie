package com.example.moviecatalogue.model.request;

import com.example.moviecatalogue.model.dao.load_cast;
import com.example.moviecatalogue.model.dao.load_movie;
import com.example.moviecatalogue.model.dao.movie_response;
import com.example.moviecatalogue.view.activity_detail;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API_interface {

    @GET("movie/now_playing")
    Call<movie_response> getMoviesNowPlaying(
            @Query("api_key") String apiKey,
            @Query("language") String languange,
            @Query("page") int pageIndex
    );

    @GET("movie/upcoming")
    Call<movie_response> getMoviesUpcoming(
            @Query("api_key") String apiKey,
            @Query("language") String languange,
            @Query("page") int pageIndex
    );

    @GET("search/movie")
    Call<movie_response> getMoviesSearch(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("include_adult") Boolean adult,
            @Query("language") String languange,
            @Query("page") int pageIndex
    );

}
