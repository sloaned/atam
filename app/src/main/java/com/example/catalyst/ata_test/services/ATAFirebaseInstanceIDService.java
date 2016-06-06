package com.example.catalyst.ata_test.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Dan on 6/5/2016.
 */
public class ATAFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private final String TAG = ATAFirebaseInstanceIDService.class.getSimpleName();

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
      //  sendRegistrationToServer(refreshedToken);
    }
}
