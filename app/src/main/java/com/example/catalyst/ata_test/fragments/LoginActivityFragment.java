package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.events.GetCurrentUserEvent;
import com.example.catalyst.ata_test.network.ApiCaller;
import com.example.catalyst.ata_test.util.NetworkConstants;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private final String TAG = LoginActivityFragment.class.getSimpleName();

    private CookieManager cookieManager;
    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;
    private String urlConnection;

    ApiCaller caller;

    LinearLayout logoContainer;
    ProgressBar spinner;
    WebView loginView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cookieManager = android.webkit.CookieManager.getInstance();

        View content = inflater.inflate(R.layout.fragment_login, container, false);

        //initialize shared preferences to save the session id.
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = prefs.edit();

        caller = new ApiCaller(getActivity());

        //initialize the views
        logoContainer = (LinearLayout) content.findViewById(R.id.atalogo);
        spinner = (ProgressBar) content.findViewById(R.id.progressBar);
        loginView = (WebView) content.findViewById(R.id.loginView);

        System.out.println("Login Fragment started...");

        //Allows the cookie to be saved
        loginView.getSettings().setDomStorageEnabled(true);

        //Setting non-webview views to invisible
        logoContainer.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);


        loginView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                urlConnection = url;

                boolean override = checkUrlForLocalHost(urlConnection);
                //Replaces any local host address with the correct network address

                if (override) {
                    view.loadUrl(urlConnection);
                }

                return override;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


                //If the user sucessfully logs in, then they get redirected to the app dashboard
                if (loginSuccessful(url)) {

                    //Setting the view to invisible for a nicer user experince.
                    view.setVisibility(View.GONE);
                    view.stopLoading();

                    //sets the container for the logo to
                    //visible, so the user sees the ATA logo.
                    logoContainer.setVisibility(View.VISIBLE);

                    //Starting the spinner so the user knows they are loading into the app
                    spinner.setVisibility(View.VISIBLE);
                    spinner.bringToFront();

                    caller.getCurrentUser();
                }
            }
        });
        loginView.loadUrl(NetworkConstants.ATA_LOGIN);




        return content;
    }

    public boolean checkUrlForLocalHost(String url) {
        //this if/else statement is need because the servers are on local host.
        //If servers are remote, this if else statement should be removed, and
        //this method should return false;
        if (url.startsWith("http://localhost")) {
            this.urlConnection = this.urlConnection.replace("http://localhost", NetworkConstants.DEV_NETWORK_ADDRESS);
            return true;
        } else {
            return false;
        }
    }

    public boolean loginSuccessful(String url) {

        Log.d(TAG, "loginSuccessful, url = " + url);

        if (url.contains(NetworkConstants.ATA_BASE) && !(url.contains(NetworkConstants.OAUTH_LOGIN)) && !(url.contains(NetworkConstants.ATA_LOGIN))) {

            //Grabbing the cookie to get the jsessionid
            String cookies = cookieManager.getCookie(url);

            Log.d(TAG, "cookie = " + cookies);

            mEditor.putString(SharedPreferencesConstants.JSESSIONID, cookies).apply();

            return true;
        }
        return false;
    }

    @Subscribe
    public void getCurrentUserSuccess(GetCurrentUserEvent event) {

        Log.d(TAG, "in getCurrentUserSuccess!!!!");

        Intent homePage = new Intent(getActivity(), DashboardActivity.class);
        startActivity(homePage);
    }
    /*
    public String editCookieString(String cookies) {
        return cookies.replace("JSESSIONID=", "");
    } */

    //Getters and setter for testing.
    public void setCookieManager(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public void setmEditor(SharedPreferences.Editor mEditor) {
        this.mEditor = mEditor;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
}