package com.example.catalyst.ata_test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/27/2016.
 */
public class SettingsFragment extends DialogFragment {

    private final String TAG = SettingsFragment.class.getSimpleName();

    // use ButterKnife to hook the logout button
    @Bind(R.id.logout_button) TextView logoutButton;

    public SettingsFragment() {}

    // basic Android view setup
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // inflate the view with the xml layout
        final View settingsView = inflater.inflate(R.layout.fragment_settings, null);

        ButterKnife.bind(this, settingsView);

        // call logout function when logout button is clicked
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ApiCaller(getActivity()).logout();
            }
        });

        builder.setView(settingsView);

        return builder.create();
    }

    // custom constructor for this dialog fragment
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
}
