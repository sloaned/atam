package com.example.catalyst.ata_test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.menus.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
    After the user logs in, they are redirected to this activity.
 */
public class DashboardActivity extends AppCompatActivity {

    //Using Butterknife to hook the searchview.
    @Bind(R.id.action_search)SearchView searchView;

    //Using butterknife to hook the listView
    @Bind(R.id.listView) View listView;

    //Using butterknife to hook the Logo.
    @Bind(R.id.action_logo) ImageView logo;

    // an invisible layout before the searchbar to prevent the searchbar from automatically
    // gaining focus on page load
    @Bind(R.id.focus_layout) LinearLayout focus;
    private TopBar topBar;

    //Setting up a tag for logging purposes.
    private static final String TAG = DashboardActivity.class.getSimpleName();

    //Basic Android Activity setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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

    /*
        close/minimize app when back button pressed
     */
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}
