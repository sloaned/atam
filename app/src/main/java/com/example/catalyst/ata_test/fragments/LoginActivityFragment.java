package com.example.catalyst.ata_test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.util.NetworkConstants;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

//    @Bind(R.id.loginView)
//    WebView loginView;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View content = inflater.inflate(R.layout.fragment_login, container, false);

        final WebView loginView = (WebView)content.findViewById(R.id.loginView);

        loginView.getSettings().setJavaScriptEnabled(true);
        loginView.getSettings().setDomStorageEnabled(true);

        loginView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String urlConection) {

                //this if/else statement is need because the servers are on local host.
                //If servers are remote, this if else statement should be removed, and
                //this method should return false;
                if (urlConection.startsWith("http://localhost")) {
                    System.out.println("Started with the LocalHost!!!");
                    urlConection = urlConection.replace("http://localhost", "http://pc30122.catalystsolves.com");
                    loginView.loadUrl(urlConection);
                    return true;
                } else {
                    System.out.println("Did not start with a local host!");
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("onPageFinished loaded");
                String cookies = android.webkit.CookieManager.getInstance().getCookie(url);
                System.out.println("Cookie: " + cookies);

                //TODO: save Jession value to ID.
            }

        });

        loginView.loadUrl(NetworkConstants.ATA_LOGIN);

        return content;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //loginView.getSettings().setJavaScriptEnabled(true);
        //loginView.loadUrl("https://www.google.com/");

    }
}


