package com.vup.arousingtimer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    int mNumOfTabs;

    public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AreatimerFragment areatimerFragment = new AreatimerFragment();
                return areatimerFragment;
            case 1:
                ManualFragment manualFragment = new ManualFragment();
                return manualFragment;
            case 2:
                AnotherFragment anotherFragment = new AnotherFragment();
                return anotherFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mNumOfTabs;
    }

}
