package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.TeamSearchResultAdapter;
import com.example.catalyst.ata_test.models.Team;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class TeamSearchTabFragment extends Fragment {

    private final String TAG = TeamSearchTabFragment.class.getSimpleName();

    private ListView listView;
    private static ArrayList<Team> teamResults = new ArrayList<Team>();
    private View teamResultView;
    private TeamSearchResultAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        teamResultView = inflater.inflate(R.layout.tab_team_results, container, false);

        listView = (ListView) teamResultView.findViewById(android.R.id.list);
        adapter = new TeamSearchResultAdapter(getActivity(), teamResults);
        listView.setAdapter(adapter);

        return teamResultView;
    }

    public static TeamSearchTabFragment newInstance(ArrayList<Team> teams) {
        teamResults = teams;

        TeamSearchTabFragment fragment = new TeamSearchTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

}
