package edu.ucsd.cse110.lab4;


import static androidx.test.espresso.Espresso.onView;
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

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.rule.UiThreadTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

//    @Test
//    @UiThreadTest
//    public void runOnUiThread(Runnable runnable) {
//        onView(withId(R.id.labelView)).perform(typeText("Parents' House"));
//        onView(withId(R.id.saveButton)).perform(click());
//        onView(withId(R.id.labelView)).check(matches(withText("Parents' House")));
//    }

    @Test
    public void mainActivityTest2() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            // perform click
            Button btn;
            btn = (Button) activity.findViewById(R.id.listButton);
            boolean clicked = btn.performClick();

            assertTrue(clicked); // assert if clicked

            // launch next screen
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);

            TextView labelView = activity.findViewById(R.id.labelView);
            assertEquals("label", labelView.getText().toString());

            //
            // onView(withId(R.id.labelView)).perform(typeText("Parents' House"));


//
//            // save info
//            btn = (Button) activity.findViewById(R.id.saveButton);
//            clicked = btn.performClick();
//            assertTrue(clicked); // assert if clicked
//
//            // back to main
//            intent = new Intent(activity, MainActivity.class);
//            activity.startActivity(intent);
//
//            // enter the label thread
//            btn = (Button) activity.findViewById(R.id.listButton);
//            clicked = btn.performClick();
//            assertTrue(clicked); // assert if clicked
//
//            intent = new Intent(activity, ProfileActivity.class);
//            activity.startActivity(intent);
//
//            labelView = activity.findViewById(R.id.labelView);
//            assertEquals("Parents' House", labelView.getText().toString());


        });
    }
}
