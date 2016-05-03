package com.example.catalyst.ata_test.menus;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.View;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.SearchActivity;

/**
 * Created by dsloane on 4/28/2016.
 */
public class TopBar {

    private final static String TAG = TopBar.class.getSimpleName();

    private Context mContext;
    private SearchView searchView;

    public SearchView getTopBar(Context context, SearchView view) {

        mContext = context;
        searchView = view;

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search for users and teams...");

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_small));

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, "");
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(0, 0);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, query);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(0, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, newText);
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        return searchView;
    }

    public void clearSearch() {
        searchView.post(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("", true);
            }
        });
    }
}
