package com.example.catalyst.ata_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.menus.TopBar;

/**
 * Created by dsloane on 4/28/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private SearchView searchView;
    private TopBar topBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        searchView = (SearchView) findViewById(R.id.action_search);

        topBar = new TopBar();

        searchView = topBar.getTopBar(this, searchView);

        searchView.clearFocus();
    }

    @Override
    public void onResume() {
        super.onResume();

        topBar.clearSearch();
    }


}
