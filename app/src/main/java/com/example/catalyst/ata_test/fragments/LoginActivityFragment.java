package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.MainActivity;
import com.example.catalyst.ata_test.util.NetworkConstants;
import com.example.catalyst.ata_test.util.SharedPreferencesConstants;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;
    private String urlConnection;

    LinearLayout logoContainer;
    ProgressBar spinner;
    WebView loginView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View content = inflater.inflate(R.layout.fragment_login, container, false);

        //initialize shared preferences to save the session id.
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = prefs.edit();

        //initialize the views
        logoContainer = (LinearLayout) content.findViewById(R.id.atalogo);
        spinner = (ProgressBar) content.findViewById(R.id.progressBar);
        loginView = (WebView) content.findViewById(R.id.loginView);

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

                if(override){
                    view.loadUrl(urlConnection);
                }

                return override;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                //Grabbing the cookie to get the jessionid
                String cookies = android.webkit.CookieManager.getInstance().getCookie(url);

                if (cookies != null) {
                    cookies = editCookieString(cookies);
                }

                //If the user sucessfully logs in, then they get redirected to the app dashboard
                if(loginSuccessful(url, cookies)) {

                    //Setting the view to invisible for a nicer user experince.
                    view.setVisibility(View.GONE);
                    view.stopLoading();

                    //sets the container for the logo to
                    //visible, so the user sees the ATA logo.
                    logoContainer.setVisibility(View.VISIBLE);

                    //Starting the spinner so the user knows they are loading into the app
                    spinner.setVisibility(View.VISIBLE);
                    spinner.bringToFront();

                    Intent homePage = new Intent(getActivity(), MainActivity.class);
                    startActivity(homePage);
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
            urlConnection = urlConnection.replace("http://localhost", NetworkConstants.DEV_NETWORK_ADDRESS);
            return true;
        } else {
            return false;
        }
    }

    public boolean loginSuccessful(String url, String cookies) {

        if (url.contains(NetworkConstants.ATA_BASE) && !(url.contains(NetworkConstants.ATA_LOGIN))) {

            mEditor.putString(SharedPreferencesConstants.JESESSIONID, cookies).apply();
            return true;
        }
        return false;
    }

    public String editCookieString(String cookies) {
        return cookies.replace("JSESSIONID=", "");
    }

}


