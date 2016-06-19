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
 * Created by dsloane on 4/29/2016.
 */
public class FeedActivity extends AppCompatActivity {

    //Setting up a tag for logging purposes.
    private static final String TAG = FeedActivity.class.getSimpleName();

    //The search bar.
    @Bind(R.id.action_search)SearchView searchView;

    //The logo located in the top left of the screen.
    @Bind(R.id.action_logo) ImageView logo;

    // an invisible layout before the searchbar to prevent the searchbar from automatically
    // gaining focus on page load
    @Bind(R.id.focus_layout) LinearLayout focus;
    
    private TopBar topBar;

    /*
        Basic Android Activity Setup.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //Typical butterknife binding.
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
