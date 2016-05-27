package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.TeamAdapter;
import com.example.catalyst.ata_test.events.UpdateTeamsEvent;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by dsloane on 4/29/2016.
 */
public class TeamsTabFragment extends Fragment {

    private final String TAG = TeamsTabFragment.class.getSimpleName();

    private ListView listView;
    private static User mUser;
    private ApiCaller caller;
    private ArrayList<Team> teamsList = new ArrayList<Team>();
    private View teamView;

    private TeamAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        teamView = inflater.inflate(R.layout.tab_teams, container, false);
        listView = (ListView) teamView.findViewById(android.R.id.list);

        if (mUser != null) {
            getTeams();
        }

        return teamView;
    }

    public static TeamsTabFragment newInstance(User user) {
        mUser = user;

        TeamsTabFragment fragment = new TeamsTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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

    public void getTeams() {
        caller = new ApiCaller(getActivity());
        caller.getTeamsByUser(mUser.getId());
    }

    @Subscribe
    public void updateTeams(UpdateTeamsEvent event) {
        teamsList = event.getTeams();
        adapter = new TeamAdapter(getActivity(), teamsList);
        listView.setAdapter(adapter);
    }


}
