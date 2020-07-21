package com.example.android.popularmovies2.Data;

import androidx.loader.content.AsyncTaskLoader;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;

import java.util.ArrayList;

public class MovieLoader extends AsyncTaskLoader<ArrayList<DiscoveredMovies.AMovie>> {

    @Override
    public ArrayList<DiscoveredMovies.AMovie> loadInBackground() {
       /* if (mUrl == null) {
            return null;
        }
        // Make the network request and
        // return a list of movie
        ArrayList<AMovie> movieList = QueryUtils.fetchMoviesData(mUrl);

        // The list should already be ready
        // mMovieList is ready but misses certain things

        return movieList;
*/

    }
}
