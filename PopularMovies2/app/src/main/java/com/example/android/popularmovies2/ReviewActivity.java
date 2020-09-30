package com.example.android.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.APIResponsePOJO.MovieReviews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    private static final String MOVIE_REVIEWS = "reviews";
    private static final String REVIEWER_NAME = "reviewerName" ;
    private static final String REVIEWER_TEXT = "reviewerText" ;

    private TextView reviewTitle;
    private ListView reviewListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviewTitle = (TextView) findViewById(R.id.review_title);
        // Store the review's listview in a variable
        reviewListView = (ListView) findViewById(R.id.review_list_view);

        // Get the intent from the Detail Activity.
        // The intent contains an arrayList of reviews
        // for the current movie.
        Intent intent = getIntent();

        // Extract the review data from the intent.
        // The data is stored in an arrayList.
        ArrayList<MovieReviews.Review> reviewArrayList =
                 intent.getParcelableArrayListExtra(MOVIE_REVIEWS);

        /**Comment a function :
         * Purpose, Parameter, return and Side Effect (opens a file)*/

        // Set the number of review on the title's textView
        reviewTitle.setText(getString(R.string.review_title,reviewArrayList.size()));

        // Create the array that will hold the keys
        // for the review hashmap.
        String [] from = {"reviewerName","reviewerText"};

        // The following array will store the id
        // of the views in the review_list_item
        int [] to = {R.id.textView_reviewer_name,R.id.textView_reviewer_text};

        // A list of hashmaps.
        // Will serve to load data into the
        // views of review_list_item
        ArrayList<HashMap<String,String>> reviewHashMapList=new ArrayList<>();

        // We'll use this variable
        // to progressivelly add new hashMaps
        // to our << reviewHashMapList >>
        HashMap<String,String> reviewHashMap = new HashMap<>();

        for (int i=0; i< reviewArrayList.size() ; i++){
            reviewHashMap = new HashMap<>();

            reviewHashMap.put(REVIEWER_NAME, reviewArrayList.get(i).getAuthor());
            reviewHashMap.put(REVIEWER_TEXT,reviewArrayList.get(i).getContent());

            reviewHashMapList.add(reviewHashMap);//add the hashmap into arrayList
        }

        SimpleAdapter reviewAdapter=
                new SimpleAdapter(this, reviewHashMapList, R.layout.review_list_item, from, to);


        reviewListView.setAdapter(reviewAdapter);
    }

}
