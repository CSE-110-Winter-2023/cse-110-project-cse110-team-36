package edu.ucsd.cse110.lab4;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import org.junit.Test;
import static org.junit.Assert.*;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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

@RunWith(RobolectricTestRunner.class)
public class sensorUnitTests {

    private static OrientationService instance;

    //private final SensorManager sensorManager;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private MutableLiveData<Float> azimuth;

    @Test
    public void test_orientation_service() {
        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onActivity(activity -> {
            SensorEventListener sensorEvent = new OrientationService(activity);
            MutableLiveData<Float> actualMultableLiveData = new MutableLiveData<>();
            ((OrientationService) sensorEvent).setMockOrientationSource(actualMultableLiveData);
            assertEquals(((OrientationService) sensorEvent).azimuth, actualMultableLiveData);
        });
    }
}
