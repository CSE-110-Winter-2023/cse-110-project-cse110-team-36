package edu.ucsd.cse110.lab4.model;
import android.app.Activity;
import android.app.AppComponentFactory;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatCallback;

import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.OrientationService;

public class Compass {
    private LocationService locationService;
    private OrientationService orientationService;
    public int zoomLevel;
    private float northRotateVal;
    private AppCompatActivity activity;
    public ImageView compassView;

    public Compass(LocationService locationService, OrientationService orientationService, AppCompatActivity activity, int zoomLevel, ImageView compassView) {
        this.locationService = locationService;
        this.orientationService = orientationService;
        this.zoomLevel = zoomLevel;
        this.compassView = compassView;
        this.activity = activity;

        this.listen();
    }

    public float getAngle() {
        return northRotateVal;
    }

    private void updateCompass() {
        compassView.setRotation(getAngle());
    }

    private void listen() {
        orientationService.getOrientation().observe(activity, angle -> {
                northRotateVal = 360 - (float) (Math.toDegrees(angle));
                updateCompass();
        });
    }
}
