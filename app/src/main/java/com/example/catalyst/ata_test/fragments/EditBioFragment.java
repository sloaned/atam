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

<<<<<<< HEAD
    private static User mUser;
=======
    // the logged in user
    private static User mUser;

    // local storage, used to store and retrieve logged in user's id
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4
    private SharedPreferences prefs;

    public EditBioFragment() {}

    // used for logging statements
    private final static String TAG = EditBioFragment.class.getSimpleName();


    // basic Android view setup
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // instantiate SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // create view and inflate it with the editbio xml layout
        View editView = inflater.inflate(R.layout.fragment_editbio, null);

        // textarea where user can type in their new profile description
        final EditText bioText = (EditText) editView.findViewById(R.id.edit_bio_textarea);

<<<<<<< HEAD
        /* populate text area with current user bio */
        bioText.setText(mUser.getProfileDescription());
=======
        /* populate text area with current user bio, unless it is currently null */
        if (!mUser.getProfileDescription().equals("null") && mUser.getProfileDescription() != null && !mUser.getProfileDescription().equals(null)) {
            bioText.setText(mUser.getProfileDescription());
        }
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4

        builder.setView(editView);

        // create button to submit when user is satisfied with new bio
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String bio = bioText.getText().toString();

                String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);
<<<<<<< HEAD

                if (myId.equals(mUser.getId())) {
=======
                // error check to ensure that logged in user is editing their own bio (this should never be able to fail)
                if (myId.equals(mUser.getId())) {
                    // build full user object (needed to update the user in the server)
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4
                    User user = new User(mUser.getId(), mUser.getFirstName(), mUser.getLastName(), mUser.getTitle(), bio,
                            mUser.getEmail(), mUser.getAvatar(), mUser.isActive(), mUser.getStartDate(), mUser.getEndDate(), mUser.getVersion());

                    ApiCaller caller = new ApiCaller(getActivity());
<<<<<<< HEAD
=======
                    // call server to update user object
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4
                    caller.updateUser(user);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /* do nothing when user clicks cancel button except close the fragment */
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

<<<<<<< HEAD
    public static EditBioFragment newInstance(User user) {
=======
    // custom constructor for dialogFragment
    public static EditBioFragment newInstance(User user) {
        // instantiate user object
>>>>>>> 4b1dd3d5679fe04d4aefbcfaf9027c4bd4c20dc4
        mUser = user;

        EditBioFragment editBioFragment = new EditBioFragment();
        Bundle args = new Bundle();

        editBioFragment.setArguments(args);
        return editBioFragment;
    }

}
