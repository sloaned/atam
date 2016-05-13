package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.KudosAdapter;
import com.example.catalyst.ata_test.events.GetKudosInfoEvent;
import com.example.catalyst.ata_test.events.UpdateKudosEvent;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/29/2016.
 */
public class KudosTabFragment extends Fragment {

    private final String TAG = KudosTabFragment.class.getSimpleName();

    private ListView listView;
    private static User mUser;
    private ApiCaller caller;
    private ArrayList<Kudo> kudosList = new ArrayList<Kudo>();
    private View kudoView;

    private KudosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        kudoView = inflater.inflate(R.layout.tab_kudos, container, false);
        listView = (ListView) kudoView.findViewById(android.R.id.list);

        getKudos();

        return kudoView;
    }

    public static KudosTabFragment newInstance(User user) {
        mUser = user;

        KudosTabFragment fragment = new KudosTabFragment();
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

    public void getKudos() {
        caller = new ApiCaller(getActivity());
        caller.getKudos(mUser.getId());
    }
    @Subscribe
    public void updateKudos(UpdateKudosEvent event) {
        Log.d(TAG, "in updateKudos!!!!!!!!!!!!");
        kudosList = event.getKudos();
        caller.getKudosReviewers(kudosList);
    }

    @Subscribe
    public void getReviewerInfoEvent(GetKudosInfoEvent event) {
        Log.d(TAG, "in getReviewerInfoEvent!!!!!!!!!!!!");
        kudosList = event.getKudos();
        adapter = new KudosAdapter(getActivity(), kudosList);
        listView.setAdapter(adapter);
    }

}
