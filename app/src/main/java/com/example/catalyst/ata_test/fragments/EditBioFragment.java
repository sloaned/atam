package com.example.catalyst.ata_test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.events.BioChangeEvent;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dsloane on 4/28/2016.
 */
public class EditBioFragment extends DialogFragment {

    private static User mUser;
    private SharedPreferences prefs;

    public EditBioFragment() {}

    private final static String TAG = EditBioFragment.class.getSimpleName();



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final View editView = inflater.inflate(R.layout.fragment_editbio, null);

        final EditText bioText = (EditText) editView.findViewById(R.id.edit_bio_textarea);

        /* populate text area with current user bio */
        if (!mUser.getProfileDescription().equals("null") && mUser.getProfileDescription() != null && !mUser.getProfileDescription().equals(null)) {
            bioText.setText(mUser.getProfileDescription());
        }

        builder.setView(editView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String bio = bioText.getText().toString();

                String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

                if (myId.equals(mUser.getId())) {
                    User user = new User(mUser.getId(), mUser.getFirstName(), mUser.getLastName(), mUser.getTitle(), bio,
                            mUser.getEmail(), mUser.getAvatar(), mUser.isActive(), mUser.getStartDate(), mUser.getEndDate(), mUser.getVersion());

                    ApiCaller caller = new ApiCaller(getActivity());
                    caller.updateUser(user);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public static EditBioFragment newInstance(User user) {
        mUser = user;

        EditBioFragment editBioFragment = new EditBioFragment();
        Bundle args = new Bundle();

        editBioFragment.setArguments(args);
        return editBioFragment;
    }

}
