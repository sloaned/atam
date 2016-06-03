package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.TeamActivity;
import com.example.catalyst.ata_test.adapters.TeamSearchResultAdapter;
import com.example.catalyst.ata_test.events.UpdateSearchEvent;
import com.example.catalyst.ata_test.events.ViewTeamEvent;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team) adapter.getItem(position);

                String teamId = team.getId();

                Intent intent = new Intent(getActivity(), TeamActivity.class)
                        .putExtra("TeamId", teamId);
                startActivity(intent);
            }
        });

        return teamResultView;
    }

    public static TeamSearchTabFragment newInstance(ArrayList<Team> teams) {
        teamResults = teams;

        TeamSearchTabFragment fragment = new TeamSearchTabFragment();
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

    @Subscribe
    public void onUpdateSearch(UpdateSearchEvent event) {
        teamResults = event.getTeams();

        /* hacky solution to a problem where notifyDataSetChanged() wasn't working
            after orientation change. So instead we just rebuild/reset the adapter
         */
        adapter = new TeamSearchResultAdapter(getActivity(), teamResults);
        listView.setAdapter(adapter);
    }
}
