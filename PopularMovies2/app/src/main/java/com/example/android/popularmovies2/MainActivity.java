package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.Data.APIClient;
import com.example.android.popularmovies2.Data.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler {

    /**
     * The Ids for the 2 Loaders to be used in
     * this activity.
     */
    private static final int MOVIE_LOADER_ID = 0;

    /**
     * Will be used as the base url and parameter will be
     */
    private String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie?";

    /**
     * The key used to save the movie list as
     * a bundle.
     */
    public String MOVIE_LIST = "movie_list";

    /**
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
    final String MOVIE_VIDEO = "videos";
    final String DISCOVER_MOVIE = "discover";

    /**
     * Will be used as values for the mSort variable.
     * This will determine the order with which the movies
     * are displayed inside the Main Activity
     */
    private String BY_POPULARITY = "popularity.desc";
    private String BY_RATINGS = "vote_average.desc";


    /** This will store the api key necessary
     *  to query any endpoint. */
    final String API_KEY = "cd401ba98e50ce8bf913cdce912aa430";

    /**
     * The Recycler and its Adapter
     */
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;


    APIInterface mApiInterface;

    /**
     * The variable that should contain the movie list
     */

    List<DiscoveredMovies.AMovie> movieList;

    /**
     * The progress Spinner
     */
    private ProgressBar mProgressSpinner;

    /**
     * Will store the sorting option.
     * The popularity is the default
     * way of displaying the movies in the main
     * Activity.
     */
    private String mSortBy;

    /**
     * The group view that will contain the
     * empty state for a bad internet connection
     */

    private RelativeLayout emptyStateRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verify if the movie list was saved as a bundle
        // and retrieve the data.
        if (savedInstanceState != null) {

            if (savedInstanceState.containsKey(MOVIE_LIST)) {
               // mMovieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST);

            }
        }

        // Store the entire emptyState view inside a variable
        emptyStateRl = (RelativeLayout) findViewById(R.id.empty_group_view);

        // Make the empty state invisible by default.
        // It will only become visible
        // if the API call is not successful.
        emptyStateRl.setVisibility(View.INVISIBLE);

        // Find the recycler view of the Main Activity layout
        // and store it onto a variable
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_activity);

        // The progress spinner to use for a good
        mProgressSpinner = (ProgressBar) findViewById(R.id.main_loading_spinner);

        //
        mProgressSpinner.setVisibility(View.INVISIBLE);

        // Create a new Grid Layout Manager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);

        // Define the properties of the Layout Manager
        int spanCount = 3; // 3 columns
        boolean includeEdge = false;

        // Attach the layout manager to the Recycler View
        mRecyclerView.setLayoutManager(layoutManager);

        // The MovieAdapter is responsible for linking our movie data with the Recycler that
        // will end up displaying the data.

        mMovieAdapter = new MovieAdapter(this, new ArrayList<DiscoveredMovies.AMovie>(), this);

        // Set the default sorting to be by popularity
        mSortBy = BY_POPULARITY;

        // Set the movie list as the data of the adapter
       // mMovieAdapter.setMovieData(mMovieList);

        // Create the thing to set fetch the movie
        // This will query the endpoint we need to return the list of movies

        /**Create a method to manage the preferences of the user.
         * If the user clicks on "popular or trendy or sum like that"
         * */
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        fetchDiscoveredMovies(mApiInterface, BY_POPULARITY);

       /* Call<DiscoveredMovies> callPopularMovies = mApiInterface.doGetDiscoveredMovies(getProperUrl(DISCOVER_MOVIE,0,BY_POPULARITY));
        callPopularMovies.enqueue(new Callback<DiscoveredMovies>() {
            @Override
            public void onResponse(Call<DiscoveredMovies> call, Response<DiscoveredMovies> response) {

                if (response.isSuccessful()) {

                    DiscoveredMovies resource = response.body();
                    *//*Call<MovieCredit> callMovieCredit;
                    Call<MovieDetail> callMovieDetail;*//*

                    mMovieAdapter.setMovieData(resource.movieList);
                    // Set the adapter onto its RecyclerView
                    mRecyclerView.setAdapter(mMovieAdapter);

                }
                else {
                    Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());
                }

                // Log.e("Movie title",movieList.get(0).getTitle());
            }

            @Override
            public void onFailure(Call<DiscoveredMovies> call, Throwable t) {
                // Will print the error in case the network operation fails
                Log.e(MainActivity.class.getSimpleName(),t.toString());
                call.cancel();
            }
        });*/

        /**
        // Start the Loader only if there's no element
        // inside our movie list. Otherwise,
        // remove the spinner from the screen.
        if(mMovieList.isEmpty()) {
            startLoaderOrEmptyState(MOVIE_LOADER_ID);
        }
        else {
            // Remove the progress from the screen.
            // As we already have data to display,
            // we will not need it.
            mProgressSpinner.setVisibility(View.GONE);
        } */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/activity_main_menu.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public void onClick(int postion) {

    }



    // If the user clicks on the menu to select his preferences
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the "Popularity" menu option
            case R.id.action_popularity:
                // Set the "mSortBy" parameter to "popularity.desc" on the url
                // used to query the API.
                // This way, the movies will be displayed
                // according to the sort preference.
                mSortBy = BY_POPULARITY;

                // Attach an empty list to the adapter.
                // This will help us to fill back with fresh data
                mMovieAdapter.setMovieData(new ArrayList<DiscoveredMovies.AMovie>());

                fetchDiscoveredMovies(mApiInterface,mSortBy);
                // This will call the appropriate endpoint and
                // return a list of movies
                // callAppropriateEndPoint();

                // We would need to recall the thing
                // enqueue it and do whatever

                // Destroy the previous loader and start a new one

                return true;

            case R.id.action_ratings:

                mSortBy = BY_RATINGS;
                mMovieAdapter.setMovieData(new ArrayList<DiscoveredMovies.AMovie>());
                fetchDiscoveredMovies(mApiInterface,mSortBy);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** This method will be used to fetch data from the "discover" endpoint.
     *
     * @param apiInterface is the interface used to query the different endpoints
     *                     of the database. In this case, it will be used for the
     *                     "discover" endpoint
     *
     * @param sortingKey Will determine weither the data should be queried based on the
     *                   their popularity or their ratings.
     * */

    public void fetchDiscoveredMovies(APIInterface apiInterface, String sortingKey){

      //  apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<DiscoveredMovies> callPopularMovies = apiInterface.doGetDiscoveredMovies(
                getProperUrl(DISCOVER_MOVIE,0,sortingKey));

        callPopularMovies.enqueue(new Callback<DiscoveredMovies>() {

            @Override
            public void onResponse(Call<DiscoveredMovies> call, Response<DiscoveredMovies> response) {

                if (response.isSuccessful()) {
                    DiscoveredMovies resource = response.body();

                    /*Call<MovieCredit> callMovieCredit;
                    Call<MovieDetail> callMovieDetail;*/
                    Log.e("eza na sircé","iyo sircé");

                    mMovieAdapter.setMovieData(resource.movieList);
                    // Set the adapter onto its RecyclerView
                    mRecyclerView.setAdapter(mMovieAdapter);

                }
                else {
                    Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<DiscoveredMovies> call, Throwable t) {
                // Will print the error in case the network operation fails
                Log.e(MainActivity.class.getSimpleName(),t.toString());
                call.cancel();
            }
        });

    }


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
        urlExtensionWithId += movieId ;


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
            case MOVIE_VIDEO: urlExtensionWithId += "/" + MOVIE_VIDEO;
                break;

            case DISCOVER_MOVIE: DiscoveredMovieUrlExtension = "discover/movie?api_key=" + API_KEY + DiscoveredMovieUrlExtra;
                return DiscoveredMovieUrlExtension;
        }

        // Finally we'll add the api key to the url extension
        // that uses the id.
        urlExtensionWithId += "?" + "api_key"  + "=" + API_KEY;

        return urlExtensionWithId;
    }
}
