package com.example.catalyst.ata_test.menus;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.activities.FeedActivity;
import com.example.catalyst.ata_test.activities.ProfileActivity;
import com.example.catalyst.ata_test.fragments.SettingsFragment;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

/**
 * Created by dsloane on 4/28/2016.
 */
/* defines the bottom navigation bar for all views */
public class BottomBar {

    //Setting up a tag for logging purposes.
    private final static String TAG = BottomBar.class.getSimpleName();

    //Context used by other classes
    private Context mContext;

    //View is used to hook into the xml with bottom bar attached.
    private View mView;

    //Declared here so later the home button can be hooked.
    private RelativeLayout homeButton;

    //Declared here so later the profile button can be hooked.
    private RelativeLayout profileButton;

    //Declared here so later the feedbutton can be hooked.
    private RelativeLayout feedButton;

    //Declared here so later the settings button can be hooked.
    private RelativeLayout settingsButton;

    //Api Caller declared here so network calls can be made later.
    private ApiCaller caller;

    //
    private SharedPreferences prefs;


    public View getBottomBar(Context context, View view) {

        //Context assigned a value here so it can be used by other methods in this class.
        mContext = context;

        //This view is to be returned at the end of this method.
        //This is used to get the hooks to the various items on the bottom bar.
        mView = view;

        //Set up the network caller here
        caller = new ApiCaller(mContext);

        //Get the system preference.
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        //Assign the home button
        homeButton = (RelativeLayout) view.findViewById(R.id.home_button);

        //Assign the profile button
        profileButton = (RelativeLayout) view.findViewById(R.id.my_profile_button);

        //Assign the feedbutton
        feedButton = (RelativeLayout) view.findViewById(R.id.feed_button);

        //Assign the settings button.
        settingsButton = (RelativeLayout) view.findViewById(R.id.settings_button);

        //Assign an OnClickListener
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });

        //Assign an OnClickListener
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });

        //Assign an OnClickListener
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeed();
            }
        });

        //Assign an OnClickListener
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        return mView;
    }

    //Upon have the settings button clicked,
    public void openSettings() {

        //Show the settings dialog.
        DialogFragment dialog = SettingsFragment.newInstance();
        if (dialog.getDialog() != null) {
            /* touching outside the modal will close it */
            dialog.getDialog().setCanceledOnTouchOutside(true);
        }
        try {

            //Cast the context to an activity so that allows access to the fragment manager.
            final Activity activity = (Activity) mContext;

            //Show the dialog to the user, use the fragment manager for this.
            dialog.show(activity.getFragmentManager(), "dialog");

        } catch (ClassCastException e) {
            //If Activity casting fails, display a message.
            Log.d(TAG, "Error getting fragment manager");
        }

    }

    //Upon clicking the profile button, this method gets called.
    public void openMyProfile() {

        //Get the user ID
        String userId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

        //Start the Profile activity, but pass the users ID to the profile page.
        Intent intent = new Intent(mContext, ProfileActivity.class).putExtra("UserId", userId);

        //Start the activity.
        mContext.startActivity(intent);

        /* make activity transition seamless */
        ((Activity)mContext).overridePendingTransition(0, 0);
    }

    //Upon clicking the dashboard icon, this method gets called.
    public void openDashboard() {

        //If the user is on the dashboard, and the dashboard button gets clicked, don't do anything.
        if (!(mContext instanceof DashboardActivity)) {
            //If the user is not on the dashboard, and the dashboard button gets clicked,
            //redirect to the dashboard.
            Intent intent = new Intent(mContext, DashboardActivity.class);

            //Starts the transition to the dashboard.
            mContext.startActivity(intent);

            /* make activity transition seamless */
            ((Activity)mContext).overridePendingTransition(0, 0);
        }
    }

    //Upon clicking the feed button, this method gets called.
    public void openFeed() {

        //If the user is not already on the feed page page
        if (!(mContext instanceof FeedActivity)) {


            Intent intent = new Intent(mContext, FeedActivity.class);

            //go to the feed page.
            mContext.startActivity(intent);

            /* make activity transition seamless */
            ((Activity)mContext).overridePendingTransition(0, 0);
        }
    }
}
