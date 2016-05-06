package com.example.catalyst.ata_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.menus.TopBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 5/3/2016.
 */
public class TeamActivity extends AppCompatActivity {

    private final static String TAG = TeamActivity.class.getSimpleName();

    @Bind(R.id.action_search)SearchView searchView;
    @Bind(R.id.action_logo) ImageView logo;
    private TopBar topBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        ButterKnife.bind(this);

        topBar = new TopBar();

        logo = topBar.setLogo(this, logo);
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
