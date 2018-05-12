package com.nverno.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.nverno.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

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
            // The extra data is the Movie object implementing Parcelable.
            if (intentThatStartedThisActivity.hasExtra("MOVIE")) {

                // Put the parcelable into a bundle.
                Bundle bundle = intentThatStartedThisActivity.getExtras();

                // Turn that bundle back into the Movie object.
                movie = bundle.getParcelable("MOVIE");

                Picasso.with(this).load(movie.getPosterImage()).into(moviePosterDetail);

                movieTitleDetail.setText(movie.getTitle());
                movieRatingDetail.setText(Double.toString(movie.getVoteAverage()));
                movieReleaseDetail.setText(movie.getReleaseDate());
                movieDescriptionDetail.setText(movie.getOverview());
            }
        }
    }
}