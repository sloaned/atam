package com.example.catalyst.ata_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.catalyst.ata_test.fragments.TeamSearchTabFragment;
import com.example.catalyst.ata_test.fragments.UserSearchTabFragment;
import com.example.catalyst.ata_test.models.SearchResult;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class SearchTabAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = ProfileTabAdapter.class.getSimpleName();

    int mNumOfTabs;
    ArrayList<User> userResults;
    ArrayList<Team> teamResults;


    public SearchTabAdapter(FragmentManager fm, int numOfTabs, ArrayList<User> users, ArrayList<Team> teams) {
        super(fm);
        mNumOfTabs = numOfTabs;
        userResults = users;
        teamResults = teams;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UserSearchTabFragment userFragment = UserSearchTabFragment.newInstance(userResults);
                return userFragment;
            case 1:
                TeamSearchTabFragment teamFragment = TeamSearchTabFragment.newInstance(teamResults);
                return teamFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
