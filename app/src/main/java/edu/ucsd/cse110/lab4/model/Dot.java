package edu.ucsd.cse110.lab4.model;
import static java.lang.String.valueOf;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.R;

public class Dot {
    private LiveData<User> user;
    private LocationService locationService;
    private Compass compass;
    private float dotRotateVal;
    private AppCompatActivity activity;
    private final float ONE_MILE = 1609.34f;
    private final float TEN_MILE = 16093.4f;
    private final float FIVEHUN_MILE = 804672f;
    private final float OUTER = 425;
    private final float TWOZ_FIRST = 235;
    private final float THREEZ_FIRST = 175;
    private final float THREEZ_SECOND = 300;
    private ImageView dot;
    private float distanceVal;
    ConstraintLayout.LayoutParams layoutParams;
    private TextView label;
    private float latVal;
    private float longVal;
    private String userlabel;
    private boolean labelVis;
    private long inactiveNum;


    public Dot(LiveData<User> user, LocationService locationService, Compass compass, AppCompatActivity activity, ImageView dot, TextView label) {
        this.user = user;
        this.locationService = locationService;
        this.compass = compass;
        this.dot = dot;
        this.activity = activity;
        this.label = label;
        this.listenAngle();
        this.listenDistance(compass.zoomLevel);
        this.labelVis = true;
        layoutParams = (ConstraintLayout.LayoutParams) dot.getLayoutParams();

        user.observe(activity, this::onUserChanged);
    }

    public void onUserChanged(User userInstance) {
        Log.v(this.userlabel, "change Dot.");
        latVal = Float.parseFloat(userInstance.latitude);
        longVal = Float.parseFloat(userInstance.longitude);
        userlabel = userInstance.label;
        inactiveNum = inactiveDuration(userInstance.updatedAt);
        updateDot();
    }

    public void updateDot() {
        layoutParams.circleAngle = getAngle();
        layoutParams.circleRadius = (int) getDistanceVal();
        dot.setLayoutParams(layoutParams);
        label.setLayoutParams(layoutParams);
        label.setRotation(getAngle());
        label.setText(userlabel);

        if (labelVis) {
            label.setVisibility(View.VISIBLE);
        } else {
            label.setVisibility(View.INVISIBLE);
        }

        //if inactive for more than 10 minutes, make dot invisible
        //if(inactiveDuration(inactiveNum) > 10){
        //    dot.setVisibility(View.INVISIBLE);
        //}

    }

    public float getAngle() {
            //get "bearing" - angle between phone's location and destination
            return dotRotateVal + compass.getAngle();
    }

    public float getDistanceVal() {
        return distanceVal;
    }

    private void listenAngle() {
        locationService.getLocation().observe(activity, coords -> {
            dotRotateVal = locationService.getBearing(latVal, longVal);
            updateDot();
        });
    }

    private void listenDistance(int zoomLevel) {
        locationService.getLocation().observe(activity, coords -> {
            float distance = locationService.getDistance(latVal, longVal);
            //100 miles in meters
            switch (zoomLevel) {
                case 1:
                    //if greater than 1 mile away, return outer circle. (and dont show name)
                    //else, return fraction w/in circle.
                    if (distance > ONE_MILE) {
                        distance = ONE_MILE;
                        labelVis = false;
                    }
                    distanceVal = (distance/ONE_MILE) * OUTER;
                    break;
                case 2:
                    //if 0-1 miles away, put w/in first circle
                    //1-10, put within second
                    //10+, put on outer (and dont show name)
                    if (distance < ONE_MILE) {
                        distanceVal = (distance/ONE_MILE) * TWOZ_FIRST;
                    } else if (distance > TEN_MILE) {
                        distanceVal = OUTER;
                        labelVis = false;
                    } else {
                        distanceVal = distance/TEN_MILE * (OUTER-TWOZ_FIRST) + TWOZ_FIRST;
                    }
                    break;
                case 3:
                    //if 0-1 miles away, put w/in first circle
                    //1-10, put within second
                    //10-500, put within third
                    //500+, put on outer (and dont show name)
                    if (distance < ONE_MILE) {
                        distanceVal = (distance/ONE_MILE) * THREEZ_FIRST;
                    } else if ((distance > ONE_MILE) && (distance < TEN_MILE)) {
                        distanceVal = ((distance/TEN_MILE) * (THREEZ_SECOND - THREEZ_FIRST)) + THREEZ_FIRST;
                    } else if ((distance > TEN_MILE) && (distance < FIVEHUN_MILE)) {
                        distanceVal = ((distance/FIVEHUN_MILE) * (OUTER - THREEZ_SECOND)) + THREEZ_SECOND;
                    } else {
                        distanceVal = OUTER;
                        labelVis = false;
                    }
                    break;
                case 4:
                    //if 0-1 miles away, put w/in first circle
                    //1-10, put within second
                    //10-500, put within third
                    //500+, put on outer (and dont show name)
                    if (distance < ONE_MILE) {
                        distanceVal = (distance/ONE_MILE) * THREEZ_FIRST;
                    } else if ((distance > ONE_MILE) && (distance < TEN_MILE)) {
                        distanceVal = ((distance/TEN_MILE) * (THREEZ_SECOND - THREEZ_FIRST)) + THREEZ_FIRST;
                    } else if ((distance > TEN_MILE) && (distance < FIVEHUN_MILE)) {
                        distanceVal = ((distance/FIVEHUN_MILE) * (OUTER - THREEZ_SECOND)) + THREEZ_SECOND;
                    } else {
                        distanceVal = OUTER;
                    }
                    break;
                default:
                    distanceVal = OUTER;
            }
            updateDot();
        });
    }

    //function to track how long user has been inactive
    public long inactiveDuration(Long seconds){
        //make date
        Date dateUpdatedAt = new Date(seconds * 1000);
        //simple date format
        //SimpleDateFormat updatedAtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String updatedAtString = simpleDate.format(date);

        //get current time and date
        //LocalDateTime dateObj = LocalDateTime.now();
        //DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Date dateCurrent = new Date();
        //String currentTimeString = dateObj.format(formatDateObj);

        //find time since this date
        long diff = dateCurrent.getTime() - dateUpdatedAt.getTime();

        //convert distance to minutes
        long inactiveDurationMinutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

        Log.d("inactive minutes", valueOf(inactiveDurationMinutes));

        return inactiveDurationMinutes;
    }

//    public long lastUpdate(long updateAt){
//        //first get date from updatedAt
//        //get epoch time to string
//        //String updatedAtString = String.valueOf(user.updatedAt);
//        //covert seconds to milliseconds
//        long seconds = updateAt;
//        //make date
//        Date dateUpdatedAt = new Date(seconds * 1000);
//        Date dateCurrent = new Date();
//        //String currentTimeString = dateObj.format(formatDateObj);
//
//        //find time since this date
//        long diff = dateCurrent.getTime() - dateUpdatedAt.getTime();
//
//        //convert distance to minutes
//        long inactiveDurationMinutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
//
//        Log.d("inactive minutes", valueOf(inactiveDurationMinutes));
//
//        return inactiveDurationMinutes;
//    }


}
