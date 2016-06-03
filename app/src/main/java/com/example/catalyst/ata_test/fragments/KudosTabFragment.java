package com.example.catalyst.ata_test.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.KudosAdapter;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/29/2016.
 */
public class KudosTabFragment extends Fragment {

    private final String TAG = KudosTabFragment.class.getSimpleName();

    @Bind(R.id.give_kudos_button_layout) RelativeLayout kudosButtonLayout;
    @Bind(R.id.give_kudos_button) Button giveKudosButton;

    private ListView listView;
    private static ArrayList<Kudo> kudosList = new ArrayList<Kudo>();
    private static String userId;
    private View kudoView;
    private ApiCaller caller;

    private KudosAdapter adapter;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* set view for the overall view contained within the tab */
        kudoView = inflater.inflate(R.layout.tab_kudos, container, false);

        ButterKnife.bind(this, kudoView);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        caller = new ApiCaller(getActivity());

        String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

        if (myId.equals(userId)) {
            kudosButtonLayout.setVisibility(View.GONE);
        }
        /* set view for the list of kudos */
        listView = (ListView) kudoView.findViewById(android.R.id.list);
        adapter = new KudosAdapter(getActivity(), kudosList);
        listView.setAdapter(adapter);

        giveKudosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return kudoView;
    }

    public static KudosTabFragment newInstance(ArrayList<Kudo> kudos, String id) {
        kudosList = kudos;
        userId = id;

        KudosTabFragment fragment = new KudosTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


}
