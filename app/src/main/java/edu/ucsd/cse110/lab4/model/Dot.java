package edu.ucsd.cse110.lab4.model;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;

import edu.ucsd.cse110.lab4.LocationService;

public class Dot {
    private User user;
    private LocationService locationService;
    private Compass compass;
    private float dotRotateVal;
    private AppCompatActivity activity;

    public Dot(User user, LocationService locationService, Compass compass, AppCompatActivity activity) {
        this.user = user;
        this.locationService = locationService;
        this.compass = compass;
        this.activity = activity;
        compass.listen();
    }

    public float getAngle() {
            //get "bearing" - angle between phone's location and destination
            dotRotateVal = locationService.getBearing(Float.parseFloat(user.latitude), Float.parseFloat(user.longitude));
            return dotRotateVal + compass.getAngle();
    }

}
