package com.example.catalyst.ata_test.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jmiller on 4/28/2016.
 */
public class CustomWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlConnection) {

        String contents = "No JESSIONID found...";

        URL url;
        URLConnection conexion;
        try {
            url = new URL(urlConnection);
            conexion = url.openConnection();
            conexion.setConnectTimeout(3000);
            conexion.connect();
            // get the size of the file which is in the header of the request
            contents = conexion.getHeaderField("JSESSIONID");
        }catch (Exception E){

        }

        return true;
    }

}
