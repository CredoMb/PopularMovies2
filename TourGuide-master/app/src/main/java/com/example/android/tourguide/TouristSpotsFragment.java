package com.example.android.tourguide;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;


public class TouristSpotsFragment extends Fragment {

    /**
     * The following group of variables represent the website URl for each Tourist Spots
     */

    // stJoseph Oratoiry website's url
    private final String stJosephOratoireURL = "https://www.saint-joseph.org/en/";
    // Mount Royal website's url
    private final String mountRoyalURL = "https://www.lemontroyal.qc.ca/en/mount-royal/territory";
    // Old Port website's url
    private final String oldPortURL = "https://www.oldportofmontreal.com/history";
    // Parc Jean Drapeau website's url
    private final String parcJeanDrapeauURL = "http://www.parcjeandrapeau.com/en/about-the-societe-du-parc-jean-drapeau/";
    // China Town website's url
    private final String chinaTownURL = "https://www.mtl.org/en/what-to-do/heritage-and-architecture/chinatown";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Create a {@link Location} ArrayList of Tourist Spots*/
        final ArrayList<Location> touristSpotsList = new ArrayList<Location>(Arrays.asList
                (new Location.LocationBuilder().setImageId(R.drawable.st_joseph_oratory_img)
                                .setTitle(getString(R.string.stJoseph))
                                .setDescription(getString(R.string.stJosephDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(stJosephOratoireURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.mount_royal_img)
                                .setTitle(getString(R.string.mountRoyal)).setDescription(getString(R.string.mountRoyalDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(mountRoyalURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.old_mtl_img)
                                .setTitle(getString(R.string.oldMontreal))
                                .setDescription(getString(R.string.oldMontrealDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(oldPortURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.jean_drapeau_img)
                                .setTitle(getString(R.string.jeanDrapeau))
                                .setDescription(getString(R.string.parcJeanDrapeauDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(parcJeanDrapeauURL).build(),
                        new Location.LocationBuilder().setImageId(R.drawable.china_town_img).setTitle(getString(R.string.chinaTown))
                                .setDescription(getString(R.string.chinaTownDesc))
                                .setRating(ReviewUtils.randomRating()).setNbReviewers(ReviewUtils.randomReviewers())
                                .setLinkToWebSite(chinaTownURL).build()));


        /** Create a new {@link LocationAdapter} of museum */
        final LocationAdapter adapter = new LocationAdapter(getContext(), touristSpotsList);

        /** Inflate a {@link ListView} from the listView layout contained inside location_list.xml */
        ListView touristSpotsListView = (ListView) inflater.inflate(R.layout.location_list, container, false);

        /** Set an {@link LocationAdapter} on the touristSpotsListView
         to populate it with data */
        touristSpotsListView.setAdapter(adapter);

        // Return the listView that contains all the tourist spots
        return touristSpotsListView;
    }


}


