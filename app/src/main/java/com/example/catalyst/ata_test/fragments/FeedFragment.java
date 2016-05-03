package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.DashboardAdapter;
import com.example.catalyst.ata_test.menus.BottomBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/29/2016.
 */
public class FeedFragment extends Fragment {

    private static final String TAG = FeedFragment.class.getSimpleName();
    private BottomBar bottomBar = new BottomBar();

    @Bind(android.R.id.list)ListView listView;

    private View feedView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        feedView = inflater.inflate(R.layout.fragment_feed, null);
        ButterKnife.bind(this, feedView);

        feedView = bottomBar.getBottomBar(getActivity(), feedView);

        return feedView;
    }

}
