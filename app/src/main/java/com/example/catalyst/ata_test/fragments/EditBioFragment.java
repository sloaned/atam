package com.example.catalyst.ata_test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.catalyst.ata_test.R;

/**
 * Created by dsloane on 4/28/2016.
 */
public class EditBioFragment extends DialogFragment {

    private static BioChangeListener callback;
    private static ProfileFragment mProfileFragment;
    private static String mBioText;

    public EditBioFragment() {}

    private final static String TAG = EditBioFragment.class.getSimpleName();

    public interface BioChangeListener {
        public void changeBio (String bio);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        try {
            callback = (BioChangeListener) mProfileFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement BioChangeListener interface");
        }

        Log.d(TAG, "callback = " + callback);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View editView = inflater.inflate(R.layout.fragment_editbio, null);

        final EditText bioText = (EditText) editView.findViewById(R.id.edit_bio_textarea);
        bioText.setText(mBioText);

        builder.setView(editView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String bio = bioText.getText().toString();
                Log.d(TAG, bio);
                callback.changeBio(bio);
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public static EditBioFragment newInstance(String bio, ProfileFragment fragment) {
        mProfileFragment = fragment;
        mBioText = bio;


        EditBioFragment editBioFragment = new EditBioFragment();
        Bundle args = new Bundle();

        editBioFragment.setArguments(args);
        return editBioFragment;
    }

}
