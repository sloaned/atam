package com.example.catalyst.ata_test.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.TeamMemberAdapter;
import com.example.catalyst.ata_test.events.ViewTeamEvent;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 5/3/2016.
 */
public class TeamFragment extends Fragment {

    // instance of the bottom bar, which will be added to the view
    private BottomBar bottomBar = new BottomBar();
    // view for this fragment
    private View teamView;
    // the team to be displayed
    private Team team;

    // have ButterKnife hook the UI elements
    @Bind(R.id.team_name) TextView teamName;
    @Bind(R.id.team_description) TextView teamDescription;
    @Bind(android.R.id.list) ListView teamMemberListView;

    // adapter for the list of team members
    private TeamMemberAdapter adapter;
    // the list of team members
    private ArrayList<User> memberList = new ArrayList<User>();

    // basic Android view setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the view with the xml fragment
        teamView = inflater.inflate(R.layout.fragment_team, null);

        ButterKnife.bind(this, teamView);
        // add the bottom bar to the view
        teamView = bottomBar.getBottomBar(getActivity(), teamView);

        // get intent from calling activity
        Intent intent = getActivity().getIntent();

        // ensure that an intent does exist and that it carries a team id (should never fail)
        if (intent != null && intent.hasExtra("TeamId")) {
            // retrieve team id from intent
            String teamId = intent.getStringExtra("TeamId");

            ApiCaller caller = new ApiCaller(getActivity());
            // make network call to get team info from server
            caller.getTeamById(teamId);
        }

        return teamView;
    }

    /*
        EventBus callback function that runs after the team has been retrieved from the server
            - updates the view with the team info
     */
    @Subscribe
    public void onUpdateTeam(ViewTeamEvent event) {

        team = event.getTeam();
        // display the team's name
        teamName.setText(team.getName());
        // display the team description
        teamDescription.setText(team.getDescription());

        memberList = team.getUserList();
        // create an adapter to display the team members
        adapter = new TeamMemberAdapter(getActivity(), memberList);
        // set the adapter
        teamMemberListView.setAdapter(adapter);
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
