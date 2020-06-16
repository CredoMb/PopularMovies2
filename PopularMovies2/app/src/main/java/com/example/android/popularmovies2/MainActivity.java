package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.android.popularmovies2.Data.AMovie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

    public static ArrayList<AMovie> mMovieList;

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

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_mainactivity);

        // The progress spinner to use for a good
        mProgressSpinner = (ProgressBar) findViewById(R.id.main_loading_spinner);





        /*
         * The MovieAdapter is responsible for linking our movie data with the Recycler that
         * will end up displaying the data.
         */
        mMovieAdapter = new MovieAdapter(this, new ArrayList<AMovie>(), this);

        // Set the movie list as the data of the adapter
        mMovieAdapter.setMovieData(mMovieList);

        // Set the adapter onto its RecyclerView
        mRecyclerView.setAdapter(mMovieAdapter);
    }
}
