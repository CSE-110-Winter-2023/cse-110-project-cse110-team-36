package edu.ucsd.cse110.lab4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.Math;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.OrientationService;
import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

/*
 * Main page, displays a compass that points north,
 * as well as a red dot that points towards user inputted values.
 */
public class CompassActivity extends AppCompatActivity {

    public OrientationService orientationService;
    LocationService locationService;
    float latVal;
    float longVal;
    float northRotateVal;
    float dotRotateVal;
    boolean mockedOrientation = false;

    String userLabel;
    String UID;
    LiveData<User> user;
    UserViewModel viewModel;

    /*
     * Updates compass according to orientation, location, and entered values on profileActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        UID = "test36";

        getPermissions();

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = viewModel.getUser(UID);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        user.observe(this, this::onUserChanged);


        orientationService = new OrientationService(this);
        locationService = new LocationService(this);
        ImageView compass = findViewById(R.id.compass_base);
        ImageView redDot = findViewById(R.id.coordDot);
        TextView label = findViewById(R.id.labelView2);
        ConstraintLayout.LayoutParams layoutParams =
                (ConstraintLayout.LayoutParams) redDot.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams1 =
                (ConstraintLayout.LayoutParams) label.getLayoutParams();

        this.orientationUpdate(compass, layoutParams, redDot, label, layoutParams1);

        this.locationUpdate(layoutParams, layoutParams1, redDot, label);


    }

    private void onUserChanged(User user) {
        latVal = Float.parseFloat(user.latitude);
        longVal = Float.parseFloat(user.longitude);
        userLabel = user.label;
        TextView labelView = this.findViewById(R.id.labelView2);
        labelView.setText(userLabel);

    }

    private void locationUpdate(ConstraintLayout.LayoutParams layoutParams,
                                ConstraintLayout.LayoutParams layoutParams1,
                                ImageView redDot, TextView label) {
        locationService.getLocation().observe(this, coordinates -> {
            // get "bearing" - angle between phone's location and destination
            dotRotateVal = locationService.getBearing(latVal, longVal);
            // update red dot's rotation by north's rotation + bearing
            layoutParams.circleAngle = (northRotateVal + dotRotateVal);
            // CALCULATE DISTANCE FROM CENTER (make separate method)
            float distance = locationService.getDistance(latVal, longVal);
            // 100 miles in meters
            if (distance > 160934) {
                distance = 160934;
            }
            float distanceFraction = distance / 160934;
            layoutParams.circleRadius = (int) ((distanceFraction) * 350);
            // end calc distance
            redDot.setLayoutParams(layoutParams);
            layoutParams1.circleAngle = (northRotateVal + dotRotateVal);
            // update label's rotation same as red dot's
            label.setLayoutParams(layoutParams1);
            label.setRotation(northRotateVal + dotRotateVal);
        });
    }

    private void orientationUpdate(ImageView compass, ConstraintLayout.LayoutParams layoutParams,
                                   ImageView redDot, TextView label,
                                   ConstraintLayout.LayoutParams layoutParams1) {
        orientationService.getOrientation().observe(this, angle -> {
            // if orientation hasn't been mocked, use real orientation,
            // otherwise, use mocked orientation
            if (!mockedOrientation) {
                // update north's rotation by orientation
                compass.setRotation(360 - (float) (Math.toDegrees(angle)));
                northRotateVal = 360 - (float) (Math.toDegrees(angle));
            } else {
                compass.setRotation(angle);
                northRotateVal = (angle);
            }
            // update red dot's rotation by north's rotation + location rotation
            layoutParams.circleAngle = (northRotateVal + dotRotateVal);
            redDot.setLayoutParams(layoutParams);
            layoutParams1.circleAngle = (northRotateVal + dotRotateVal);
            // rotate label same as dot
            label.setLayoutParams(layoutParams1);
        });
    }

    private void getPermissions() {
        if ((ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }


    public void loadProfile() {
        // get shared values with profileActivity
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);

        // get entered lat/long
        String latitudeString = preferences.getString("latitudeString", "0");
        String longitudeString = preferences.getString("longitudeString", "0");

        // get entered label
        String labelString = preferences.getString("labelString", "label");
        TextView labelView = this.findViewById(R.id.labelView);

        // get entered orientation
        String mockOrientationString = preferences.getString("mockOrientation", "");

        // check orientation validity
        // if no orientation entered, do not mock orientation
        if (!mockOrientationString.equals("")) {
            Float mockOrientationNum = Float.parseFloat(mockOrientationString);
            MutableLiveData<Float> mockOrientation = new MutableLiveData<Float>();
            // if invalid orientation, do not mock orientation
            if ((mockOrientationNum < 360) && (mockOrientationNum > -1)) {
                mockOrientation.postValue(mockOrientationNum);
                orientationService.setMockOrientationSource(mockOrientation);
                mockedOrientation = true;
            }
        }

        latVal = Float.parseFloat(latitudeString);
        longVal = Float.parseFloat(longitudeString);


    }

    /*
     * halts updates to compass on pause (as to not drain battery)
     */
    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();
    }

    /*
     * Begins profileActivity when "list" is clicked
     */
    public void onLaunchListClicked(View view) {
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLaunchViewClicked(View view) {
        TextView UIDView = this.findViewById(R.id.TextViewUID);
        UID = UIDView.getText().toString();
    }

    public void onZoomInClicked(View view) {
        var animationZoomIn =
                AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        var image = findViewById(R.id.compass_base);
        image.startAnimation(animationZoomIn);

        image = findViewById(R.id.compass_base2);
        image.startAnimation(animationZoomIn);
    }


    public void onZoomOutClicked(View view) {
        var animationZoomOut =
                AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        var image = findViewById(R.id.compass_base);
        image.startAnimation(animationZoomOut);

        image = findViewById(R.id.compass_base2);
        image.startAnimation(animationZoomOut);
    }
}
