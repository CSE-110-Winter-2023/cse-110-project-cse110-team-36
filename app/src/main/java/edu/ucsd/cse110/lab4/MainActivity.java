package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    OrientationService orientationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orientationService = new OrientationService(this);
        ImageView compass = findViewById(R.id.compass_base);
        TextView orientation = findViewById(R.id.orientationView);

        orientationService.getOrientation().observe(this, angle -> {
            compass.setRotation(360 - (float) (Math.toDegrees(angle)));
            orientation.setText(Float.toString((float) (Math.toDegrees(angle))));
        });
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