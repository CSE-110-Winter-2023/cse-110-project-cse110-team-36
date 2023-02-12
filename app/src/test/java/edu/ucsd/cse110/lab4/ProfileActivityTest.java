package edu.ucsd.cse110.lab4;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import static org.junit.Assert.*;

import android.widget.TextView;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ProfileActivityTest {

    @Test
    public void test_get_empty_latitude() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            TextView latitude_coordinate = (TextView) activity.findViewById(R.id.latitude);
            assertEquals("", latitude_coordinate.getText().toString());
        });
    }

    @Test
    public void test_get_empty_longitude() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            TextView longitude_coordinate = (TextView) activity.findViewById(R.id.longitude);
            assertEquals("", longitude_coordinate.getText().toString());
        });
    }

    @Test
    public void test_get_non_empty_latitude() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            TextView latitude_coordinate = (TextView) activity.findViewById(R.id.latitude);

            assertEquals("", latitude_coordinate.getText().toString());
        });
    }

}
