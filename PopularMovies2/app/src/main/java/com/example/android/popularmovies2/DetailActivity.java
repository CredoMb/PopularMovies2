package com.example.android.popularmovies2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.APIResponsePOJO.MovieCredit;
import com.example.android.popularmovies2.APIResponsePOJO.MovieDetail;
import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import com.example.android.popularmovies2.APIResponsePOJO.MovieTrailers;
import com.example.android.popularmovies2.Database.AppDatabase;
import com.example.android.popularmovies2.Database.AppExecutors;
import com.example.android.popularmovies2.Database.DetailViewModel;
import com.example.android.popularmovies2.Database.FavoriteEntry;
import com.example.android.popularmovies2.NetworkOperations.APIClient;
import com.example.android.popularmovies2.NetworkOperations.APIInterface;
import com.example.android.popularmovies2.NetworkOperations.GlideHelperClass;
import com.example.android.popularmovies2.NetworkOperations.QueryUtils;
import com.example.android.popularmovies2.databinding.ActivityDetailBinding;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Map<String,Object>> {

    private static final String TAG = DetailActivity.class.getSimpleName();


     // The Ids for the Loader to be used in
     // this activity.

    private static final int MOVIE_LOADER_ID = 1;

    // Will be used to store the trailer
    // in the Map of the Loader result.
    private static final String MOVIE_TRAILERS = "trailers";

    // Will be used to store the reviews
    // in the Map of the Loader result.
    private static final String MOVIE_REVIEWS = "reviews";

    // Will be used to store the credit
    // in the Map of the Loader result.
    private static final String MOVIE_CREDIT ="credit" ;


    ActivityDetailBinding mBinding;

    // The key to get the extra date our of the intent
    public static String EXTRA_POSITION = "movie position";

    //The default position of a movie inside the movie list
    private int DEFAULT_POSITION = -1;

    // This constant will be used as the maximum
    // value for the review.
    private final String REVIEW_AVERAGE_MAXIMUM = "/10";


    // The following 5 variables will store
    // the views of the detail_header_layout.xml

    private TextView mMovieTitleTV;
    private ImageView mMoviePoster;
    private TextView mMovieYearTV;
    private TextView mMovieLenghtTV;
    private TextView mMovieRatingTV;
    private MaterialButton mMovieFavoriteButton;

    // Will be used to access each endpoint of the
    //  API
    APIInterface mApiInterface;

    // The following variable will store
    // the views of the detail_body_layout.xml

    private TextView mMovieSynopsisTV;
    private ListView mTrailerListView;

    // The following variable will store
    // the movie that has just been
    // clicked on by the user.

    DiscoveredMovies.Movie mCurrentMovie;

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

    // The following variable will represent the database
    // that will store the favorite movies.
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

        // Initialize the database
        mDb = AppDatabase.getInstance(getApplicationContext());

        // Set up the view model to observe the database.
        // This will automatically update
        // the favorite lists whenever the data base changes.
        // On top of that, the method "setupViewModel" verifies the current
        // movie is marked as favorite and alter the color of
        // its favorite view.
        setupViewModel();

        // The following variables will carry all the information related to the
        // current movie. This infos include : the general infos, the details, the
        // reviews, the trailers and the movie director.
        mCurrentMovie = mMovieList.get(mPosition);

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        /**
         * Initialize all the variables that will hold the
         * views of the detail activity layout.
         *
         * */
        mMovieTitleTV = (TextView)  mBinding.movieHeader.findViewById(R.id.textViewMovieTitle);
        mMoviePoster = (ImageView) mBinding.movieHeader.findViewById(R.id.imageViewMoviePoster);
        mMovieYearTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieYear);
        mMovieLenghtTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieLength);
        mMovieRatingTV = (TextView) mBinding.movieHeader.findViewById(R.id.textViewMovieRating);
        mMovieFavoriteButton = (MaterialButton) mBinding.movieHeader.findViewById(R.id.buttonFavorite);
        mMovieSynopsisTV = (TextView) mBinding.movieBody.findViewById(R.id.textViewMovieSynopsis);

        // Activate the Loader to fetch the complementary informations
        // of the current movie and populate the UI with it.
        startLoaderOrEmptyState(MOVIE_LOADER_ID);

    }

    @Override
    public Loader<Map<String,Object>> onCreateLoader(int i, Bundle bundle) {

        return new AsyncTaskLoader<Map<String,Object>>(this) {
            @Override
            public Map<String,Object> loadInBackground() {

                Map<String,Object> movieInfosHashMap = new HashMap<String,Object>();

                if (mApiInterface == null) {
                    return movieInfosHashMap;
                }
                // Make the network request and
                // return a list of movie.
                // Each movie on the list only contains the
                // Image and details of the movie.

                // Get the reviews, credit and trailer of the current movie
                // from the appropriate endpoints.
                movieInfosHashMap.put(MOVIE_TRAILERS,
                        QueryUtils.getMovieTrailers(mCurrentMovie, mApiInterface));

                movieInfosHashMap.put(MOVIE_REVIEWS,
                        QueryUtils.getMovieReviews(mCurrentMovie, mApiInterface));

                movieInfosHashMap.put(MOVIE_CREDIT,
                        QueryUtils.getMovieCredit(mCurrentMovie, mApiInterface));

                return movieInfosHashMap;
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Map<String,Object>> loader, Map<String,Object> movieInfosHashMap) {

        if (movieInfosHashMap != null) {

            // Get all the complementary data from the hashmap
            MovieTrailers trailers = (MovieTrailers) movieInfosHashMap.get(MOVIE_TRAILERS);
            MovieCredit credit = (MovieCredit)  movieInfosHashMap.get(MOVIE_CREDIT);
            MovieReviews reviews = (MovieReviews) movieInfosHashMap.get(MOVIE_REVIEWS);

            // Add the complementary information
            // to the current movie.
            mCurrentMovie.setMovieTrailers(trailers);
            mCurrentMovie.setMovieCredit(credit);
            mCurrentMovie.setMovieReviews(reviews);

            // Update the movie list with the extended version
            // of the current movie
            mMovieList.set(mPosition,mCurrentMovie);

            // Now populate the layout with the
            // current movie's data
            populateDetailLayout();
        }
        // else --> make the relative view appear
        // that's it !

    }

    @Override
    public void onLoaderReset(Loader<Map<String,Object>> loader) {

        // Create a new empty Movie list for the Adapter


        // If there's no internet connection display the emptystate view
        /*if (!isNetworkConnected()) {
            emptyStateRl.setVisibility(View.VISIBLE);
        }*/

    }

    /**
     * This method will populate the layout
     * with the current movie's data
     */

    private void populateDetailLayout () {

        List<MovieTrailers.Trailer> currentMovieTrailerList = new ArrayList<MovieTrailers.Trailer>();
        currentMovieTrailerList = mCurrentMovie.getMovieTrailers().trailerList;
        final List<MovieReviews.Review> currentMovieReviewList = mCurrentMovie.getMovieReviews().reviewList;
        final MovieDetail currentMovieDetails = mCurrentMovie.getMovieDetail();
        final String currentMovieDirector = mCurrentMovie.getMovieCredit().getDirectorName();

        // Chercher une application ou j'ai une

        // Add content to the views
        mMovieTitleTV.setText(mCurrentMovie.getTitle());
        setImageWithUri(mMoviePoster, mCurrentMovie.getPosterPath());
        mMovieYearTV.setText(mCurrentMovie.getYear());
        mMovieLenghtTV.setText(currentMovieDetails.getFormattedLength());
        mMovieRatingTV.setText(String.valueOf(mCurrentMovie.getVoteAverage()) + REVIEW_AVERAGE_MAXIMUM);

        // Set a listener onto the favorite textview,
        // so that when clicked, it
        // registers or removes a movie from the favorites.
        mMovieFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Convert the current movie poster to an array
                // of byte.
                byte[] posterDrawableByte = drawableToByte(mMoviePoster.getDrawable());

                // Build a new favorite by using the movie features.
                // Each feature/property of the favorite represent
                // an entry in the database
                final FavoriteEntry fav = new FavoriteEntry(mCurrentMovie.getOverview(),
                        mCurrentMovie.getId(),
                        mCurrentMovie.getVoteAverage(),
                        0,
                        mCurrentMovie.getReleaseDate()
                        , posterDrawableByte);

                // If the movie is not marked as a favorite,
                // then add it to the favorite data base.
                if (!mCurrentMovie.isFavorite) {

                    // Insert the favorite into the database.
                    insertNewFav(fav);

                    // Change the icon of the favorite button
                    // to the plain star
                    mMovieFavoriteButton.setIcon(
                            getDrawable(R.drawable.ic_baseline_star_24)
                    );

                    // Change the text of the favorite button.
                    mMovieFavoriteButton.setText(getString(R.string.favorite_remove));

                    // Show a Toast to notify the user
                    // that the movie has been added to the
                    // database.
                    Toast.makeText(getApplicationContext(), mCurrentMovie.getTitle() +" "+
                            getString(R.string.added_to_fav), Toast.LENGTH_LONG).show();
                } else {

                    // Remove the movie from the favorite
                    // data base
                    removeFromFav(fav, mCurrentMovie.getTitle());

                    // Set its favorite boolean to
                    // false. Why can't we set it
                    // from the Main activity ?
                    mCurrentMovie.isFavorite = false;

                    // Change the icon of the favorite button
                    // to the star stroke
                    mMovieFavoriteButton.setIcon(
                            getDrawable(R.drawable.ic_baseline_star_border_24)
                    );

                    // Change the text of the favorite button.
                    mMovieFavoriteButton.setText(getString(R.string.favorite_add));
                }

            }
        });
        mMovieSynopsisTV.setText(mCurrentMovie.getOverview());

        // Add the trailers and reviews to the UI
        addTrailerAndReviews(currentMovieTrailerList,currentMovieReviewList);
    }

    /**
     *  This method will be used to populate the review sample
     *  and trailer data on the UI.
     *
     *  @param movieTrailerList is the list of trailers for the current movie
     *  @param movieReviewList is the list of reviews for the current movie
     *
     *  */
   private void addTrailerAndReviews(final List<MovieTrailers.Trailer> movieTrailerList,
                                     List<MovieReviews.Review> movieReviewList) {

       List<String> trailersTextList = new ArrayList<String>();

       // If the movie has trailer, add to each list index
       // the text "Trailer " + "i+1" . "i+1" represent the position
       // of a trailer. Ex: If a movies has 2 trailer, the
       // list will contain the following texts "Trailer 1" & "Trailer 2"
       if (movieTrailerList != null && !movieTrailerList.isEmpty()) {
           for (int i = 0; i < movieTrailerList.size(); i++) {

               trailersTextList.add(getString(R.string.trailer_position,i+1));
           }
       }

       // Turn the trailer text list into an array
       // and use it as the data source for our adapter.
       String[] trailerTextArray = trailersTextList.toArray(new String[trailersTextList.size()]);
       final ArrayAdapter simpleAdapter = new ArrayAdapter<>(this, R.layout.trailer_list_item, R.id.trailer_position, trailerTextArray);

       // Find the listview and attach the simple adapter to it
       mTrailerListView = (ListView) findViewById(R.id.trailer_list);
       mTrailerListView.setAdapter(simpleAdapter);

       // Open an intent to read the trailer video once
       // one of the trailers is clicked
       mTrailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Create an Intent to open the video
               // of the trailer.
               Intent intent = new Intent(Intent.ACTION_VIEW,
                       Uri.parse(movieTrailerList.get(i).getTrailerUrl())
               );

               if (intent.resolveActivity(getPackageManager()) != null) {
                   // Start an Activity with the intent
                   startActivity(intent);
               }
           }
       });

       // Create variables to hold informations about
       // the review sample.
       String numberOfReviews = getString(R.string.no_review);
       String firstReviewContent = "";
       String firstReviewAuthor = "";

       // Check if the movie has reviews
       if (movieReviewList != null && !movieReviewList.isEmpty()) {
           // Set the number of reviews with the text "reviews"

           numberOfReviews = getString(R.string.review_title, movieReviewList.size());

           // Get the text of the first review
           firstReviewContent = movieReviewList.get(0).getContent();

           // Get the author of the first review
           firstReviewAuthor = movieReviewList.get(0).getAuthor();

           mBinding.buttonReviewSummaryShowAllReview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                   intent.putExtra(DetailActivity.EXTRA_POSITION, -1);

                   Bundle reviewData = new Bundle();


                   // reviewData
                   //

                   startActivity(intent);
               }
           });

       } else {

           // Make invisible the views for the body and
           // the author of the review sample.
           mBinding.textViewReviewSummarySampleAuthor.setVisibility(View.INVISIBLE);
           mBinding.textViewReviewSummarySampleBody.setVisibility(View.INVISIBLE);

           // Remove the "show all reviews" button by
           // making it invisible
           mBinding.buttonReviewSummaryShowAllReview.setVisibility(View.INVISIBLE);
       }

       mBinding.textViewReviewSummaryTitle.setText(numberOfReviews);
       mBinding.textViewReviewSummarySampleAuthor.setText(firstReviewAuthor);
       mBinding.textViewReviewSummarySampleBody.setText(firstReviewContent);
   }

    /**
     * Method to Check the Network connection and return true or false
     * based on the network connection state.
     *
     */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void startLoaderOrEmptyState(int loaderId) {
        // Check the status of the network, then either launch the Loader or
        // display the Empty State

        // MOVIE_LOADER_ID
        if (isNetworkConnected()) {
            getLoaderManager().initLoader(loaderId, null, DetailActivity.this).forceLoad();
        } else {
           // mEmptyStateRl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method will close the activity
     * and display a toast. Will be used
     * when there are no movie data available
     */

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.data_not_available), Toast.LENGTH_LONG).show();
    }

    /**
     * This method help us to load an image onto an image view
     * by using the GlideHelperClass object.
     *
     * @param imageView is the imageView we should load the image onto
     * @param imageUri  is the uri of the image to load.
     */

    private void setImageWithUri(ImageView imageView, String imageUri) {

        GlideHelperClass glideHelper = new GlideHelperClass(this,
                imageUri,
                R.drawable.placeholder_image,
                imageView);

        // This will load the image, from the API to the
        // image view
        glideHelper.loadImage();
    }

    /**
     * This method setup the view model to observe the favorite data base.
     * <p>
     * As we have 2 lists of favorite movies, one in the MainActivity
     * and the other in the DetailActivity, we need to update them
     * everytime the database changes.
     * <p>
     * This will be possible with the view model observing the database.
     */
    private void setupViewModel() {

        // Ok mister ViewModel Provider could find me the
        // View Model that as the nature "DetailViewModel.class".
        // No problem, let me find among the view model set
        DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        // This will set an observer onto the data base
        // it self.
        viewModel.getFavorites().observe(this, new Observer<List<FavoriteEntry>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntry> favoriteEntries) {
                Log.d(TAG, getString(R.string.view_model_setup));

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

                    // Change the icon of the favorite button
                    // to the plain star
                    mMovieFavoriteButton.setIcon(
                            getDrawable(R.drawable.ic_baseline_star_24)
                    );

                    // Change the text of the favorite button.
                    mMovieFavoriteButton.setText(getString(R.string.favorite_remove));

                }
            }
        });
    }

    /**
     * This method is used to insert a new element into the
     * favorite data base. The task is done inside a
     * working thread.
     *
     * @param fav is the FavoriteEntry object to insert
     *            in the database
     */
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

    /**
     * This method is used to remove an element from the
     * favorite data base. The task is done inside a
     * working thread.
     *
     * @param fav        is the FavoriteEntry object to remove
     *                   from the database
     * @param movieTitle is the title of the movie to remove.
     *                   It's used to create a confirmation Toast.
     */
    public void removeFromFav(final FavoriteEntry fav, final String movieTitle) {

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

                // On the main thread, display a toast message
                // to let the user know that the movie was removed from
                // the favorites.
                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), movieTitle +" "+
                                getString(R.string.removed_favorite), Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }

    /**
     * This method converts a "Drawable" to an array of byte.
     * This way, we can store the movie image inside the database.
     *
     * @param drawable is the actual drawable to convert
     */
    private byte[] drawableToByte(Drawable drawable) {

        // Turn the drawable into a bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Turn the bitmap into a byteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // Return the byte array after conversion.
        return byteArray;
    }

    /**
     * This method tells us wether or not a given movie
     * is in the favorite list.
     * <p>
     * If the movie is a favorite, the method returns true
     * and false otherwise
     *
     * @param movie represent the movie we are checking
     *              for in the favorite list
     */
    private boolean isAFavorite(DiscoveredMovies.Movie movie) {

        // get the list
        for (FavoriteEntry fav : mFavoriteMovies) {

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
