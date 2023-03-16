package edu.ucsd.cse110.lab4.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.lab4.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ZoomActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void zoomActivityTest() {
        // First screen: 1 compass circle
        ViewInteraction compass_base = onView(
                allOf(withId(R.id.compass_base),
                        withParent(allOf(withId(R.id.include),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        compass_base.check(matches(isDisplayed()));

        ViewInteraction zoomInBtn = onView(
                allOf(withId(R.id.ZoomInButton), withText("+"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        zoomInBtn.check(matches(isNotClickable()));

        ViewInteraction zoomOutBtn = onView(
                allOf(withId(R.id.ZoomOutButton), withText("-"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        zoomOutBtn.perform(click());

        // Second screen: 2 compass circles
        ViewInteraction compass_base2 = onView(
                allOf(withId(R.id.compass_base2),
                        withParent(allOf(withId(R.id.include),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        compass_base2.check(matches(isDisplayed()));

        compass_base = onView(
                allOf(withId(R.id.compass_base),
                        withParent(allOf(withId(R.id.include),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        compass_base.check(matches(isDisplayed()));

        zoomInBtn = onView(
                allOf(withId(R.id.ZoomInButton), withText("+"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        zoomInBtn.check(matches(isDisplayed()));

        zoomOutBtn = onView(
                allOf(withId(R.id.ZoomOutButton), withText("-"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        zoomOutBtn.check(matches(isDisplayed()));
    }
}
