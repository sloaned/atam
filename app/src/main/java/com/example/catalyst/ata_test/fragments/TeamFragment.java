package com.example.catalyst.ata_test.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.TeamMemberAdapter;
import com.example.catalyst.ata_test.events.UpdateTeamMembersEvent;
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
    private final static String TAG = TeamFragment.class.getSimpleName();

    private BottomBar bottomBar = new BottomBar();
    private View teamView;
    private Team team;

    @Bind(R.id.team_name) TextView teamName;
    @Bind(R.id.team_description) TextView teamDescription;
    @Bind(android.R.id.list) ListView teamMemberListView;

    private TeamMemberAdapter adapter;
    private ArrayList<User> memberList = new ArrayList<User>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        teamView = inflater.inflate(R.layout.fragment_team, null);

        ButterKnife.bind(this, teamView);
        teamView = bottomBar.getBottomBar(getActivity(), teamView);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("Team")) {
            team = (Team) intent.getSerializableExtra("Team");

            teamName.setText(team.getName());
            teamDescription.setText(team.getDescription());

            memberList = team.getUserList();

            ApiCaller caller = new ApiCaller(getActivity());

            caller.getTeamMembers(team);

        }

        return teamView;
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
    public void updateTeamMembers(UpdateTeamMembersEvent event) {
        memberList = event.getMemberList();
        adapter = new TeamMemberAdapter(getActivity(), memberList);
        teamMemberListView.setAdapter(adapter);
    }

}
