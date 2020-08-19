package com.example.android.popularmovies2.NetworkOperations;

import android.util.Log;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;
import com.example.android.popularmovies2.APIResponsePOJO.MovieTrailers;
import com.example.android.popularmovies2.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public final class QueryUtils {

    /**
     * The Tag to be used in log messages
     */
    private static String TAG = MainActivity.class.getSimpleName();

    /**
     * This will store the api key necessary
     * to query any endpoint.
     */
    private static final String API_KEY = "cd401ba98e50ce8bf913cdce912aa430";

    /**
     * The following constants will be use to form
     * the right URL based on the endpoints we'd like to
     * query. As an example, to query
     * the "movie credit" endpoint, we will
     * build the URL using "MOVIE_CREDIT"
     */
    private static final String MOVIE_CREDIT = "credits";
    private static final String MOVIE_DETAILS = "details";
    private static final String MOVIE_REVIEWS = "reviews";
    private static final String MOVIE_TRAILER = "videos";
    private static final String DISCOVER_MOVIE = "discover";

    /**
     * Will be used
     * to fetch data from
     * the server. It contains
     * the base url.
     */
    private static APIInterface mApiInterface;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /**
     * Continu
     */
    public static List<DiscoveredMovies.Movie> fetchMoviesData(final APIInterface apiInterface, final String sortingKey) {

        // Create the variable "callPopularMovies" that will help us make
        // an API call on the "discover" endpoint.
        Call<DiscoveredMovies> callPopularMovies = apiInterface.doGetDiscoveredMovies(
                getProperUrl(DISCOVER_MOVIE, 0, sortingKey));

        // Create a response variable that will
        // receive the response from the API call.
        Response<DiscoveredMovies> response = null;

        // Make the API call, try to talk to the server
        // and get popular movies out of the call.
        try {
            response = callPopularMovies.execute();
        } catch (Exception e) {
            Log.e(TAG, "A problem occured with the API call " + e.getMessage());
        }

        // Create url --> make http --> get the json onto an other method
        DiscoveredMovies resource = new DiscoveredMovies();
        // Check if the call was successful and
        // use the response.
        if (response != null && response.isSuccessful()) {

            // from the server response,
            // get the discovered movie and return.
            resource = response.body();

        } else {
            Log.e(MainActivity.class.getSimpleName(), "API Response unsuccessful, code : " + response.code());
        }

        return completeMoviesDetails(resource);

    }


    public static MovieCredit getMovieCredit(DiscoveredMovies.Movie movie, final APIInterface apiInterface) {

        // From the credit endpoint of the API,
        // Get the credit info of every movie in the list.

        Call<MovieCredit> callMovieCredit = apiInterface.doGetMovieCredit(getProperUrl(MOVIE_CREDIT, movie.getId(), ""));
        Response<MovieCredit> response = null;

        // Make the API call to the credit endpoint
        try {
            response = callMovieCredit.execute();
        } catch (Exception e) {
            Log.e(TAG, "A problem occured with the API call on the Credit endpoint" + e.getMessage());
        }

        MovieCredit resource = new MovieCredit();
        String unsuccessfulMessage = "API Response unsuccessful on the Credits endpoint";

        // In case the call was successful
        // use the data from the server's response
        if (response != null && response.isSuccessful()) {
            resource = response.body();

        } else {
            // Only if the response is not null,
            // add its code to the unsuccessfulMessage variable
            if (response != null) {
                unsuccessfulMessage += ", code :" + response.code();
            }

            Log.e(MainActivity.class.getSimpleName(), unsuccessfulMessage);
            // In case the response wasn't successful, add a null object
            // to the credit info of the movie
            movie.setMovieCredit(null);
        }
        return resource;
    }

    private static MovieDetail getMovieDetails(DiscoveredMovies.Movie movie, final APIInterface apiInterface) {

        Call<MovieDetail> callMovieDetail = apiInterface.doGetMovieDetail(getProperUrl(MOVIE_DETAILS, movie.getId(), ""));
        Response<MovieDetail> response = null;

        // Make the API call to the details endpoint
        try {
            response = callMovieDetail.execute();
        } catch (Exception e) {
            Log.e(TAG, "A problem occured with the API call on the Details endpoint " + e.getMessage());
        }

        MovieDetail resource = new MovieDetail();
        String unsuccessfulMessage = "API Response unsuccessful on the Details endpoint";

        // In case the call was successful
        // use the data from the server's response
        if (response != null && response.isSuccessful()) {
            resource = response.body();

            // Once we get the movie details,
            // add it to the mMoviesCredit list.
            // This will contain the details infos for all the movies previously fetched
            // movie.setMovieCredit(resource);
            return resource;

        } else {

            // Only if the response is not null,
            // add its code to the unsuccessfulMessage variable
            if (response != null) {
                unsuccessfulMessage += ", code :" + response.code();
            }

            Log.e(MainActivity.class.getSimpleName(), unsuccessfulMessage);

            // In case the response wasn't successful, add a null object
            // to the details info of the movie
            movie.setMovieDetails(null);
        }

        return resource;
    }

    public static MovieReviews getMovieReviews(DiscoveredMovies.Movie movie, final APIInterface apiInterface) {

        // From the review endpoint of the API,
        // Get the review info of every movie in the list.

        Call<MovieReviews> callMovieReview = apiInterface.doGetMovieReviews(getProperUrl(MOVIE_REVIEWS, movie.getId(), ""));
        Response<MovieReviews> response = null;

        // Make the API call to the review endpoint
        try {
            response = callMovieReview.execute();
        } catch (Exception e) {
            Log.e(TAG, "A problem occured with the API call on the Reviews endpoint" + e.getMessage());
        }

        MovieReviews resource = new MovieReviews();
        String unsuccessfulMessage = "API Response unsuccessful on the Reviews endpoint";

        // In case the call was successful
        // use the data from the server's response
        if (response != null && response.isSuccessful()) {
            resource = response.body();

        } else {

            // Only if the response is not null,
            // add its code to the unsuccessfulMessage variable
            if (response != null) {
                unsuccessfulMessage += ", code :" + response.code();
            }

            Log.e(MainActivity.class.getSimpleName(), unsuccessfulMessage);

            // In case the response wasn't successful, add a null object
            // to the reviews info of the movie
            movie.setMovieReviews(null);
        }
        return resource;

    }

    public static MovieTrailers getMovieTrailers(DiscoveredMovies.Movie movie, final APIInterface apiInterface) {

        // From the trailers endpoint of the API,
        // Get the trailers info of every movie in the list.

        Call<MovieTrailers> callMovieTrailers = apiInterface.doGetMovieTrailers(getProperUrl(MOVIE_TRAILER, movie.getId() , ""));
        Response<MovieTrailers> response = null;

        // Make the API call to the review endpoint
        // Why the API call is not happening ?
        // Log.e("zeu link",getProperUrl(MOVIE_TRAILER, movie.getId() , ""));

        try {
            response = callMovieTrailers.execute();
        } catch (Exception e) {
            Log.e(TAG, "A problem occured with the API call on the Trailers endpoint " + e.getMessage());
        }

        MovieTrailers resource = new MovieTrailers();
        String unsuccessfulMessage = "API Response unsuccessful on the Trailers endpoint";

        // In case the call was successful
        // use the data from the server's response
        if (response != null && response.isSuccessful()) {
            resource = response.body();

        } else {

            // Only if the response is not null,
            // add its code to the unsuccessfulMessage variable
            if (response != null) {
                unsuccessfulMessage += ", code :" + response.code();
            }

            Log.e(MainActivity.class.getSimpleName(), unsuccessfulMessage);

            // In case the response wasn't successful, add a null object
            // to the trailers info of the movie.

            // The line bellow has no effect because the parameter
            // doesn't carry changes outside of the method.

            /**We set the trailer to null in case of
             * a failure.
             *
             * Now, let find out manually if those movies
             * have trailer or not*/
            movie.setMovieTrailers(null);

            // if we can al

            // Shouldn't this be somewhere else ?
            // I don't know where to put it...
            //
            //
        }
        return resource;

    }

    /**
     * This method produce the proper string url to be used by the apiinterface.
     *
     * @param dataType will determine the endpoint that will should query
     * @param movieId  Will only be used when we need information about one specific
     *                 movie.Determine which movie we're requesting information for.
     * @param sortBy   Will only be used when we need to get a collection of movies.
     *                 In this case, the movies will be based on the
     *                 value of "sortBy"
     */

    private static String getProperUrl(String dataType, int movieId, String sortBy) {

        // The following will be used to query the
        // discovered movies endpoint.
        String DiscoveredMovieUrlExtra = "&language=en-US&sort_by=" + sortBy
                + "&include_adult=true&include_video=false&page=1";

        String DiscoveredMovieUrlExtension = "";

        // The following will be used to
        // get information about one movie. An extension will be
        // added to the url to determine the endpoint we query information from.
        // We will query information from one of the following endpoint :
        // credit, reviews or video
        String urlExtensionWithId = "movie/";
        urlExtensionWithId += movieId;

        /* "https://api.themoviedb.org/3/movie/{movieId}" or "https://api.themoviedb.org/3/"
         *  will be used as the principal url.
         *  We will add to it one of the
         *  extensions present in the switch statement.
         *  For example, if the user want to query the movie credit
         *  endpoint, we will add the following to the first base url : "/credits"
         *
         *  It will help us to query the right endpoint and
         *  get the data we are looking for. */

        switch (dataType) {
            // Add the extension to get the data related to the credit of a given movie.
            // By credit, we mean the people who participated into the construction of the movie.
            case MOVIE_CREDIT:
                urlExtensionWithId += "/" + MOVIE_CREDIT;
                break;

            /*case MOVIE_DETAILS: urlExtensionWithId += "&" + "language" + "=" + "en-US";
            break;*/

            // Add the extension to get the data related to the trailer of a given movie.
            case MOVIE_TRAILER:
                urlExtensionWithId += "/" + MOVIE_TRAILER;
                break;

            // Add the extension to get the reviews related to a given movie.
            case MOVIE_REVIEWS:
                urlExtensionWithId += "/" + MOVIE_REVIEWS;
                break;

            case DISCOVER_MOVIE:
                DiscoveredMovieUrlExtension = "discover/movie?api_key=" + API_KEY + DiscoveredMovieUrlExtra;
                return DiscoveredMovieUrlExtension;
        }

        // Finally we'll add the api key to the url extension
        // that uses the id.
        urlExtensionWithId += "?" + "api_key" + "=" + API_KEY;

        return urlExtensionWithId;
    }


    // Fetch complementary information (credit, detail,
    // trailers and reviews) for each movie and
    // add them.

    private static List<DiscoveredMovies.Movie> completeMoviesDetails(DiscoveredMovies discoveredMovies) {

        // In case the movie list is empty,
        // display a warning and return an empty list
        if (discoveredMovies.movieList.isEmpty()) {
            Log.w(TAG, "The API returned an empty movie list");
            return new ArrayList<DiscoveredMovies.Movie>();
        }

        // Extract the list of movies from the
        // discoveredMovies object.
        List<DiscoveredMovies.Movie> movieList = discoveredMovies.movieList;

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        // Variables to store all the extra
        // information about the movie.
        // Those informations will be fetched from
        // different endpoints of the API.
      //  MovieCredit credit = new MovieCredit();
        // MovieTrailers trailers = new MovieTrailers();
        //MovieReviews reviews = new MovieReviews();
        MovieDetail details = new MovieDetail();

        // For each movie, add its extra information.
        for (int i = 0; i < movieList.size(); i++) {

            // Fetch the credit information of the current movie
            //trailers = getMovieTrailers(movieList.get(i), mApiInterface);
            //credit = getMovieCredit(movieList.get(i), mApiInterface);
           // reviews = getMovieReviews(movieList.get(i), mApiInterface);
            details = getMovieDetails(movieList.get(i), mApiInterface);

            // Set the new credit information of the current movie
            //movieList.get(i).setMovieCredit(credit);
           // movieList.get(i).setMovieTrailers(trailers);
          //  movieList.get(i).setMovieReviews(reviews);
            movieList.get(i).setMovieDetails(details);

            //

            /*
            getMovieDetail(resource.movieList,apiInterface);
            getMoviesReviews(resource.movieList,apiInterface);*/

        }

        return movieList;
    }

}
