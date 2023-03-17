package edu.ucsd.cse110.lab4.activity;


import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.onData;
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
public class CreateAccountTest {

    @Rule
    public ActivityScenarioRule<MyInfoActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MyInfoActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void createAccountBDD() {
        ViewInteraction editText = onView(withId(R.id.user_input_name));
        editText.perform(typeText("abc"));
        editText.check(matches(withText("abc")));

        onView(allOf(withId(R.id.clear_btn), withText("clear"))).perform(click());
        editText.perform(typeText("xyzt"));
        editText.check(matches(withText("xyzt")));

        onView(allOf(withId(R.id.add_btn), withText("Create"))).perform(click());

        ViewInteraction userName = onView(withId(R.id.user_name));
        userName.check(matches(withText("xyzt")));

        // onView(allOf(withId(R.id.exit_button), withText("x"))).perform(click());
    }
}
