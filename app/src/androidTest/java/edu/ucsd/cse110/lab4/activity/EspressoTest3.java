package edu.ucsd.cse110.lab4.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
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
public class EspressoTest3 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void espressoTest3() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.user_name), withText("alejandro"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        editText.check(matches(withText("alejandro")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.user_uid), withText("5e79bb87-3f6"),
                        withParent(allOf(withId(R.id.exit_button),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        editText2.check(matches(withText("5e79bb87-3f6")));

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.exit_button),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(withId(androidx.appcompat.R.id.decor_content_parent)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.user_input_uid), withText("05c01f19-ed31-4aa8-afac-0ba0832f310c "),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        editText3.check(matches(withText("05c01f19-ed31-4aa8-afac-0ba0832f310c ")));

        ViewInteraction button = onView(
                allOf(withId(R.id.user_add_btn), withText("ADD"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.user_add_btn), withText("ADD"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.user_item_uid), withText("05c01f19-ed31-4aa8-afac-0ba0832f310c"),
                        withParent(withParent(withId(R.id.recycle_user))),
                        isDisplayed()));
        textView.check(matches(withText("05c01f19-ed31-4aa8-afac-0ba0832f310c")));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.user_exit_btn), withText("EXIT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.user_exit_btn), withText("EXIT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));
    }
}