package edu.ucsd.cse110.lab4.activity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;


import org.junit.Before;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;

@LargeTest
public class MilestoneScenarioTests {

    //ActivityScenario<MainActivity>
    ActivityScenario<MyInfoActivity> scenario;

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Before
    void setup() {
        //first time launching app: Start in MyInfoActivity
        var scenario = ActivityScenario.launch(MyInfoActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
    }

    /*



Copy this UID and send it to the other three devices (through text, email, etc. Not through compass app.)  [US1]
Now, repeat steps a-d on the other three devices, naming them "Mom", "Emma", and "Melissa". Copy each UID and send it to the first device (Julia).  [US1]
Now, back on Julia's device, click the "add friends" button. You should see a text box, an "add" button, and an "x" button.  [US1]
Copy Mom's UID and paste it into the text box. Click save. You should now be on the Compass page.  [US1]
Repeat steps g-h for Emma and Melissa's UIDs.  [US1]
On Mom, Emma, and Melissa's devices, copy Julia's UID, enter it on the add friends page, and click save.  [US1]
Ensure that all devices have the app running and have location services working.
Place Julia's device at coordinates 32.879932, -117.235844. Place Emma's device at 32.743859, -117.229436. Place Mom's device at 33.057255, -116.582069. Place Melissa's device at 32.878242, -117.236160.
     */
    @LargeTest
    void testStoryOne() {
        //On the first device, Start the app for the first time. You should see a text box prompting you to enter your name.  [US1]
        //Enter "Julia" and click "next"  [US1]
//        ViewInteraction editText = onView(
//                allOf(withId(R.id.user_name), withText("Julia"),
//                        withParent(allOf(withId(R.id.exit_button),
//                                withParent(withId(android.R.id.content)))),
//                        isDisplayed()));

        ViewInteraction editText = onView(withId(R.id.user_name))
                .perform(typeText("Julia"));
        editText.check(matches(withText("Julia")));
        //Now, you should see a screen with a UID (a string of letters and numbers) displayed. [US1]

    }
}