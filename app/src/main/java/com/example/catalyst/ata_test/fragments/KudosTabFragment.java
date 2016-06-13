package com.example.catalyst.ata_test.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.KudosAdapter;
import com.example.catalyst.ata_test.events.AddKudoEvent;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

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

    // use ButterKnife to hook the area of the view containing the kudos button
    @Bind(R.id.give_kudos_button_layout) RelativeLayout kudosButtonLayout;
    // use ButterKnife to hook the kudos button itself
    @Bind(R.id.give_kudos_button) Button giveKudosButton;

    // view for the list of kudos
    private ListView listView;
    // list for the kudos
    private static ArrayList<Kudo> kudosList = new ArrayList<Kudo>();
    // this profile's user, used here to send to the GiveKudosFragment
    private static User mUser;

    // view for this fragment
    private View kudoView;

    // instance of the network calling class
    private ApiCaller caller;

    // adapter for the list of kudos
    private KudosAdapter adapter;

    // local storage instance
    private SharedPreferences prefs;

    // basic Android view setup
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* set view for the overall view contained within the tab */
        kudoView = inflater.inflate(R.layout.tab_kudos, container, false);

        ButterKnife.bind(this, kudoView);

        // instantiate instances of local storage and of network calling class
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        caller = new ApiCaller(getActivity());

        // id of the logged in user
        String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

        /*
            If this is the logged in user's own profile, remove the "Give Kudos" button,
            as they should not be able to give themselves kudos
         */
        if (myId.equals(mUser.getId())) {
            kudosButtonLayout.setVisibility(View.GONE);
        }
        /* set view for the list of kudos */
        listView = (ListView) kudoView.findViewById(android.R.id.list);
        adapter = new KudosAdapter(getActivity(), kudosList);
        listView.setAdapter(adapter);

        giveKudosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGiveKudoFragment();
            }
        });

        return kudoView;
    }

    // custome constructor for this fragment
    public static KudosTabFragment newInstance(ArrayList<Kudo> kudos, User user) {
        kudosList = kudos;
        mUser = user;

        KudosTabFragment fragment = new KudosTabFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    // open the "Give Kudos" dialog fragment on button click
    public void openGiveKudoFragment() {
        DialogFragment dialog = GiveKudosFragment.newInstance(mUser);
        if (dialog.getDialog() != null) {
            dialog.getDialog().setCanceledOnTouchOutside(true);
        }
        dialog.show(getFragmentManager(), "dialog");
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

    // EventBus callback function after kudos has been posted to the server
    //      - reloads user profile with new kudos
    @Subscribe
    public void onAddKudo(AddKudoEvent event) {
        caller.getProfile(mUser.getId());
    }


}