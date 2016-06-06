package com.example.catalyst.ata_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.catalyst.ata_test.fragments.TeamSearchTabFragment;
import com.example.catalyst.ata_test.fragments.UserSearchTabFragment;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class SearchTabAdapter extends FragmentStatePagerAdapter {

    //Setting up a tag for logging purposes.
    private static final String TAG = ProfileTabAdapter.class.getSimpleName();

    //Holds the value of
    public int mNumOfTabs;

    //Holds the list of users.
    private ArrayList<User> userResults;

    //Holds the list of teams.
    private ArrayList<Team> teamResults;

    //Contructor.
    public SearchTabAdapter(FragmentManager fm, int numOfTabs, ArrayList<User> users, ArrayList<Team> teams) {
        super(fm);

        //Assigns the value
        mNumOfTabs = numOfTabs;

        //Loads user data into the Arraylist.
        userResults = users;

        //Loads team data into the ArrayList.
        teamResults = teams;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //If the user tab is open, only display users to the user.
                UserSearchTabFragment userFragment = UserSearchTabFragment.newInstance(userResults);
                return userFragment;
            case 1:
                //If the team tab is open, only display team names to the user.
                TeamSearchTabFragment teamFragment = TeamSearchTabFragment.newInstance(teamResults);
                return teamFragment;
            default:
                return null;
        }
    }

    //Returns the number of tabs that exist.
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
