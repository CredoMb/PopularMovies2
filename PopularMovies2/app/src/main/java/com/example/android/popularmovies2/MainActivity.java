package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;
import com.example.android.popularmovies2.NetworkOperations.APIClient;
import com.example.android.popularmovies2.NetworkOperations.APIInterface;
import com.example.android.popularmovies2.NetworkOperations.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<DiscoveredMovies.Movie>>{

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
     * Will be used as values for the mSort variable.
     * This will determine the order with which the movies
     * are displayed inside the Main Activity
     */
    private String BY_POPULARITY = "popularity.desc";
    private String BY_RATINGS = "vote_average.desc";


    /**
     * The Recycler and its Adapter
     */
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;


    APIInterface mApiInterface;

    /**
     * The variable that should contain the movie list
     */

    List<DiscoveredMovies.Movie> mMovieList;

    /**
     * The list of credit for all the movies fetched from
     * the API.
     * */
    List<MovieCredit> mMoviesCredit;

    /**
     * The list of detail for all the movies fetched from
     * the API.
     * */
    List<MovieDetail> mMoviesDetail;

    /**
     * The list of review for all the movies fetched from
     * the API.
     * */
    List<MovieReviews> mMoviesReviews;

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

        mMovieAdapter = new MovieAdapter(this, new ArrayList<DiscoveredMovies.Movie>(), this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // Set the default sorting to be by popularity
        mSortBy = BY_POPULARITY;

        // Initialize the variable that should store the credit of
        // the movie. This variable will be changed inside the getCredit
        mMoviesCredit = new ArrayList<MovieCredit>();
        mMoviesDetail =  new ArrayList<MovieDetail>();
        mMoviesReviews = new ArrayList<MovieReviews>();

        // Set the movie list as the data of the adapter
        // mMovieAdapter.setMovieData(mMovieList);

        // Create the thing to set fetch the movie
        // This will query the endpoint we need to return the list of movies

        /**
         *
         * Create a method to manage the preferences of the user.
         * If the user clicks on "popular or trendy or sum like that"
         *
         * */
        mApiInterface = APIClient.getClient().create(APIInterface.class);


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

        // Start the loader to display the movies that we fetched
        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, MainActivity.this).forceLoad();
    }

    @Override
    public Loader<List<DiscoveredMovies.Movie>> onCreateLoader(int i, Bundle bundle) {

        return new AsyncTaskLoader<List<DiscoveredMovies.Movie>>(this) {
            @Override
            public List<DiscoveredMovies.Movie> loadInBackground() {

                if (mApiInterface == null) {
                    return new ArrayList<DiscoveredMovies.Movie>();
                }
                // Make the network request and
                // return a list of movie
                List<DiscoveredMovies.Movie> movieList
                        = QueryUtils.fetchMoviesData(mApiInterface, mSortBy);

                // The list should already be ready
                // mMovieList is ready but misses certain things

                return movieList;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<List<DiscoveredMovies.Movie>> loader, List<DiscoveredMovies.Movie> movies) {

        // Clear the adapter by setting an empty ArrayList
        mMovieAdapter.setMovieData(new ArrayList<DiscoveredMovies.Movie>());

        if (movies != null && !movies.isEmpty()) {

            // Update the movie list of DetailActivity
            DetailActivity.mMovieList = movies;

            // Set data onto the adapter
            mMovieList = movies;
            mMovieAdapter.setMovieData(mMovieList);
        }
           // else --> make the relative view appear
          // that's it !

    }

    @Override
    public void onLoaderReset(Loader<List<DiscoveredMovies.Movie>> loader) {

        // Create a new empty Movie list for the Adapter
        mMovieList = new ArrayList<DiscoveredMovies.Movie>();
        mMovieAdapter = new MovieAdapter(this, mMovieList, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // If there's no internet connection display the emptystate view
        /*if (!isNetworkConnected()) {
            emptyStateRl.setVisibility(View.VISIBLE);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/activity_main_menu.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }


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


                // This will call the appropriate endpoint and
                // return a list of movies
                // callAppropriateEndPoint();

                // We would need to recall the thing
                // enqueue it and do whatever

                // Destroy the previous loader and start a new one

                return true;

            case R.id.action_ratings:

                mSortBy = BY_RATINGS;
                // mMovieAdapter.setMovieData(new ArrayList<DiscoveredMovies.Movie>());

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

    // Here was the fetchMoviesData

    /**
     * The next 3 methods will help us get extra information
     * about all the movies we fetched from the API.
     * Those informations are: the credit (getMoviesCredit),
     * the detail (getMoviesDetail) and reviews (getMoviesReviews)
     *
     * Each of these 3 block of infos is located in a different endpoint.
     *
     * @param mMovieList holds the list of movies fetched from the API.
     *                  Each movie possess a couple of basic information
     *
     * @param apiInterface is an entity of @APIInterface. It will help us to perform
     *                     the API call to the proper endpoint of the API.
     * */

    /** Here where the 4 getters for the movie endpoint*/


    @Override
    public void onClick(int position) {

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_POSITION, position);

            startActivity(intent);


    }
}
