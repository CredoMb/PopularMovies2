package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;
import com.example.android.popularmovies2.APIResponsePOJO.MovieTrailers;
import com.example.android.popularmovies2.Database.FavoriteEntry;
import com.example.android.popularmovies2.NetworkOperations.APIClient;
import com.example.android.popularmovies2.NetworkOperations.APIInterface;
import com.example.android.popularmovies2.NetworkOperations.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<DiscoveredMovies.Movie>>{

    /**
     * The Ids for the Loader to be used in
     * this activity.
     */
    private static final int MOVIE_LOADER_ID = 0;

    /**
     * Will be used as the base url for the API
     */
    private String MOVIE_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie?";

    /**
     * The key used to save the movie list as
     * a bundle.
     */
    public String MOVIE_LIST = "movie_list";

    /**
     * Will be used as values for the mSort variable.
     * This will determine the category of movies
     * to display inside the Main Activity
     */
    private String BY_POPULARITY = "popularity.desc";
    private String BY_RATINGS = "vote_average.desc";


    /**
     * The Recycler and its Adapter
     */
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    /** Will be used to access each endpoint of the
     *  API*/
    APIInterface mApiInterface;

    /**
     * The variable that should contain the movie list
     */

    List<DiscoveredMovies.Movie> mMovieList;

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
     * empty state for a bad internet connection.
     */
    private RelativeLayout emptyStateRl;

    public static List<FavoriteEntry> favoriteMovies = new ArrayList<FavoriteEntry>();

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

        // Make the progress spinner invisible
        mProgressSpinner.setVisibility(View.INVISIBLE);

        // Create a new Grid Layout Manager
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);

        // Define the properties of the Layout Manager
        int spanCount = 3; // 3 columns
        boolean includeEdge = false;

        // Attach the layout manager to the Recycler View
        mRecyclerView.setLayoutManager(layoutManager);

        // The MovieAdapter is responsible for linking our movie data with the Recycler who
        // will end up displaying the data.
        mMovieAdapter = new MovieAdapter(this, new ArrayList<DiscoveredMovies.Movie>(), this);
        mRecyclerView.setAdapter(mMovieAdapter);

        // Set the default sorting to be by popularity.
        // It means that the app will fetch popular movies
        // by default.
        mSortBy = BY_POPULARITY;

        /**
         *
         * Create a method to manage the preferences of the user.
         * If the user clicks on "popular or trendy or sum like that"
         *
         * */

        // Initialize the API interface. This will be used to fetch
        // data from different endpoint of the API.
        mApiInterface = APIClient.getClient().create(APIInterface.class);

        // Start the Loader only if there's no element
        // inside our movie list. Otherwise,
        // remove the spinner from the screen.
        if(mMovieList == null || mMovieList.isEmpty()) {
            startLoaderOrEmptyState(MOVIE_LOADER_ID);
        }
        else {
            // Remove the progress spinner from the screen.
            // We know that we have data to display,
            // we will not need the spinner.
            mProgressSpinner.setVisibility(View.GONE);

            // Attach the movieList to the adapter
            mMovieAdapter.setMovieData(mMovieList);
        }
    }

    @Override
    public Loader<List<DiscoveredMovies.Movie>> onCreateLoader(int i, Bundle bundle) {

        // Set the visibility of the spinner.
        mProgressSpinner.setVisibility(View.VISIBLE);

        return new AsyncTaskLoader<List<DiscoveredMovies.Movie>>(this) {
            @Override
            public List<DiscoveredMovies.Movie> loadInBackground() {

                if (mApiInterface == null) {
                    return new ArrayList<DiscoveredMovies.Movie>();
                }
                // Make the network request and
                // return a list of movie.
                // Each movie on the list only contains the
                // Image and details of the movie.

                // The reviews, credit and trailer will be fetch
                // in the Detail Activity.
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

        // Remove the spinner from the screen.
        mProgressSpinner.setVisibility(View.GONE);

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
        if (!isNetworkConnected()) {
            emptyStateRl.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Execute certain task based on the internet connection status.
     * If the connection is on, initiate the loader
     * other wise, display the empty state view
     */
    private void startLoaderOrEmptyState(int loaderId) {

        // Check the status of the network, then either launch the Loader or
        // display the Empty State

        if (isNetworkConnected()) {
            getLoaderManager().initLoader(loaderId, null, MainActivity.this).forceLoad();
        } else {
            emptyStateRl.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Method to Check the Network connection and return true or false
     * based on the connection state. If the device is connected, the method
     * returns true, else it returns false.
     */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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

    @Override
    public void onClick(int position) {

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_POSITION, position);

            startActivity(intent);


    }

}









































