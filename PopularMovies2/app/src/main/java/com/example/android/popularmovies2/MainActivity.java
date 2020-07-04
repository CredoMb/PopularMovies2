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
import com.example.android.popularmovies2.Data.*;
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


    APIInterface apiInterface;

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

        // Set the movie list as the data of the adapter
       // mMovieAdapter.setMovieData(mMovieList);

        // Create the thing to set fetch the movie
        // This will query the endpoint we need to return the list of movies

        /**Create a method to manage the preferences of the user.
         * If the user clicks on "popular or trendy or sum like that"
         *
         * */
        apiInterface = APIClient.getClient().create(APIInterface.class);

        //fetchDiscoveredMovies(APIInterface apiInterface, String sortingKey);

        Call<DiscoveredMovies> callPopularMovies = apiInterface.doGetPopularMovies("discover/movie?api_key=cd401ba98e50ce8bf913cdce912aa430&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=1");

        //getProperUrl(DISCOVER_MOVIE,0,BY_POPULARITY)

        callPopularMovies.enqueue(new Callback<DiscoveredMovies>() {
            @Override
            public void onResponse(Call<DiscoveredMovies> call, Response<DiscoveredMovies> response) {

                Log.e("Surcessful","Esimbi");

                if (response.isSuccessful()) {

                    DiscoveredMovies resource = response.body();
                    Call<MovieCredit> callMovieCredit;
                    Call<MovieDetail> callMovieDetail;

                    mMovieAdapter.setMovieData(resource.movieList);
                    // Set the adapter onto its RecyclerView
                    mRecyclerView.setAdapter(mMovieAdapter);

                }
                else {
                    Log.e(MainActivity.class.getSimpleName(),"API Response unsuccessful, code : "+ response.code());
                }

                // Make sure the response from the api is not null.
                // Then, either transfert the result to the list or
                // display a message to the log.
                /*if(resource!=null){
                    //movieList = resource.movieList ;

                    movieList.addAll(resource.movieList);
                }*/

                // Log.e("Movie title",movieList.get(0).getTitle());
            }

            @Override
            public void onFailure(Call<DiscoveredMovies> call, Throwable t) {
                // Will print the error in case the network operation fails
                Log.e(MainActivity.class.getSimpleName(),t.toString());
                call.cancel();
            }
        });

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
                mMovieAdapter.setMovieData(new ArrayList<DiscoveredMovies.AMovie>());

                // This will call the appropriate endpoint and
                // return a list of movies
               // callAppropriateEndPoint();

                // We would need to recall the thing
                // enqueue it and do whatever

                // Destroy the previous loader and start a new one

                return true;
            case 2:
        }
        return false;
    }

    /*public void fetchDiscoveredMovies(APIInterface apiInterface, String sortingKey){

        apiInterface.sortBy = sortingKey;

        // We need to build the url externally to the
        // api interface, bitch !

        Call<DiscoveredMovies> callPopularMovies = apiInterface.doGetPopularMovies();

        callPopularMovies.enqueue(new Callback<DiscoveredMovies>() {

            @Override
            public void onResponse(Call<DiscoveredMovies> call, Response<DiscoveredMovies> response) {

                if (response.isSuccessful()) {

                    DiscoveredMovies resource = response.body();
                    Call<MovieCredit> callMovieCredit;
                    Call<MovieDetail> callMovieDetail;

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
    }*/
    public String getProperUrl(String dataType, int movieId, String sortBy) {

        // The following will be used to query the
        // discovered movies endpoint.
        String DiscoveredMovieUrlExtra = "&language=en-US&sort_by="+ sortBy
                +"&include_adult=true&include_video=false&page=1";

        String DiscoveredMovieUrlExtension = "" ;

        // The following will be used to
        // get information about one movie. An extension will be
        // added to the url to determine the endpoint we query information from.
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
            case MOVIE_CREDIT: urlExtensionWithId += "/"+ MOVIE_CREDIT ;
                break;

            /*case MOVIE_DETAILS: urlExtensionWithId += "&" + "language" + "=" + "en-US";
            break;*/

            case MOVIE_REVIEWS: urlExtensionWithId += "/" + MOVIE_REVIEWS;
                break;

            case MOVIE_VIDEO: urlExtensionWithId += "/" + MOVIE_VIDEO;
                break;

            case DISCOVER_MOVIE: DiscoveredMovieUrlExtension = "discover/movie?api_key=" + API_KEY + DiscoveredMovieUrlExtra;
                Log.e("Leu lien",DiscoveredMovieUrlExtension);
                return DiscoveredMovieUrlExtension;
        }

        /* discover/movie?api_key=cd401ba98e50ce8bf913cdce912aa430&language=en-US&sort_by=popularity.desc&include_adult=true&include_video=false&page=1*/

        // Finally we'll add the api key to our url extension
        // which will be "...?api_key={the key}"
        urlExtensionWithId += "?" + "api_key"  + "=" + API_KEY;

        // https://api.themoviedb.org/3/movie/419704?api_key=cd401ba98e50ce8bf913cdce912aa430&language=en-US

        Log.e("The link extension",urlExtensionWithId);
        return urlExtensionWithId;
    }
}
