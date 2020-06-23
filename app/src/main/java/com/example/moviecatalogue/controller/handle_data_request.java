package com.example.moviecatalogue.controller;

import android.content.Context;
import android.net.DnsResolver;

import com.example.moviecatalogue.model.dao.load_movie;
import com.example.moviecatalogue.model.dao.movie_response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class handle_data_request {

    rv_nPlaying_upcoming recycleAdapter;

    int currentPage, TOTAL_PAGES;
    Context context;

    public handle_data_request(int currentPage, int TOTAL_PAGES, Context context) {
        this.currentPage = currentPage;
        this.TOTAL_PAGES = TOTAL_PAGES;
        this.context = context;
    }

    public Callback<movie_response> callbackFirstPage = new Callback<movie_response>() {
        @Override
        public void onResponse(Call<movie_response> call, Response<movie_response> response) {
            recycleAdapter = new rv_nPlaying_upcoming(context);
            List<load_movie> results = fetchResults(response);
            recycleAdapter.addAll(results);

            if (currentPage <= TOTAL_PAGES) recycleAdapter.addLoadingFooter();
//            else isLastPage = true;
        }

        @Override
        public void onFailure(Call<movie_response> call, Throwable t) {

        }
    };

    private List<load_movie> fetchResults(Response<movie_response> response) {
        movie_response topRatedMovies = response.body();
        return topRatedMovies.getMovies();
    }

    public Callback<movie_response> callbackNextPage = new Callback<movie_response>() {
        @Override
        public void onResponse(Call<movie_response> call, Response<movie_response> response) {
            List<load_movie> results = fetchResults(response);
        }

        @Override
        public void onFailure(Call<movie_response> call, Throwable t) {

        }
    };

//    private void loadNextPage() {
//
//        callNowPlayingMoviesApi().enqueue(new Callback<movie_response>() {
//            @Override
//            public void onResponse(Call<movie_response> call, Response<movie_response> response) {
//                recycleAdapter.removeLoadingFooter();
//                isLoading = false;
//
//                List<load_movie> results = fetchResults(response);
//                recycleAdapter.addAll(results);
//
//                if (currentPage != TOTAL_PAGES) recycleAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<movie_response> call, Throwable t) {
//                t.printStackTrace();
//                // TODO: 08/11/16 handle failure
//            }
//        });
//    }

}
