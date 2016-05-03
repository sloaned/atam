package com.example.catalyst.ata_test.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by dsloane on 5/3/2016.
 */
@RunWith(AndroidJUnit4.class)
public class BottomBarNavTest {

    @Rule
    public ActivityTestRule<DashboardActivity> mActivityRule = new ActivityTestRule<>(DashboardActivity.class);


    @Test
    public void goToProfile() {
        onView(withId(R.id.my_profile_button))
                .perform(click());

        String username = "User Name";

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
}
