package com.example.catalyst.ata_test;

import android.content.SharedPreferences;
import android.webkit.CookieManager;

import com.example.catalyst.ata_test.fragments.LoginActivityFragment;
import com.example.catalyst.ata_test.util.NetworkConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Jmiller on 5/2/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPageJUnitTest {

    private static final String BASE_URL = NetworkConstants.ATA_BASE;
    private static final String LOGIN_URL = NetworkConstants.ATA_LOGIN;

    private android.webkit.CookieManager cm;
    private CookieManager instance;

    private LoginActivityFragment loginFragment;
    private SharedPreferences pref;

    @Before
    public void setup() {
        loginFragment = new LoginActivityFragment();
        pref = Mockito.mock(SharedPreferences.class);
        cm = Mockito.mock(android.webkit.CookieManager.class);
    }

    @Test
    public void testPositiveForLocalHost() {
        Assert.assertTrue(loginFragment.checkUrlForLocalHost("http://localhost:8090/login"));
    }

    @Test
    public void testNegativeForLocalHost() {
        Assert.assertFalse(loginFragment.checkUrlForLocalHost(NetworkConstants.DEV_NETWORK_ADDRESS));
    }

    @Test
    public void testLoginLogicPositive() {

        Mockito.doNothing().when(pref.edit().putString("", "")).apply();
        Assert.assertTrue(loginFragment.loginSuccessful(NetworkConstants.ATA_BASE, ""));

    }
}
