package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    OrientationService orientationService;
    LocationService locationService;
    float latVal;
    float longVal;
    float northRotateVal;
    float dotRotateVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        orientationService = new OrientationService(this);
        locationService = new LocationService(this);
        ImageView compass = findViewById(R.id.compass_base);
        ImageView redDot = findViewById(R.id.coordDot);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) redDot.getLayoutParams();

        this.loadProfile();

        orientationService.getOrientation().observe(this, angle -> {
            compass.setRotation(360 - (float) (Math.toDegrees(angle)));
            northRotateVal = 360 - (float) (Math.toDegrees(angle));
            layoutParams.circleAngle = (northRotateVal + dotRotateVal);
            redDot.setLayoutParams(layoutParams);
        });

        locationService.getLocation().observe(this, coords -> {
            dotRotateVal = locationService.getBearing(latVal, longVal);
            layoutParams.circleAngle = (northRotateVal + dotRotateVal);
            redDot.setLayoutParams(layoutParams);
            //redDot.setRotation(northRotateVal + dotRotateVal);
        });
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);

        String latitudeString = preferences.getString("latitudeString", "0");
        String longitudeString = preferences.getString("longitudeString", "0");

        latVal = Float.parseFloat(latitudeString);
        longVal = Float.parseFloat(longitudeString);

    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();
    }


    public void onLaunchListClicked(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    //set mock orientation
    public void setOrientation(View view){
        TextView orientation = findViewById(R.id.orientation);
        int orientationDegrees = Integer.parseInt(orientation.getText().toString());
        //azimuth value, 0 is north, -180/180 is south
    }
}