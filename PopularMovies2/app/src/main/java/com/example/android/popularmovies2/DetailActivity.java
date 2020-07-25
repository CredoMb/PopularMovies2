package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import com.example.android.popularmovies2.APIResponsePOJO.MovieTrailers;
import com.example.android.popularmovies2.Data.GlideHelperClass;
import com.example.android.popularmovies2.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;

    // The key to get the extra date our of the intent
    public static String EXTRA_POSITION = "movie position";

    //The default position of a movie inside the movie list
    private int DEFAULT_POSITION = -1;


    // This constant will be used as the maximum
    // value for the review.
    private final String REVIEW_AVERAGE_MAXIMUM = "/10";


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

    private ListView mTrailerListView;

    // Will store the position of the
    // clicked Movie, after a movie as
    // been clicked in the MainActivity
    int mPosition;

    // Will store the movie that was clicked
    // in the MainActivity.
    DiscoveredMovies.Movie mClickedMovie;

    // Will store the list of movies gotten from
    // the network request made in the MainActivity
    public static List<DiscoveredMovies.Movie> mMovieList = new ArrayList<DiscoveredMovies.Movie>();

    // Will store the credit of all the fetched movies.
    // This includes the crew and the actors.

    private TextView reviewSumTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // The following variables will carry all the information related to the
        // current movie. This infos include : the general infos, the details, the
        // reviews, the trailers and the movie director.
        DiscoveredMovies.Movie currentMovie = mMovieList.get(mPosition);
        List<MovieTrailers.Trailer> currentMovieTrailerList = currentMovie.getMovieTrailers().trailerList;
        MovieDetail currentMovieDetails = currentMovie.getMovieDetail();
        String currentMovieDirector = currentMovie.getMovieCredit().getDirectorName();
        List<MovieReviews.Review> currentMovieReviewList = currentMovie.getMovieReviews().reviewList;

        /**All the variables above don't create bugs to
         * the program*/


      //  empty = mMoviesReviews.get(mPosition).movieId;

        // MovieTrailers currentMovieTrailers = mMoviesTrailers.get(mPosition);

        /** Initialize all the variables that will hold the
         *  views of the detail activity header layout.
         *
         *   All the following view are from a separated
         *  layout included in the layout of the detail activity
         *
         *  */
        mMoviePoster = (ImageView) mBinding.movieHeader.findViewById(R.id.imageViewMoviePoster);
        mMovieYearTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieYear);
        mMovieLenghtTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieLength);
        mMovieRatingTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieRating);
        mMovieFavoriteTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewFavorite);
        mMovieSynopsisTV = (TextView) mBinding.movieBody.findViewById(R.id.textViewMovieSynopsis);

        // Add content to the views

        setImageWithUri(mMoviePoster,currentMovie.getPosterPath());

        // I don't know what to do now...
        mMovieYearTV.setText(currentMovie.getYear());
        mMovieLenghtTV.setText(currentMovieDetails.getFormattedLength());
        mMovieRatingTV.setText(String.valueOf(currentMovie.getVoteAverage())+REVIEW_AVERAGE_MAXIMUM);
        /* */ //mMovieFavoriteTV.setText();
        mMovieSynopsisTV.setText(currentMovie.getOverview());

        // Each row in the list stores country name, currency and flag

        /* List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();

            hm.put("txt", "Country : " + countries[i]);
            hm.put("cur","Currency : " + currency[i]);
            hm.put("flag", Integer.toString(flags[i]) );

            aList.add(hm);
        }
        */

        List <HashMap<String,String>> trailerWithPosition = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> trailerHashMap = new HashMap<String,String>();

        // Why is the size thing not working ?
        int i = currentMovie.getMovieTrailers().trailerList.size();

        // The line bellow will add the following couple

        /*
        for (int i =0; i < currentMovieTrailerList.size(); i++) {

            *//*trailerHashMap.put("trailer","Trailer "+ String.valueOf(i+1));
            trailerWithPosition.add(trailerHashMap);*//*
        }*/

        // Trailer positions
        //

        // Keys used in Hashmap to
        // store the " Trailer" + "position" text.
        String[] from = {"trailer"};

        // Ids of views in listview_layout
        int[] to = {R.id.trailer_position};

        // Besides from the context, which is the first parameter,
        // this adapter takes in a list of HashMap(trailerWithPosition),
        // a layout, a "source" (from) array and a "destination" (to).

        // Context + list
/*
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), trailerWithPosition,
                R.layout.detail_body_layout, from, to);

        mTrailerListView = (ListView) findViewById(R.id.trailer_list);
        mTrailerListView.setAdapter(adapter);*/

        /*
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listview);

        // Setting the adapter to the listView
        listView.setAdapter(adapter); */

     //   Toast.makeText(this, mMoviesTrailers.size(), Toast.LENGTH_SHORT).show();

/*
        List<String> trailerList = new ArrayList<String>();
        trailerList = currentMovie.getMovieTrailers().trailerList;

        ArrayAdapter<String> trailerAdapter = new ArrayAdapter<String>(this,
                R.layout.trailer_list_item,trailerList);
        mTrailerListView.setAdapter(trailerAdapter);*/

        /**/

        // Set the title of the review with the
        // exact number of reviews made for the current movie.
      //  mBinding.textViewReviewSummaryTitle.setText(buildReviewTitle(mMoviesReviews.get(mPosition)));

        /**/
    }

    // pass the 4 lists
    // private void displayMovieInfo()

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Movie Data is not available", Toast.LENGTH_LONG).show();
    }

    private String buildReviewTitle(MovieReviews movieReviews) {
        return getString(R.string.review_title, movieReviews.reviewList.size());

        // Vote count located in "Movie" is different from review number from the "MovieReview"
        //  The vote count is usually a bigger number
    }

    private void setImageWithUri(ImageView imageView, String imageUri){

        GlideHelperClass glideHelper = new GlideHelperClass(this,
                imageUri,
                R.drawable.placeholder_image,
                imageView);

        // This will load the image, from the API to the
        // image view
        glideHelper.loadImage();
    }

}
