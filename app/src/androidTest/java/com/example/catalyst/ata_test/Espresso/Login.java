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
 * Created by dsloane on 6/15/2016.
 */
@RunWith(AndroidJUnit4.class)
public class Login {


    public static final int waitingTestTime = 1000;

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

    public static void login() {
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
        onView(isRoot()).perform(waitId(R.id.bottom_bar, 5000));
    }

    public static void logout() {
        onView(withId(R.id.settings_icon)).perform(click());

        onView(withId(R.id.logout_button)).perform(click());

        onView(withId(R.id.loginView)).check(matches(isDisplayed()));
    }
}
