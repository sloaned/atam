package com.example.catalyst.ata_test.menus;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.activities.FeedActivity;
import com.example.catalyst.ata_test.activities.ProfileActivity;
import com.example.catalyst.ata_test.events.ProfileEvent;
import com.example.catalyst.ata_test.fragments.SettingsFragment;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

/**
 * Created by dsloane on 4/28/2016.
 */
/* defines the bottom navigation bar for all views */
public class BottomBar {

    private final static String TAG = BottomBar.class.getSimpleName();

    private Context mContext;
    private View mView;

    private LinearLayout bottomBar;
    private RelativeLayout homeButton;
    private RelativeLayout profileButton;
    private RelativeLayout feedButton;
    private RelativeLayout settingsButton;

    private User mUser;
    private ApiCaller caller;

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    public View getBottomBar(Context context, View view) {
        mContext = context;
        mView = view;

        caller = new ApiCaller(mContext);
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = prefs.edit();

        bottomBar = (LinearLayout) view.findViewById(R.id.bottom_bar);
        homeButton = (RelativeLayout) view.findViewById(R.id.home_button);
        profileButton = (RelativeLayout) view.findViewById(R.id.my_profile_button);
        feedButton = (RelativeLayout) view.findViewById(R.id.feed_button);
        settingsButton = (RelativeLayout) view.findViewById(R.id.settings_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfile();
            }
        });

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeed();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        return mView;
    }

    public void openSettings() {
        DialogFragment dialog = SettingsFragment.newInstance();
        if (dialog.getDialog() != null) {

            /* touching outside the modal will close it */
            dialog.getDialog().setCanceledOnTouchOutside(true);
        }
        try {
            final Activity activity = (Activity) mContext;
            dialog.show(activity.getFragmentManager(), "dialog");
        } catch (ClassCastException e) {
            Log.d(TAG, "Error getting fragment manager");
        }

    }

    public void openMyProfile() {

        String userId = prefs.getString(SharedPreferencesConstants.USER_ID, null);

        caller.getUserById(userId);

       /* Intent intent = new Intent(mContext, ProfileActivity.class);
        mContext.startActivity(intent);  */

        /* make activity transition seamless */
     //   ((Activity)mContext).overridePendingTransition(0, 0);
    }

    public void openDashboard() {
        if (mContext instanceof DashboardActivity) {

        } else {
            Intent intent = new Intent(mContext, DashboardActivity.class);
            mContext.startActivity(intent);

            /* make activity transition seamless */
            ((Activity)mContext).overridePendingTransition(0, 0);
        }
    }

    public void openFeed() {
        if (mContext instanceof FeedActivity) {

        } else {
            Intent intent = new Intent(mContext, FeedActivity.class);
            mContext.startActivity(intent);

            /* make activity transition seamless */
            ((Activity)mContext).overridePendingTransition(0, 0);
        }
    }


}
