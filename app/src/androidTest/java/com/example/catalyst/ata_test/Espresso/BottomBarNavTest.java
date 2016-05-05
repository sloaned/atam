package com.example.catalyst.ata_test.Espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.DashboardActivity;

import org.hamcrest.Matcher;
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

    @Test
    public void openSettings() {
        onView(withId(R.id.settings_button))
                .perform(click());

        String logout = "LOG OUT";

        onView(withId(R.id.logout_button))
                .check(matches(withText(logout)));
    }

    @Test
    public void leaveAndReturnToDashboard() {
        onView(withId(R.id.feed_button))
                .perform(click());

        /*String username = "User Name";

        onView(withId(R.id.user_name))
                .check(matches(withText(username)));

        onView(isRoot()).perform(waitId(R.id.bottom_bar, 3000)); */

        onView(withId(R.id.home_button))
                .perform(click());

       // String title = "My Teams";

        onView(withId(R.id.dashboard_title))
                .check(matches(withText("My Teams")));
    }

    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

//                // timeout happens
//                throw new PerformException.Builder()
//                        .withActionDescription(this.getDescription())
//                        .withViewDescription(HumanReadables.describe(view))
//                        .withCause(new TimeoutException())
//                        .build();
            }
        };
    }

}
