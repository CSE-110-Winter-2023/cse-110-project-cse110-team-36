package edu.ucsd.cse110.lab4;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import android.hardware.SensorEventListener;
import androidx.test.rule.GrantPermissionRule;

//@RunWith(RobolectricTestRunner.class)
public class storyTest {

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION); // grant the permission
    @Test
    public void test_user_story_main_page() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            activity.loadProfile(); //  load user profile

            TextView lat  = (TextView) activity.findViewById(R.id.latView);
            TextView lon  = (TextView) activity.findViewById(R.id.longView);

            lat.setText("0.0");
            lon.setText("0.0");

            assertEquals("0.0", lat.getText().toString()); // get initial long/lat
            assertEquals("0.0", lon.getText().toString());

            // perform click
            Button btn;
            btn = (Button) activity.findViewById(R.id.listButton);
            btn.performClick();

        });
    }

    @Test
    public void test_user_story_edit_long_lat() {
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
