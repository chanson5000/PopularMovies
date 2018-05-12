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
import android.view.View;
import android.widget.Toast;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.util.JsonParse;
import com.nverno.popularmovies.util.MovieDb;
import com.nverno.popularmovies.util.Network;

import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Movie>>,
        PosterAdapter.PosterAdapterOnClickHandler {

    private PosterAdapter mPosterAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    // Arbitrary ID used to identify the loader.
    private static final int POSTER_LOADER = 0;

    // For ButterKnife
//    @BindView(R.id.movie_poster) ImageView moviePosterIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: Check out possible implementation of getSupportActionBar()

        // If we decide to use ButterKnife in the future.
//        ButterKnife.bind(this);

        // Get our reference of RecyclerView from XML.
        mRecyclerView = findViewById(R.id.recycler_view_posters);

        // TODO: Implement progress bar.

        // LinearLayoutManager measures and positions item views within the RecyclerView.
        // Note: There are other LayoutManagers available.
        GridLayoutManager layoutManager =
                new GridLayoutManager(this, 2);

        // Associate the LayoutManager with the RecyclerView.
        mRecyclerView.setLayoutManager(layoutManager);

        // Improves performance if changes in content do not change the child layout size.
        mRecyclerView.setHasFixedSize(true);

        // The PosterAdapter links our poster images with the views that display them.
        mPosterAdapter = new PosterAdapter(this);

        // Set the adapter, attaching it to the RecyclerView in the layout.
        mRecyclerView.setAdapter(mPosterAdapter);

        int loaderId = POSTER_LOADER;

        LoaderManager.LoaderCallbacks<List<Movie>> callback = MainActivity.this;
        // TODO: Implement showLoading();
        // showLoading();

        // Ensure the loader is initialized. One is created if doesn't exist, else last is used.
        // TODO: Implement this correctly.


        getSupportLoaderManager().initLoader(loaderId, null, callback);

    }

    /**
     * Called by the {@link android.support.v4.app.LoaderManagerImpl} when a new Loader needs to be
     * created. This Activity only uses one loader, so we don't necessarily NEED to check the
     * loaderId, but this is certainly best practice.
     *
     * @param id The loader ID for which we need to create a loader
     * @param bundle   Any arguments supplied by the caller
     * @return A new Loader instance that is ready to start loading.
     */
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
                    URL requestUrl = MovieDb.getTopRatedUrl();
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

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mPosterAdapter.setPosterData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    @Override
    public void onClick(Movie movieForDay) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        intentToStartDetailActivity.putExtra("MOVIE", movieForDay);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if (menuItemThatWasSelected == R.id.action_settings) {
            Context context = MainActivity.this;
            String message = "Settings clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void showPosterDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
