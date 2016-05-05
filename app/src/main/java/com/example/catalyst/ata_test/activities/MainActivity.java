package com.example.catalyst.ata_test.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.catalyst.ata_test.R;

/**
 * Created by dsloane on 4/22/2016.
 */
public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = prefs.edit();


        if (!loggedIn()) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!loggedIn()) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    private boolean loggedIn() {

//        String loggedInUser = prefs.getString(SharedPreferencesConstants.JESESSIONID, null);
//        Log.d(TAG, "loggedInUser = " + loggedInUser);
//        if (loggedInUser == null || loggedInUser.equals(null)) {
//            return false;
//        }
        return true;
    }
}
