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

    //Setting up a tag for logging purposes.
    private static final String TAG = ProfileTabAdapter.class.getSimpleName();

    //Holds the value for the number of tabs.
    public int mNumOfTabs;

    //Used to hold a profile object.
    public Profile mProfile;

    //Contructor
    public ProfileTabAdapter(FragmentManager fm, int numOfTabs, Profile profile) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mProfile = profile;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //If the kudo fragment is selected, load the kudos for the user.
                KudosTabFragment kudosFragment = KudosTabFragment.newInstance(mProfile.getKudos(), mProfile.getUser().getId());
                return kudosFragment;
            case 1:
                //If the reviews tab is selected, load the reiviews for said user.
                //TODO: Load the reviews for said user here.
                return new ReviewsTabFragment();
            case 2:
                //If the teams tab is selected, load the teams the user is apart of.
                TeamsTabFragment teamsFragment = TeamsTabFragment.newInstance(mProfile.getTeams());
                return teamsFragment;
            default:
                return null;
        }
    }

    //Returns the number of tabs that exisist.
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
