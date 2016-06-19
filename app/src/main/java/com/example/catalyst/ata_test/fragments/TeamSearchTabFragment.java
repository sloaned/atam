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

import butterknife.Bind;

/**
 * Created by dsloane on 6/1/2016.
 */
public class TeamSearchTabFragment extends Fragment {

    // listView for the team search results
    private ListView listView;
    // list of all teams that match the user's search query
    private static ArrayList<Team> teamResults = new ArrayList<Team>();
    // view for the entire fragment
    private View teamResultView;
    // adapter to describe how to display the list
    private TeamSearchResultAdapter adapter;

    // basic Android view setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        teamResultView = inflater.inflate(R.layout.tab_team_results, container, false);

        listView = (ListView) teamResultView.findViewById(R.id.team_result_list);//(android.R.id.list);

        // set adapter with list of team results
        adapter = new TeamSearchResultAdapter(getActivity(), teamResults);
        listView.setAdapter(adapter);

        // when a single team result is clicked, go that team's team page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team) adapter.getItem(position);

                String teamId = team.getId();

                // create intent with the team id as a string extra
                Intent intent = new Intent(getActivity(), TeamActivity.class)
                        .putExtra("TeamId", teamId);
                // open TeamActivity
                startActivity(intent);
            }
        });

        return teamResultView;
    }

    // custom constructor for this tab fragment
    public static TeamSearchTabFragment newInstance(ArrayList<Team> teams) {
        teamResults = teams;

        TeamSearchTabFragment fragment = new TeamSearchTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    /*
        EventBus function which is called every time the search results are updated after the
        query is modified
            - updates the view with the new team results list
     */
    @Subscribe
    public void onUpdateSearch(UpdateSearchEvent event) {
        teamResults = event.getTeams();

        /* hacky solution to a problem where adapter.notifyDataSetChanged() wasn't working
            after orientation change. So instead, we just rebuild/reset the adapter
         */
        adapter = new TeamSearchResultAdapter(getActivity(), teamResults);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // register the EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        // unregister the EventBus
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}
