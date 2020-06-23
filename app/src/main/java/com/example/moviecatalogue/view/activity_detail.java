package com.example.moviecatalogue.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import java.util.List;

public class activity_detail extends AppCompatActivity {

    ImageView img_movie, img_favourite;
    TextView tvTitle, tvRelease, tvOverview, tvPopularity;
    RecyclerView rv_cast_scroll;
    Toolbar mToolbar;

    List<load_cast> loadCasts = new ArrayList<>();

    MainActivity mn;
    rv_cast rv_cast_adapter;
    load_movie loadMovie;
    value_handle valueHandle;

    sqlite_helper helper;
    SQLiteDatabase db;
    crud_tb crudTb;
    Boolean condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Detail Catalogue Movie");

        tvTitle = findViewById(R.id.title_movie);
        tvRelease = findViewById(R.id.release_movie);
        tvOverview = findViewById(R.id.overview_movie);
        tvPopularity = findViewById(R.id.popularity_movie);
        img_movie = findViewById(R.id.img_movie);
        rv_cast_scroll = findViewById(R.id.rv_cast_detail);
        img_favourite = findViewById(R.id.ic_favourite);

        loadMovie = new load_movie();
        valueHandle = new value_handle();

        Bundle bundle = getIntent().getExtras();
        loadMovie.setId(Integer.parseInt(bundle.getString("id")));
        loadMovie.setTitle(bundle.getString("title"));
        loadMovie.setOverview(bundle.getString("overview"));
        loadMovie.setPopularity(Double.parseDouble(bundle.getString("popularity")));
        loadMovie.setReleaseDate(bundle.getString("release"));
        loadMovie.setPosterPath(bundle.getString("image"));

        mn = new MainActivity();
        helper = new sqlite_helper(getApplicationContext());
        db = helper.getReadableDatabase();
        crudTb = new crud_tb(db);

        if (crudTb.selectOne(loadMovie.getId()) == loadMovie.getId()) {
            img_favourite.setImageResource(R.drawable.ic_star_black_24dp);
            condition = false;
        } else {
            img_favourite.setImageResource(R.drawable.ic_star_border_black_24dp);
            condition = true;
        }

        setValueFirst();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_cast_scroll.setLayoutManager(layoutManager);
        rv_cast_scroll.setHasFixedSize(true);

        AndroidNetworking.initialize(getApplicationContext());
        getData(loadMovie.getId());

        img_favourite.setOnClickListener(clickListener);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setValueFirst() {
        tvTitle.setText(loadMovie.getTitle());
        tvRelease.setText("Release : "+loadMovie.getReleaseDate());
        tvOverview.setText(loadMovie.getOverview());
        tvPopularity.setText("" + loadMovie.getPopularity());
        Glide
                .with(this)
                .load(valueHandle.BASE_URL_IMG + loadMovie.getPosterPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_movie);
    }

    public void getData(int movie_id) {
        String url = "https://api.themoviedb.org/3/movie/" + movie_id + "/credits?api_key=" + mn.API_KEY;
        AndroidNetworking.get(url)
//                .addQueryParameter("cast")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cast");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject data = jsonArray.getJSONObject(i);
//                                    //adding the product to product list
                                loadCasts.add(new load_cast(
                                        data.getString("cast_id"),
                                        data.getString("name"),
                                        data.getString("profile_path")
                                ));
                            }
                            rv_cast_adapter = new rv_cast(loadCasts, activity_detail.this);
                            rv_cast_scroll.setAdapter(rv_cast_adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                    }
                });

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ic_favourite:
                    if (!condition) {
                        img_favourite.setImageResource(R.drawable.ic_star_border_black_24dp);
                        crudTb.deleteData(loadMovie.getId());
                        condition = true;
                        Toast.makeText(activity_detail.this, "Movie Delete from Favourite", Toast.LENGTH_SHORT).show();
                    } else if (condition) {
                        img_favourite.setImageResource(R.drawable.ic_star_black_24dp);
                        Toast.makeText(activity_detail.this, "Movie Add to Favourite", Toast.LENGTH_SHORT).show();
                        condition = false;
                        crudTb.insertData(loadMovie);
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.share:
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_TEXT, "Title : " + loadMovie.getTitle() + "\n\n"
                        + "Overview : " + loadMovie.getOverview() + "\n\n"
                        + "Release Data : " + loadMovie.getReleaseDate()
                );
                startActivity(Intent.createChooser(myIntent, "Send To :"));
                break;
        }
        return true;
    }
}
