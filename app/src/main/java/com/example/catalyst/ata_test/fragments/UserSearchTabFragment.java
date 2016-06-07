package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.ProfileActivity;
import com.example.catalyst.ata_test.adapters.UserSearchResultAdapter;
import com.example.catalyst.ata_test.events.UpdateSearchEvent;
import com.example.catalyst.ata_test.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by dsloane on 6/1/2016.
 */
public class UserSearchTabFragment extends Fragment {

    // listView for the user search results
    private ListView listView;
    // list of all users that match the user's search query
    private static ArrayList<User> userResults = new ArrayList<User>();
    // view for the entire fragment
    private View userResultView;
    // adapter to describe how to display the list
    private UserSearchResultAdapter adapter;

    // basic Android view setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userResultView = inflater.inflate(R.layout.tab_team_results, container, false);

        listView = (ListView) userResultView.findViewById(android.R.id.list);
        // set adapter with list of team results
        adapter = new UserSearchResultAdapter(getActivity(), userResults);
        listView.setAdapter(adapter);

        // when a single user result is clicked, go that user's profile page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);

                // create intent with the user id as a string extra
                Intent intent = new Intent(getActivity(), ProfileActivity.class)
                        .putExtra("UserId", user.getId());
                // open ProfileActivity
                startActivity(intent);
            }
        });

        return userResultView;
    }

    // custom constructor for tab fragment
    public static UserSearchTabFragment newInstance(ArrayList<User> users) {
        userResults = users;

        UserSearchTabFragment fragment = new UserSearchTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    /*
        EventBus function which is called every time the search results are updated after the
        query is modified
            - updates the view with the new user results list
     */
    @Subscribe
    public void onUpdateSearch(UpdateSearchEvent event) {
        userResults = event.getUsers();

        /* hacky solution to a problem where notifyDataSetChanged() wasn't working
            after orientation change. So instead we just rebuild/reset the adapter
         */
        adapter = new UserSearchResultAdapter(getActivity(), userResults);
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
