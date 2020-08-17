package com.example.android.popularmovies2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import com.example.android.popularmovies2.APIResponsePOJO.MovieTrailers;
import com.example.android.popularmovies2.Database.AppDatabase;
import com.example.android.popularmovies2.Database.AppExecutors;
import com.example.android.popularmovies2.Database.DetailViewModel;
import com.example.android.popularmovies2.Database.FavoriteEntry;
import com.example.android.popularmovies2.NetworkOperations.GlideHelperClass;
import com.example.android.popularmovies2.databinding.ActivityDetailBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

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

    /**
     * The following variable will store
     * the movie that has just been
     * clicked on.
     * */

     DiscoveredMovies.Movie mCurrentMovie;

     /*
     List<MovieTrailers.Trailer> mCurrentMovieTrailerList = mCurrentMovie.getMovieTrailers().trailerList;
     MovieDetail mCurrentMovieDetails = mCurrentMovie.getMovieDetail();
     String mCurrentMovieDirector;
     List<MovieReviews.Review> mCurrentMovieReviewList ;
     */

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

    // The following variable will represent the database
    // of favorite movies.
    private AppDatabase mDb;

    // Will hold the list of favorite movies
    private static List<FavoriteEntry> mFavoriteMovies = new ArrayList<FavoriteEntry>();

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
        // Initialize the database and set up the view model to
        // observe the database. This will automatically update
        // the favorite lists whenever the data base changes.
        mDb = AppDatabase.getInstance(getApplicationContext());

        // On top of that, the method "setupViewModel" verifies if a
        // movie is marked as favorite to alter the color of
        // its TextView
        setupViewModel();

        // The following variables will carry all the information related to the
        // current movie. This infos include : the general infos, the details, the
        // reviews, the trailers and the movie director.
        mCurrentMovie = mMovieList.get(mPosition);
        List<MovieTrailers.Trailer> currentMovieTrailerList = new ArrayList<MovieTrailers.Trailer>();
        currentMovieTrailerList =
                mCurrentMovie.getMovieTrailers().trailerList;
        final MovieDetail currentMovieDetails = mCurrentMovie.getMovieDetail();
        final String currentMovieDirector = mCurrentMovie.getMovieCredit().getDirectorName();
        final List<MovieReviews.Review> currentMovieReviewList = mCurrentMovie.getMovieReviews().reviewList;


        /**All the variables above don't create bugs to
         * the program*/

        // MovieTrailers currentMovieTrailers = mMoviesTrailers.get(mPosition);

        /** Initialize all the variables that will hold the
         *  views of the header layout from the detail activity .
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
        setImageWithUri(mMoviePoster,mCurrentMovie.getPosterPath());


        // I don't know what to do now...
        mMovieYearTV.setText(mCurrentMovie.getYear());

/*
        Log.e("Laidui ",String.valueOf(mCurrentMovie.getId()));
*/

        mMovieLenghtTV.setText(currentMovieDetails.getFormattedLength());
        mMovieRatingTV.setText(String.valueOf(mCurrentMovie.getVoteAverage())+REVIEW_AVERAGE_MAXIMUM);

        // Set a listener onto the favorite textview,
        // so that when clicked on, it
        // registers or removes a movie from the favorites.
        mMovieFavoriteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Convert the current movie poster to an array
                // of byte.
                byte[] posterDrawableByte = drawableToByte(mMoviePoster.getDrawable());

                // Build a new favorite with all it feature.
                // Each feature/property of the favorite represent
                // an entry in the database
                final FavoriteEntry fav = new FavoriteEntry(mCurrentMovie.getOverview(),
                        mCurrentMovie.getId(),
                        mCurrentMovie.getVoteAverage(),
                        0,
                         mCurrentMovie.getReleaseDate()
                        , null);

                // Check for the Kissing Booth

                // If the movie is not marked as a favorite,
                // then add it to the favorite data base.
                 if (!mCurrentMovie.isFavorite) {
                    // Insert the favorite into the database.
                    insertNewFav(fav);

                     // Show a Toast to notify the user
                     // that the movie has been added to the
                     // database.
                     Toast.makeText(getApplicationContext(),mCurrentMovie.getTitle() +
                             " was added to the favorites",Toast.LENGTH_LONG).show();

                    // Change the mention of the favorite textView
                  //  mMovieFavoriteTV.setTextColor(getColor(R.color.colorAccent));

                    //
                }

                 /*else if (currentMovie.isFavorite) */
                 else {

                    // Remove the movie from the favorite
                    // data base
                    removeFromFav(fav,mCurrentMovie.getTitle());

                    // Set its favorite boolean to
                    // false. Why can't we set it
                    // from the Main activity ?
                    mCurrentMovie.isFavorite = false;
                }

            }
        });
        mMovieSynopsisTV.setText(mCurrentMovie.getOverview());

        // Each row in the list stores country name, currency and flag

        List<String> trailersTextList = new ArrayList<String>();

        if(currentMovieTrailerList!= null && !currentMovieTrailerList.isEmpty()  ) {
            for (int i =0; i < currentMovieTrailerList.size(); i++) {
                trailersTextList.add("Trailer "+ String.valueOf(i+1));

            }
        }

        String[] trailerTextArray = trailersTextList.toArray(new String[trailersTextList.size()]);
        // Log.e("Leu trilere",trailerTextArray[1]);
        // android.R.layout.simple_list_item_1

        ArrayAdapter simpleAdapter = new ArrayAdapter<>(this,R.layout.trailer_list_item, R.id.trailer_position,trailerTextArray);

        // ArrayAdapter simpleAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,trailerTextArray);

        mTrailerListView = (ListView) findViewById(R.id.trailer_list);
        mTrailerListView.setAdapter(simpleAdapter);


        // Trailer positions
        //

        // Keys used in Hashmap to
        // store the " Trailer" + "position" text.
        String[] from = {"trailer"};

        // Ids of views in listview_layout
        int[] to = {R.id.trailer_position};

        /**/

        // Set the title of the review with the
        // exact number of reviews made for the current movie.
      //  mBinding.textViewReviewSummaryTitle.setText(buildReviewTitle(mMoviesReviews.get(mPosition)));


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

    /** */
    private void setupViewModel(){

        // Ok mister ViewModel Provider could find me the
        // View Model that as the nature "DetailViewModel.class".
        // No problem, let me find among the view model set
        DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        // This will set an observer onto the data base
        // it self.
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoriteEntries) {
                Log.d(TAG, "Updating list of favorites from LiveData in ViewModel");

                // Update the list of favorite movies
                // in the MainActivity and DetailActivity.
                MainActivity.favoriteMovies = favoriteEntries;
                mFavoriteMovies = favoriteEntries;

                // Verify if the current movie is part of the
                // favorite list.
                if (isAFavorite(mCurrentMovie)) {

                    // In case the movie is present in the
                    // favorite list, then set its
                    // "isFavorite" variable to be true.
                    mCurrentMovie.isFavorite = true;

                    // Change the mention of the favorite textView.
                    mMovieFavoriteTV.setTextColor(getColor(R.color.colorAccent));

                }
                // We can setup the view model and verify the favorite
                //
            }
        });
    }

    public void insertNewFav(final FavoriteEntry fav) {

        // Create a working thread so the
        // database operation won't block the
        // UI thread.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // insert new task in the database.
                mDb.favoriteDao().insertFavorite(fav);

            }
        });
    }

    public void removeFromFav(final FavoriteEntry fav,final String movieTitle) {

        // If the current movie was already in the favorite
        // list, this means that it should be removed.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Remove the movie from the favorite list
                mDb.favoriteDao().deleteFavorite(fav);

                // Remove the movie from the favorite
                // list of the main activity.
                MainActivity.favoriteMovies.remove(fav);

                Toast.makeText(getApplicationContext(),movieTitle +
                        " was removed from the favorites",Toast.LENGTH_LONG).show();
            }
        });
    }

    private byte[] drawableToByte(Drawable drawable) {

        // Turn the drawable into a bitmap
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        // Turn the bitmap into a byteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Return the byte array after conversion.
        return byteArray;
    }

    private boolean isAFavorite(DiscoveredMovies.Movie movie) {

        // get the list
        for (FavoriteEntry fav:mFavoriteMovies){

            // In case the movie is found in the
            // favorite list, then return true.
            if (movie.getId() == fav.getTmdbId()) {
                return true;
            }
        }

        // If it is, then set its boolean
        return false;
    }

}
