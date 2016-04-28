package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.menus.BottomBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/28/2016.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private BottomBar bottomBar = new BottomBar();
    private View profileView;

    @Bind(R.id.user_info_area)LinearLayout userInfoArea;
    @Bind(R.id.user_info_text_area) RelativeLayout userInfoTextArea;
    @Bind(R.id.profile_pic) ImageView profilePic;
    @Bind(R.id.user_name) TextView username;
    @Bind(R.id.user_role) TextView userRole;
    @Bind(R.id.edit_bio_button) ImageView editBioButton;
    @Bind(R.id.user_bio) TextView userBio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileView = inflater.inflate(R.layout.fragment_profile, null);

        ButterKnife.bind(this, profileView);
        profileView = bottomBar.getBottomBar(getActivity(), profileView);


        return profileView;
    }



}
