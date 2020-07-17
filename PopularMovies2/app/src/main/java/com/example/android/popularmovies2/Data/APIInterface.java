package com.example.android.popularmovies2.Data;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;
import com.example.android.popularmovies2.APIResponsePOJO.MovieTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {

    /**The Key doesn't appear on public repo */
    public final static String API_KEY = "cd401ba98e50ce8bf913cdce912aa430";

    /**
     * All the @GET method will
     * fetch data from a specific endpoint of the API.
     *
     * These endpoints will be accessed by using
     * the correct url extension.
     *
     * The urls will be built in the MainActivity
     * */

    // Get the list of popular movies
    @GET
    Call<DiscoveredMovies> doGetDiscoveredMovies(@Url String url);

        /* Queries the movie credit endpoint */
    @GET
    Call<MovieCredit> doGetMovieCredit(@Url String url);

        /* Queries the movie detail endpoint */
    @GET
    Call<MovieDetail> doGetMovieDetail(@Url String url);

        /* Queries the review endpoint */
    @GET
    Call<MovieReviews> doGetMovieReviews(@Url String url);

    /* Queries the trailer endpoint */
    @GET
    Call<MovieTrailers> doGetMovieTrailers(@Url String url);
}
