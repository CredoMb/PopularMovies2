package com.example.android.testforbooklistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    // When the user executes the search
    // It get sent to a searchable activity

    // The searchable activity performs the search
    // Enable the search dialog for the activity

    // 2 things : Activity + Xml layout
    // To activate the search dialog : OnSearchRequested()

    // Indicate to the system which searchable activity
    // Will receive the queries

    // Get the intent, verify the action and get the query

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String query;
        TextView test = (TextView) findViewById(R.id.test_Text_View);

        // create database object databaseObject = new DbBackend(SearchableActivity.this);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
        });*/

        //handleIntent(getIntent());


        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            test.setText(query);
        }
    }
}

