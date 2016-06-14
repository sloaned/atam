package com.example.catalyst.ata_test.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.catalyst.ata_test.R;

import java.util.Date;

/**
 * Created by dsloane on 6/14/2016.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void notify (Context context, String title, String message) {

        Date date = new Date();
        int id = (int) ((date.getTime())/1000);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ata)
                        .setContentTitle(title)
                        .setContentText(message);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(id, builder.build());
    }
}
