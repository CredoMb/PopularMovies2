package com.example.android.tourguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class EventsFragment extends Fragment {
    /**
     * The following group of variables represent the website URl for each events
     */

    // Osheaga website's url
    private final String osheagaURL = "https://www.osheaga.com/en";
    // Rogers Cup website's url
    private final String rogersCupURL = "https://www.rogerscup.com/";
    // Jazz Festival website's url
    private final String jazzFestivalURL = "https://www.montrealjazzfest.com/en-CA";
    // Taste Montreal website's url
    private final String tasteMontrealURL = "https://www.mtl.org/en/experience/best-food-tours";
    // Just For Laughs website's url
    private final String justForLaughsURL = "https://www.hahaha.com/en";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Create a {@link Location} ArrayList of events*/

        final ArrayList<Location> eventList = new ArrayList<Location>(Arrays.asList
                (new Location.LocationBuilder().setImageId(R.drawable.osheaga_img)
                                .setTitle(getString(R.string.osheaga))
                                .setDescription(getString(R.string.osheagaDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(osheagaURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.rogers_cup_img)
                                .setTitle(getString(R.string.rogersCup)).setDescription(getString(R.string.rogersCupDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(rogersCupURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.jazz_festival_img)
                                .setTitle(getString(R.string.jazzFestival))
                                .setDescription(getString(R.string.jazzFestivalDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(jazzFestivalURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.taste_mtl_img)
                                .setTitle(getString(R.string.tasteMTL))
                                .setDescription(getString(R.string.tasteMTLDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(tasteMontrealURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.just_for_laughs_img).setTitle(getString(R.string.justForLaughs))
                                .setDescription(getString(R.string.justForLaughsDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(justForLaughsURL).build()));


        /** Create a new {@link LocationAdapter} for the events */
        final LocationAdapter adapter = new LocationAdapter(getContext(), eventList);

        /** Inflate a {@link ListView} from the listView layout contained inside location_list.xml */
        ListView eventListView = (ListView) inflater.inflate(R.layout.location_list, container, false);

        /** Set an {@link LocationAdapter} on the eventListView
         to populate it with data */
        eventListView.setAdapter(adapter);

        // Return the listView that contains all the events
        return eventListView;
    }


}
