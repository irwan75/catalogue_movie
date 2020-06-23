package com.example.moviecatalogue.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.controller.utils.value_handle;
import com.example.moviecatalogue.model.dao.load_movie;
import com.example.moviecatalogue.view.activity_detail;

import java.util.ArrayList;

public class rv_favorite extends RecyclerView.Adapter<rv_favorite.ViewHolder> {

    ArrayList<load_movie> arrayList;
    ArrayList<String> overview;
    value_handle valueHandle = new value_handle();
    Context context;

    public rv_favorite(ArrayList<load_movie> arrayList, ArrayList<String> overview, Context context) {
        this.arrayList = arrayList;
        this.overview = overview;
        this.context = context;
    }

    @Override
    public rv_favorite.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_list_movie, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(rv_favorite.ViewHolder holder, int position) {
        holder.tvTitle.setText(arrayList.get(position).getTitle());
        holder.tvRelease.setText(arrayList.get(position).getReleaseDate());
        holder.tvOverview.setText(overview.get(position));
        Glide
                .with(context)
                .load(valueHandle.BASE_URL_IMG + arrayList.get(position).getPosterPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
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
                .into(holder.img_movie);

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.putExtra(Intent.EXTRA_TEXT, "Title : "+arrayList.get(position).getTitle()+"\n\n"
                        +"Overview : "+overview.get(position)+"\n\n"
                        +"Release Data : "+arrayList.get(position).getReleaseDate()
                );
                context.startActivity(Intent.createChooser(myIntent,"Send To :"));
            }
        });

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bnd = new Bundle();
                bnd.putString("id", String.valueOf(arrayList.get(position).getId()));
                bnd.putString("title", String.valueOf(arrayList.get(position).getTitle()));
                bnd.putString("overview", String.valueOf(overview.get(position)));
                bnd.putString("popularity", String.valueOf(arrayList.get(position).getPopularity()));
                bnd.putString("release", String.valueOf(arrayList.get(position).getReleaseDate()));
                bnd.putString("image", arrayList.get(position).getPosterPath());
                Intent intent = new Intent(context, activity_detail.class);
                intent.putExtras(bnd);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_movie;
        TextView tvTitle, tvOverview, tvRelease;
        Button btnDetail, btnShare;
        ProgressBar progressBar;

        public ViewHolder( View itemView) {
            super(itemView);

            img_movie = itemView.findViewById(R.id.iv_movie);
            progressBar = itemView.findViewById(R.id.movie_progress);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvRelease = itemView.findViewById(R.id.tv_date);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);

        }
    }
}
