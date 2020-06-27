package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.Data.APIInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler{

    /**
     * The Ids for the 2 Loaders to be used in
     * this activity.
     */
    private static final int MOVIE_LOADER_ID = 0;

    /**
     * Will be used as values for the mSort variable.
     * This will determine the order with which the movies
     * are displayed inside the Main Activity
     */
    private String BY_POPULARITY = "popularity.desc";
    private String BY_RATINGS = "vote_average.desc";

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
     * The Recycler and its Adapter
     */
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    /**
     * The variable that should contain the movie list
     */
    APIInterface apiInterface;

    // The following variables will contain
    // informations fetched from the API
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

        // Initialize the movie list to make sure there
        // are no "null pointer" exceptions
        mMovieList = new ArrayList<AMovie>();

        mMovieList.add(new AMovie());
        mMovieList.add(new AMovie());
        mMovieList.add(new AMovie());
        mMovieList.add(new AMovie());

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

        mMovieAdapter = new MovieAdapter(this, mMovieList, this);

        // Set the movie list as the data of the adapter
       // mMovieAdapter.setMovieData(mMovieList);

        // Set the adapter onto its RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);

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
    public void onClick(int postion) {

    }
}
