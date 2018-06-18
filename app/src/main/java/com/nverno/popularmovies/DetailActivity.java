package com.nverno.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.viewmodel.MoviesViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_poster_detail)
    ImageView moviePosterDetail;
    @BindView(R.id.movie_title_detail)
    TextView movieTitleDetail;
    @BindView(R.id.movie_rating_detail)
    TextView movieRatingDetail;
    @BindView(R.id.movie_release_detail)
    TextView movieReleaseDetail;
    @BindView(R.id.move_description_detail)
    TextView movieDescriptionDetail;

    MoviesViewModel moviesViewModel;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Context mContext = this;

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            // The extra data is the Movie object implementing Parcelable.
            if (intentThatStartedThisActivity.hasExtra("MOVIE")) {

                // Put the parcelable into a bundle.
                Bundle bundle = intentThatStartedThisActivity.getExtras();

                // Turn that bundle back into the Movie object.
                final int movieId = bundle.getInt("MOVIE");

                moviesViewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {

                        if (movies != null) {

                            for (Movie letMovie : movies) {
                                if (letMovie.getId() == movieId) {
                                    movie = letMovie;
                                }
                            }
                        }

//                        mLoadingSpinner.setVisibility(View.INVISIBLE);
                        Picasso.with(mContext).load(movie.getPosterImage()).into(moviePosterDetail);

                        movieTitleDetail.setText(movie.getTitle());
                        movieRatingDetail.setText(Double.toString(movie.getVoteAverage()));
                        movieReleaseDetail.setText(movie.getReleaseDate());
                        movieDescriptionDetail.setText(movie.getOverview());
                    }
                });
            }
        }
    }
}