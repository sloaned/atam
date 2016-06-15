package com.example.catalyst.ata_test.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;

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
 * Created by dsloane on 6/15/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SearchBarTest {

    @Rule
    public ActivityTestRule<DashboardActivity> mActivityRule = new ActivityTestRule<>(DashboardActivity.class);


    @Test
    public void goToSearchPage() {
        onView(withId(R.id.search_src_text))
                .perform(click());

        String notifications = "Notifications";

        onView(withId(R.id.feed_title))
                .check(matches(withText(notifications)));
    }
}
