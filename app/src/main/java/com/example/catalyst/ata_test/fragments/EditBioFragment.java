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
import com.example.catalyst.ata_test.events.BioChangeEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dsloane on 4/28/2016.
 */
public class EditBioFragment extends DialogFragment {

    private static String mBioText;

    public EditBioFragment() {}

    private final static String TAG = EditBioFragment.class.getSimpleName();



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View editView = inflater.inflate(R.layout.fragment_editbio, null);

        final EditText bioText = (EditText) editView.findViewById(R.id.edit_bio_textarea);

        /* populate text area with current user bio */
        bioText.setText(mBioText);

        builder.setView(editView);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String bio = bioText.getText().toString();

                /* activates callback function in profile fragment to update the profile view */
                EventBus.getDefault().post(new BioChangeEvent(bio));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public static EditBioFragment newInstance(String bio) {
        mBioText = bio;

        EditBioFragment editBioFragment = new EditBioFragment();
        Bundle args = new Bundle();

        editBioFragment.setArguments(args);
        return editBioFragment;
    }

}
