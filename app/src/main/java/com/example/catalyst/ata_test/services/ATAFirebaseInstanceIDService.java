package com.example.catalyst.ata_test.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.catalyst.ata_test.util.SharedPreferencesConstants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by dsloane on 6/13/2016.
 */
public class ATAFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private final String TAG = ATAFirebaseInstanceIDService.class.getSimpleName();

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.

        Log.d(TAG, "firebase instance = " + FirebaseInstanceId.getInstance());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = prefs.edit();

        mEditor.putString(SharedPreferencesConstants.FCM_TOKEN, refreshedToken).apply();



        // TODO: Implement this method to send any registration to your app's servers.
        //  sendRegistrationToServer(refreshedToken);
    }
}