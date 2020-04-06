package com.example.android.tourguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Location> {

    /**
     * The constructor
     *
     * @param context is the current context the LocationAdapter is being created in
     * @param list    is a list of Location that will be used to fill the listView
     */

    public LocationAdapter(Context context, ArrayList<Location> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        // Verify if an existing view is being reused, other wise inflate a new one
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // From the LocationAdapter, get the Location object located on "position"
        final Location currentLocation = getItem(position);

        // Find the image view on the list_item.xml layout with the id "location_image_view"
        ImageView locationImage = (ImageView) listItemView.findViewById(R.id.location_image_view);
        // Fill the image view with the corresponding location's image
        locationImage.setImageResource(currentLocation.getImageId());

        // Find the textView on the list_item.xml layout with the id "title_text_view"
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        // Fill the title textView with the corresponding title (name of the location)
        titleTextView.setText(currentLocation.getTitle());

        // Find the textView on the list_item.xml layout with the id "description_text_view"
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_text_view);
        // Fill the description textView the corresponding description of the location
        descriptionTextView.setText(currentLocation.getDescription());

        // Find the ratingBar on the list_item.xml layout with the id "rating_bar"
        RatingBar ratingBar = (RatingBar) listItemView.findViewById(R.id.rating_bar);
        // Set the correct rating on the ratingBar
        ratingBar.setRating(currentLocation.getRating());

        // Find the TextView on the list_item.xml layout with the id "total_reviews"
        TextView ratingCount = (TextView) listItemView.findViewById(R.id.total_reviews);
        // Convert the current number of reviewers from an "int" to a "String"
        String nbReviewers = String.format("%d", currentLocation.getNbReviewers());
        // Set the current number of reviewers as the text inside "()" of the ratingCount textView
        ratingCount.setText('(' + nbReviewers + ')');

        // Find the textView on the list_item.xml layout with the id "link_text_view"
        TextView linkToWebSite = (TextView) listItemView.findViewById(R.id.link_text_view);

        // Set OnClickListener on the linkToWebSite
        linkToWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Whenever the linkToWebSite is clicked, open the location's webPage
                Uri webPage = Uri.parse(currentLocation.getLinkToWebsite());
                Intent openWebPage = new Intent(Intent.ACTION_VIEW, webPage);

                // Check if there's an application capable of handling the intent, then start it
                if (openWebPage.resolveActivity(getContext().getPackageManager()) != null) {
                    getContext().startActivity(openWebPage);
                }
            }
        });

        // Return the listView with the all the info related to the current location (correct Image and textViews)
        // to be shown inside the ListView
        return listItemView;

    }
}
