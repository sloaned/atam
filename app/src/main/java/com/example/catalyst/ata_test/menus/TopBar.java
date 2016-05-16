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

/* defines the search bar functionality */
public class TopBar {

    private final static String TAG = TopBar.class.getSimpleName();

    private Context mContext;
    private SearchView searchView;
    private ImageView logoView;

    public ImageView setLogo(Context context, ImageView logo) {
        mContext = context;
        logoView = logo;

        /* set logo image to be another home/dashboard button */
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

        SearchView.SearchAutoComplete search_text = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.text_small));

        /* start the search activity as soon as the search bar has focus */
        search_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        return searchView;
    }

    /* clear the search bar on activity load */
    public void clearSearch() {
        searchView.post(new Runnable() {
            @Override
            public void run() {
                searchView.setQuery("", true);
            }
        });
    }
}
