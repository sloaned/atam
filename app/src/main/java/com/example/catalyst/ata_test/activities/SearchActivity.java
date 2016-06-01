package com.example.catalyst.ata_test.activities;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.SearchResultAdapter;
import com.example.catalyst.ata_test.data.DBHelper;
import com.example.catalyst.ata_test.events.InitialSearchEvent;
import com.example.catalyst.ata_test.menus.TopBar;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/26/2016.
 */

// currently only searches for users, not teams/projects
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private SearchResultAdapter adapter;
   // private ApiCaller caller;

    @Bind(android.R.id.list)ListView listView;
    @Bind(R.id.action_logo) ImageView logo;
    private ArrayList<User> results = new ArrayList<User>();
    private ArrayList<User> users = new ArrayList<User>();
    private SearchView searchView;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

      //  caller = new ApiCaller(this);

        final Intent intent = getIntent();

        searchView = (SearchView) findViewById(R.id.action_search);

        TopBar topBar = new TopBar();
        logo = topBar.setLogo(this, logo);

        bundle = savedInstanceState;

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_query_hint));

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));

        EventBus.getDefault().register(this);

        final String query = intent.getStringExtra(SearchManager.QUERY);

        // put the query that was typed in during the calling activity back into the searchbar
        searchView.post(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery(query, true);
            }
        });

        // creates an arrayList of all Catalyst employees
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ApiCaller caller = new ApiCaller(getApplicationContext());
                caller.getAllUsers();
            }
        };
        new Thread(runnable).start();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                intent.putExtra(SearchManager.QUERY, query);
                searchUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // change the search query in the intent
                intent.putExtra(SearchManager.QUERY, newText);


                if (!newText.equals("")) { // the query is not empty
                    searchUsers(newText);
                    return true;
                } else { // empty query - do not display any results
                    results.clear();
                    adapter.notifyDataSetChanged();
                    return false;
                }
            }
        });

        TextView cancelSearch = (TextView) findViewById(R.id.action_cancel_search);

        /* called when cancel button next to search bar is pressed.
             Closes search activity and returns to previous activity
        */
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new SearchResultAdapter(this, results);

        listView.setAdapter(adapter);

        /*
            open profile of given user when a search result is clicked
            (should be updated when results include teams/projects to check
            the type of result and to start appropriate activity)
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);

                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class)
                        .putExtra("User", user.getId());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    public void searchUsers(String query) {

        results.clear();

        // ignore case when performing queries
        query = query.toLowerCase();
        for (User user : users) {
            String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
            if ((user.getFirstName() != null && user.getLastName() != null) && name.contains(query)) {
                results.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onInitialSearchEvent(InitialSearchEvent event) {
        this.users = event.userList;

        Intent intent = getIntent();

        /*
            check for query either from intent or from bundle (query saved in bundle on orientation change)
         */
        String savedInstanceQuery = "";
        if (bundle != null && bundle.getString("QUERY") != null) {
            savedInstanceQuery = ((String) bundle.getString("QUERY"));
        }
        final String query = intent.getStringExtra(SearchManager.QUERY) != null ? intent.getStringExtra(SearchManager.QUERY) : savedInstanceQuery;

        // perform search on query
        searchView.post(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery(query, true);
            }
        });

        // query isn't empty, and/or query has been changed since orientation change
        if (!query.equals("") && !query.equals(savedInstanceQuery)) {

            searchView.post(new Runnable() {
                @Override
                public void run() {
                    searchView.setQuery(query, true);
                }
            });

            searchUsers(query);
        } else if(!query.equals("")) { // orientation change, query not empty
            results.clear();
            for (User user : bundle.<User>getParcelableArrayList("RESULTS")) {
                results.add(user);
            }
            adapter.notifyDataSetChanged();
        }
        intent.putExtra(SearchManager.QUERY, (String) null);
    }

    /* save the instance state so that query and search results do not disappear
        upon device orientation change
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("QUERY", searchView.getQuery().toString());
        savedInstanceState.putParcelableArrayList("RESULTS", results);
        super.onSaveInstanceState(savedInstanceState);
    }

}
