package com.example.android.popularmovies2.Data;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {

    /**The Key doesn't appear on public repo */
    public final static String API_KEY = "";

    /**
     *
     * Constants to be used inside the url extension as the possible value of sortBy.
     * This will determine the sortage of the API response.
     *
     * */
    public final static String BY_POPULARITY = "popularity.desc";
    public final static String BY_RATINGS = "vote_average.desc";

    /**
     *
     *  The following variables will help us build the
     *  url that will give us access to the "discover" endpoint
     *  of the movie database API.
     *
     *  */


    public String sortBy = BY_POPULARITY;

    final static String URL_EXTRA_PARAMETERS = "&language=en-US&sort_by="+ sortBy +"&include_adult=true&include_video=false&page=1";
    final static String DISCOVER_URL_EXTENSION = "discover/movie?api_key=" + API_KEY + URL_EXTRA_PARAMETERS;

    /**
     * All the @GET method will
     * fetch data from a specific endpoint of the API.
     *
     * These endpoints will be accessed by using
     * the correct url extension.
     * */

    // Get the list of popular movies
    @GET(DISCOVER_URL_EXTENSION)
    Call<DiscoveredMovies> doGetPopularMovies();


    /**
     *  The following get method will receive custom
     *  url made with the movie's ids.
     *  The url will be built in the MainActivity
     *
     *  */

        /* Queries the movie credit endpoint */
    @GET
    Call<MovieCredit> doGetMovieCredit(@Url String url);

        /* Queries the movie detail endpoint */
    @GET
    Call<MovieDetail> doGetMovieDetail(@Url String url);

        /* Queries the review endpoint */
    @GET
    Call<MovieReviews> doGetMovieReviews(@Url String url);

    /*@GET
    Call<MovieTrailer> doGetMovieTrailer(@Url String url);
    */
}
