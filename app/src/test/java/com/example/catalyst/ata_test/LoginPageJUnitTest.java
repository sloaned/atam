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

    private CookieManager cm;;
    private LoginActivityFragment loginFragment;
    private SharedPreferences pref;
    private SharedPreferences.Editor pEditor;

    @Before
    public void setup() {
        loginFragment = new LoginActivityFragment();
        pref = Mockito.mock(SharedPreferences.class);
        pEditor = Mockito.mock(SharedPreferences.Editor.class);
        cm = Mockito.mock(CookieManager.class);

    }

    @Test
    public void testPositiveForLocalHost() {

        loginFragment.setUrlConnection("http://localhost:8090/login");
        Assert.assertTrue(loginFragment.checkUrlForLocalHost("http://localhost:8090/login"));
    }

    @Test
    public void testNegativeForLocalHost() {
        Assert.assertFalse(loginFragment.checkUrlForLocalHost(NetworkConstants.DEV_NETWORK_ADDRESS));
    }

    @Test
    public void testLoginLogicPositive() {
        loginFragment.setCookieManager(cm);
        loginFragment.setmEditor(pEditor);
        loginFragment.setPrefs(pref);

        Mockito.when(cm.getCookie(Mockito.anyString())).thenReturn("JSESSIONID=nothingimportant");
        Mockito.when(pEditor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(pEditor);
        Assert.assertTrue(loginFragment.loginSuccessful(BASE_URL));
    }

    @Test
    public void testLoginLogicNegative(){
        loginFragment.setCookieManager(cm);
        loginFragment.setmEditor(pEditor);
        loginFragment.setPrefs(pref);

        Mockito.when(cm.getCookie(Mockito.anyString())).thenReturn("JSESSIONID=nothingimportant");
        Mockito.when(pEditor.putString(Mockito.anyString(), Mockito.anyString())).thenReturn(pEditor);
        Assert.assertFalse(loginFragment.loginSuccessful(LOGIN_URL));
    }

    @Test
    public void editCookieString(){
        String uneditedCookie = "JSESSIONID=ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String editedCookie = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Assert.assertEquals(loginFragment.editCookieString(uneditedCookie), editedCookie);
    }



}
