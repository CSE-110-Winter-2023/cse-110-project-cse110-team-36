package edu.ucsd.cse110.lab4;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

@RunWith(AndroidJUnit4.class)
public class testLocationService {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    private static final String PROVIDER_NAME = "provider_name";
    //private static final String UNKNOWN_PROVIDER_NAME = "unknown_provider_name";

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

            Location toLocation = new Location(GPS_PROVIDER);
            instance.realLocation = new Location(GPS_PROVIDER);
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
            assertEquals(pair, instance.locationValue.getValue());

            pair = new Pair<Double, Double>(-0.1, -0.2);
            assertEquals(mockDataSource, instance.locationValue);
            assertNotEquals(pair, instance.locationValue.getValue());
            mockDataSource = new MutableLiveData<>(pair);
            assertNotEquals(mockDataSource, instance.locationValue);
            instance.setMockOrientationSource(mockDataSource);
            assertEquals(mockDataSource, instance.locationValue);
            assertEquals(pair, instance.locationValue.getValue());
        });
    }

    @Test
    public void testOnLocationChanged() { // test
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
//            Pair<Double, Double> pair = new Pair<Double, Double>(-0.1, 0.2);
//            MutableLiveData<Pair<Double, Double>> mockDataSource = new MutableLiveData<>(pair);
//            LocationService instance = new LocationService(activity);
//            Location location = new Location();


//            public void onLocationChanged(@NonNull Location location) {
//                this.locationValue.postValue(new Pair<Double, Double>(location.getLatitude(), location.getLongitude()));
//                this.realLocation = location;
        });
    }

    @Test
    public void testOnLocationChangedList() { //  test
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);
            //instance.onLocationChanged(instance.realLocation);
        });
    }

    @Test
    public void testOnFlushComplete() { // test
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);
            instance.onFlushComplete(1);
        });
    }

    @Test
    public void testOnStatusChanged() { // test
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);
            instance.onStatusChanged(GPS_PROVIDER, 1, null);
        });
    }

    @Test
    public void testProviderEnabledOrDisabled() { // test
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            LocationService instance = new LocationService(activity);

            LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
            instance.onProviderEnabled(GPS_PROVIDER);
            assertTrue(locationManager.isProviderEnabled(GPS_PROVIDER));
            instance.onProviderDisabled(PROVIDER_NAME);
            assertFalse(locationManager.isProviderEnabled(PROVIDER_NAME));

        });
    }
}

