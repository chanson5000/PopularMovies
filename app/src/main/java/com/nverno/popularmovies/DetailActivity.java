package com.nverno.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.database.Cursor;

// You must add "dataBinding.enabled = true" to your build file for this to import.
import android.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nverno.popularmovies.databinding.ActivityDetailBinding;
import com.nverno.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    // The ID responsible for loading the poster details for each poster.
    private static final int ID_DETAIL_LOADER = 427;

    private Movie movie;

    private ImageView moviePosterDetail;
    private TextView movieTitleDetail;
    private TextView movieRatingDetail;
    private TextView movieReleaseDetail;
    private TextView movieDescriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePosterDetail = findViewById(R.id.movie_poster_detail);
        movieTitleDetail = findViewById(R.id.movie_title_detail);
        movieRatingDetail = findViewById(R.id.movie_rating_detail);
        movieReleaseDetail = findViewById(R.id.movie_release_detail);
        movieDescriptionDetail = findViewById(R.id.move_description_detail);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            if (intentThatStartedThisActivity.hasExtra("MOVIE")) {

                Bundle bundle = intentThatStartedThisActivity.getExtras();

                movie = bundle.getParcelable("MOVIE");

//                movie = intentThatStartedThisActivity.getExtras().("MOVIE");

                Picasso.with(this).load(movie.getPosterImage()).into(moviePosterDetail);

                movieTitleDetail.setText(movie.getTitle());
                movieRatingDetail.setText(Double.toString(movie.getVoteAverage()));
                movieReleaseDetail.setText(movie.getReleaseDate());
                movieDescriptionDetail.setText(movie.getOverview());

        }
        }

    }


}