package com.nverno.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nverno.popularmovies.adapter.TrailerAdapter;
import com.nverno.popularmovies.model.Trailer;
import com.nverno.popularmovies.viewmodel.TrailersViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerAdapterOnClickHandler {

    private TrailerAdapter mTrailerAdapter;

    @BindView(R.id.recycler_trailers)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_trailers)
    TextView mNoTrailers;
    @BindView(R.id.trailer_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.trailer_header)
    TextView mTrailerHeader;

    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_TITLE = "MOVIE_NAME_EXTRA";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mTrailerAdapter = new TrailerAdapter(this, this);

        mRecyclerView.setAdapter(mTrailerAdapter);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null
                && intentThatStartedThisActivity.hasExtra(MOVIE_ID)
                && intentThatStartedThisActivity.hasExtra(MOVIE_TITLE)) {

            Bundle bundle = intentThatStartedThisActivity.getExtras();
            mMovieTitle.setText(bundle.getString(MOVIE_TITLE));
            setTrailersView(bundle.getInt(MOVIE_ID));
        }
    }

    private void setTrailersView(final int movieId) {
        TrailersViewModel trailersViewModel = ViewModelProviders.of(this)
                .get(TrailersViewModel.class);

        trailersViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {

                List<Trailer> adapterTrailers = new ArrayList<>();

                for (Trailer trailer : trailers) {
                    if (trailer.getMovieId() == movieId) {
                        adapterTrailers.add(trailer);
                    }
                }

                if (adapterTrailers.isEmpty()) {
                    mTrailerHeader.setVisibility(View.INVISIBLE);
                    mNoTrailers.setVisibility(View.VISIBLE);

                    return;
                }

                mTrailerAdapter.setTrailersData(adapterTrailers);
            }
        });
    }

    @Override
    public void onClick(Trailer trailer) {

        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getTrailer()));

        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
