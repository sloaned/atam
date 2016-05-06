package com.example.catalyst.ata_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.menus.TopBar;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dsloane on 5/3/2016.
 */
public class TeamActivity extends AppCompatActivity {

    private final static String TAG = TeamActivity.class.getSimpleName();

    private SearchView searchView;
    private TopBar topBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        searchView = (SearchView) findViewById(R.id.action_search);

        topBar = new TopBar();

        searchView = topBar.getTopBar(this, searchView);
    }

    @Override
    public void onStart() {
        super.onStart();
       // EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
     //   EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        topBar.clearSearch();
    }
}
