package com.hackoholics.onfrugal.main.presentation;

import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hackoholics.onfrugal.main.presentation.map.FindOfferMapFragment;
import com.hackoholics.onfrugal.main.presentation.map.TabMapFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private FindOfferMapFragment mapFragment;

    PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FindOfferListTabFragment();
            case 1:
                return  new TabMapFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
