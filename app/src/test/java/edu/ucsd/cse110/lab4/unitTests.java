//
//
//package edu.ucsd.cse110.lab4;
//
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//
//import org.junit.Rule;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.widget.TextView;
//
//import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Pair;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.rule.GrantPermissionRule;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import java.util.List;
//
//import android.hardware.SensorEventListener;
//
//@RunWith(RobolectricTestRunner.class)
//public class unitTests {
//    @Rule
//    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
//            new ActivityScenarioRule<>(MainActivity.class);
//
//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.ACCESS_FINE_LOCATION");
//    
//    @Test
//    public void test_singleton() {
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//
//            LocationService instance = null;
//            assertEquals(null, instance);
//
//            instance = new LocationService(activity);
//            assertNotEquals(null, instance);
//            assertTrue(instance instanceof LocationService);
//
//            LocationService old_instance = instance;
//            instance = instance.singleton(activity);
//            assertNotEquals(null, instance);
//            assertTrue(instance instanceof LocationService);
//            assertNotEquals(old_instance, instance);
//        });
//    }
//}