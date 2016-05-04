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
import android.widget.ListView;
import android.widget.TextView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.adapters.SearchResultAdapter;
import com.example.catalyst.ata_test.data.DBHelper;
import com.example.catalyst.ata_test.models.User;
import com.example.catalyst.ata_test.network.ApiCaller;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dsloane on 4/26/2016.
 */
public class SearchActivity extends AppCompatActivity implements ApiCaller.UpdateSearchListener {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private SearchResultAdapter adapter;
    final ApiCaller caller = new ApiCaller(this, null);

    @Bind(android.R.id.list)ListView listView;
    private View resultView;
    private ArrayList<User> results = new ArrayList<User>();
    private ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                caller.getAllUsers();
            }
        };
        new Thread(runnable).start();

        final SearchView searchView = (SearchView) findViewById(R.id.action_search);


        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search for users and teams...");

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return false;
            }
        });

        TextView cancelSearch = (TextView) findViewById(R.id.action_cancel_search);

        Log.d(TAG, "cancelSearch text = " + cancelSearch.getText());

        cancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "cancel clicked!");
                finish();
            }
        });

        adapter = new SearchResultAdapter(this, results);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);

                Intent intent = new Intent(SearchActivity.this, ProfileActivity.class)
                        .putExtra("User", user);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        final String query = intent.getStringExtra(SearchManager.QUERY);
        if (!query.equals("")) {
            searchView.post(new Runnable() {
                @Override
                public void run() {
                    searchView.setQuery(query, true);
                }
            });

            searchUsers(query);
        }

    }

    public void cancelSearch() {
        Log.d(TAG, "cancel clicked!");
        finish();
    }


    public void searchUsers(String query) {
        results.clear();
        query = query.toLowerCase();
        for (User user : users) {
            String name = user.getFirstName().toLowerCase() + " " + user.getLastName().toLowerCase();
            if ((user.getFirstName() != null && user.getLastName() != null) && name.contains(query)) {
                results.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refreshUsers(ArrayList<User> users) {
        this.users = users;
    }

}
