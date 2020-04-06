package com.example.android.tourguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class RestaurantsFragment extends Fragment {

    /**
     * The following group of variables represent the website URl for each museum
     */

    // Portus360 website's url
    private final String portus360URL = "https://portus360.com/en/";
    // Deville dinner bar website's url
    private final String devilleURL = "http://www.devilledinerbar.com/";
    // Il Focolaio website's url
    private final String ilfocolaioURL = "https://ilfocolaio.ca/en/";
    // Bar Goerge website's url
    private final String barGeorgeURL = "https://www.bargeorge.ca/";
    // Ikanos website's url
    private final String ikanosURL = "https://ikanos.ca/en/home/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Create a {@link Location} ArrayList of restaurants*/
        final ArrayList<Location> restaurantsList = new ArrayList<Location>(Arrays.asList
                (new Location.LocationBuilder().setImageId(R.drawable.portus360_resto_img)
                                .setTitle(getString(R.string.portus360))
                                .setDescription(getString(R.string.portus360Desc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(portus360URL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.deville_resto_img)
                                .setTitle(getString(R.string.deville)).setDescription(getString(R.string.devilleDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(devilleURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.il_focolaio_resto_img)
                                .setTitle(getString(R.string.ilFocolaio))
                                .setDescription(getString(R.string.ilFocolaioDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(ilfocolaioURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.bar_george_resto_img)
                                .setTitle(getString(R.string.barGeorge))
                                .setDescription(getString(R.string.barGeorgeDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(barGeorgeURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.ikanos_resto_img).setTitle(getString(R.string.ikanos))
                                .setDescription(getString(R.string.ikanosDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(ikanosURL).build()));


        /** Create a new {@link LocationAdapter} of restaurants */
        final LocationAdapter adapter = new LocationAdapter(getContext(), restaurantsList);

        // But which layout ?
        /** Find a reference to the {@link ListView} in the layout */
        ListView restaurantsListView = (ListView) inflater.inflate(R.layout.location_list, container, false);

        /** Set an {@link LocationAdapter} on the restaurantListView
         to populate it with data */
        restaurantsListView.setAdapter(adapter);

        // Return the listView that contains all the restaurants
        return restaurantsListView;
    }


}
