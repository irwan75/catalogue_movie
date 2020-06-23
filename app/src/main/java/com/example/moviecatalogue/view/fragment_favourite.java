package com.example.moviecatalogue.view;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.controller.rv_cast;
import com.example.moviecatalogue.controller.rv_favorite;
import com.example.moviecatalogue.controller.utils.sqlite_helper;
import com.example.moviecatalogue.controller.utils.value_handle;
import com.example.moviecatalogue.model.dao.load_cast;
import com.example.moviecatalogue.model.dao.load_movie;
import com.example.moviecatalogue.model.request.crud_tb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_favourite extends Fragment {

    RecyclerView recyclerView;
    rv_favorite rv_favorite_adapter;
    MainActivity mainActivity;
    ProgressBar progressBar;

    sqlite_helper helper;
    SQLiteDatabase db;
    crud_tb crudTb;
    value_handle valueHandle;

    ArrayList<load_movie> loadMovies = new ArrayList<>();
    ArrayList<String> overview = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = new MainActivity();
        valueHandle = new value_handle();

        progressBar = view.findViewById(R.id.progressBar_circular);
        helper = new sqlite_helper(getContext());
        db = helper.getReadableDatabase();
        crudTb = new crud_tb(db);

        recyclerView = view.findViewById(R.id.rv_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        loadData();

    }

    public void loadData(){
        loadMovies = crudTb.select();

        AndroidNetworking.initialize(getContext());
        for (int i = 0; i<loadMovies.size();i++){
            getDataOverview(loadMovies.get(i).getId());
        }

        Handler hnd = new Handler();
        hnd.postDelayed(new Runnable() {
            @Override
            public void run() {
                rv_favorite_adapter = new rv_favorite(loadMovies, overview, getContext());
                recyclerView.setAdapter(rv_favorite_adapter);
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);
    }

    public void getDataOverview(int movie_id) {
        String url = "https://api.themoviedb.org/3/movie/"+movie_id+"?api_key="+mainActivity.API_KEY+"&language="+valueHandle.LANGUANGE;
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            overview.add(response.getString("overview"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}
