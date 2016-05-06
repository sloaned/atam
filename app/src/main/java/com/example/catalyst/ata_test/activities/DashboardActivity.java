package com.example.catalyst.ata_test.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.internal.view.SupportMenu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.DashboardAdapter;
import com.example.catalyst.ata_test.fragments.DashboardFragment;
import com.example.catalyst.ata_test.menus.TopBar;

import org.greenrobot.eventbus.EventBus;

public class DashboardActivity extends AppCompatActivity {

    private SearchView searchView;
    private View listView;
    private TopBar topBar;

    private static final String TAG = DashboardActivity.class.getSimpleName();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        searchView = (SearchView) findViewById(R.id.action_search);
        listView = findViewById(R.id.listView);

        topBar = new TopBar();
        searchView = topBar.getTopBar(this, searchView);
    }


    @Override
    public void onResume() {
        super.onResume();
        topBar.clearSearch();
    }


}
