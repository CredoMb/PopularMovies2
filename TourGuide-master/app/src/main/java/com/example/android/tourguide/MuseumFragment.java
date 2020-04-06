package com.example.android.tourguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MuseumFragment extends Fragment {


    /**
     * The following group of variables represent the website URl for each museum
     */

    // Fine art museum website's url
    private final String fineArtMuseumUrl = "https://www.mbam.qc.ca/en/";
    // McCord museum website's url
    private final String mcCordMuseumUrl = "https://www.musee-mccord.qc.ca/en/";
    // Art Contemporain museum website's url
    private final String artComtemporainMuseumUrl = "https://macm.org/en/";
    // Pact museum website's url
    private final String pacMuseumUrl = "https://pacmusee.qc.ca/en/";
    // RedPath Museum website's url
    private final String redPathMuseumUrl = "https://www.mcgill.ca/redpath/";

    // Start with the intent of opening a webpage
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Create a {@link Location} ArrayList of museums*/
        final ArrayList<Location> museumList = new ArrayList<Location>(Arrays.asList
                (new Location.LocationBuilder().setImageId(R.drawable.fine_art_museum_img)
                                .setTitle(getString(R.string.fineArt))
                                .setDescription(getString(R.string.fineArtDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(fineArtMuseumUrl).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.mccord_museum_img)
                                .setTitle(getString(R.string.mcCord)).setDescription(getString(R.string.mcCordDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(mcCordMuseumUrl).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.mac_museum_img)
                                .setTitle(getString(R.string.artContempo))
                                .setDescription(getString(R.string.artContempoDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(artComtemporainMuseumUrl).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.pac_museum_img)
                                .setTitle(getString(R.string.pac))
                                .setDescription(getString(R.string.pacDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(pacMuseumUrl).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.red_museum_img).setTitle(getString(R.string.redPath))
                                .setDescription(getString(R.string.redPathDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(redPathMuseumUrl).build()));


        /** Create a new {@link LocationAdapter} of museums */
        final LocationAdapter adapter = new LocationAdapter(getContext(), museumList);

        /** Find a reference to the {@link ListView} in the layout */
        ListView museumListView = (ListView) inflater.inflate(R.layout.location_list, container, false);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        museumListView.setAdapter(adapter);

        return museumListView;
    }

}

