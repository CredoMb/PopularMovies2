package com.example.android.popularmovies2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{


    private List<AMovie> mMovieData = new ArrayList<AMovie>();
    private Context mContext;

    public interface MovieAdapterOnClickHandler {
        void onClick(int postion);
    }

    final private MovieAdapterOnClickHandler mClickHandler;

    /**
     * Constructor of the adaptor
     */
    public MovieAdapter(Context context, List<AMovie> movieData,
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
}
