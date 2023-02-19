package edu.ucsd.cse110.lab4;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.location.LocationManager.GPS_PROVIDER;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.platform.app.ActivityInvoker;
import androidx.test.rule.GrantPermissionRule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static java.security.AccessController.getContext;

import android.content.SharedPreferences;

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
