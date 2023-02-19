package edu.ucsd.cse110.lab4;
import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Pair;
import android.widget.Button;
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
    public void testSaveButton() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

        activity.loadProfile(); //  load user profile

        TextView lat  = (TextView) activity.findViewById(R.id.latitude);
        TextView lon  = (TextView) activity.findViewById(R.id.longitude);

        lat.setText("0.0");
        lon.setText("0.0");

        assertEquals("0.0", lat.getText().toString()); // get initial long/lat
        assertEquals("0.0", lon.getText().toString());

        // perform click
        Button btn;
        btn = (Button) activity.findViewById(R.id.saveButton);
        boolean clicked = btn.performClick();

        assertTrue(clicked); // assert if clicked

        // launch next screen
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);

        });
    }

    @Test
    public void testEditLongLat() {
        // launch new page
        ActivityScenario<ProfileActivity> scenario_1 = ActivityScenario.launch(ProfileActivity.class);
        scenario_1.moveToState(Lifecycle.State.CREATED);
        scenario_1.moveToState(Lifecycle.State.STARTED);
        scenario_1.onActivity(activity_1 -> {
            activity_1.loadProfile();
            TextView lat_1  = (TextView) activity_1.findViewById(R.id.latitude);
            TextView lon_1  = (TextView) activity_1.findViewById(R.id.longitude);

            lat_1.setText("0.05");
            lon_1.setText("-0.01");

            assertEquals("0.05", lat_1.getText().toString()); // initial long/lat
            assertEquals("-0.01", lon_1.getText().toString());
        });
    }
}
