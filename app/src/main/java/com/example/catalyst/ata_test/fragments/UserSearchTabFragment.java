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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);

                Intent intent = new Intent(getActivity(), ProfileActivity.class)
                        .putExtra("User", user.getId());
                startActivity(intent);
            }
        });

        return userResultView;
    }

    public static UserSearchTabFragment newInstance(ArrayList<User> users) {
        userResults = users;

        UserSearchTabFragment fragment = new UserSearchTabFragment();
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
        userResults = event.getUsers();

        /* hacky solution to a problem where notifyDataSetChanged() wasn't working
            after orientation change. So instead we just rebuild/reset the adapter
         */
        adapter = new UserSearchResultAdapter(getActivity(), userResults);
        listView.setAdapter(adapter);
    }

}
