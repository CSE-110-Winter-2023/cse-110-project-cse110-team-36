package edu.ucsd.cse110.lab4.activity;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.OrientationService;
import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.Compass;
import edu.ucsd.cse110.lab4.model.Dot;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.viewmodel.ListViewModel;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

/*
 * Main page, displays a compass that points north, as well as a red dot that points towards user inputted values.
 */
public class MainActivity extends AppCompatActivity {

    /*
     * Check if user opens app for the first time
     * Otherwise, run TwoZoomActivity (Default)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissions();

        // User open the app for the first time
        firstTimeOpenApp();

        Intent intent = new Intent(this, TwoZoomActivity.class);
        startActivity(intent);
        finish();
    }


    private void getPermissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    public void firstTimeOpenApp() {
        // Create sharedPreferences to check if user opens app before
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAccessed = sharedPreferences.getBoolean(getString(R.string.is_accessed), false);

        //on first start up, go to profileActivity
        if (!isAccessed) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.is_accessed), Boolean.TRUE);
            editor.apply();
            startActivity(new Intent(this, MyInfoActivity.class));
        }
    }
}