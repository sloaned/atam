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
import com.example.catalyst.ata_test.models.Kudo;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dsloane on 6/3/2016.
 */
public class GiveKudosFragment extends DialogFragment {

    // the user receiving the kudos
    private static User mUser;

    // instance of the network caller class
    private ApiCaller caller;

    // local storage instance to get logged in user's user id
    private SharedPreferences prefs;

    public GiveKudosFragment() {}

    private final String TAG = GiveKudosFragment.class.getSimpleName();

    // basic Android setup for dialog fragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // instantiate network class
        caller = new ApiCaller(getActivity());

        // instantiate shared preferences instance
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // inflate view with xml layout
        View giveKudoView = inflater.inflate(R.layout.fragment_give_kudo, null);

        // the textarea where the kudo comment is written
        final EditText kudoText = (EditText) giveKudoView.findViewById(R.id.give_kudo_textarea);

        // fragment title
        TextView giveKudoLabel = (TextView) giveKudoView.findViewById(R.id.give_kudo_label);
        giveKudoLabel.setText("Give Kudos to " + mUser.getFirstName());

        // bind view to fragment builder
        builder.setView(giveKudoView);

        // add submit functionality on button press
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // get the value of the entered kudos
                String kudoComment = kudoText.getText().toString();
                // create a new date set to current time, save it as a string
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                String dateString = sdf.format(date);

                // build user object, set id to that of currently logged in user
                User reviewer = new User();
                String myId = prefs.getString(SharedPreferencesConstants.USER_ID, null);
                reviewer.setId(myId);

                // build kudo object to send to server
                Kudo kudo = new Kudo(mUser.getId(), reviewer, kudoComment, dateString);

                /*
                    basic error checking to make sure the logged in user is not giving
                    themselves kudos (this should never fail)
                 */
                if (!myId.equals(null) && myId != null && !myId.equals(mUser.getId())) {
                    caller.postKudo(kudo);
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close dialog fragment on cancel button click
            }
        });

        return builder.create();
    }

    // custom creator for the fragment
    public static GiveKudosFragment newInstance(User user) {
        mUser = user;

        GiveKudosFragment fragment = new GiveKudosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}