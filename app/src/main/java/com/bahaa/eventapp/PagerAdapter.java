package com.bahaa.eventapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bahaa.eventapp.fragments.ExploreFragment;
import com.bahaa.eventapp.fragments.InterestedFragment;
import com.bahaa.eventapp.fragments.NearbyFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //Setting up the View Pager with tabs
    private int mTabsNum;

    public PagerAdapter(FragmentManager fm, int tabsNum) {
        super(fm);
        this.mTabsNum = tabsNum;
    }

    //Here we control the flow of the pager, What Fragment to go on clicking to which Tab..
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NearbyFragment();
            case 1:
                return new ExploreFragment();
            case 2:
                return new InterestedFragment();
            default:
                return new NearbyFragment();
        }
    }

    @Override
    public int getCount() {
        return mTabsNum;
    }
}
