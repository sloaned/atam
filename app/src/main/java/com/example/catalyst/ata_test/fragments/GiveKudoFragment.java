package com.example.catalyst.ata_test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.events.BioChangeEvent;
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dsloane on 6/3/2016.
 */
public class GiveKudoFragment extends DialogFragment {

    private static User mUser;
    private ApiCaller caller;
    private SharedPreferences prefs;

    public GiveKudoFragment() {}

    private final String TAG = GiveKudoFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        caller = new ApiCaller(getActivity());
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        final View giveKudoView = inflater.inflate(R.layout.fragment_give_kudo, null);
        final EditText kudoText = (EditText) giveKudoView.findViewById(R.id.give_kudo_textarea);
        TextView giveKudoLabel = (TextView) giveKudoView.findViewById(R.id.give_kudo_label);

        giveKudoLabel.setText("Give kudo to " + mUser.getFirstName());

        builder.setView(giveKudoView);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String kudoComment = kudoText.getText().toString();
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String dateString = sdf.format(date);
                User reviewer = new User();
                String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);
                reviewer.setId(myId);

                Kudo kudo = new Kudo(mUser.getId(), reviewer, kudoComment, dateString);

                if (!myId.equals(null) && myId != null && !myId.equals(mUser.getId())) {
                    caller.postKudo(kudo);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    public static GiveKudoFragment newInstance(User user) {
        mUser = user;

        GiveKudoFragment fragment = new GiveKudoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}