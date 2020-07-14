package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import com.example.android.popularmovies2.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;

    // The key to get the extra date our of the intent
    public static String EXTRA_POSITION = "movie position";

    //The default position of a movie inside the movie list
    private int DEFAULT_POSITION = -1;

    /**
     *  The following 5 variables will store
     *  the views of the detail_header_layout.xml
     *
     *  As it's included in the activity_detail.xml,
     *  we can not use data binding to reference to its views.
     *  */
    private ImageView mMoviePoster;
    private TextView mMovieYearTV;
    private TextView mMovieLenghtTV;
    private TextView mMovieRatingTV;
    private TextView mMovieFavoriteTV;

    /**
     *  The following variable will store
     *  the views of the detail_body_layout.xml
     *
     *  As it's included in the activity_detail.xml,
     *  we can not use data binding to reference its views.
     *  */

    private TextView mMovieSynopsisTV;

    // Will store the position of the
    // clicked Movie, after a movie as
    // been clicked in the MainActivity
    int mPosition;

    // Will store the movie that was clicked on
    DiscoveredMovies.AMovie mClickedMovie;

    // Will store the list of movies gotten from
    // the network request made in the MainActivity
    public static List<DiscoveredMovies.AMovie> mMovieList;

    // Will store the credit of all the fetched movies.
    // This includes the crew and the actors.
    public static List<MovieCredit> mMoviesCredits = new ArrayList<MovieCredit>();

    // Will store certain extra details about all the fetched movies
    public static List<MovieDetail> mMoviesDetails = new ArrayList<MovieDetail>();

    // Will store reviews for all the fetched movies
    public static List<MovieReviews> mMoviesReviews = new ArrayList<MovieReviews>();

    private TextView reviewSumTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // Receive the intent from the Main Activity
        Intent intent = getIntent();

        // If the intent is null, display a toast with an error message
        if (intent == null) {
            closeOnError();
        }

        // Get the extra associated with the intent.
        // This extra represent the mPosition of the movie
        // that was clicked on
        mPosition = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (mPosition == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // Verify if the List of movie is not empty
        if (mMovieList == null || mMovieList.isEmpty()) {
            // movie data unavailable
            closeOnError();
            return;
        }

        /** Initialize all the variables that will hold the
         *  views of the detail activity header layout.
         *
         *  All the following view are from a separated
         *  layout included in the layout of the detail activity
         *  */
        mMoviePoster = (ImageView) mBinding.movieHeader.findViewById(R.id.imageViewMoviePoster);
        mMovieYearTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieYear);
        mMovieLenghtTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieLength);
        mMovieRatingTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieRating);
        mMovieFavoriteTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewFavorite);
        mMovieSynopsisTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieSynopsis);

        // Set the title of the review with the
        // exact number of reviews made for the current movie.
        mBinding.textViewReviewSummaryTitle.setText(buildReviewTitle(mMoviesReviews.get(mPosition)));
    }

    // pass the 4 lists
    // private void displayMovieInfo()

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Movie Data is not available", Toast.LENGTH_LONG).show();
    }

    private String buildReviewTitle(MovieReviews movieReviews) {

        return getString(R.string.review_title, movieReviews.reviewList.size());

        // Vote count located in "AMovie" is different from review number from the "MovieReview"
        //  The vote count is usually a bigger number
    }
    //
    // What should we do now ? Idk !
}
