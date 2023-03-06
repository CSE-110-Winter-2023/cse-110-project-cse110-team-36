package edu.ucsd.cse110.lab4;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import android.content.SharedPreferences;

import edu.ucsd.cse110.lab4.activity.MainActivity;

@RunWith(AndroidJUnit4.class)
public class testMainActivity {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void testInstanceVariables() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            assertTrue(activity.orientationService instanceof OrientationService);
            assertTrue(activity.locationService instanceof LocationService);

        });
    }
    @Test
    public void testLoadProfileInMain() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            Context context = activity.getApplicationContext();
            SharedPreferences preferences = context.getSharedPreferences("coordinates", MODE_PRIVATE);
            String mockOrientationString = preferences.getString("mockOrientation", "");
            assertFalse(activity.mockedOrientation);
            activity.loadProfile(); //  load user profile

            if (!mockOrientationString.equals("")) {
                assertTrue(activity.mockedOrientation);
            } else {
                assertFalse(activity.mockedOrientation);
            }

        });
    }

    @Test
    public void onPauseWorking() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            activity.onPause();
        });
    }
}
