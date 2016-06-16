package com.example.catalyst.ata_test.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;
import com.example.catalyst.ata_test.activities.LoginActivity;
import com.example.catalyst.ata_test.activities.SearchActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by dsloane on 6/15/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SearchBarTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class) {

        @Override
        protected void afterActivityLaunched() {
            // Enable JS!
            onWebView(withId(R.id.loginView)).forceJavascriptEnabled();

            LoginTest.login();
        }

        @Override
        protected void afterActivityFinished() {
            LoginTest.logout();
        }

    };


    @Test
    public void goToSearchPage() {
        onView(withId(R.id.search_src_text))
                .perform(click());

        // check that we're on the search page
        onView(withText("TEAMS")).perform(click());

        // click on the cancel button
        onView(withId(R.id.action_cancel_search)).perform(click());
    }

    @Test
    public void searchForATeam() {
        onView(withId(R.id.search_src_text))
                .perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("ata mobile"));

        // check that we're on the search page
        onView(withText("TEAMS")).perform(click());

        String expectedResult = "ATA Mobile";

        // click on the result
        onData(anything())
                .inAdapterView(withId(R.id.team_result_list)).atPosition(0)
                .onChildView(withId(R.id.team_name))
                .perform(click());

        // check that we're on the correct team page
        onView(withId(R.id.team_name))
                .check(matches(withText(expectedResult)));
    }

    @Test
    public void searchForAUser() {
        onView(withId(R.id.search_src_text))
                .perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("sloane"));

        // check that we're on the search page
        onView(withText("PEOPLE")).perform(click());

        String expectedResult = "Daniel Sloane";

        // click on the result
        onData(anything())
                .inAdapterView(withId(R.id.user_result_list)).atPosition(0)
                .onChildView(withId(R.id.user_name))
                .perform(click());

        // check that we're on the correct profile page
        onView(withId(R.id.user_name))
                .check(matches(withText(expectedResult)));
    }
}
