package com.example.android.tourguidetatahungary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;

    private Context mContext;

    public CategoryFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new AttractionFragment();
            case 1:
                return new MuseumsFragment();
            case 2:
                return new EventFragment();
            default:
                return new UsefulFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.category_attractions);
            case 1:
                return mContext.getResources().getString(R.string.category_museums);
            case 2:
                return mContext.getResources().getString(R.string.category_events);
            default:
                return mContext.getResources().getString(R.string.category_useful);
        }
    }
}


