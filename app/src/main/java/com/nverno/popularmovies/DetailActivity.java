package com.nverno.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.FavoriteMovieRepository;
import com.nverno.popularmovies.viewmodel.MoviesViewModel;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

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
    @BindView(R.id.click_reviews)
    Button mReviewClickButton;
    @BindView(R.id.favorite_movie)
    TextView mFavoriteMovie;

    MoviesViewModel moviesViewModel;

    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_SORT_TYPE = "MOVIE_SORT_TYPE";
    private static final String MOVIE_TITLE = "MOVIE_NAME_EXTRA";

    private static Movie mMovie;
    private static int movieId;
    private static int sortType;
    private static boolean isFavoriteMovie;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mContext = this;

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null
                && intentThatStartedThisActivity.hasExtra(MOVIE_ID)
                && intentThatStartedThisActivity.hasExtra(MOVIE_SORT_TYPE)) {

            Bundle bundle = intentThatStartedThisActivity.getExtras();

            if (bundle != null) {
                movieId = bundle.getInt(MOVIE_ID);
                sortType = bundle.getInt(MOVIE_SORT_TYPE);
            }
        }

        setUpMainView();
    }

    private void setUpMainView() {
        moviesViewModel = ViewModelProviders.of(this)
                .get(MoviesViewModel.class);

        moviesViewModel.setSelectedMovie(sortType, movieId);

        moviesViewModel.getSelectedMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie != null) {
                    Picasso.with(mContext).load(movie.getPosterImage()).into(moviePosterDetail);

                    movieTitleDetail.setText(movie.getTitle());
                    movieRatingDetail.setText(String.format(Locale.getDefault(),
                            "%.1f",
                            movie.getVoteAverage()));
                    movieReleaseDetail.setText(movie.getReleaseDate());
                    movieDescriptionDetail.setText(movie.getOverview());

                    mMovie = movie;
                }
            }
        });

        moviesViewModel.isFavoriteMovie(movieId).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean movieIsFavorite) {
                if (movieIsFavorite != null && movieIsFavorite) {
                    mFavoriteMovie.setTypeface(null, Typeface.BOLD);
                    isFavoriteMovie = true;
                } else {
                    mFavoriteMovie.setTypeface(null, Typeface.NORMAL);
                    isFavoriteMovie = false;
                }
            }
        });
    }

    public void toggleFavorite(View view) {
        FavoriteMovieRepository favoriteMovieRepository =
                new FavoriteMovieRepository(getApplicationContext());

        if (isFavoriteMovie) {
            favoriteMovieRepository.remove(mMovie);
        } else {
            favoriteMovieRepository.add(mMovie);
        }
    }

    public void showReviews(View view) {
        Class destinationClass = ReviewActivity.class;
        Intent intentToStartReviewActivity = new Intent(this, destinationClass);

        intentToStartReviewActivity.putExtra(MOVIE_ID, mMovie.getId());
        intentToStartReviewActivity.putExtra(MOVIE_TITLE, mMovie.getTitle());

        startActivity(intentToStartReviewActivity);
    }

    public void showTrailers(View view) {
        Class destinationClass = TrailerActivity.class;
        Intent intentToStartTrailerActivity = new Intent(this, destinationClass);

        intentToStartTrailerActivity.putExtra(MOVIE_ID, mMovie.getId());
        intentToStartTrailerActivity.putExtra(MOVIE_TITLE, mMovie.getTitle());

        startActivity(intentToStartTrailerActivity);
    }
}