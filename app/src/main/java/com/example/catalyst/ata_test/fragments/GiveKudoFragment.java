package com.example.catalyst.ata_test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.events.BioChangeEvent;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dsloane on 6/3/2016.
 */
public class GiveKudoFragment extends DialogFragment {

    private static String kudoReceiverId;
    private ApiCaller caller;

    public GiveKudoFragment() {}

    private final String TAG = GiveKudoFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        caller = new ApiCaller(getActivity());

        final View giveKudoView = inflater.inflate(R.layout.fragment_give_kudo, null);
        final EditText kudoText = (EditText) giveKudoView.findViewById(R.id.give_kudo_textarea);

        builder.setView(giveKudoView);

        builder.setPositiveButton("Give it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String kudoComment = kudoText.getText().toString();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public static GiveKudoFragment newInstance(String userId) {
        kudoReceiverId = userId;

        GiveKudoFragment fragment = new GiveKudoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
