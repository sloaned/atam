package com.example.catalyst.ata_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.catalyst.ata_test.fragments.KudosTabFragment;
import com.example.catalyst.ata_test.fragments.ReviewsTabFragment;
import com.example.catalyst.ata_test.fragments.TeamsTabFragment;
import com.example.catalyst.ata_test.models.Profile;
import com.example.catalyst.ata_test.models.User;

/**
 * Created by dsloane on 4/29/2016.
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = TabAdapter.class.getSimpleName();

    int mNumOfTabs;
    Profile mProfile;

    public TabAdapter(FragmentManager fm, int numOfTabs, Profile profile) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mProfile = profile;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                KudosTabFragment kudosFragment = KudosTabFragment.newInstance(mProfile.getKudos());
                return kudosFragment;
            case 1:
                return new ReviewsTabFragment();
            case 2:
                TeamsTabFragment teamsFragment = TeamsTabFragment.newInstance(mProfile.getTeams());
                return teamsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
