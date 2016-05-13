package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.TabAdapter;
import com.example.catalyst.ata_test.events.BioChangeEvent;
import com.example.catalyst.ata_test.menus.BottomBar;
import com.example.catalyst.ata_test.models.User;

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
    private User user;

    @Bind(R.id.user_info_area)LinearLayout userInfoArea;
    @Bind(R.id.user_info_text_area) RelativeLayout userInfoTextArea;
    @Bind(R.id.profile_pic) ImageView profilePic;
    @Bind(R.id.user_name) TextView username;
    @Bind(R.id.user_title) TextView userTitle;
    @Bind(R.id.edit_bio_button) ImageView editBioButton;
    @Bind(R.id.user_bio) TextView userBio;
    @Bind(R.id.tab_layout) TabLayout profileTabs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileView = inflater.inflate(R.layout.fragment_profile, null);

        ButterKnife.bind(this, profileView);
        profileView = bottomBar.getBottomBar(getActivity(), profileView);

        editBioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditBioFragment();
            }
        });

        profileTabs.addTab(profileTabs.newTab().setText("KUDOS"));
        profileTabs.addTab(profileTabs.newTab().setText("REVIEWS"));
        profileTabs.addTab(profileTabs.newTab().setText("TEAMS"));
        profileTabs.setTabGravity(TabLayout.GRAVITY_FILL);




        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("User")) {
            user = (User) intent.getSerializableExtra("User");
            username.setText(user.getFirstName() + " " + user.getLastName());
            userTitle.setText(user.getTitle());
            if (user.getDescription() != null) {
                userBio.setText(user.getDescription());
            }

        }

        final ViewPager viewPager = (ViewPager) profileView.findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager(), profileTabs.getTabCount(), user);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profileTabs));

        profileTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "tab selected = " + tab.getText() + ", position = " + tab.getPosition() + ", viewpager current item = " + viewPager.getCurrentItem());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "tab unselected = " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "tab reselected = " + tab.getText());
            }
        });


        return profileView;
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


    public void openEditBioFragment() {
        String bio = userBio.getText().toString();
        DialogFragment dialog = EditBioFragment.newInstance(bio);
        if (dialog.getDialog() != null) {
            dialog.getDialog().setCanceledOnTouchOutside(true);
        }
        dialog.show(getFragmentManager(), "dialog");
    }

    @Subscribe
    public void changeBio(BioChangeEvent event) {
        userBio.setText(event.getBio());
    }

}
