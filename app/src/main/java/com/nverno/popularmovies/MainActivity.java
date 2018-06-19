package com.nverno.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.nverno.popularmovies.database.PopularMovieDatabase;
import com.nverno.popularmovies.database.ReviewDatabase;
import com.nverno.popularmovies.database.TopRatedMovieDatabase;
import com.nverno.popularmovies.database.TrailerDatabase;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.repository.PopularMovieRepository;
import com.nverno.popularmovies.repository.ReviewRepository;
import com.nverno.popularmovies.repository.TopRatedMovieRepository;
import com.nverno.popularmovies.repository.TrailerRepository;
import com.nverno.popularmovies.viewmodel.PopularMoviesViewModel;
import com.nverno.popularmovies.viewmodel.TopRatedMoviesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        PosterAdapter.PosterAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PosterAdapter mPosterAdapter;

    @BindView(R.id.recycler_view_posters) RecyclerView mRecyclerView;
    @BindView(R.id.main_activity_progress_bar) ProgressBar mLoadingSpinner;

    // To identify our sort types.
    private static final int SORT_POPULAR = 0;
    private static final int SORT_TOP_RATED = 1;

    // A persistent way to keep track of our sort type.
    private static int sMovieSortType;

    // To identify that we are moving movie data through an intent.
    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_SORT_TYPE = "MOVIE_SORT_TYPE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        if (mLoadingSpinner.getVisibility() == View.INVISIBLE) {
            mLoadingSpinner.setVisibility(View.VISIBLE);
        }

        // Using the GridLayoutManager with 2 columns.
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Associate the LayoutManager with the RecyclerView.
        mRecyclerView.setLayoutManager(layoutManager);

        // Improves performance if changes in content do not change the child layout size.
        mRecyclerView.setHasFixedSize(true);

        // The PosterAdapter links our poster images with the views that display them.
        mPosterAdapter = new PosterAdapter(this,this);

        // Set the adapter, attaching it to the RecyclerView in the layout.
        mRecyclerView.setAdapter(mPosterAdapter);

        populateMoviesDatabase();

        initializeMoviesViewModel();
    }

    // TODO: Reduce the use of this function for when needed, not on every onCreate().

    private void populateMoviesDatabase () {

        PopularMovieDatabase mPopMovieDb = PopularMovieDatabase.getInstance(getApplicationContext());
        TopRatedMovieDatabase mTopMovieDb = TopRatedMovieDatabase.getsInstance(getApplicationContext());

        PopularMovieRepository popularMovieRepository = new PopularMovieRepository();
        TopRatedMovieRepository topRatedMovieRepository = new TopRatedMovieRepository();

        popularMovieRepository.loadPopularMovies(mPopMovieDb);
        topRatedMovieRepository.loadTopRatedMovies(mTopMovieDb);
    }

    private void setPopularMoviesView() {
        PopularMoviesViewModel popularMoviesViewModel = ViewModelProviders.of(this)
                .get(PopularMoviesViewModel.class);


        popularMoviesViewModel.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mLoadingSpinner.setVisibility(View.INVISIBLE);
                mPosterAdapter.setPosterData(movies);
            }
        });
    }

    private void setTopRatedMoviesView() {
        TopRatedMoviesViewModel topRatedMoviesViewModel= ViewModelProviders.of(this)
                .get(TopRatedMoviesViewModel.class);

        topRatedMoviesViewModel.getTopRatedMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mLoadingSpinner.setVisibility(View.INVISIBLE);
                mPosterAdapter.setPosterData(movies);
            }
        });
    }

    private void initializeMoviesViewModel() {
        if (sMovieSortType == SORT_POPULAR) {
            setPopularMoviesView();
        } else {
            setTopRatedMoviesView();
        }
    }

    @Override
    public void onClick(Movie movieForDay) {
        Context context = this;

        // Where this click intent is going...
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        initializeExtraData(movieForDay.getId());

        intentToStartDetailActivity.putExtra(MOVIE_ID, movieForDay.getId());
        intentToStartDetailActivity.putExtra(MOVIE_SORT_TYPE, sMovieSortType);

        startActivity(intentToStartDetailActivity);
    }

    private void initializeExtraData(int movieId) {
        ReviewDatabase reviewDatabase = ReviewDatabase.getInstance(getApplicationContext());
        ReviewRepository reviewRepository = new ReviewRepository();
        reviewRepository.loadMovieReviews(reviewDatabase, movieId);

        TrailerDatabase trailerDatabase = TrailerDatabase.getInstance(getApplicationContext());
        TrailerRepository trailerRepository = new TrailerRepository();
        trailerRepository.loadMovieTrailers(trailerDatabase, movieId);
    }

    // Create and inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Sort by most popular is default and checked.
        if (sMovieSortType == 0) {
            menu.findItem(R.id.sort_by_most_popular).setChecked(true);
        } else {
            menu.findItem(R.id.sort_by_top_rated).setChecked(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // If sort by most popular has been clicked...
            case R.id.sort_by_most_popular:
                // and the sort type is not already "most popular"
                if (sMovieSortType != SORT_POPULAR) {
                    mLoadingSpinner.setVisibility(View.VISIBLE);
                    // set the sort type to "most popular"
                    sMovieSortType = SORT_POPULAR;
                    // set that item as checked.
                    item.setChecked(true);
                    // Set the view
                    initializeMoviesViewModel();
                }
                return true;

                // Every thing like above, but the opposite.
            case R.id.sort_by_top_rated:
                if (sMovieSortType != SORT_TOP_RATED) {
                    mLoadingSpinner.setVisibility(View.VISIBLE);
                    sMovieSortType = SORT_TOP_RATED;
                    item.setChecked(true);
                    initializeMoviesViewModel();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
