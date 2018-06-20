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

import com.nverno.popularmovies.database.FavoriteMovieDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.FavoriteMovieRepository;
import com.nverno.popularmovies.viewmodel.FavoriteMoviesViewModel;
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
    @BindView(R.id.click_reviews)
    Button mReviewClickButton;
    @BindView(R.id.favorite_movie)
    TextView mFavoriteMovie;

    PopularMoviesViewModel popularMoviesViewModel;
    TopRatedMoviesViewModel topRatedMoviesViewModel;
    FavoriteMoviesViewModel favoriteMoviesViewModel;

    private static List<Movie> favoriteMovies;

    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_SORT_TYPE = "MOVIE_SORT_TYPE";
    private static final String MOVIE_TITLE = "MOVIE_NAME_EXTRA";

    // To identify our sort types.
    private static final int SORT_POPULAR = 0;
    private static final int SORT_TOP_RATED = 1;
    private static final int SHOW_FAVORITES = 3;

    private static Movie movie;
    private static int movieId;
    private static int sortType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        final Context mContext = this;

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null
                && intentThatStartedThisActivity.hasExtra(MOVIE_ID)
                && intentThatStartedThisActivity.hasExtra(MOVIE_SORT_TYPE)) {

            Bundle bundle = intentThatStartedThisActivity.getExtras();

            movieId = bundle.getInt(MOVIE_ID);
            sortType = bundle.getInt(MOVIE_SORT_TYPE);
        }

        getFavoriteMovies();

        if (sortType == SORT_POPULAR) {
            popularMoviesViewModel = ViewModelProviders.of(this)
                    .get(PopularMoviesViewModel.class);
            popularMoviesViewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {

                    if (movies != null) {

                        for (Movie letMovie : movies) {
                            if (letMovie.getId() == movieId) {
                                movie = letMovie;
                            }
                        }

                        Picasso.with(mContext).load(movie.getPosterImage()).into(moviePosterDetail);

                        movieTitleDetail.setText(movie.getTitle());
                        movieRatingDetail.setText(Double.toString(movie.getVote_average()));
                        movieReleaseDetail.setText(movie.getRelease_date());
                        movieDescriptionDetail.setText(movie.getOverview());


                    }
                }
            });
        } else if (sortType == SORT_TOP_RATED) {
            topRatedMoviesViewModel = ViewModelProviders.of(this)
                    .get(TopRatedMoviesViewModel.class);
            topRatedMoviesViewModel.getTopRatedMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {

                    if (movies != null) {

                        for (Movie letMovie : movies) {
                            if (letMovie.getId() == movieId) {
                                movie = letMovie;
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
            favoriteMoviesViewModel = ViewModelProviders.of(this)
                    .get(FavoriteMoviesViewModel.class);
            favoriteMoviesViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    if (movies != null) {
                        for (Movie letMovie : movies) {
                            if (letMovie.getId() == movieId) {
                                movie = letMovie;
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


    private void getFavoriteMovies() {
        favoriteMoviesViewModel = ViewModelProviders.of(this)
                .get(FavoriteMoviesViewModel.class);
        favoriteMoviesViewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies == null) return;

                favoriteMovies = movies;

                if (checkIfFavorite(movieId, movies)) {
                    mFavoriteMovie.setTypeface(null, Typeface.BOLD);
                }
            }
        });
    }

    private boolean checkIfFavorite(int movieId, List<Movie> favorites) {
        for (Movie letMovie : favorites) {
            if (letMovie.getId() == movieId) {
                return true;
            }
        }
        return false;
    }

    public void toggleFavorite(View view) {
        FavoriteMovieDatabase favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(getApplicationContext());
        FavoriteMovieRepository favoriteMovieRepository = new FavoriteMovieRepository();

        if (checkIfFavorite(movieId, favoriteMovies)) {
            mFavoriteMovie.setTypeface(null, Typeface.NORMAL);

            favoriteMovieRepository.removeFavoriteMovie(favoriteMovieDatabase, movie);
        } else {
            mFavoriteMovie.setTypeface(null, Typeface.BOLD);

            favoriteMovieRepository.addFavoriteMovie(favoriteMovieDatabase, movie);
        }
    }

    public void showReviews(View view) {
        Context context = this;

        Class destinationClass = ReviewActivity.class;
        Intent intentToStartReviewActivity = new Intent(context, destinationClass);

        intentToStartReviewActivity.putExtra(MOVIE_ID, movie.getId());
        intentToStartReviewActivity.putExtra(MOVIE_TITLE, movie.getTitle());

        startActivity(intentToStartReviewActivity);
    }

    public void showTrailers(View view) {
        Context context = this;

        Class destinationClass = TrailerActivity.class;

        Intent intentToStartTrailerActivity = new Intent(context, destinationClass);

        intentToStartTrailerActivity.putExtra(MOVIE_ID, movie.getId());
        intentToStartTrailerActivity.putExtra(MOVIE_TITLE, movie.getTitle());

        startActivity(intentToStartTrailerActivity);
    }
}