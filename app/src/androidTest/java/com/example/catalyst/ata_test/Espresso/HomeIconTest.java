package com.example.catalyst.ata_test.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;

/**
 * Created by dsloane on 6/16/2016.
 */
@RunWith(AndroidJUnit4.class)
public class HomeIconTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class) {
        @Override
        protected void afterActivityLaunched() {
            if (!(getActivity() instanceof LoginActivity)) {
                Login.logout();
            }
            // Enable JS!
            onWebView(withId(R.id.loginView)).forceJavascriptEnabled();
            Login.login();
        }

        @Override
        protected void afterActivityFinished() {
            Login.logout();
        }
    };


    @Test
    public void leaveAndReturnToDashboardViaHomeIcon() {
        onView(withId(R.id.feed_button))
                .perform(click());

        String notifications = "Notifications";

        onView(withId(R.id.feed_title))
                .check(matches(withText(notifications)));

        onView(withId(R.id.action_logo))
                .perform(click());

        onView(withId(R.id.dashboard_title))
                .check(matches(withText("My Teams")));
    }
}
