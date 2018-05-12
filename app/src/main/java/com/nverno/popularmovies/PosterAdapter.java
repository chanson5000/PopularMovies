package com.nverno.popularmovies;

import android.content.Context;
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

    private final PosterAdapterOnClickHandler mClickHandler;

    public interface PosterAdapterOnClickHandler {
        void onClick(Movie movieForDay);
    }

    public PosterAdapter(PosterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    class PosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView posterView;

        PosterAdapterViewHolder(View view) {
            super(view);
            posterView = view.findViewById(R.id.movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movieForDay = movies.get(adapterPosition);
            mClickHandler.onClick(movieForDay);
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
