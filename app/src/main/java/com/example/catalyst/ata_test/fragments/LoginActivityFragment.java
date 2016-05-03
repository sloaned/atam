package com.example.catalyst.ata_test.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private final static String TAG = LoginActivityFragment.class.getSimpleName();

    private SharedPreferences prefs;
    private SharedPreferences.Editor mEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View content = inflater.inflate(R.layout.fragment_login, container, false);

        //initialize shared preferences to save the session id.
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = prefs.edit();

        //initialize the views
        final LinearLayout logoContainer = (LinearLayout)content.findViewById(R.id.atalogo);
        final ProgressBar spinner = (ProgressBar)content.findViewById(R.id.progressBar);
        final WebView loginView = (WebView)content.findViewById(R.id.loginView);

        //Allows the cookie to be saved
        loginView.getSettings().setDomStorageEnabled(true);

        //Setting non-webview views to invisible
        logoContainer.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);

        loginView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String urlConection) {

                //this if/else statement is need because the servers are on local host.
                //If servers are remote, this if else statement should be removed, and
                //this method should return false;

                if (urlConection.startsWith("http://localhost")) {
                    //Replaces any local host address with the correct network address
                    urlConection = urlConection.replace("http://localhost", NetworkConstants.DEV_NETWORK_ADDRESS);
                    loginView.loadUrl(urlConection);
                    return true;
                } else {

                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d(TAG, "url = " + url);

                //Grabbing the cookie to get the jessionid
                String cookies = android.webkit.CookieManager.getInstance().getCookie(url);

                //This if/else statments stops the user from redirecting to
                //ATA's home page, and instead loads our home page/dashboard.

                if (url.contains(NetworkConstants.ATA_BASE) && !(url.contains(NetworkConstants.ATA_LOGIN))) {

                    if (cookies != null) {
                        cookies = cookies.replace("JSESSIONID=", "");
                    }

                    mEditor.putString(SharedPreferencesConstants.JESESSIONID, cookies).apply();

                    //Setting the view to invisible for a nicer user experince.
                    loginView.setVisibility(View.GONE);
                    loginView.stopLoading();

                    //sets the container for the logo to
                    //visible, so the user sees the ATA logo.
                    logoContainer.setVisibility(View.VISIBLE);

                    //Starting the spinner so the user knows they are loading into the app
                    spinner.setVisibility(View.VISIBLE);
                    spinner.bringToFront();

                    //Redirecting the user the mobile app home activity.
                    Intent homePage = new Intent(getActivity(), MainActivity.class);
                    startActivity(homePage);
                }
            }
        });

        loginView.loadUrl(NetworkConstants.ATA_LOGIN);

        return content;
    }
}