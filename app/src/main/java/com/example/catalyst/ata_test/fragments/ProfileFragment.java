package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.ProfileTabAdapter;
import com.example.catalyst.ata_test.events.BioChangeEvent;
import com.example.catalyst.ata_test.events.ProfileEvent;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.Profile;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/28/2016.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private BottomBar bottomBar = new BottomBar();
    private View profileView;

    // the profile of the user
    private Profile mProfile;

    // network caller to get the user profile
    private ApiCaller caller;

    private SharedPreferences prefs;

    private ProfileTabAdapter adapter;

    // use ButterKnife to bind UI elements to the view
        // user's profile picture
    @Bind(R.id.profile_pic) ImageView profilePic;
        // user's name
    @Bind(R.id.user_name) TextView username;
        // user's title
    @Bind(R.id.user_title) TextView userTitle;
        // button to allow the user to edit their own bio (removed if this isn't their profile)
    @Bind(R.id.edit_bio_button) ImageView editBioButton;
        // user's bio/profile description
    @Bind(R.id.user_bio) TextView userBio;
        // the tabs for kudos/reviews/teams
    @Bind(R.id.tab_layout) TabLayout profileTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileView = inflater.inflate(R.layout.fragment_profile, null);

        ButterKnife.bind(this, profileView);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        caller = new ApiCaller(getActivity());

        // add the bottom bar to the view
        profileView = bottomBar.getBottomBar(getActivity(), profileView);

        editBioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditBioFragment();
            }
        });

        // add the three tabs
        profileTabs.addTab(profileTabs.newTab().setText("KUDOS"));
        profileTabs.addTab(profileTabs.newTab().setText("REVIEWS"));
        profileTabs.addTab(profileTabs.newTab().setText("TEAMS"));
        // horizontally center the tabs
        profileTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        // get intent from calling activity
        Intent intent = getActivity().getIntent();
        // get id of user from intent
        if (intent != null && intent.hasExtra("UserId")) {
            String userId = intent.getStringExtra("UserId");
            // get id of logged in user
            String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

            // if this is not the logged in user's own profile, remove the edit bio button
            if (!userId.equals(myId)) {
                editBioButton.setVisibility(View.GONE);
            }

            // call the server to get the
            caller.getProfile(userId);

        }

        return profileView;
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

    /*
        called when edit bio button is clicked
        - opens the edit bio dialog fragment
     */
    public void openEditBioFragment() {
        String bio = userBio.getText().toString();
        DialogFragment dialog = EditBioFragment.newInstance(mProfile.getUser());
        if (dialog.getDialog() != null) {
            dialog.getDialog().setCanceledOnTouchOutside(true);
        }
        dialog.show(getFragmentManager(), "dialog");
    }

    /* callback function from editBioFragment to update user bio */
    @Subscribe
    public void changeBio(BioChangeEvent event) {
        caller.getProfile(mProfile.getUser().getId());
    }

    /*
        EventBus callback function that runs after the call to get the profile from
        the server has finished
            - updates the view with the user info, and sets up the tab fragments
     */
    @Subscribe
    public void getProfile(ProfileEvent event) {
        mProfile= event.getProfile();

        User user = mProfile.getUser();

        // display the user's info
        username.setText(user.getFirstName() + " " + user.getLastName());
        userTitle.setText(user.getTitle());
        if (user.getProfileDescription() != null && !user.getProfileDescription().equals("null")) {
            userBio.setText(user.getProfileDescription());
        }

        // the viewPager containing the kudos/review/team fragments, which will be tied to the tabs
        final ViewPager viewPager = (ViewPager) profileView.findViewById(R.id.pager);

        // define adapter to specify what fragments to open on tab clicks
        adapter = new ProfileTabAdapter(getActivity().getSupportFragmentManager(), profileTabs.getTabCount(), mProfile);

        // set the adapter
        viewPager.setAdapter(adapter);

        // bind the viewPager to the tabs
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profileTabs));

        // describes what should happen when different tabs are clicked
        profileTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // open the page that corresponds with the clicked tab
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        });

    }
}



/*
    Weekend changes:

      - added textAlignment:center to the tablayout in search activity xml pages
      - added openGiveKudoFragment function in KudosTabFragment
      - updated kudo model to match kudoApi in server
      - deleted InitialSearchEvent
      - updated getProfile json parsing to reflect new kudo model (date, reviewer)
      - added postKudo call in ApiCaller (gson/json stuff may be wrong)
      - added addKudoEvent
      - added addKudoEvent listener in KudosTabFragment that recalls/reloads the profile
      - updated user model to match User object in server
      - added full user json parsing in getProfile()
      - editBioFragment now takes in full user, builds user object on positive button click and calls updateUser
      - made updateUser call in ApiCaller
      - removed content from BioChangeEvent
      - BioChangeEvent listener function in ProfileFragment to recall/reload profile
      - started adding some FCM stuff in the manifest/gradle dependencies/services
 */
