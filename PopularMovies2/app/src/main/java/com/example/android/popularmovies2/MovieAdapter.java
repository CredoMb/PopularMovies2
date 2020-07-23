package com.example.android.popularmovies2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies2.APIResponsePOJO.DiscoveredMovies;
import com.example.android.popularmovies2.Data.GlideHelperClass;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private List<DiscoveredMovies.Movie> mMovieData = new ArrayList<DiscoveredMovies.Movie>();
    private Context mContext;
    private final int  NUMBER_OF_MOVIE = 4;

    public interface MovieAdapterOnClickHandler {
        void onClick(int position);
    }

    final private MovieAdapterOnClickHandler mClickHandler;

    /**
     * Constructor of the adaptor
     */
    public MovieAdapter(Context context, List<DiscoveredMovies.Movie> movieData,
                        MovieAdapterOnClickHandler clickHandler) {

        mContext = context;
        mMovieData = movieData;
        mClickHandler = clickHandler;
    }

    /**
     * This is where the views of our adapter will be inflated.
     * After that, they'll be used.*/

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mMoviePosterIv;

        MovieAdapterViewHolder(View view) {
            super(view);

            // Get the ImageView from the list item layout
            mMoviePosterIv = (ImageView) view.findViewById(R.id.movie_poster);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);

    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the movie
     * image for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieAdapterViewHolder The ViewHolder which should be updated to represent the
     *                               contents of the item at the given position in the data set.
     * @param position               The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {

        // movieAdapterViewHolder.mMovieThumbnailIv.setImageURI(Uri.parse(ImagebaseUrl));
        DiscoveredMovies.Movie currentMovie = mMovieData.get(position);

        GlideHelperClass glideHelper = new GlideHelperClass(mContext,
                currentMovie.getPosterPath(),
                R.drawable.placeholder_image,
                movieAdapterViewHolder.mMoviePosterIv);

        // This will load the image, from the API to the
        // image view

        glideHelper.loadImage();

        /**
        movieAdapterViewHolder.mMovietitleTv.setText(currentMovie.getTitle());
        movieAdapterViewHolder.mMovieyearTv.setText(currentMovie.getYear()); */

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our list of movies
     */
    @Override
    public int getItemCount() {
        if (mMovieData == null || mMovieData.isEmpty()) return 0;

        return NUMBER_OF_MOVIE;
    }

    /**
     * This method is used to set the movies on a existing MovieAdapter.
     * This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new movie data to be displayed.
     */
    public void setMovieData(List<DiscoveredMovies.Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

}
