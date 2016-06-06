package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.TeamActivity;
import com.example.catalyst.ata_test.adapters.DashboardAdapter;
import com.example.catalyst.ata_test.events.UpdateTeamsEvent;
import com.example.catalyst.ata_test.events.ViewTeamEvent;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/27/2016.
 */
public class DashboardFragment extends Fragment {

    // used for log statements
    private static final String TAG = DashboardFragment.class.getSimpleName();

    // use ButterKnife to hook the ListView for teams
    @Bind(android.R.id.list)ListView listView;

    // adapter for the list of teams
    private DashboardAdapter adapter;

    // fragment's view which is inflated with dashboard fragment's xml layout
    private View homeView;

    // list to contain logged in user's teams
    private ArrayList<Team> mTeams = new ArrayList<Team>();

    // bottom bar containing navigation to other activities
    private BottomBar bottomBar = new BottomBar();

    // instance of ApiCaller used to make network calls
    private ApiCaller caller;

    // local saved data, used here to retrieve logged in user's user ID
    private SharedPreferences prefs;

    // basic Android view setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflate view with dashboard fragment xml layout
        homeView = inflater.inflate(R.layout.fragment_dashboard, null);

        // bind UI elements to view with ButterKnife
        ButterKnife.bind(this, homeView);

        // instantiate SharedPreferences instance
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // add bottom bar to view
        homeView = bottomBar.getBottomBar(getActivity(), homeView);

        // instantiate adapter with list of teams
        adapter = new DashboardAdapter(getActivity(), mTeams);

        // instantiate apiCaller with current activity
        caller = new ApiCaller(getActivity());

        // bind adapter to listView
        listView.setAdapter(adapter);

        /* clicking on a team name brings you to the team page */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // identify which team the user clicked on
                Team team = (Team) adapter.getItem(position);

                // get that teams' id
                String teamId = team.getId();

                // create intent with id of the team the user clicked on
                Intent intent = new Intent(getActivity(), TeamActivity.class)
                        .putExtra("TeamId",  teamId);

                // go to team page of the team the user clicked on
                startActivity(intent);
            }
        });

        // call server to get logged in user's teams
        getTeams();

        return homeView;
    }

    /* make network call to get list of teams */
    public void getTeams() {
        // make sure list of teams is cleared before populating it
        mTeams.clear();

        // get currently logged in user's user ID
        String userId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

        // call server to get all of the currently logged in user's teams
        caller.getTeamsByUser(userId);
    }

    /* EventBus callback function, called after network call completes, populates team
        list with logged in user's teams
    */
    @Subscribe
    public void refreshTeams(UpdateTeamsEvent event) {
        for (Team team : event.getTeams()) {
            mTeams.add(team);
        }
        // update the list view with the new data
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        // register EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        // unregister EventBus
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}
