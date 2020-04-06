package com.example.android.tourguide;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TourPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    final private int PAGE_COUNT = 4;// 4;


    public TourPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * This method would return the appropriate fragment for the ViewPager
     *
     * @param position is the position of the Fragment in the View Pager
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TouristSpotsFragment();
            case 1:
                return new EventsFragment();
            case 2:
                return new RestaurantsFragment();
            case 3:
                return new MuseumFragment();
            default:
                return null;
        }
    }

    /**
     * Returns the number of fragment available for the ViewPager
     */

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    /**
     * This method returns the title that correspond to a specify position
     * @param position represent the position of the Fragment in the View Pager
     */

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return mContext.getString(R.string.touristSpot);
            case 1:
                return mContext.getString(R.string.event);
            case 2:
                return mContext.getString(R.string.restaurant);
            case 3:
                return mContext.getString(R.string.museum);
        }
        return null;
    }
}
