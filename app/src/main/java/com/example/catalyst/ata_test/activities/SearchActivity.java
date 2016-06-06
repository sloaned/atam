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

    //Using Butterknife to hook the profileTabs
    @Bind(R.id.tab_layout) TabLayout profileTabs;

    //Using Butterknife to hook the View Pager
    @Bind(R.id.pager) ViewPager viewPager;

    //After a database call happens, the results for users are stored in this array.
    private ArrayList<User> users = new ArrayList<User>();

    //After a database call happens, the results for teams are stored in this array.
    private ArrayList<Team> teams = new ArrayList<Team>();

    //This pulls data from the users array. This list is edited with each additonal character
    //add to the search feild after two characters.
    private ArrayList<User> userResults = new ArrayList<User>();

    //This pulls data from the teams array. This list is edited with each additonal character
    //add to the search feild after two characters.
    private ArrayList<Team> teamResults = new ArrayList<Team>();

    //Search declared a class local variable to be accessed by inner classes and interface instantiation.
    private SearchView searchView;

    private String searchTerm;
    private String savedQuery;
    private int searchLength = 0;

    //Basic Android Activity setup.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Log.d(TAG, "in onCreate!!!!!");

        profileTabs.addTab(profileTabs.newTab().setText("PEOPLE"));
        profileTabs.addTab(profileTabs.newTab().setText("TEAMS"));
        profileTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        searchView = (SearchView) findViewById(R.id.action_search);

        TopBar topBar = new TopBar();
        logo = topBar.setLogo(this, logo);

        final Bundle bundle = savedInstanceState;

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_query_hint));

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));


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

        final Intent intent = getIntent();

        final String oldQuery = intent.getStringExtra(SearchManager.QUERY);
        searchTerm = oldQuery;
        searchLength = searchTerm.length();

        searchView.setQuery(oldQuery, true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                intent.putExtra(SearchManager.QUERY, newText);
                searchTerm = newText;

                if (searchTerm.length() > 2 || (searchTerm.length() == 2 && searchLength == 3)) {
                    searchLength = searchTerm.length();
                    search(searchTerm);
                    return true;
                } else if (searchTerm.length() == 2 && searchLength == 1) {
                    searchLength = searchTerm.length();
                    savedQuery = searchTerm;
                    ApiCaller caller = new ApiCaller(getApplicationContext());
                    caller.search(searchTerm);
                    return true;
                }

                searchLength = searchTerm.length();
                users.clear();
                teams.clear();
                userResults.clear();
                teamResults.clear();

                EventBus.getDefault().post(new UpdateSearchEvent(userResults, teamResults));
                return false;
            }
        });

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
    }

    public void search(String query) {
        userResults.clear();
        teamResults.clear();
        // ignore case when performing queries
        query = query.toLowerCase();
        for (User user : users) {
            String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
            Log.d(TAG, "userList in search, name = " + user.getFirstName() + " " + user.getLastName());
            if ((user.getFirstName() != null && user.getLastName() != null) && name.contains(query)) {
                userResults.add(user);
                Log.d(TAG, "Match with name " + user.getFirstName() + user.getLastName());
            }
        }
        for (Team team : teams) {
            String name = team.getName().toLowerCase();
            if (team.getName() != null && name.contains(query)) {
                teamResults.add(team);
            }
        }

        EventBus.getDefault().post(new UpdateSearchEvent(userResults, teamResults));
    }

    @Subscribe
    public void onSearchEvent(SearchEvent event) {

        Log.d(TAG, "in onSearchEvent!!!");

        Log.d(TAG, "adapter = " + adapter);
        if (adapter == null) {
            Log.d(TAG, "adapter was null!!!!!!");
            viewPager = (ViewPager) findViewById(R.id.pager);
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

        }


        users.clear();
        teams.clear();

        for (SearchResult result : event.getResults()) {
            Log.d(TAG, "onSearchEvent " + result.toString());
            if (result.getUser() != null) {
                users.add(result.getUser());
            } else if (result.getTeam() != null) {
                teams.add(result.getTeam());
            }
        }

        search(searchTerm);
    }

    /* save the instance state so that query and search results do not disappear
        upon device orientation change
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("QUERY", searchView.getQuery().toString());
        savedInstanceState.putString("SAVEDQUERY", savedQuery);
        savedInstanceState.putParcelableArrayList("USERS", users);
        savedInstanceState.putParcelableArrayList("TEAMS", teams);

        // save which tab is selected
        savedInstanceState.putInt("tabState", profileTabs.getSelectedTabPosition());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        searchTerm = savedInstanceState.getString("QUERY");
        searchLength = searchTerm.length();
        savedQuery = savedInstanceState.getString("SAVEDQUERY");
        users = savedInstanceState.getParcelableArrayList("USERS");
        teams = savedInstanceState.getParcelableArrayList("TEAMS");

        // stay on same tab as was selected in previous orientation
        TabLayout.Tab tab = profileTabs.getTabAt(savedInstanceState.getInt("tabState"));
        tab.select();

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
        //Unregisgtering the Eventbus.
        EventBus.getDefault().unregister(this);

        super.onPause();
    }

}
