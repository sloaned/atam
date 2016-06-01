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
    private ApiCaller caller;
    private static ArrayList<Team> teamsList = new ArrayList<Team>();
    private View teamView;

    private TeamAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        teamView = inflater.inflate(R.layout.tab_teams, container, false);
        listView = (ListView) teamView.findViewById(android.R.id.list);

        adapter = new TeamAdapter(getActivity(), teamsList);
        listView.setAdapter(adapter);

        return teamView;
    }

    public static TeamsTabFragment newInstance(ArrayList<Team> teams) {
        teamsList = teams;

        TeamsTabFragment fragment = new TeamsTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

}
