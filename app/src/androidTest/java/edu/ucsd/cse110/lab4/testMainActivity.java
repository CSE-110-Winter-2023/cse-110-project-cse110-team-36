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

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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

}
