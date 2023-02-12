package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    OrientationService orientationService;
    LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orientationService = new OrientationService(this);
        locationService = new LocationService(this);
        ImageView compass = findViewById(R.id.compass_base);
        TextView orientation = findViewById(R.id.orientationView);
        TextView location = findViewById(R.id.locationView);

        this.loadProfile();

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        orientationService.getOrientation().observe(this, angle -> {
            compass.setRotation(360 - (float) (Math.toDegrees(angle)));
            orientation.setText(Float.toString((float) (Math.toDegrees(angle))));
        });

        locationService.getLocation().observe(this, coords -> {
            location.setText(Double.toString(coords.first) + "," + Double.toString(coords.second));
        });
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);

        String latitudeString = preferences.getString("latitudeString", "default");
        String longitudeString = preferences.getString("longitudeString", "default");
        TextView latView = this.findViewById(R.id.latView);
        TextView longView = this.findViewById(R.id.longView);

        latView.setText(latitudeString);
        longView.setText(longitudeString);
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
}