package com.example.catalyst.ata_test.util;

/**
 * Created by Jmiller on 4/28/2016.
 */
public class NetworkConstants {

    //Alternatively, do your PC name
    //http://pc30122.catalystsolves.com <--jacob millers pc name
    //http://pc30120.catalystsolves.com <-- Dan Sloane's computer
    //10.0.2.2:9999 <-- genymotion


    //TODO: Remove this upon deployment of the server
    public static final String DEV_NETWORK_ADDRESS = "http://pc30120.catalystsolves.com";


    //ATAM's urls
    //TODO: When the server is deployed, change ATAM_BASE to its new webaddress.
    public static final String ATAM_BASE =  DEV_NETWORK_ADDRESS + ":8090";
    public static final String ATAM_LOGIN =  ATAM_BASE + "/login";

    //OAUTH urls
    public static final String OAUTH_BASE = "http://172.17.30.21:9999";
    public static final String OAUTH_LOGIN = OAUTH_BASE + "/uaa/login";
    public static final String OAUTH_LOGOUT = OAUTH_BASE + "/oauth/revoke-token";
}
