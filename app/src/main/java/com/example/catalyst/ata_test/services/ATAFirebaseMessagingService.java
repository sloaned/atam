package com.example.catalyst.ata_test.services;

import com.google.firebase.messaging.FirebaseMessagingService;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.notifications.NotificationReceiver;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

/**
 * Created by dsloane on 6/13/2016.
 */
public class ATAFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = ATAFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map data = message.getData();
        Log.d(TAG, "message = " + message);
        Log.d(TAG, "data = " + data);

        NotificationReceiver receiver = new NotificationReceiver();
        receiver.notify(this, "Notification", "You received a kudo!");
    }

}
