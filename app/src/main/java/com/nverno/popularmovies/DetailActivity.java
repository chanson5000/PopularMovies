package com.nverno.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.viewmodel.MoviesViewModel;
import com.nverno.popularmovies.viewmodel.PopularMoviesViewModel;
import com.nverno.popularmovies.viewmodel.TopRatedMoviesViewModel;
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

    PopularMoviesViewModel popularMoviesViewModel;
    TopRatedMoviesViewModel topRatedMoviesViewModel;

    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_SORT_TYPE = "MOVIE_SORT_TYPE";

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Context mContext = this;

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null
                && intentThatStartedThisActivity.hasExtra(MOVIE_ID)
                && intentThatStartedThisActivity.hasExtra(MOVIE_SORT_TYPE)) {
            // The extra data is the Movie object implementing Parcelable.
            Bundle bundle = intentThatStartedThisActivity.getExtras();

            final int movieId = bundle.getInt(MOVIE_ID);
            final int sortType = bundle.getInt(MOVIE_SORT_TYPE);

            if (sortType == 0) {
                popularMoviesViewModel = ViewModelProviders.of(this).get(PopularMoviesViewModel.class);
                popularMoviesViewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {

                        if (movies != null) {

                            for (Movie letMovie : movies) {
                                if (letMovie.getId() == movieId) {
                                    movie = letMovie;
//                                    return;
                                }
                            }
                        }

                        Picasso.with(mContext).load(movie.getPosterImage()).into(moviePosterDetail);

                        movieTitleDetail.setText(movie.getTitle());
                        movieRatingDetail.setText(Double.toString(movie.getVote_average()));
                        movieReleaseDetail.setText(movie.getRelease_date());
                        movieDescriptionDetail.setText(movie.getOverview());
                    }
                });
            } else {
                topRatedMoviesViewModel = ViewModelProviders.of(this).get(TopRatedMoviesViewModel.class);
                topRatedMoviesViewModel.getTopRatedMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(@Nullable List<Movie> movies) {

                        if (movies != null) {

                            for (Movie letMovie : movies) {
                                if (letMovie.getId() == movieId) {
                                    movie = letMovie;
//                                    return;
                                }
                            }
                        }

                        Picasso.with(mContext).load(movie.getPosterImage()).into(moviePosterDetail);

                        movieTitleDetail.setText(movie.getTitle());
                        movieRatingDetail.setText(Double.toString(movie.getVote_average()));
                        movieReleaseDetail.setText(movie.getRelease_date());
                        movieDescriptionDetail.setText(movie.getOverview());
                    }
                });
            }
        }
    }
}