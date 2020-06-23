package com.example.moviecatalogue.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import java.util.List;

public class rv_nPlaying_upcoming extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;
    value_handle valueHandle = new value_handle();

    List<load_movie> load_movies;
    Context context;

    public rv_nPlaying_upcoming(Context context) {
        this.context = context;
        load_movies = new ArrayList<>();
    }

    public List<load_movie> getLoad_movies() {
        return load_movies;
    }

    public void setLoad_movies(List<load_movie> load_movies) {
        this.load_movies = load_movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.frame_list_movie, parent, false);
        viewHolder = new MovieVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        load_movie result = load_movies.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final MovieVH movieVH = (MovieVH) holder;

                movieVH.mMovieTitle.setText(result.getTitle());

                movieVH.btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        myIntent.putExtra(Intent.EXTRA_TEXT, "Title : "+result.getTitle()+"\n\n"
                        +"Overview : "+result.getOverview()+"\n\n"
                                +"Release Data : "+result.getReleaseDate()
                        );
                        context.startActivity(Intent.createChooser(myIntent,"Send To :"));
                    }
                });

                movieVH.btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bnd = new Bundle();
                        bnd.putString("id", String.valueOf(result.getId()));
                        bnd.putString("title", String.valueOf(result.getTitle()));
                        bnd.putString("overview", String.valueOf(result.getOverview()));
                        bnd.putString("popularity", String.valueOf(result.getPopularity()));
                        bnd.putString("release", String.valueOf(result.getReleaseDate()));
                        bnd.putString("image", result.getPosterPath());
                        Intent intent = new Intent(context, activity_detail.class);
                        intent.putExtras(bnd);
                        context.startActivity(intent);
                    }
                });
//                movieVH.mYear.setText(
//                        result.getReleaseDate().substring(0, 4)  // we want the year only
//                                + " | "
//                                + result.getOriginalLanguage().toUpperCase()
//                );
                movieVH.mMovieDesc.setText(result.getOverview());
                /**
                 * Using Glide to handle image loading.
                 * Learn more about Glide here:
                 *
                 */
                Glide
                        .with(context)
                        .load(valueHandle.BASE_URL_IMG + result.getPosterPath())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                movieVH.mProgress.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                // image ready, hide progress now
                                movieVH.mProgress.setVisibility(View.GONE);
                                return false;   // return false if you want Glide to handle everything else.
                            }
                        })
                        .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                        .centerCrop()
                        .into(movieVH.mPosterImg);

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return load_movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == load_movies.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(load_movie r) {
        load_movies.add(r);
        notifyItemInserted(load_movies.size() - 1);
    }

    public void addAll(List<load_movie> moveResults) {
        for (load_movie result : moveResults) {
            add(result);
        }
    }

    public void remove(load_movie r) {
        int position = load_movies.indexOf(r);
        if (position > -1) {
            load_movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new load_movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = load_movies.size() - 1;
        load_movie result = getItem(position);

        if (result != null) {
            load_movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public load_movie getItem(int position) {
        return load_movies.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class MovieVH extends RecyclerView.ViewHolder {
        private TextView mMovieTitle;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;
        private Button btnShare;
        private Button btnDetail;

        public MovieVH(View itemView) {
            super(itemView);

            mMovieTitle = itemView.findViewById(R.id.tv_title);
            mMovieDesc =  itemView.findViewById(R.id.tv_overview);
            mYear =  itemView.findViewById(R.id.tv_date);
            mPosterImg =  itemView.findViewById(R.id.iv_movie);
            mProgress =  itemView.findViewById(R.id.movie_progress);
            btnShare = itemView.findViewById(R.id.btn_share);
            btnDetail = itemView.findViewById(R.id.btn_detail);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
