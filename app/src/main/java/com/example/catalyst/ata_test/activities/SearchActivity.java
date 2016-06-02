package com.example.catalyst.ata_test.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.ProfileTabAdapter;
import com.example.catalyst.ata_test.adapters.SearchTabAdapter;
import com.example.catalyst.ata_test.adapters.UserSearchResultAdapter;
import com.example.catalyst.ata_test.events.InitialSearchEvent;
import com.example.catalyst.ata_test.events.SearchEvent;
import com.example.catalyst.ata_test.menus.TopBar;
import com.example.catalyst.ata_test.models.SearchResult;
import com.example.catalyst.ata_test.models.Team;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/26/2016.
 */

// currently only searches for users, not teams/projects
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private SearchTabAdapter adapter;
   // private ApiCaller caller;

    @Bind(R.id.action_logo) ImageView logo;
    @Bind(R.id.tab_layout) TabLayout profileTabs;
    private ArrayList<User> userResults = new ArrayList<User>();
    private ArrayList<Team> teamResults = new ArrayList<Team>();


    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Team> teams = new ArrayList<Team>();
    private SearchView searchView;
    private Bundle bundle;

    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        profileTabs.addTab(profileTabs.newTab().setText("PEOPLE"));
        profileTabs.addTab(profileTabs.newTab().setText("TEAMS"));
        profileTabs.setTabGravity(TabLayout.GRAVITY_FILL);


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
        searchTerm = query;

        // put the query that was typed in during the calling activity back into the searchbar
        searchView.post(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery(query, true);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTerm = query;
                intent.putExtra(SearchManager.QUERY, query);
                searchUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchTerm = newText;

                // change the search query in the intent
                intent.putExtra(SearchManager.QUERY, newText);

                if (searchTerm.length() > 2) {
                    searchUsers(searchTerm);
                    searchTeams(searchTerm);
                    return true;
                } else if (searchTerm.length() == 2) {
                    ApiCaller caller = new ApiCaller(getApplicationContext());
                    caller.search(searchTerm);
                }
                userResults.clear();
                teamResults.clear();

                return false;
            /*
                if (!newText.equals("")) { // the query is not empty
                    searchUsers(newText);
                    return true;
                } else { // empty query - do not display any results
                    results.clear();
                    adapter.notifyDataSetChanged();
                    return false;
                }  */
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

     //   adapter = new UserSearchResultAdapter(this, userResults);

      //  listView.setAdapter(adapter);

        /*
            open profile of given user when a search result is clicked
            (should be updated when results include teams/projects to check
            the type of result and to start appropriate activity)
         */
     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);

                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class)
                        .putExtra("User", user.getId());
                startActivity(intent);
            }
        });  */


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

        userResults.clear();
        // ignore case when performing queries
        query = query.toLowerCase();
        for (User user : users) {
            String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
            if ((user.getFirstName() != null && user.getLastName() != null) && name.contains(query)) {
                userResults.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void searchTeams(String query) {

        teamResults.clear();
        // ignore case when performing queries
        query = query.toLowerCase();
        for (Team team : teams) {
            String name = team.getName().toLowerCase();
            if (team.getName() != null && name.contains(query)) {
                teamResults.add(team);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onSearchEvent(SearchEvent event) {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new SearchTabAdapter(getSupportFragmentManager(), profileTabs.getTabCount(), userResults, teamResults);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profileTabs));


        profileTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "tab selected = " + tab.getText() + ", position = " + tab.getPosition() + ", viewpager current item = " + viewPager.getCurrentItem());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "tab unselected = " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "tab reselected = " + tab.getText());
            }
        });


        users.clear();
        teams.clear();

        for (SearchResult result : event.getResults()) {
            if (result.getUser() != null) {
                users.add(result.getUser());
            } else if (result.getTeam() != null) {
                teams.add(result.getTeam());
            }
        }

        searchUsers(searchTerm);
        searchTeams(searchTerm);
    }
/*
    @Subscribe
    public void onInitialSearchEvent(InitialSearchEvent event) {
        this.users = event.userList;

        Intent intent = getIntent();


        // check for query either from intent or from bundle (query saved in bundle on orientation change)

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
            userResults.clear();
            for (User user : bundle.<User>getParcelableArrayList("USERRESULTS")) {
                userResults.add(user);
            }
            adapter.notifyDataSetChanged();
        }
        intent.putExtra(SearchManager.QUERY, (String) null);
    }  */

    /* save the instance state so that query and search results do not disappear
        upon device orientation change
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("QUERY", searchView.getQuery().toString());
        savedInstanceState.putParcelableArrayList("USERS", users);
        savedInstanceState.putParcelableArrayList("TEAMS", teams);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        searchTerm = savedInstanceState.getString("QUERY");
        users = savedInstanceState.getParcelableArrayList("USERS");
        teams = savedInstanceState.getParcelableArrayList("TEAMS");

        searchUsers(searchTerm);

    }

}
