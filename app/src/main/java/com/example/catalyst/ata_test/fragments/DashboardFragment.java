package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.DashboardAdapter;
import com.example.catalyst.ata_test.data.DBHelper;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.Team;

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
    private ArrayList<String> mTeams = new ArrayList<String>();
    private BottomBar bottomBar = new BottomBar();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.content_dashboard, null);
        ButterKnife.bind(this, homeView);

        homeView = bottomBar.getBottomBar(getActivity(), homeView);

        adapter = new DashboardAdapter(getActivity(), mTeams);

        listView.setAdapter(adapter);

        getTasks();

        return homeView;
    }

    public void getTasks() {
        mTeams.clear();
        DBHelper dbHelper = new DBHelper(getActivity());

        ArrayList<Team> teams = dbHelper.getTeams();
        for (Team team : teams) {
            Log.d(TAG, "getting " + team.getName());
            mTeams.add(team.getName());
        }
        dbHelper.close();
        adapter.notifyDataSetChanged();
    }

}
