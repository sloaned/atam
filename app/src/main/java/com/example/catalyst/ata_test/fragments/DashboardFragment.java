package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.DashboardAdapter;
import com.example.catalyst.ata_test.data.DBHelper;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.network.ApiCaller;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/27/2016.
 */
public class DashboardFragment extends Fragment implements ApiCaller.UpdateDashboardListener {

    private static final String TAG = DashboardFragment.class.getSimpleName();

    private DashboardAdapter adapter;
    @Bind(android.R.id.list)ListView listView;
    private View homeView;
    private ArrayList<String> mTeams = new ArrayList<String>();
    private BottomBar bottomBar = new BottomBar();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_dashboard, null);
        ButterKnife.bind(this, homeView);

        homeView = bottomBar.getBottomBar(getActivity(), homeView);

        adapter = new DashboardAdapter(getActivity(), mTeams);

        listView.setAdapter(adapter);

        getTeams();

        return homeView;
    }

    public void getTeams() {
        mTeams.clear();
        ApiCaller caller = new ApiCaller(getActivity(), this);
        caller.getAllTeams();
    }

    @Override
    public void refreshTeams(ArrayList<Team> teams) {
        for (Team team : teams) {
            Log.d(TAG, "getting " + team.getName());
            mTeams.add(team.getName());
        }
        adapter.notifyDataSetChanged();
    }

}
