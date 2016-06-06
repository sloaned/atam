package com.example.catalyst.ata_test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.menus.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 5/3/2016.
 */
public class TeamActivity extends AppCompatActivity {

    //Setting up a tag for logging purposes.
    private final static String TAG = TeamActivity.class.getSimpleName();

    //Using Butterknife to hook the searchViwe
    @Bind(R.id.action_search)SearchView searchView;

    //Using Butterknife to hook the logo.
    @Bind(R.id.action_logo) ImageView logo;

    // an invisible layout before the searchbar to prevent the searchbar from automatically
    // gaining focus on page load
    @Bind(R.id.focus_layout) LinearLayout focus;


    private TopBar topBar;

    //Basic Android Activity Setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        ButterKnife.bind(this);

        topBar = new TopBar();

        logo = topBar.setLogo(this, logo);
        searchView = topBar.getTopBar(this, searchView);
    }


    @Override
    public void onResume() {
        //Calling the super to do the default onResume functionality.
        super.onResume();

        //clears the search bar
        topBar.clearSearch();

        //refocuses the invisible element.
        focus.requestFocus();
    }
}
