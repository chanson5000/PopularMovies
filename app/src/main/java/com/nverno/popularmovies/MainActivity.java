package com.nverno.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.util.JsonParse;
import com.nverno.popularmovies.util.MovieDb;
import com.nverno.popularmovies.util.Network;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Movie>>,
        PosterAdapter.PosterAdapterOnClickHandler {

    private PosterAdapter mPosterAdapter;

    @BindView(R.id.recycler_view_posters) private RecyclerView mRecyclerView;

    LoaderManager.LoaderCallbacks<List<Movie>> mCallback;

    // To identify our loader type. (only using one, anyway)
    private static final int POSTER_LOADER = 0;

    // To identify our sort types.
    private static final int SORT_MOST_POPULAR = 0;
    private static final int SORT_TOP_RATED = 1;

    // A persistent way to keep track of our sort type.
    private static int sMovieSortType = SORT_MOST_POPULAR;

    // To identify that we are moving movie data through an intent.
    private static final String INTENT_MOVIE_DATA = "MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Using the GridLayoutManager with 2 columns.
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Associate the LayoutManager with the RecyclerView.
        mRecyclerView.setLayoutManager(layoutManager);

        // Improves performance if changes in content do not change the child layout size.
        mRecyclerView.setHasFixedSize(true);

        // The PosterAdapter links our poster images with the views that display them.
        mPosterAdapter = new PosterAdapter(this);

        // Set the adapter, attaching it to the RecyclerView in the layout.
        mRecyclerView.setAdapter(mPosterAdapter);

        mCallback = MainActivity.this;
        getSupportLoaderManager().initLoader(POSTER_LOADER, null, mCallback);

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle bundle) {

        return new AsyncTaskLoader<List<Movie>>(this) {

            List<Movie> posterData = null;

            @Override
            public void onStartLoading() {
                if (posterData != null) {
                    deliverResult(posterData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Movie> loadInBackground() {
                URL requestUrl;
                if (sMovieSortType == SORT_TOP_RATED) {
                    requestUrl = MovieDb.getTopRatedUrl();
                } else {
                    requestUrl = MovieDb.getPopularUrl();
                }

                try {
                    String jsonResponse = Network.fetchHttpsResponse(requestUrl);

                    List<Movie> movies = JsonParse.topRatedResults(jsonResponse);

                    return movies;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(List<Movie> data) {
                posterData = data;
                super.deliverResult(data);
            }
        };
    }

    // When our AsyncTask finishes loading, we can set the data into the PosterAdapter.
    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mPosterAdapter.setPosterData(data);
    }

    // I don't think I needed to use this yet, but I have to override it...
    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

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
        menu.findItem(R.id.sort_by_most_popular).setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // If sort by most popular has been clicked...
            case R.id.sort_by_most_popular:
                // and the sort type is not already "most popular"
                if (sMovieSortType != SORT_MOST_POPULAR) {
                    // set the sort type to "most popular"
                    sMovieSortType = SORT_MOST_POPULAR;
                    // set that item as checked.
                    item.setChecked(true);
                    // restart the loader to refresh the poster sort type
                    getSupportLoaderManager().restartLoader(POSTER_LOADER, null, mCallback);
                }
                return true;

                // Every thing like above, but the opposite.
            case R.id.sort_by_top_rated:
                if (sMovieSortType != SORT_TOP_RATED) {
                    sMovieSortType = SORT_TOP_RATED;
                    item.setChecked(true);
                    getSupportLoaderManager().restartLoader(POSTER_LOADER, null, mCallback);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
