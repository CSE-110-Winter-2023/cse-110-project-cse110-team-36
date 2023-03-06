package edu.ucsd.cse110.lab4;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import android.content.Context;
import android.hardware.SensorManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import edu.ucsd.cse110.lab4.activity.MainActivity;

@RunWith(AndroidJUnit4.class)

public class testOrientationService {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void testConstructorOrientationService() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService orientationService = new OrientationService(activity);
            assertEquals((SensorManager) activity.getSystemService(Context.SENSOR_SERVICE), orientationService.sensorManager);
            assertTrue(orientationService.azimuth instanceof MutableLiveData);

        });
    }

    @Test
    public void testSingleTon() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService instance = null;
            assertEquals(null, instance);

            instance = new OrientationService(activity);
            assertNotEquals(null, instance);
            assertTrue(instance instanceof OrientationService);

            OrientationService old_instance = instance;
            instance = instance.singleton(activity);
            assertNotEquals(null, instance);
            assertTrue(instance instanceof OrientationService);
            assertNotEquals(old_instance, instance);

        });
    }

    @Test
    public void testOnSensorChanged() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            Context context = activity.getApplicationContext();
            OrientationService or = new OrientationService(activity);
            assertNull(or.magnetometerReading);
            //
        });
    }

    @Test
    public void testGetOrientation() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService instance = new OrientationService(activity);
            assertEquals(instance.azimuth, instance.getOrientation());
            assertTrue(instance.getOrientation() instanceof LiveData);
        });
    }

    @Test
    public void testUnRegisteredSensorListeners() {

    }

    @Test
    public void testMockOrientation() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            OrientationService or = new OrientationService(activity);
            MutableLiveData<Float> val = or.azimuth;
            or.setMockOrientationSource(val);
            assertEquals(val, or.azimuth);

            val = new MutableLiveData<>();
            assertNotEquals(val, or.azimuth);

            or.setMockOrientationSource(val);
            assertEquals(val, or.azimuth);
        });
    }
}
