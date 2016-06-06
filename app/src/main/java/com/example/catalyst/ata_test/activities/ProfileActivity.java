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
 * Created by dsloane on 4/28/2016.
 * Upon clicking the profile button icon, or by tapping an employee, the user is redirected to this
 * activity.
 */
public class ProfileActivity extends AppCompatActivity {

    //Setting up a tag for logging purposes.
    private static final String TAG = ProfileActivity.class.getSimpleName();

    //Using butterknife to hook the search view
    @Bind(R.id.action_search)SearchView searchView;

    //Using butterknife to hook the logo.
    @Bind(R.id.action_logo) ImageView logo;

    // an invisible layout before the searchbar to prevent the searchbar from automatically
    // gaining focus on page load
    @Bind(R.id.focus_layout) LinearLayout focus;

    private TopBar topBar;

    //Basic Android Activity setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        topBar = new TopBar();
        logo = topBar.setLogo(this, logo);
        searchView = topBar.getTopBar(this, searchView);

        searchView.clearFocus();
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
