package com.example.android.popularmovies2.Data;

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

    /** The Tag to be used in log messages*/
    String TAG = getClass().getSimpleName();

    /**
     *  This will store the api key necessary
     *  to query any endpoint.
     *
     *  */
    final String API_KEY = "cd401ba98e50ce8bf913cdce912aa430";

    /**
     *
     *  The following constants will be use to form
     *  the right URL based on the endpoints we'd like to
     *  query. As an example, to query
     *  the "movie credit" endpoint, we will
     *  build the URL using "MOVIE_CREDIT"
     *
     *  */
    final String MOVIE_CREDIT = "credits";
    final String MOVIE_DETAILS = "details";
    final String MOVIE_REVIEWS = "reviews";
    final String MOVIE_TRAILER = "videos";
    final String DISCOVER_MOVIE = "discover";

    /**
     * Will be used
     * to fetch data from
     * the server. It contains
     * the base url.
     *
     * */
    APIInterface mApiInterface;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    /** Continu*/
    public List<DiscoveredMovies.AMovie> fetchMoviesData(final APIInterface apiInterface, final String sortingKey){

        // Create the variable "callPopularMovies" that will help us make
        // an API call on the "discover" endpoint.
        Call<DiscoveredMovies> callPopularMovies = apiInterface.doGetDiscoveredMovies(
                getProperUrl(DISCOVER_MOVIE,0,sortingKey));

        // Create a response variable that will
        // receive the response from the API call.
        Response<DiscoveredMovies> response = null;

        // Make the API call, try to talk to the server
        // and get popular movies out of the call.
        try{
             response = callPopularMovies.execute();
        }
        catch (Exception e) {
            Log.e(TAG,"A problem occured with the API call " + e.getMessage());
        }

        // Create url --> make http --> get the json onto an other method
        DiscoveredMovies resource = new DiscoveredMovies();
        // Check if the call was successful and
        // use the response.
        if (response!= null && response.isSuccessful()) {

            // from the server response,
            // get the discovered movie and return.
            resource = response.body();

        }
        else {
            Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());
        }

        return completeMoviesData(resource);

    }

    private MovieCredit getMoviesCredit(DiscoveredMovies.AMovie movie, final APIInterface apiInterface) {

        // From the credit endpoint of the API,
        // Get the credit info of every movie in the list.

        Call<MovieCredit> callMovieCredit = apiInterface.doGetMovieCredit(getProperUrl(MOVIE_CREDIT,movie.getId(),""));
        Response<MovieCredit> response = null;

        // Make the API call to the credit endpoint
        try{
            response = callMovieCredit.execute();
        }
        catch (Exception e) {
            Log.e(TAG,"A problem occured with the API call " + e.getMessage());
        }

            // In case the call was successful
            // use the data from the server's response
            if(response != null && response.isSuccessful()){
                MovieCredit resource = response.body();

                // Once we get the movie credit,
                // add it to the mMoviesCredit list.
                // This will contain the credit infos for all the movies previously fetched
               // movie.setMovieCredit(resource);
                return resource;
            }

            else {
                Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());

                // In case the response wasn't successful, add a null object
                // to the credit info of the movie
                movie.setMovieCredit(null);
            }
              return null;
    }


    /*public void getMoviesDetail(List<DiscoveredMovies.AMovie> movieList, final APIInterface apiInterface){

        for (DiscoveredMovies.AMovie movie : movieList) {

            Call<MovieDetail> callMovieDetail = apiInterface.doGetMovieDetail(getProperUrl(MOVIE_DETAILS,movie.getId(),""));
            callMovieDetail.enqueue(new Callback<MovieDetail>() {

                @Override
                public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {

                    if(response.isSuccessful()){
                        MovieDetail resource = response.body();

                        // Once we get the movie detail,
                        // add it to the mMoviesDetail list.
                        // This will contain the detail infos for all the movies previously fetched.
                        mMoviesDetail.add(resource);
                        DetailActivity.mMoviesDetails.add(resource);
                    }

                    else {
                        Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());

                        // In case the response wasn't successful, add a null object
                        // to the reviews list. This will ensure that
                        // all the list contains the same number of elements
                        // and every position across all the lists
                        // reference information about the same movie.
                        DetailActivity.mMoviesDetails.add(null);
                    }
                    // Should we close the call at the end ?
                }

                @Override
                public void onFailure(Call<MovieDetail> call, Throwable t) {
                    // Will print the error in case the network operation fails
                    Log.e(MainActivity.class.getSimpleName(),t.toString());
                    call.cancel();
                }
            });

        }
    }

    public void getMoviesReviews(List<DiscoveredMovies.AMovie> movieList, final APIInterface apiInterface){
        for (DiscoveredMovies.AMovie movie : movieList) {

            Call<MovieReviews> callMovieReviews = apiInterface.doGetMovieReviews(getProperUrl(MOVIE_REVIEWS,movie.getId(),""));
            callMovieReviews.enqueue(new Callback<MovieReviews>() {

                @Override
                public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                    if(response.isSuccessful()){
                        MovieReviews resource = response.body();

                        // Once we get the movie review,
                        // add it to the mMoviesReviews list.
                        // This will contain the review infos for all the movies previously fetched.
                        mMoviesReviews.add(resource);
                        DetailActivity.mMoviesReviews.add(resource);
                    }

                    else {
                        Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());

                        // In case the response wasn't successful, add a null object
                        // to the reviews list. This will ensure that
                        // all the list contains the same number of elements
                        // and every position across all the lists
                        // reference information about the same movie.
                        DetailActivity.mMoviesReviews.add(null);
                    }
                    // Should we close the call at the end ?
                }

                @Override
                public void onFailure(Call<MovieReviews> call, Throwable t) {
                    // Will print the error in case the network operation fails
                    Log.e(MainActivity.class.getSimpleName(),t.toString());
                    call.cancel();
                }
            });

        }
    }

    *//** Continue here, ladies and gentleman*/
    /*
    public void getMoviesTrailersAndPopulateAdapter(List<DiscoveredMovies.AMovie> movieList, final APIInterface apiInterface) {

        Call<MovieTrailers> callMovieTrailers; //= apiInterface.doGetMovieTrailers(getProperUrl(MOVIE_TRAILER,movie.getId(),""));

        for (DiscoveredMovies.AMovie movie : movieList) {


            callMovieTrailers = apiInterface.doGetMovieTrailers(getProperUrl(MOVIE_TRAILER,movie.getId(),""));
            callMovieTrailers.enqueue(new Callback<MovieTrailers>() {

                @Override
                public void onResponse(Call<MovieTrailers> call, Response<MovieTrailers> response) {
                    // Dois je essayer de faire un baye ici
                    //

                    if(response.isSuccessful()) {

                        MovieTrailers resource = response.body();
                        // Once we get the movie review,
                        // add it to the mMoviesReviews list.
                        // This will contain the review infos for all the movies previously fetched.

                        //  mMoviesTrailers.add(resource);
                        DetailActivity.mMoviesTrailers.add(resource);

                        Log.e("Ba banini","ba de risque");

                        *//*if(resource.trailerList.isEmpty()) {
                           // Toast.makeText(this,"eza videu",Toast.LENGTH_LONG).show();
                            //Log.e("Ba banini",resource.trailerList.get(0).getTrailerUrl());

                            // Prendre l'id d'un des 4 films et essayer de trouver manuellment ses
                            // trailers puis
                        }
                        else {

                            //Log.e("Baa vide","trailer");

                        }*//*
                    }

                    else {
                        Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());
                        // In case the response wasn't successful, add a null object
                        // to the trailer list. This will ensure that
                        // all the list contains the same number of elements
                        // and every position across all the lists
                        // reference information about the same movie.
                        DetailActivity.mMoviesTrailers.add(null);
                    }
                    // Should we close the call at the end ?
                }

                @Override
                public void onFailure(Call<MovieTrailers> call, Throwable t) {
                    // Will print the error in case the network operation fails
                    Log.e(MainActivity.class.getSimpleName(),t.toString());
                    call.cancel();
                }
            });

        }

        // Get the movie list gotten from the API
        // and set it to the adapter as the movie data.
        mMovieAdapter.setMovieData(movieList);
        // Set the adapter onto its RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);
    }
*/
    // Pour chaque film : prendre ses details, reviews & credit
    //

    /** This method produce the proper string url to be used by the apiinterface.
     *
     * @param dataType will determine the endpoint that will should query
     *
     * @param movieId Will only be used when we need information about one specific
     *                movie.Determine which movie we're requesting information for.
     *
     * @param sortBy  Will only be used when we need to get a collection of movies.
     *                In this case, the movies will be based on the
     *                value of "sortBy"
     * */

    public String getProperUrl(String dataType, int movieId, String sortBy) {

        // The following will be used to query the
        // discovered movies endpoint.
        String DiscoveredMovieUrlExtra = "&language=en-US&sort_by="+ sortBy
                +"&include_adult=true&include_video=false&page=1";

        String DiscoveredMovieUrlExtension = "" ;

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

        switch (dataType){
            // Add the extension to get the data related to the credit of a given movie.
            // By credit, we mean the people who participated into the construction of the movie.
            case MOVIE_CREDIT: urlExtensionWithId += "/"+ MOVIE_CREDIT ;
                break;

            /*case MOVIE_DETAILS: urlExtensionWithId += "&" + "language" + "=" + "en-US";
            break;*/

            // Add the extension to get the reviews related to a given movie.
            case MOVIE_REVIEWS: urlExtensionWithId += "/" + MOVIE_REVIEWS;
                break;

            // Add the extension to get the data related to the trailer of a given movie.
            case MOVIE_TRAILER: urlExtensionWithId += "/" + MOVIE_TRAILER;
                break;

            case DISCOVER_MOVIE: DiscoveredMovieUrlExtension = "discover/movie?api_key=" + API_KEY + DiscoveredMovieUrlExtra;
                return DiscoveredMovieUrlExtension;
        }

        // Finally we'll add the api key to the url extension
        // that uses the id.
        urlExtensionWithId += "?" + "api_key"  + "=" + API_KEY;

        return urlExtensionWithId;
    }


    // Fetch complementary information (credit, detail,
    // trailers and reviews) for each movie and
    // add them.

    public List<DiscoveredMovies.AMovie> completeMoviesData(DiscoveredMovies discoveredMovies) {

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        // Variables to store all the extra
        // information about the movie.
        // Those informations will be fetched from
        // different endpoints of the API.
        MovieCredit credit = new MovieCredit();
        MovieTrailers trailers = new MovieTrailers();
        MovieReviews reviews = new MovieReviews();
        MovieDetail details = new MovieDetail();

        // Extract the list of movies from the
        // discoveredMovies object.
        List<DiscoveredMovies.AMovie> movieList = discoveredMovies.movieList;

        // For each movie, add its extra information.
        for (int i=0; i < movieList.size(); i++) {

            // Fetch the credit information of the current movie
            credit = getMoviesCredit(movieList.get(i),mApiInterface);

            // Set the new credit information of the current movie
            movieList.get(i).setMovieCredit(credit);

            /*
            getMoviesDetail(resource.movieList,apiInterface);

            getMoviesReviews(resource.movieList,apiInterface);*/

            // This method will help us do 3 things:
            // Retrieve the trailer data from the api,
            // set the movie data onto the adapter
            // and attach the adapter to the Recycler view
            //getMoviesTrailersAndPopulateAdapter(resource.movieList,apiInterface);

        }

        return movieList;
    }

}
