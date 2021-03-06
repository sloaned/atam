package com.example.catalyst.ata_test.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.ProfileActivity;
import com.example.catalyst.ata_test.adapters.DashboardAdapter;
import com.example.catalyst.ata_test.events.ProfileEvent;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/29/2016.
 */
public class FeedFragment extends Fragment {

    // used for log statements
    private static final String TAG = FeedFragment.class.getSimpleName();
    private BottomBar bottomBar = new BottomBar();

    @Bind(android.R.id.list)ListView listView;

    private View feedView;

    // basic Android view setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate view with feed xml layout
        feedView = inflater.inflate(R.layout.fragment_feed, null);
        ButterKnife.bind(this, feedView);

        feedView = bottomBar.getBottomBar(getActivity(), feedView);

        return feedView;
    }

// commented out until EventBus is needed
/*
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
*/
}
