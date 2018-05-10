package com.nverno.popularmovies;

import com.nverno.popularmovies.model.Movie;
import com.nverno.popularmovies.util.MovieDb;
import com.nverno.popularmovies.util.Network;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    private Movie movie;
    private int id;
    private String original_title;
    private String poster_path;
    private String overview;
    private double vote_average;
    private String release_date;
    private static final String poster_url = "https://image.tmdb.org/t/p/w185";
    private static final String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TOP_RATED = "movie/top_rated";
    private static final String AUTH_STRING = "?api_key=";
    private static final String AUTH = AUTH_STRING + MovieDb.API_KEY;

    @Before
    public void setUp() throws Exception {
        id = 278;
        original_title = "The Shawshank Redemption";
        poster_path = "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg";
        overview = "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.";
        vote_average = 8.32;
        release_date = "1994-09-10";

        movie = new Movie(id,
                original_title,
                poster_path,
                overview,
                vote_average,
                release_date);

    }

    @Test
    public void MovieClass_addedObject() throws Exception {
        assertNotNull(movie);
        assertEquals(movie.getId(), id);
        assertEquals(movie.getTitle(), original_title);
        assertEquals(movie.getPosterPath(), poster_path);
        assertEquals(movie.getOverview(), overview);
        assertEquals(movie.getVoteAverage(), vote_average, 0.01);
        assertEquals(movie.getReleaseDate(), release_date);
        assertEquals(movie.getPosterImage(), poster_url + movie.getPosterPath());
    }

    @Test
    public void fetchHttp_returnDataString() throws Exception {
        URL url = new URL(MOVIEDB_BASE_URL + TOP_RATED + AUTH);
        String response = Network.fetchHttp(url);
        System.out.print(response);

        assertNotNull(response);
    }

    @Test
    public void getTopRated_returnListOfMovies() {
        List<Movie> topRateResults = MovieDb.getTopRated();

        int len = topRateResults.size();
        for (int i = 0; i < len; i++) {
            System.out.println("---------------");
            System.out.println("Title: " + topRateResults.get(i).getTitle());
            System.out.println("Poster path: " + topRateResults.get(i).getPosterImage());
            System.out.println("Overview: " + topRateResults.get(i).getOverview());
            System.out.println("Vote Average: " + topRateResults.get(i).getVoteAverage());
            System.out.println("Release date: " + topRateResults.get(i).getReleaseDate());
        }
        assertNotNull(topRateResults);
    }
}