package edu.ucsd.cse110.lab4.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.not;

import static kotlin.jvm.internal.Intrinsics.checkNotNull;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.lab4.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ZoomTest {
    @Rule
    public ActivityScenarioRule<OneZoomActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(OneZoomActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void zoomBDD() {
        // initially
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(doesNotExist());
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        // one out-zoom
        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        // two out-zooms
        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(matches(isDisplayed()));

        // 3 or more out-zooms
        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(matches(isDisplayed()));

        // one in-zoom
        onView(allOf(withId(R.id.ZoomInButton), withText("+"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(matches(isDisplayed()));

        // two in-zooms
        onView(allOf(withId(R.id.ZoomInButton), withText("+"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        // 3 or more in-zooms
        onView(allOf(withId(R.id.ZoomInButton), withText("+"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(doesNotExist());
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        onView(allOf(withId(R.id.ZoomInButton), withText("+"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(doesNotExist());
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        onView(allOf(withId(R.id.ZoomInButton), withText("+"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(doesNotExist());
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        // mixing zoom in and out
        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        onView(allOf(withId(R.id.ZoomInButton), withText("+"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(doesNotExist());
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(doesNotExist());

        onView(allOf(withId(R.id.ZoomOutButton), withText("-"))).perform(click());
        onView(withId(R.id.compass_base)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base2)).check(matches(isDisplayed()));
        onView(withId(R.id.compass_base3)).check(matches(isDisplayed()));
    }
}

