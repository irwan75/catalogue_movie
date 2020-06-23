package com.example.moviecatalogue.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.controller.rv_nPlaying_upcoming;
import com.example.moviecatalogue.controller.utils.value_handle;
import com.example.moviecatalogue.controller.utils.pagination_scroll_listener;
import com.example.moviecatalogue.model.dao.load_movie;
import com.example.moviecatalogue.model.dao.movie_response;
import com.example.moviecatalogue.model.request.API_interface;
import com.example.moviecatalogue.model.request.API_request;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_search extends Fragment {

    RecyclerView mRecyclerView;
    rv_nPlaying_upcoming recycleAdapter;
    MainActivity mn;
    value_handle chooseLanguange;

    EditText et_keyword;
    Button btnSearch;

    ProgressDialog progressDialog;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage;

    private API_interface api_interface;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentPage = PAGE_START;
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSearch = view.findViewById(R.id.btnSearch);
        et_keyword = view.findViewById(R.id.et_keyword);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Harap Tunggu");

        mn = new MainActivity();
        chooseLanguange = new value_handle();

        recycleAdapter = new rv_nPlaying_upcoming(getContext());
        mRecyclerView = view.findViewById(R.id.list_movie_search);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(recycleAdapter);

        mRecyclerView.addOnScrollListener(new pagination_scroll_listener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage +=1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                },1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        btnSearch.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSearch :
                    progressDialog.show();
                    Handler hnd = new Handler();
                    hnd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            api_interface = API_request.getClient().create(API_interface.class);
                            loadFirstPage();
                            progressDialog.dismiss();
                        }
                    }, 3000);
                    break;
            }
        }
    };

    private void loadFirstPage() {

        callSearchMoviesApi().enqueue(new Callback<movie_response>() {
            @Override
            public void onResponse(Call<movie_response> call, Response<movie_response> response) {

                List<load_movie> results = fetchResults(response);
                recycleAdapter.addAll(results);

                if (currentPage <= TOTAL_PAGES) recycleAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<movie_response> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), ""+t, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private List<load_movie> fetchResults(Response<movie_response> response) {
        movie_response topRatedMovies = response.body();
        return topRatedMovies.getMovies();
    }

    private void loadNextPage() {
        callSearchMoviesApi().enqueue(new Callback<movie_response>() {
            @Override
            public void onResponse(Call<movie_response> call, Response<movie_response> response) {
                recycleAdapter.removeLoadingFooter();
                isLoading = false;

                List<load_movie> results = fetchResults(response);
                recycleAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) recycleAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<movie_response> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });
    }

    private Call<movie_response> callSearchMoviesApi() {
        return api_interface.getMoviesSearch(
                mn.API_KEY, et_keyword.getText().toString().trim(), true, chooseLanguange.LANGUANGE,
                currentPage
        );
    }

}
