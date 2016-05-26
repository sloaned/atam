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
import com.example.catalyst.ata_test.util.NetworkConstants;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

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

                    Intent homePage = new Intent(getActivity(), DashboardActivity.class);
                    startActivity(homePage);
                }
            }
        });
        loginView.loadUrl(NetworkConstants.ATA_LOGIN);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

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

        if (url.contains(NetworkConstants.OAUTH_SUCCESS) && !(url.contains(NetworkConstants.OAUTH_LOGIN))) {


            try {

                URL obj = new URL(url);
                URLConnection conn = obj.openConnection();
                Map<String, List<String>> map = conn.getHeaderFields();

                System.out.println("Printing Response Header...\n");

                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    System.out.println("Key : " + entry.getKey()
                            + " ,Value : " + entry.getValue());
                }


                System.out.println("\n Done");

            } catch (Exception e) {
                e.printStackTrace();
            }

            //Grabbing the cookie to get the jessionid
            String cookies = cookieManager.getCookie(url);

            if (cookies != null) {
                cookies = editCookieString(cookies);
            }
            Log.d(TAG, "cookie = " + cookies);

            mEditor.putString(SharedPreferencesConstants.JESESSIONID, cookies).apply();
            return true;
        }
        return false;
    }

    public String editCookieString(String cookies) {
        return cookies.replace("JSESSIONID=", "");
    }

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
}