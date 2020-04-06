package com.example.android.tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Get the viewPager from the activity_main.xml layout
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create a TourPagerAdapter to provide content to the viewPager
        TourPagerAdapter tourPagerAdapter = new TourPagerAdapter(this, getSupportFragmentManager());

        // Set the pagerAdapter on the View Pager
        viewPager.setAdapter(tourPagerAdapter);

        // Inflate a TabLayout from the activity_main Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager that contains all the fragments
        tabLayout.setupWithViewPager(viewPager);
    }
}
