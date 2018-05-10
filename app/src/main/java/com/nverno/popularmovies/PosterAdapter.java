package com.nverno.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nverno.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {

    private Context mContext;
    private List<Movie> movies;

    public PosterAdapter(@NonNull Context context) {
        mContext = context;
    }

    class PosterAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView posterView;

        PosterAdapterViewHolder(View view) {
            super(view);

            posterView = view.findViewById(R.id.movie_poster);
        }
    }

    @Override
    public PosterAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutId = R.layout.poster_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, viewGroup, false);

        view.setFocusable(true);

        return new PosterAdapterViewHolder(view);
    }

    @Override
        public void onBindViewHolder(PosterAdapterViewHolder posterAdapterViewHolder, int position) {

        Picasso.with(mContext).load(movies.get(position).getPosterImage()).into(posterAdapterViewHolder.posterView);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.size();
    }


    public void setPosterData(List<Movie> data) {
        movies = data;
        notifyDataSetChanged();
    }
}
