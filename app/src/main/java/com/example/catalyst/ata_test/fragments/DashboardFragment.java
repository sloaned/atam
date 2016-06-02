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

    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardAdapter adapter;
    @Bind(android.R.id.list)ListView listView;
    private View homeView;
    private ArrayList<Team> mTeams = new ArrayList<Team>();
    private BottomBar bottomBar = new BottomBar();
    private ApiCaller caller;

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_dashboard, null);
        ButterKnife.bind(this, homeView);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = prefs.edit();

        homeView = bottomBar.getBottomBar(getActivity(), homeView);

        adapter = new DashboardAdapter(getActivity(), mTeams);

        caller = new ApiCaller(getActivity());

        listView.setAdapter(adapter);

        /* clicking on a team name brings you to the team page */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team) adapter.getItem(position);

                String teamId = team.getId();

                caller.getTeamById(teamId);
            }
        });


        getTeams();

        return homeView;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    /* make network call to get list of teams */
    public void getTeams() {
        mTeams.clear();
        String userId = prefs.getString(SharedPreferencesConstants.USER_ID, null);
        caller.getTeamsByUser(userId);
    }

    /* callback function populate list with teams */
    @Subscribe
    public void refreshTeams(UpdateTeamsEvent event) {
        for (Team team : event.getTeams()) {
            mTeams.add(team);
        }
        adapter.notifyDataSetChanged();
    }

    /* open team page when a team is clicked on */
    @Subscribe
    public void viewTeam(ViewTeamEvent event) {
        Log.d(TAG, event.getTeam().toString());
        Intent intent = new Intent(getActivity(), TeamActivity.class)
                .putExtra("Team", (Serializable) event.getTeam());
        startActivity(intent);
    }

}
