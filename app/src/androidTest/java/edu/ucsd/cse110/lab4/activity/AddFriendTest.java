package edu.ucsd.cse110.lab4.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static kotlin.jvm.internal.Intrinsics.checkNotNull;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.lab4.R;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFriendTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void addFriendBDD() {
        // add friend page
        ViewInteraction addFriendBtn = onView(
                allOf(withId(R.id.listButton), withText("ADD FRIEND"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        addFriendBtn.perform(click());

        // add the 1st person
        ViewInteraction editText = onView(withId(R.id.user_input_uid));
        editText.perform(typeText("123"));
        editText.check(matches(withText("123")));
        onView(allOf(withId(R.id.user_add_btn), withText("Add"))).perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.user_item_uid), withText("123"),
                        withParent(withParent(withId(R.id.recycle_user))),
                        isDisplayed()));
        textView.check(matches(withText("123")));

        // clear text then add the 2nd person
        editText.perform(clearText());
        editText.perform(typeText("1234"));
        editText.check(matches(withText("1234")));
        onView(allOf(withId(R.id.user_add_btn), withText("Add"))).perform(click());

        textView = onView(
                allOf(withId(R.id.user_item_uid), withText("1234"),
                        withParent(withParent(withId(R.id.recycle_user))),
                        isDisplayed()));
        textView.check(matches(withText("1234")));

        // clear text then add the 3rd person
        editText.perform(clearText());
        editText.perform(typeText("05c01f19-ed31-4aa8-afac-0ba0832f310c"));
        editText.check(matches(withText("05c01f19-ed31-4aa8-afac-0ba0832f310c")));
        onView(allOf(withId(R.id.user_add_btn), withText("Add"))).perform(click());

        textView = onView(
                allOf(withId(R.id.user_item_uid),
                        withText("05c01f19-ed31-4aa8-afac-0ba0832f310c"),
                        withParent(withParent(withId(R.id.recycle_user))),
                        isDisplayed()));
        textView.check(matches(withText("05c01f19-ed31-4aa8-afac-0ba0832f310c")));

        // exit
        onView(allOf(withId(R.id.user_exit_btn), withText("Exit"))).perform(click());
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
