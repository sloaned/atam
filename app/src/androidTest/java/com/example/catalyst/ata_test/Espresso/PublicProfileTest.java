package com.example.catalyst.ata_test.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.catalyst.ata_test.Login;
import com.example.catalyst.ata_test.R;
import com.example.catalyst.ata_test.activities.LoginActivity;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by dsloane on 6/16/2016.
 */
@RunWith(AndroidJUnit4.class)
public class PublicProfileTest {


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


            onView(withId(R.id.search_src_text))
                    .perform(click());
            onView(isAssignableFrom(EditText.class)).perform(typeText("lotspeich"));

            // check that we're on the search page
            onView(withText("TEAMS")).perform(click());
            onView(withText("PEOPLE")).perform(click());
            onView(withText("TEAMS")).perform(click());
            onView(withText("PEOPLE")).perform(click());

            // click on the result
            onData(anything())
                    .inAdapterView(withId(R.id.user_result_list)).atPosition(0)
                    .onChildView(withId(R.id.user_name))
                    .perform(click());
        }

        @Override
        protected void afterActivityFinished() {
            Login.logout();
        }
    };


    @Test
    public void checkThatEditBioButtonIsNotVisible() {
        onView(isRoot()).perform(Login.waitId(R.id.bottom_bar, 5000));
        onView(withId(R.id.edit_bio_button))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void checkThatGiveKudosButtonIsVisible() {
        onView(isRoot()).perform(Login.waitId(R.id.give_kudos_button, 5000));
        onView(withId(R.id.give_kudos_button))
                .check(matches(isDisplayed()));
    }

    @Test
    public void swipeBetweenTabs() {

        onView(isRoot()).perform(Login.waitId(R.id.kudos_layout, 5000));

        onView(withId(R.id.kudos_layout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.pager))
                .perform(swipeLeft());

        onView(withId(R.id.reviews_layout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.pager))
                .perform(swipeLeft());

        onView(withId(R.id.teams_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void giveKudos() {
        onView(isRoot()).perform(Login.waitId(R.id.bottom_bar, 5000));

        // count how many kudos there are now
        final int[] oldCounts = new int[1];
        onView(withId(R.id.kudos_list)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                oldCounts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));


        onView(withId(R.id.give_kudos_button))
                .perform(click());




        onView(withId(R.id.give_kudo_textarea))
                .perform(typeText("test kudos"));
        onView(withText("Submit"))
                .perform(click());

        onView(isRoot()).perform(Login.waitId(R.id.bottom_bar, 3000));

        // count number of kudos again
        final int[] newCounts = new int[1];
        onView(withId(R.id.kudos_list)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                newCounts[0] = listView.getCount();

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        // check that one kudo has been added
        assert((oldCounts[0] + 1) == newCounts[0]);

    }
}
