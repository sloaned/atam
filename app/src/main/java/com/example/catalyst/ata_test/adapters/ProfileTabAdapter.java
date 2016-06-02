package com.example.catalyst.ata_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.catalyst.ata_test.fragments.KudosTabFragment;
import com.example.catalyst.ata_test.fragments.ReviewsTabFragment;
import com.example.catalyst.ata_test.fragments.TeamsTabFragment;
import com.example.catalyst.ata_test.models.Profile;

/**
 * Created by dsloane on 4/29/2016.
 */
public class ProfileTabAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = ProfileTabAdapter.class.getSimpleName();

    int mNumOfTabs;
    Profile mProfile;

    public ProfileTabAdapter(FragmentManager fm, int numOfTabs, Profile profile) {
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
