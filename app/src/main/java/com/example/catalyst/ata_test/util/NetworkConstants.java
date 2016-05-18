package com.example.catalyst.ata_test.util;

/**
 * Created by Jmiller on 4/28/2016.
 */
public class NetworkConstants {

    //Alternatively, do your PC name
    //http://pc30122.catalystsolves.com <--jacob millers pc name
    //http://pc30120.catalystsolves.com <-- Dan Sloane's computer
    //10.0.2.2:9999 <-- genymotion


    public static final String DEV_NETWORK_ADDRESS = "http://pc30120.catalystsolves.com";

    //When deployed, change this to the web address
    public static final String ATA_BASE =  DEV_NETWORK_ADDRESS + ":8090";
    public static final String ATA_LOGIN =  ATA_BASE + "/login";

    public static final String OAUTH_LOGIN = DEV_NETWORK_ADDRESS + ":9999" + "/uaa/login";
    public static final String OAUTH_SUCCESS = DEV_NETWORK_ADDRESS + ":9999" + "/uaa/";
}
