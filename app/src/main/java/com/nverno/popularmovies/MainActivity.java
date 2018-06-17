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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.viewmodel.MoviesViewModel;

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
    private static final String INTENT_MOVIE_DATA = "MOVIE";

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

        initializeMoviesViewModel();
    }

    private void initializeMoviesViewModel() {
        MoviesViewModel viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        viewModel.getTopRatedMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mLoadingSpinner.setVisibility(View.INVISIBLE);
                mPosterAdapter.setPosterData(movies);
            }
        });
    }

    @Override
    public void onClick(Movie movieForDay) {
        Context context = this;

        // Where this click intent is going...
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        // Pushing our parcelable movie data through the intent.
        intentToStartDetailActivity.putExtra(INTENT_MOVIE_DATA, movieForDay);

        startActivity(intentToStartDetailActivity);
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
                    // set the sort type to "most popular"
                    sMovieSortType = SORT_POPULAR;
                    // set that item as checked.
                    item.setChecked(true);
                    // restart the loader to refresh the poster sort type
                }
                return true;

                // Every thing like above, but the opposite.
            case R.id.sort_by_top_rated:
                if (sMovieSortType != SORT_TOP_RATED) {
                    sMovieSortType = SORT_TOP_RATED;
                    item.setChecked(true);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
