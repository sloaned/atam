package com.example.catalyst.ata_test.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.action.ViewActions.pressBack;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by dsloane on 5/3/2016.
 */
@RunWith(AndroidJUnit4.class)
public class BottomBarNavTest {

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
    public void goToProfile() {
        onView(withId(R.id.my_profile_button))
                .perform(click());
        //String username = "User Name";
        String username = "Daniel Sloane";

        onView(withId(R.id.user_name))
                .check(matches(withText(username)));
    }

    @Test
    public void goToFeed() {
        onView(withId(R.id.feed_button))
                .perform(click());

        String notifications = "Notifications";

        onView(withId(R.id.feed_title))
                .check(matches(withText(notifications)));
    }

    @Test
    public void openSettings() {
        onView(withId(R.id.settings_button))
                .perform(click());

        String logout = "LOG OUT";

        onView(withId(R.id.logout_button))
                .check(matches(withText(logout)));

        onView(withId(R.id.logout_button))
                .perform(pressBack());
    }

    @Test
    public void leaveAndReturnToDashboard() {
        onView(withId(R.id.feed_button))
                .perform(click());

        String notifications = "Notifications";

        onView(withId(R.id.feed_title))
                .check(matches(withText(notifications)));

        onView(withId(R.id.home_button))
                .perform(click());

        onView(withId(R.id.dashboard_title))
                .check(matches(withText("My Teams")));
    }

}
