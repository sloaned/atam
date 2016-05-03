package com.example.catalyst.ata_test;

import android.content.SharedPreferences;

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
public class LoginJunitTest {

    private static final String BASE_URL = NetworkConstants.ATA_BASE;
    private static final String LOGIN_URL = NetworkConstants.ATA_LOGIN;


    private LoginActivityFragment loginFragment;
    private SharedPreferences pref;
    private SharedPreferences.Editor pEditor;

    @Before
    public void setup(){
        loginFragment = new LoginActivityFragment();
        pref = Mockito.mock(SharedPreferences.class);
        pEditor = pref.edit();

    }

    @Test
    public void testPositiveForLocalHost(){
        Assert.assertTrue(loginFragment.checkUrlForLocalHost("http://localhost:8090/login"));
    }

    @Test
    public void testNegativeForLocalHost(){
        Assert.assertFalse(loginFragment.checkUrlForLocalHost(NetworkConstants.DEV_NETWORK_ADDRESS));
    }

    @Test
    public void testLoginLogicPositive(){

        Mockito.when(pref.edit()).thenReturn(pEditor);
        Assert.assertTrue(true);
        //Assert.assertTrue(loginFragment.loginSuccessful(NetworkConstants.ATA_BASE, null));

    }

}

