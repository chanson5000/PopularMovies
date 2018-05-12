package com.nverno.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.nverno.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_poster_detail) ImageView moviePosterDetail;
    @BindView(R.id.movie_title_detail) TextView movieTitleDetail;
    @BindView(R.id.movie_rating_detail) TextView movieRatingDetail;
    @BindView(R.id.movie_release_detail) TextView movieReleaseDetail;
    @BindView(R.id.move_description_detail) TextView movieDescriptionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            // The extra data is the Movie object implementing Parcelable.
            if (intentThatStartedThisActivity.hasExtra("MOVIE")) {

                // Put the parcelable into a bundle.
                Bundle bundle = intentThatStartedThisActivity.getExtras();

                // Turn that bundle back into the Movie object.
                Movie movie = bundle.getParcelable("MOVIE");

                Picasso.with(this).load(movie.getPosterImage()).into(moviePosterDetail);

                movieTitleDetail.setText(movie.getTitle());
                movieRatingDetail.setText(Double.toString(movie.getVoteAverage()));
                movieReleaseDetail.setText(movie.getReleaseDate());
                movieDescriptionDetail.setText(movie.getOverview());
            }
        }
    }
}