package com.example.catalyst.ata_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.catalyst.ata_test.fragments.KudosTabFragment;
import com.example.catalyst.ata_test.fragments.ReviewsTabFragment;
import com.example.catalyst.ata_test.fragments.TeamsTabFragment;

/**
 * Created by dsloane on 4/29/2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = TabAdapter.class.getSimpleName();

    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "TabAdapter getItem called, position # " + position);
        switch (position) {
            case 0:
                KudosTabFragment kudosTab = new KudosTabFragment();
                return kudosTab;
            case 1:
                ReviewsTabFragment reviewsTab = new ReviewsTabFragment();
                return reviewsTab;
            case 2:
                TeamsTabFragment teamsTab = new TeamsTabFragment();
                return teamsTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
