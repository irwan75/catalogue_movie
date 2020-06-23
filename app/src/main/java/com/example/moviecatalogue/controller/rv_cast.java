package com.example.moviecatalogue.controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.dao.load_cast;
import com.example.moviecatalogue.model.dao.load_movie;

import java.util.List;

public class rv_cast extends RecyclerView.Adapter<rv_cast.ViewHolder> {

    List<load_cast> loadCasts;
    Context context;
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w185";

    public rv_cast(List<load_cast> loadCasts, Context context) {
        this.loadCasts = loadCasts;
        this.context = context;
    }

    @Override
    public rv_cast.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_cast, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(rv_cast.ViewHolder holder, int position) {

//        Log.i("Image",""+loadCasts.get(position).getProfile_path());
        holder.tv_nama_cast.setText(loadCasts.get(position).getName());
        Glide
                .with(context)
                .load(BASE_URL_IMG+loadCasts.get(position).getProfile_path())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // image ready, hide progress now
                        holder.progressBar.setVisibility(View.GONE);
                        return false;   // return false if you want Glide to handle everything else.
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .into(holder.img_cast);
    }

    @Override
    public int getItemCount() {
        int nilai;
        if (loadCasts.size()>10){
            nilai=10;
        }else {
            nilai = loadCasts.size();
        }
        return nilai;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nama_cast;
        ImageView img_cast;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progress_circular);
            img_cast = itemView.findViewById(R.id.img_cast);
            tv_nama_cast = itemView.findViewById(R.id.name_cast);
        }
    }
}
