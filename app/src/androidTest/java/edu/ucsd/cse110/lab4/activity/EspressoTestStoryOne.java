package edu.ucsd.cse110.lab4.activity;


import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import edu.ucsd.cse110.lab4.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestStoryOne {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void espressoTestStoryOne() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.user_input_name), withText("alejandro"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        editText.check(matches(withText("alejandro")));

        /*
        ViewInteraction button = onView(
                allOf(withId(R.id.add_btn), withText("CREATE"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.add_btn), withText("CREATE"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.user_uid), withText("0b45d802-e0b"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        editText2.check(matches(withText("0b45d802-e0b")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.user_name), withText("alejandro"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        editText3.check(matches(withText("alejandro")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.user_name), withText("alejandro"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        editText4.check(matches(withText("alejandro")));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.user_exit_btn), withText("X"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.user_exit_btn), withText("X"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.user_input_uid), withText("123"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        editText5.check(matches(withText("123")));

        ViewInteraction textView = onView(
                allOf(withId(R.id.user_item_uid), withText("123"),
                        withParent(withParent(withId(R.id.recycle_user))),
                        isDisplayed()));
        textView.check(matches(withText("123")));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.user_add_btn), withText("ADD"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        ViewInteraction button6 = onView(
                allOf(withId(R.id.user_exit_btn), withText("EXIT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.user_exit_btn), withText("EXIT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));

         */
    }
}