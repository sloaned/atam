package com.example.catalyst.ata_test.menus;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.activities.SearchActivity;

/**
 * Created by dsloane on 4/28/2016.
 */
public class TopBar {

    private final static String TAG = TopBar.class.getSimpleName();

    private Context mContext;
    private SearchView searchView;
    private ImageView logoView;

    public ImageView setLogo(Context context, ImageView logo) {
        mContext = context;
        logoView = logo;

        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DashboardActivity.class);
                mContext.startActivity(intent);
            }
        });

        return logoView;
    }

    public SearchView getTopBar(Context context, SearchView view) {

        mContext = context;
        searchView = view;

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(mContext.getResources().getString(R.string.search_query_hint));
        searchView.setFocusable(true);
        searchView.setFocusableInTouchMode(true);

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_small));
        search_text.setFocusableInTouchMode(true);
        search_text.setFocusable(true);

        search_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "focus of text area changed, focus of text area = " + hasFocus + "!!!!!!!!!!!!!!!");
                if (hasFocus) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.setAction(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, "");
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(0, 0);
                }
            }
        });

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                Log.d(TAG, "focus changed, focus = " + hasFocus + "!!!!!!!!!!!!!!!");
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
                if (!newText.trim().equals("") && newText.length() > 1) {
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
