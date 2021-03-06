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
import android.widget.TextView;

import com.nverno.popularmovies.adapter.PosterAdapter;
import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.viewmodel.MoviesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        PosterAdapter.PosterAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private PosterAdapter mPosterAdapter;

    private MoviesViewModel moviesViewModel;

    @BindView(R.id.recycler_view_posters)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_activity_progress_bar)
    ProgressBar mLoadingSpinner;
    @BindView(R.id.no_favorites)
    TextView mTextNoFavorites;

    // To identify our sort types.
    private static final int SORT_POPULAR = 0;
    private static final int SORT_TOP_RATED = 1;
    private static final int SHOW_FAVORITES = 2;

    // A persistent way to keep track of our sort type.
    private static int sMovieSortType;

    // To identify that we are moving movie data through an intent.
    private static final String MOVIE_ID = "MOVIE_ID_EXTRA";
    private static final String MOVIE_SORT_TYPE = "MOVIE_SORT_TYPE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        initViewModel();

        setViewModel();
    }

    private void initView() {
        // Using the GridLayoutManager with 2 columns.
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Associate the LayoutManager with the RecyclerView.
        mRecyclerView.setLayoutManager(layoutManager);

        // Improves performance if changes in content do not change the child layout size.
        mRecyclerView.setHasFixedSize(true);

        // The PosterAdapter links our poster images with the views that display them.
        mPosterAdapter = new PosterAdapter(this, this);

        // Set the adapter, attaching it to the RecyclerView in the layout.
        mRecyclerView.setAdapter(mPosterAdapter);
    }

    private void initViewModel() {
        moviesViewModel = ViewModelProviders.of(this)
                .get(MoviesViewModel.class);

        moviesViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {

                mPosterAdapter.setPosterData(movies);

                if (movies == null) {
                    showLoadingIndicator();
                } else if (movies.isEmpty()) {
                    showNoMovies();
                } else {
                    hideAllIndicators();
                }
            }
        });
    }

    private void setViewModel() {
        showLoadingIndicator();

        switch (sMovieSortType) {
            case SORT_POPULAR:
                moviesViewModel.loadPopularMovies();
                break;
            case SORT_TOP_RATED:
                moviesViewModel.loadTopRatedMovies();
                break;
            default:
                moviesViewModel.loadFavoriteMovies();
                break;
        }
    }

    @Override
    public void onClick(Movie movieForDay) {
        Context context = this;

        // Where this click intent is going...
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra(MOVIE_ID, movieForDay.getId());
        intentToStartDetailActivity.putExtra(MOVIE_SORT_TYPE, sMovieSortType);

        startActivity(intentToStartDetailActivity);
    }

    // Create and inflate the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // Sort by most popular is default and checked.
        switch (sMovieSortType) {
            case SORT_POPULAR:
                menu.findItem(R.id.sort_by_most_popular).setChecked(true);
                break;
            case SORT_TOP_RATED:
                menu.findItem(R.id.sort_by_top_rated).setChecked(true);
                break;
            default:
                menu.findItem(R.id.show_favorites).setChecked(true);
                break;
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
                    // set the sort type to "most popular"
                    sMovieSortType = SORT_POPULAR;
                    // set that item as checked.
                    item.setChecked(true);
                    // Set the view
                    setViewModel();
                }
                return true;

            // Every thing like above, but the opposite.
            case R.id.sort_by_top_rated:
                if (sMovieSortType != SORT_TOP_RATED) {
                    sMovieSortType = SORT_TOP_RATED;
                    item.setChecked(true);
                    setViewModel();
                }
                return true;

            // For favorites
            case R.id.show_favorites:
                if (sMovieSortType != SHOW_FAVORITES) {
                    sMovieSortType = SHOW_FAVORITES;
                    item.setChecked(true);
                    setViewModel();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLoadingIndicator() {
        hideNoMovies();
        if (mLoadingSpinner.getVisibility() == View.INVISIBLE) {
            mLoadingSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void hideLoadingIndicator() {
        if (mLoadingSpinner.getVisibility() == View.VISIBLE) {
            mLoadingSpinner.setVisibility(View.INVISIBLE);
        }
    }

    private void showNoMovies() {
        hideLoadingIndicator();
        if (mTextNoFavorites.getVisibility() == View.INVISIBLE) {
            mTextNoFavorites.setVisibility(View.VISIBLE);
        }
    }

    private void hideNoMovies() {
        if (mTextNoFavorites.getVisibility() == View.VISIBLE) {
            mTextNoFavorites.setVisibility(View.INVISIBLE);
        }
    }

    private void hideAllIndicators(){
        hideNoMovies();
        hideLoadingIndicator();
    }
}
