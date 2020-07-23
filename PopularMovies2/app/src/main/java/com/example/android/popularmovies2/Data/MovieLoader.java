package com.example.android.popularmovies2.Data;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;

import java.util.ArrayList;
import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<DiscoveredMovies.Movie>> {


    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = MovieLoader.class.getName();

    /**
     * Query Url
     **/
    private String mSortingKey;
    private APIInterface mApiInterface;

    /** The constructor  */
    public MovieLoader(Context context,APIInterface apiInterface,String sortingKey) {

        super(context);
        mApiInterface = apiInterface;
        mSortingKey = sortingKey;
    }

    /** This will help load the data */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**This will cause the "QueryUtils.fetchMoviesData" to be executed on a background
     thread  */
    @Override
    public List<DiscoveredMovies.Movie> loadInBackground() {

        if (mApiInterface == null) {
            return new ArrayList<DiscoveredMovies.Movie>();
        }
        // Make the network request and
        // return a list of movie
        List<DiscoveredMovies.Movie> movieList = QueryUtils.fetchMoviesData(mApiInterface,mSortingKey);

        // The list should already be ready
        // mMovieList is ready but misses certain things

        return movieList;

    }
}
