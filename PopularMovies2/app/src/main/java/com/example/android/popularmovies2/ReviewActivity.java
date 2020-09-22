package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private static final String MOVIE_REVIEWS = "reviews";
    private ListView reviewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Store the review's listview in a variable
        reviewListView = (ListView) findViewById(R.id.review_list_view);

        // Get the intent from the Detail Activity.
        // The intent contains an arrayList of reviews
        // for the current movie.
        Intent intent = getIntent();

        // Extract the review data contained in the intent.
        // The data is store in an arrayList.
         ArrayList<MovieReviews.Review> reviewArrayList =
                 intent.getParcelableArrayListExtra(MOVIE_REVIEWS);

        Toast.makeText(this,reviewArrayList.get(0).getAuthor(),Toast.LENGTH_LONG).show();

    }

}
