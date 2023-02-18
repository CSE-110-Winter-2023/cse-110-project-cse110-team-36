

package edu.ucsd.cse110.lab4;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import org.junit.runner.RunWith;

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
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import java.util.List;

import android.hardware.SensorEventListener;

@RunWith(AndroidJUnit4.class)
public class testLocationService {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void testSingleton() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {

            LocationService instance = null;
            assertEquals(null, instance);

            instance = new LocationService(activity);
            assertNotEquals(null, instance);
            assertTrue(instance instanceof LocationService);

            LocationService old_instance = instance;
            instance = instance.singleton(activity);
            assertNotEquals(null, instance);
            assertTrue(instance instanceof LocationService);
            assertNotEquals(old_instance, instance);
        });
    }

    @Test
    public void testLocationServiceConstructor() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);
            assertTrue(instance instanceof LocationService);
            assertTrue(instance.locationValue instanceof MutableLiveData);
            assertEquals(activity, instance.activity);
            assertTrue(instance.locationManager instanceof LocationManager);
        });
    }

    @Test
    public void testGetBearing() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);

            float latitude = 10.0F;
            float longitude = 20.0F;

            Location toLocation = new Location(LocationManager.GPS_PROVIDER);
            instance.realLocation = new Location(LocationManager.GPS_PROVIDER);
            toLocation.setLatitude(latitude);
            toLocation.setLongitude(longitude);
            float expectedNewLocation = instance.realLocation.bearingTo(toLocation);
            float actualNewLocation = instance.getBearing(latitude, longitude);

            assertTrue(expectedNewLocation == actualNewLocation);

            latitude = -10.0F;
            longitude = 0.0F;
            actualNewLocation = instance.getBearing(latitude, longitude);
            assertFalse(expectedNewLocation == actualNewLocation);

            expectedNewLocation = actualNewLocation;
            actualNewLocation = instance.getBearing(latitude, longitude);
            assertTrue(expectedNewLocation == actualNewLocation);
        });
    }

    @Test
    public void testGetLocation() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);
            LiveData<Pair<Double, Double>> location = instance.getLocation();
            assertEquals(instance.locationValue, location);

            instance.locationValue = new MutableLiveData<>();
            assertNotEquals(instance.locationValue, location);

            location = instance.getLocation();
            assertEquals(instance.locationValue, location);
        });
    }

    @Test
    public void testSetMockOrientationSource() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            Pair<Double, Double> pair = new Pair<Double, Double>(-0.1, 0.2);
            MutableLiveData<Pair<Double, Double>> mockDataSource = new MutableLiveData<>(pair);
            LocationService instance = new LocationService(activity);
            instance.setMockOrientationSource(mockDataSource);
            assertEquals(mockDataSource, instance.locationValue);
        });

//        public void setMockOrientationSource(MutableLiveData<Pair<Double, Double>> mockDataSource) {
//            unregisterLocationListener();
//            this.locationValue = mockDataSource;
//        }
    }

}