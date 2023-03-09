package edu.ucsd.cse110.lab4.model;
import android.app.Activity;
import android.app.AppComponentFactory;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.OrientationService;

public class Compass {
    private LocationService locationService;
    private OrientationService orientationService;
    private float northRotateVal;
    private AppCompatActivity activity;

    public Compass(LocationService locationService, OrientationService orientationService, Activity activity) {
        this.locationService = locationService;
        this.orientationService = orientationService;
    }

    public float getAngle() {
        return northRotateVal;
    }

    public void listen() {
        orientationService.getOrientation().observe(activity, angle -> {
                northRotateVal = 360 - (float) (Math.toDegrees(angle));
        });
    }
}
