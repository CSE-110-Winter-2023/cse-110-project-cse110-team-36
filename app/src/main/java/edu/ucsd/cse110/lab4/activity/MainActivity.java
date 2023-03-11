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
import android.preference.PreferenceManager;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.OrientationService;
import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDao;
import edu.ucsd.cse110.lab4.model.UserDao_Impl;
import edu.ucsd.cse110.lab4.model.UserDatabase;
import edu.ucsd.cse110.lab4.model.UserRepository;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

/*
 * Main page, displays a compass that points north, as well as a red dot that points towards user inputted values.
 */
public class MainActivity extends AppCompatActivity {

    public OrientationService orientationService;
    LocationService locationService;
    float latVal;
    float longVal;
    float northRotateVal;
    float dotRotateVal;
    boolean mockedOrientation = false;
    String label;

    /*
     * Updates compass according to orientation, location, and entered values on profileActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get permissions
        getPermissions();

//        Intent intent = new Intent(this, UserActivity.class);
//        startActivity(intent);
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        LiveData<User> user = viewModel.getUser("test37");
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
        TextView label = findViewById(R.id.labelView);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) redDot.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) label.getLayoutParams();

        this.orientationUpdate(compass, layoutParams, redDot, label, layoutParams1);

        this.locationUpdate(layoutParams, layoutParams1, redDot, label);

//        Float userLat = Float.parseFloat(user.latitude);
//        Float userLong = Float.parseFloat(user.longitude);
//        String userLabel = user.label;
//
//        latVal = userLat;
//        longVal = userLong;
//        label = userLabel;

//        //get values from profileActivity
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isAccessed = sharedPreferences.getBoolean(getString(R.string.is_accessed), false);

        //on first start up, go to profileActivity
//        if (!isAccessed) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(getString(R.string.is_accessed), Boolean.TRUE);
//            editor.commit();
//            startActivity(new Intent(this, ProfileActivity.class));
//        }




//        //load values from profileActivity
//        this.loadProfile();





    }

    private void onUserChanged(User user) {
        latVal = Float.parseFloat(user.latitude);
        longVal = Float.parseFloat(user.longitude);
        label = user.label;

    }

    private void locationUpdate(ConstraintLayout.LayoutParams layoutParams, ConstraintLayout.LayoutParams layoutParams1, ImageView redDot, TextView label) {
        locationService.getLocation().observe(this, coords -> {
            //get "bearing" - angle between phone's location and destination
            dotRotateVal = locationService.getBearing(latVal, longVal);
            //update red dot's rotation by north's rotation + bearing
            layoutParams.circleAngle = (northRotateVal + dotRotateVal);
            redDot.setLayoutParams(layoutParams);
            layoutParams1.circleAngle = (northRotateVal + dotRotateVal);
            //update label's rotation same as red dot's
            label.setLayoutParams(layoutParams1);
            label.setRotation(northRotateVal + dotRotateVal);
        });
    }

    private void orientationUpdate(ImageView compass, ConstraintLayout.LayoutParams layoutParams, ImageView redDot, TextView label, ConstraintLayout.LayoutParams layoutParams1) {
        orientationService.getOrientation().observe(this, angle -> {
            //if orientation hasn't been mocked, use real orientation, otherwise, use mocked orientation
            if (!mockedOrientation) {
                //update north's rotation by orientation
                compass.setRotation(360 - (float) (Math.toDegrees(angle)));
                northRotateVal = 360 - (float) (Math.toDegrees(angle));
            } else {
                compass.setRotation(angle);
                northRotateVal = (angle);
            }
            //update red dot's rotation by north's rotation + location rotation
            layoutParams.circleAngle = (northRotateVal + dotRotateVal);
            redDot.setLayoutParams(layoutParams);
            layoutParams1.circleAngle = (northRotateVal + dotRotateVal);
            //rotate label same as dot
            label.setLayoutParams(layoutParams1);
        });
    }

    private void getPermissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }


    /*
     * Loads values from profileActivity
     */
    public void loadProfile() {
        //get shared values with profileActivity
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);

        //get entered lat/long
        String latitudeString = preferences.getString("latitudeString", "0");
        String longitudeString = preferences.getString("longitudeString", "0");

        //get entered label
        String labelString = preferences.getString("labelString", "label");
        TextView labelView = this.findViewById(R.id.labelView);

        //get entered orientation
        String mockOrientationString = preferences.getString("mockOrientation", "");

        //check orientation validity
        //if no orientation entered, do not mock orientation
        if (!mockOrientationString.equals("")) {
            Float mockOrientationNum = Float.parseFloat(mockOrientationString);
            MutableLiveData<Float> mockOrientation = new MutableLiveData<Float>();
            //if invalid orientation, do not mock orientation
            if ((mockOrientationNum < 360) && (mockOrientationNum > -1)) {
                mockOrientation.postValue(mockOrientationNum);
                orientationService.setMockOrientationSource(mockOrientation);
                mockedOrientation = true;
            }
        }

        latVal = Float.parseFloat(latitudeString);
        longVal = Float.parseFloat(longitudeString);

        labelView.setText(labelString);
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
    public void onLaunchMyInfoClicked(View view) {
        Intent intent = new Intent(this, MyInfoActivity.class);
        startActivity(intent);
        finish();
    }
}