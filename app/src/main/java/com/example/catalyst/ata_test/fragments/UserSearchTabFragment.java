package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.TeamSearchResultAdapter;
import com.example.catalyst.ata_test.adapters.UserSearchResultAdapter;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class UserSearchTabFragment extends Fragment {

    private final String TAG = UserSearchTabFragment.class.getSimpleName();

    private ListView listView;
    private static ArrayList<User> userResults = new ArrayList<User>();
    private View userResultView;
    private UserSearchResultAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userResultView = inflater.inflate(R.layout.tab_team_results, container, false);

        listView = (ListView) userResultView.findViewById(android.R.id.list);
        adapter = new UserSearchResultAdapter(getActivity(), userResults);
        listView.setAdapter(adapter);

        return userResultView;
    }

    public static UserSearchTabFragment newInstance(ArrayList<User> users) {
        userResults = users;

        UserSearchTabFragment fragment = new UserSearchTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

}
