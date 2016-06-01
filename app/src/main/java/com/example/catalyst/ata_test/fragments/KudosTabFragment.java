package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.KudosAdapter;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.network.ApiCaller;

import java.util.ArrayList;

/**
 * Created by dsloane on 4/29/2016.
 */
public class KudosTabFragment extends Fragment {

    private final String TAG = KudosTabFragment.class.getSimpleName();

    private ListView listView;
    private ApiCaller caller;
    private static ArrayList<Kudo> kudosList = new ArrayList<Kudo>();
    private View kudoView;

    private KudosAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* set view for the overall view contained within the tab */
        kudoView = inflater.inflate(R.layout.tab_kudos, container, false);

        /* set view for the list of kudos */
        listView = (ListView) kudoView.findViewById(android.R.id.list);

        if (kudosList.size() > 0) {
            RelativeLayout fakeKudo = (RelativeLayout) kudoView.findViewById(R.id.fake_kudo);
            fakeKudo.setVisibility(View.GONE);
        }
        adapter = new KudosAdapter(getActivity(), kudosList);
        listView.setAdapter(adapter);

        return kudoView;
    }

    public static KudosTabFragment newInstance(ArrayList<Kudo> kudos) {
        kudosList = kudos;

        KudosTabFragment fragment = new KudosTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


}
