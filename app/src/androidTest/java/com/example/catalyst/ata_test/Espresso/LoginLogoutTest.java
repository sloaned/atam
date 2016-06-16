package com.example.catalyst.ata_test.Espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;

/**
 * Created by Jmiller on 5/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginLogoutTest {

    public static final int waitingTestTime = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> mLoginPage = new ActivityTestRule<LoginActivity>(LoginActivity.class, false, true) {
        @Override
        protected void afterActivityLaunched() {
            // Enable JS!
            onWebView(withId(R.id.loginView)).forceJavascriptEnabled();

        }
    };

    @Test
    public void checkForWebView() {
        onView(withId(R.id.loginView)).check(matches(isDisplayed()));
    }

    @Test
    public void loginThenLogout() {

       onWebView(withId(R.id.loginView)).withElement(findElement(Locator.NAME, "username"))
                .perform(DriverAtoms.webKeys("atamldap"));

        onWebView(withId(R.id.loginView)).withElement(findElement(Locator.NAME, "password"))
                .perform(DriverAtoms.webKeys("yIJintO2F7DitSY7spnB"));



        onWebView(withId(R.id.loginView)).withElement(findElement(Locator.ID, "submit-btn"))
                .perform(webClick());

        //Commented this line of code out because OAuth quit asking for authorization.
        onWebView(withId(R.id.loginView)).withElement(findElement(Locator.ID, "authorize"))
                .perform(webClick());

        //Esspresso Equivilent of Thread.Sleep()
        onView(isRoot()).perform(Login.waitId(R.id.bottom_bar, 3000));

        //Checks to make sure the bottom bar is there.
        onView(withId(R.id.settings_icon)).perform(click());

        onView(withId(R.id.logout_button)).perform(click());

        onView(withId(R.id.loginView)).check(matches(isDisplayed()));

    }

}
