package com.example.moviecatalogue.model.request;

import androidx.annotation.NonNull;

import com.example.moviecatalogue.model.dao.movie_response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_request {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

//    private static API_request apiClient;
//    private static Retrofit retrofit;

//    private API_request(int movie_id){
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.themoviedb.org/3/movie/"+movie_id+"/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }

//    public static synchronized API_request getInstance(int movie_id){
//        if(apiClient == null){
//            apiClient = new API_request(movie_id);
//        }
//        return apiClient;
//    }

//    public API_interface getApi(){
//        return retrofit.create(API_interface.class);
//    }

}
