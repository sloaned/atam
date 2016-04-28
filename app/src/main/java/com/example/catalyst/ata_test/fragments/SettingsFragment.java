package com.example.catalyst.ata_test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.MainActivity;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/27/2016.
 */
public class SettingsFragment extends DialogFragment {

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;


    private final String TAG = SettingsFragment.class.getSimpleName();

    @Bind(R.id.logout_button) TextView logoutButton;

    public SettingsFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View settingsView = inflater.inflate(R.layout.fragment_settings, null);

        ButterKnife.bind(this, settingsView);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = prefs.edit();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "logout button clicked!!!!!");
                mEditor.putString(SharedPreferencesConstants.PREFS_USER, null).apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setView(settingsView);

        return builder.create();
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
