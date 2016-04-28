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
import com.example.catalyst.ata_test.fragments.DashboardFragment;

public class DashboardActivity extends AppCompatActivity {

    private SearchView searchView;// = (SearchView) findViewById(R.id.action_search);
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.action_search);
        view = findViewById(R.id.listView);
        //view.requestFocus();

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search for users and teams...");



       // searchView.clearFocus();

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));
       // search_text.clearFocus();

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                    intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, "");
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });

        // getCurrentFocus().clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, query);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                    intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, newText);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.post(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("", true);
            }
        });

        view.requestFocus();
    }


}
