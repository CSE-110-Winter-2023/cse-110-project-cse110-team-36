package edu.ucsd.cse110.lab4.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

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

public class OneZoomActivity extends AppCompatActivity {

    public OrientationService orientationService;
    LocationService locationService;
    String UID;
    ListViewModel viewModel;
    UserViewModel userViewModel;
    Compass compass;
    List<User> userList;

    /*
     * Updates compass according to orientation, location, and entered values on profileActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_zoom);


        getPermissions();

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        orientationService = new OrientationService(this);
        locationService = new LocationService(this);
        ImageView compass1 = findViewById(R.id.compass_base);

        compass = new Compass(locationService, orientationService, this, 1, compass1);
        addUsers();
    }


    private void addUsers() {
//        viewModel.getUsers().observe(this, users -> {
//            userList = viewModel.getUsers().getValue();
//        });
        List<User> userList = viewModel.getAllUsers();

        if (userList == null) {
            return;
        }

        for (User thisUser : userList) {
            String UID = thisUser.uniqueID;
            LiveData<User> currUser = userViewModel.getUser(UID);
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.include);
            LayoutInflater inflater = getLayoutInflater();
            View myLayout = inflater.inflate(R.layout.dot_layout, mainLayout, false);
            ImageView dotID = myLayout.findViewById(R.id.coordDot);
            TextView label = myLayout.findViewById(R.id.labelView);
            Dot dot = new Dot(currUser, locationService, compass, this, dotID, label);
            mainLayout.addView(myLayout);
        }
        return;
    }

    private void getPermissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
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

    public void onZoomOutClicked(View view) {
        Intent intent = new Intent(this, TwoZoomActivity.class);
        startActivity(intent);
        finish();
    }
}