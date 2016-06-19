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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.SearchTabAdapter;
import com.example.catalyst.ata_test.events.SearchEvent;
import com.example.catalyst.ata_test.events.UpdateSearchEvent;
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

public class SearchActivity extends AppCompatActivity {

    //Setting up a tag for logging purposes.
    private static final String TAG = SearchActivity.class.getSimpleName();

    //Declare adapter as a class variable.
    private SearchTabAdapter adapter;

    //Using Butterknife to hook the logo
    @Bind(R.id.action_logo) ImageView logo;

    //Using ButterKnife to hook the cancel search button
    @Bind(R.id.action_cancel_search) TextView cancelSearch;

    //Using Butterknife to hook the profileTabs
    @Bind(R.id.tab_layout) TabLayout profileTabs;

    //Using Butterknife to hook the View Pager
    @Bind(R.id.pager) ViewPager viewPager;

    //After a database call happens, the results for users are stored in this array.
    private ArrayList<User> users = new ArrayList<User>();

    //After a database call happens, the results for teams are stored in this array.
    private ArrayList<Team> teams = new ArrayList<Team>();

    //This pulls data from the users array. This list is edited with each additional character
    //add to the search feild after two characters.
    private ArrayList<User> userResults = new ArrayList<User>();

    //This pulls data from the teams array. This list is edited with each additional character
    //add to the search feild after two characters.
    private ArrayList<Team> teamResults = new ArrayList<Team>();

    //Search declared a class local variable to be accessed by inner classes and interface instantiation.
    private SearchView searchView;

    // the current value of the query
    private String searchTerm;

    // the current length of the query
    private int searchLength = 0;

    //Basic Android Activity setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        // add two tabs and give them labels
        profileTabs.addTab(profileTabs.newTab().setText("PEOPLE"));
        profileTabs.addTab(profileTabs.newTab().setText("TEAMS"));

        // center the tabs horizontally
        profileTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        searchView = (SearchView) findViewById(R.id.action_search);

        // add top bar to the view and give the logo functionality as another home button
        TopBar topBar = new TopBar();
        logo = topBar.setLogo(this, logo);

        // keep searchbar maximized
        searchView.setIconifiedByDefault(false);

        // add query hint to search bar ("Search for users and teams...")
        searchView.setQueryHint(getResources().getString(R.string.search_query_hint));

        /* set font size for search bar */
        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));

        /* called when cancel button next to search bar is pressed.
             Closes search activity and returns to previous activity
        */
        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /* intent passed from calling activity. Declared final so it can be used
             in the onQueryTextListener
         */
        final Intent intent = getIntent();

        // set searchTerm to current value of the query passed in the intent
        searchTerm = intent.getStringExtra(SearchManager.QUERY);

        // set length of current query (used to determine whether to perform a new search or not)
        searchLength = searchTerm.length();

        // place current query into the searchbar
        searchView.setQuery(searchTerm, true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /* hitting enter button has no effect on search */
                return false;
            }

            // perform search and filter results as user types and deletes
            @Override
            public boolean onQueryTextChange(String newText) {

                /* change the intent's saved query string as user types/deletes */
                intent.putExtra(SearchManager.QUERY, newText);

                /* change search term as user types/deletes */
                searchTerm = newText;

                /* if current search term is currently greater than two characters (or this action was deleting back to two characters),
                    then we already have base lists of users/teams to filter from, and do not need to perform another network call. We
                    can simply search the existing lists of users/teams
                 */
                if (searchTerm.length() > 2 || (searchTerm.length() == 2 && searchLength == 3)) {
                    // keep track of how many characters are currently entered
                    searchLength = searchTerm.length();
                    search(searchTerm);
                    return true;
                } /* Two new characters have just been typed. Perform a network call to search based on those two characters, and
                    populate base user/team lists with the results
                 */
                else if (searchTerm.length() == 2 && searchLength == 1) {
                    // keep track of how many characters are currently entered
                    searchLength = searchTerm.length();

                   // savedQuery = searchTerm;
                    ApiCaller caller = new ApiCaller(getApplicationContext());
                    caller.search(searchTerm);
                    return true;
                }

                /* otherwise, there are fewer than two characters entered. Clear all data and results. */

                users.clear();
                teams.clear();
                userResults.clear();
                teamResults.clear();

                // keep track of how many characters are currently entered
                searchLength = searchTerm.length();

                // have EventBus tell tab fragments to update lists
                EventBus.getDefault().post(new UpdateSearchEvent(userResults, teamResults));
                return false;
            }
        });

        // create new adapter for the search tabs and pass in the lists of results
        adapter = new SearchTabAdapter(getSupportFragmentManager(), profileTabs.getTabCount(), userResults, teamResults);

        // set the adapter
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profileTabs));

        profileTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    // perform local search based on filtering down the current user/team lists
    public void search(String query) {
        // clear current results lists before repopulating them
        userResults.clear();
        teamResults.clear();
        // ignore case when performing queries
        query = query.toLowerCase();

        // match query with user names in list
        for (User user : users) {
            String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
            if ((user.getFirstName() != null && user.getLastName() != null) && name.contains(query)) {
                userResults.add(user);
            }
        }

        // match query with team names in list
        for (Team team : teams) {
            String name = team.getName().toLowerCase();
            if (team.getName() != null && name.contains(query)) {
                teamResults.add(team);
            }
        }

        EventBus.getDefault().post(new UpdateSearchEvent(userResults, teamResults));
    }

    /* called when network search has completed.
        populates base user/team lists and calls initial search function
     */
    @Subscribe
    public void onSearchEvent(SearchEvent event) {

        /* clear base lists of users/teams before repopulating them */
        users.clear();
        teams.clear();

        /*
            search result will contain either a user or a team. Add to appropriate list
         */
        for (SearchResult result : event.getResults()) {
            if (result.getUser() != null) {
                users.add(result.getUser());
            } else if (result.getTeam() != null) {
                teams.add(result.getTeam());
            }
        }

        /*
            do a search matching current query to user/team lists. As query is currently
            two characters long, results lists will be identical to base user/team lists
         */
        search(searchTerm);
    }

    /* save the instance state so that query and search results do not disappear
        upon device orientation change
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // save current info to restore when activity resumes after orientation change
        savedInstanceState.putString("QUERY", searchView.getQuery().toString());
        savedInstanceState.putParcelableArrayList("USERS", users);
        savedInstanceState.putParcelableArrayList("TEAMS", teams);

        // save which tab is selected
        savedInstanceState.putInt("tabState", profileTabs.getSelectedTabPosition());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // restore info from previous orientation
        searchTerm = savedInstanceState.getString("QUERY");
        searchLength = searchTerm.length();
        users = savedInstanceState.getParcelableArrayList("USERS");
        teams = savedInstanceState.getParcelableArrayList("TEAMS");

        // stay on same tab as was selected in previous orientation
        TabLayout.Tab tab = profileTabs.getTabAt(savedInstanceState.getInt("tabState"));
        tab.select();

        // perform search if current query is long enough
        if (searchLength >= 2) {
            search(searchTerm);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Registering the Eventbus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        //Unregistering the Eventbus.
        EventBus.getDefault().unregister(this);

        super.onPause();
    }
}
