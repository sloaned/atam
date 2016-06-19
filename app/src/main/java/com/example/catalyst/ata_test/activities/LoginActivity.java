package com.example.catalyst.ata_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import com.example.catalyst.ata_test.R;


/**
 * Up starting the app, the login activity is started.
 */
public class LoginActivity extends AppCompatActivity {

    //Basic Android Activity setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //This method was overriden so the user can't go back an activity after loggign out.
    @Override
    public void onBackPressed() {
        return;
    }
}
