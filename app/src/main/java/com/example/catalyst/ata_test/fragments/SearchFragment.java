package com.example.catalyst.ata_test.fragments;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.SearchResultAdapter;
import com.example.catalyst.ata_test.data.DBHelper;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/27/2016.
 */
public class SearchFragment extends Fragment implements ApiCaller.UpdateSearchListener {

    private static final String TAG = SearchFragment.class.getSimpleName();

    private SearchResultAdapter adapter;

    @Bind(android.R.id.list)ListView listView;
    private View resultView;
    private ArrayList<User> results = new ArrayList<User>();

    private ArrayList<User> users = new ArrayList<User>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resultView = inflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, resultView);

        adapter = new SearchResultAdapter(getActivity(), results);

        listView.setAdapter(adapter);

        Intent intent = getActivity().getIntent();
        String query = intent.getStringExtra(SearchManager.QUERY);

        ApiCaller caller = new ApiCaller(getActivity(), this);
        caller.getAllUsers();

        searchUsers(query);

        Log.d(TAG, "in the result fragment!!!!!!!!!");

        return resultView;
    }

    public void searchUsers(String query) {
        results.clear();

        for (User user : users) {
            Log.d(TAG, "getting " + user.getFirstName());
            results.add(user);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshUsers(ArrayList<User> users) {
        this.users = users;
    }
}
